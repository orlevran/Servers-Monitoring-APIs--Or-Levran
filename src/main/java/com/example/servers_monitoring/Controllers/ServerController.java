package com.example.servers_monitoring.Controllers;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

import jakarta.validation.Valid;
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

    @GetMapping
    public List<ServerResponse> list() {
        return serverRepo.findAll()
            .stream()
            .map(s -> new ServerResponse(
                    s.getId(), s.getName(), s.getProtocol(), s.getUrl(), s.getHost(),
                    s.getPort(), s.getCurrentStatus(), s.getLastTransitionAt(),
                    s.getConsecutiveSuccesses(), s.getConsecutiveFailures()
            ))
            .toList();
    }

    @PostMapping
    public ResponseEntity<ServerResponse> create(@Valid @RequestBody CreateServerRequest req) {
        // (Optional) quick conditional validation
        switch (req.getProtocol()) {
            case HTTP, HTTPS -> {
                if (req.getUrl() == null || req.getUrl().isBlank()) {
                    return ResponseEntity.badRequest().build();
                }
            }
            case FTP, SSH -> {
                if (req.getHost() == null || req.getHost().isBlank() || req.getPort() == null) {
                    return ResponseEntity.badRequest().build();
                }
            }
        }

        var saved = serverRepo.save(req.toEntity());

        var body = new ServerResponse(
            saved.getId(), saved.getName(), saved.getProtocol(), saved.getUrl(), saved.getHost(),
            saved.getPort(), saved.getCurrentStatus(), saved.getLastTransitionAt(),
            saved.getConsecutiveSuccesses(), saved.getConsecutiveFailures()
        );

        return ResponseEntity
            .created(URI.create("/api/v1/servers/" + saved.getId()))
            .body(body);
    }

    @PostMapping("/_ping")
    public String pingPost() {
        return "pong";
    }
}
