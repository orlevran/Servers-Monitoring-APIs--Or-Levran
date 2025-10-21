package com.example.servers_monitoring.Services;

import java.time.Instant;
import java.util.Objects;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.example.servers_monitoring.Models.Entities.RequestLogEntity;
import com.example.servers_monitoring.Models.Entities.ServerEntity;
import com.example.servers_monitoring.Models.Enums.HealthStatus;
import com.example.servers_monitoring.Monitoring.ProbeResult;
import com.example.servers_monitoring.Monitoring.ProtocolCheckFactory;
import com.example.servers_monitoring.Repositories.RequestLogRepository;
import com.example.servers_monitoring.Repositories.ServerRepository;

import jakarta.transaction.Transactional;

@Service
public class MonitorService {
    private final ServerRepository serverRepo;
    private final RequestLogRepository logRepo;
    private final ProtocolCheckFactory factory;
    private final AlertService alertService;

    public MonitorService(ServerRepository serverRepo, RequestLogRepository logRepo,
                          ProtocolCheckFactory factory, AlertService alertService)
    {
        this.serverRepo = serverRepo;
        this.logRepo = logRepo;
        this.factory = factory;
        this.alertService = alertService;
    }

    @Transactional
    public RequestLogEntity checkNowAndPersist(Long serverId)
    {
        ServerEntity server = serverRepo.findById(serverId)
            .orElseThrow(() -> new IllegalArgumentException("Server not found"));

        // 1) Probe via strategy
        ProbeResult probe = factory.get(server.getProtocol()).probe(server);

        // 2) Build success decision: protocol ok AND latency < 45s
        boolean success = probe.successUnder45s();

        // 3) Persist log
        RequestLogEntity log = new RequestLogEntity(server, Instant.now(), success, probe.statusCode, probe.latencyMs, probe.error);
        logRepo.save(log);

        // 4) Update rolling counters & status transitions
        HealthStatus before = server.getCurrentStatus();
        if(success)
        {
            server.setConsecutiveSuccesses(Objects.requireNonNullElse(server.getConsecutiveSuccesses(), 0) + 1);
            server.setConsecutiveFailures(0);
            if(server.getConsecutiveSuccesses() >= 5 && server.getCurrentStatus() != HealthStatus.HEALTHY)
            {
                server.setCurrentStatus(HealthStatus.HEALTHY);
                server.setLastTransitionAt(Instant.now());
            }
        }
        else
        {
            server.setConsecutiveFailures(Objects.requireNonNullElse(server.getConsecutiveFailures(), 0) + 1);
            server.setConsecutiveSuccesses(0);

            var oldStatus = server.getCurrentStatus();
            
            if (server.getConsecutiveFailures() >= 3 && server.getCurrentStatus() != HealthStatus.UNHEALTHY)
            {
                server.setCurrentStatus(HealthStatus.UNHEALTHY);
                server.setLastTransitionAt(Instant.now());

                if (oldStatus != HealthStatus.UNHEALTHY) {
                    alertService.sendUnhealthyAlert(server, "Check Server definitions");
                }
            }
        }
        serverRepo.save(server);

        // 5) Alert only on transition to UNHEALTHY
        if (before != HealthStatus.UNHEALTHY && server.getCurrentStatus() == HealthStatus.UNHEALTHY)
        {
            alertService.sendUnhealthyAlert(server, probe.error == null ? "Unknown" : probe.error);
        }

        return log;
    }
}
