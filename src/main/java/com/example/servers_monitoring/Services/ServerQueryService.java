package com.example.servers_monitoring.Services;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.servers_monitoring.Models.DTOs.HealthyAtResponse;
import com.example.servers_monitoring.Models.DTOs.PagedResponse;
import com.example.servers_monitoring.Models.DTOs.RequestLogResponse;
import com.example.servers_monitoring.Models.DTOs.ServerResponse;
import com.example.servers_monitoring.Models.Entities.RequestLogEntity;
import com.example.servers_monitoring.Models.Entities.ServerEntity;
import com.example.servers_monitoring.Models.Enums.HealthStatus;
import com.example.servers_monitoring.Repositories.RequestLogRepository;
import com.example.servers_monitoring.Repositories.ServerRepository;

@Service
public class ServerQueryService {
    private final ServerRepository serverRepo;
    private final RequestLogRepository logRepo;

    public ServerQueryService(ServerRepository serverRepo, RequestLogRepository logRepo) {
        this.serverRepo = serverRepo;
        this.logRepo = logRepo;
    }

    public ServerEntity getServerOrThrow(Long id) {
        return serverRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Server not found"));
    }

    public ServerResponse buildServerDetail(Long id)
    {
        var server = getServerOrThrow(id);
        var logs = logRepo.findTop10ByServerOrderByTimestampDesc(server);

        ServerResponse response = new ServerResponse();
        response.setId(server.getId());
        response.setName(server.getName());
        response.setProtocol(server.getProtocol());
        response.setUrl(server.getUrl());
        response.setHost(server.getHost());
        response.setPort(server.getPort());
        response.setCurrentStatus(server.getCurrentStatus());
        response.setLastTransitionAt(server.getLastTransitionAt());
        response.setConsecutiveSuccesses(server.getConsecutiveSuccesses());
        response.setConsecutiveFailures(server.getConsecutiveFailures());
        response.setCreatedAt(server.getCreatedAt());
        response.setUpdatedAt(server.getUpdatedAt());
        response.setLast10Requests(logs.stream().map(this::toLog).toList());
        return response;
    }

    private RequestLogResponse toLog(RequestLogEntity e) {
        RequestLogResponse r = new RequestLogResponse();
        r.setTimestamp(e.getTimestamp());
        r.setSuccess(Boolean.TRUE.equals(e.getSuccess()));
        r.setStatusCode(e.getProtocolStatusCode());
        r.setLatencyMs(e.getLatencyMs());
        r.setError(e.getErrorMessage());
        return r;
    }

    public PagedResponse<RequestLogResponse> history(Long id, int page, int size) {
        var s = getServerOrThrow(id);
        var pg = logRepo.findByServerOrderByTimestampDesc(s, PageRequest.of(page, size));
        PagedResponse<RequestLogResponse> resp = new PagedResponse<>();
        resp.setPage(page); resp.setSize(size);
        resp.setTotalElements(pg.getTotalElements());
        resp.setItems(pg.getContent().stream().map(this::toLog).toList());
        return resp;
    }

        /** Replays counters up to timestamp to answer healthy-at */
    public HealthyAtResponse healthyAt(Long id, Instant at) {
        var s = getServerOrThrow(id);
        int succ = 0, fail = 0;
        HealthStatus status = HealthStatus.UNKNOWN;

        List<RequestLogEntity> logs = logRepo
                .findTop200ByServerAndTimestampLessThanEqualOrderByTimestampDesc(s, at);

        // newest -> oldest; replay forward requires reverse order
        for (int i = logs.size() - 1; i >= 0; i--) {
            var l = logs.get(i);
            if (l.getSuccess()) {
                succ++; fail = 0;
                if (succ >= 5) status = HealthStatus.HEALTHY;
            } else {
                fail++; succ = 0;
                if (fail >= 3) status = HealthStatus.UNHEALTHY;
            }
        }

        HealthyAtResponse response = new HealthyAtResponse(id, at, status == HealthStatus.HEALTHY);
        return response;
    } 
}
