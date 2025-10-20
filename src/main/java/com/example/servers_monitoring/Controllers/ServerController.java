package com.example.servers_monitoring.Controllers;

import java.time.Instant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servers_monitoring.Models.DTOs.*;
//import com.example.servers_monitoring.Models.DTOs.RequestLogResponse;
//import com.example.servers_monitoring.Models.DTOs.ServerResponse;
import com.example.servers_monitoring.Models.Entities.ServerEntity;
import com.example.servers_monitoring.Repositories.ServerRepository;
import com.example.servers_monitoring.Services.MonitorService;
import com.example.servers_monitoring.Services.ServerQueryService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/servers")
public class ServerController {
    private final MonitorService monitorService;
    private final ServerQueryService queryService;
    private final ServerRepository serverRepo;

    public ServerController(MonitorService monitorService, ServerQueryService queryService, ServerRepository serverRepo) {
        this.monitorService = monitorService;
        this.queryService = queryService;
        this.serverRepo = serverRepo;
    }

    @GetMapping("/{id}/check")
    public CheckServerResponse check(@PathVariable Long id){
        var log = monitorService.checkNowAndPersist(id);
        ServerEntity server = log.getServer();

        RequestLogResponse last = new RequestLogResponse(
            log.getTimestamp(), log.getSuccess(), log.getProtocolStatusCode(), log.getLatencyMs(), log.getErrorMessage()
        );

        CheckServerResponse response = new CheckServerResponse(
            server.getId(), server.getName(), server.getProtocol(), server.getUrl(), server.getHost(),
            server.getPort(), server.getCurrentStatus(), server.getLastTransitionAt(),
            server.getConsecutiveSuccesses(), server.getConsecutiveFailures(), last
        );

        response.setLastRequest(last);
        return response;
    }

    @GetMapping("/{id}")
    public ServerResponse getOne(@PathVariable Long id) {
        return queryService.buildServerDetail(id);
    }

    @GetMapping("/{id}/requests")
    public PagedResponse<RequestLogResponse> history(@PathVariable Long id,
        @RequestParam(defaultValue="0") @Min(0) int page, @RequestParam(defaultValue="50") @Min(1) int size) {
        return queryService.history(id, page, size);
    }

    @GetMapping("/{id}/healthy-at")
    public HealthyAtResponse healthyAt(@PathVariable Long id,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timestamp) {
        return queryService.healthyAt(id, timestamp);
    }
}
