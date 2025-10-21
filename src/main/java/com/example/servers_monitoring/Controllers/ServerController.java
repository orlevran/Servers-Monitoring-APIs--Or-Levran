package com.example.servers_monitoring.Controllers;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.example.servers_monitoring.Models.DTOs.*;
import com.example.servers_monitoring.Models.Entities.ServerEntity;
import com.example.servers_monitoring.Models.Entities.UserEntity;
import com.example.servers_monitoring.Repositories.RequestLogRepository;
import com.example.servers_monitoring.Repositories.ServerRepository;
import com.example.servers_monitoring.Repositories.UserRepository;
import com.example.servers_monitoring.Services.MonitorService;
import com.example.servers_monitoring.Services.ServerQueryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1")
public class ServerController {
    private final MonitorService monitorService;
    private final ServerQueryService queryService;
    private final ServerRepository serverRepo;
    private final RequestLogRepository logRepo;
    private final UserRepository usersRepo;

    public ServerController(MonitorService monitorService, ServerQueryService queryService, ServerRepository serverRepo,
        RequestLogRepository logRepo, UserRepository usersRepo) {
        this.monitorService = monitorService;
        this.queryService = queryService;
        this.serverRepo = serverRepo;
        this.logRepo = logRepo;
        this.usersRepo = usersRepo;
    }

    @PostMapping("/servers")
    public ResponseEntity<ServerResponse> create(@Valid @RequestBody CreateServerRequest req) {
        try{
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
                saved.getConsecutiveSuccesses(), saved.getConsecutiveFailures(), Instant.now(), Instant.now()
            );

            return ResponseEntity.created(URI.create("/api/v1/servers/" + saved.getId())).body(body);
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/servers/{id}/check")
    public ResponseEntity<CheckServerResponse> check(@PathVariable Long id){
        try{
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
            return ResponseEntity.ok().body(response);
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/servers/{id}")
    public ResponseEntity<ServerResponse> getOne(@PathVariable Long id) {
        try{
            return ResponseEntity.ok().body(queryService.buildServerDetail(id));
        }
        catch (IllegalArgumentException notFound) {
            return ResponseEntity.notFound().build();
        } 
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/servers/{id}/requests")
    public ResponseEntity<PagedResponse<RequestLogResponse>> history(@PathVariable Long id,
        @RequestParam(defaultValue="0") @Min(0) int page, @RequestParam(defaultValue="50") @Min(1) int size) {

        try{
            return ResponseEntity.ok().body(queryService.history(id, page, size));
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/servers/{id}/healthy-at")
    public ResponseEntity<HealthyAtResponse> healthyAt(@PathVariable Long id,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timestamp) {

        try{
            return ResponseEntity.ok().body(queryService.healthyAt(id, timestamp));
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/servers")
    public ResponseEntity<List<ServerResponse>> list() {
        try{
            var servers = serverRepo.findAll();
            var result = servers.stream().map(s -> {
                var response = new ServerResponse(
                    s.getId(), s.getName(), s.getProtocol(), s.getUrl(),
                    s.getHost(), s.getPort(), s.getCurrentStatus(),
                    s.getLastTransitionAt(), s.getConsecutiveSuccesses(),
                    s.getConsecutiveFailures(), s.getCreatedAt(), s.getUpdatedAt());

                var last10 = logRepo.findTop10ByServerOrderByTimestampDesc(s).stream()
                .map(e -> new RequestLogResponse(
                    e.getTimestamp(),
                    Boolean.TRUE.equals(e.getSuccess()),
                    e.getProtocolStatusCode(),
                    e.getLatencyMs(),
                    e.getErrorMessage()
                ))
                .toList();

                response.setLast10Requests(last10);

                return response;
            }).toList();

            return ResponseEntity.ok(result);
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/servers/{id}")
    public ResponseEntity<ServerResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateServerRequest req){
        try{
            var server = queryService.getServerOrThrow(id);

            if (req.getProtocol() != null){
                switch (req.getProtocol()){
                    case HTTP, HTTPS -> {
                        if(req.getUrl() == null || req.getUrl().isBlank()){
                            return ResponseEntity.badRequest().build();
                        }
                    }
                    case FTP, SSH -> {
                        if(req.getHost() == null || req.getHost().isBlank() || req.getPort() == null){
                            return ResponseEntity.badRequest().build();
                        }
                    }
                }
                server.setProtocol(req.getProtocol());
            }
            
            if (req.getName() != null && !req.getName().isBlank()) server.setName(req.getName());
            if (req.getUrl() != null && !req.getUrl().isBlank()) server.setUrl(req.getUrl());
            if (req.getHost() != null && !req.getHost().isBlank()) server.setHost(req.getHost());
            if (req.getPort() != null) server.setPort(req.getPort());
            if (req.getUsername() != null && !req.getUsername().isBlank()) server.setUsername(req.getUsername());
            if (req.getPassword() != null && !req.getPassword().isBlank()) server.setPassword(req.getPassword());

            var saved = serverRepo.save(server);

            var body = new ServerResponse(saved.getId(), saved.getName(), saved.getProtocol(), saved.getUrl(), saved.getHost(), saved.getPort(),
                saved.getCurrentStatus(), saved.getLastTransitionAt(), saved.getConsecutiveSuccesses(), saved.getConsecutiveFailures(),
                saved.getCreatedAt(), Instant.now());

            return ResponseEntity.ok(body);
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!serverRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        try{
            logRepo.deleteByServerId(id);   // <-- purge children first
            serverRepo.deleteById(id); 
            return ResponseEntity.noContent().build();
        }
        catch(Exception ex){
            return ResponseEntity.status(404).body(
                Map.of(
                    "error", "Delete failed: server has related request logs",
                    "serverId", id,
                    "message", ex.getMessage()
                )
            );
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> CreateUser(@RequestBody @Validated CreateUserRequest req) {
        try{
            var existing = usersRepo.findByEmail(req.getEmail());
            if (existing.isPresent()) {
                return ResponseEntity.ok(existing.get());
            }

            var u = new UserEntity();
            u.setEmail(req.getEmail());
            var saved = usersRepo.save(u);

            return ResponseEntity
                .created(URI.create("/api/v1/users/" + saved.getId()))
                .body(saved);
        }
        catch(Exception ex){
            return ResponseEntity.status(409).body(
                Map.of(
                    "error", "User creation failed",
                    "message", ex.getMessage())
            );
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> UsersList() {
        try{
            return ResponseEntity.ok(usersRepo.findAll());
        }
        catch(Exception ex)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
