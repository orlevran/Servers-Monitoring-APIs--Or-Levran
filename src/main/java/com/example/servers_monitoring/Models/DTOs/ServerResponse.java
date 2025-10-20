package com.example.servers_monitoring.Models.DTOs;

import com.example.servers_monitoring.Models.Enums.*;
import java.time.Instant;
import java.util.List;

public class ServerResponse {
    private Long id;
    private String name;
    private Protocol protocol;
    private String url;
    private String host;
    private Integer port;

    private HealthStatus currentStatus;
    private Instant lastTransitionAt;
    private Integer consecutiveSuccesses;
    private Integer consecutiveFailures;

    private Instant createdAt;
    private Instant updatedAt;

    private List<RequestLogResponse> last10Requests;

    public ServerResponse(Long id, String name, Protocol protocol, String url, String host, Integer port,
            HealthStatus currentStatus, Instant lastTransitionAt, Integer consecutiveSuccesses,
            Integer consecutiveFailures) {
        setId(id);
        setName(name);
        setProtocol(protocol);
        setUrl(url);
        setHost(host);
        setPort(port);
        setCurrentStatus(currentStatus);
        setLastTransitionAt(lastTransitionAt);
        setConsecutiveSuccesses(consecutiveSuccesses);
        setConsecutiveFailures(consecutiveFailures);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public HealthStatus getCurrentStatus() {
        return this.currentStatus;
    }

    public void setCurrentStatus(HealthStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Instant getLastTransitionAt() {
        return this.lastTransitionAt;
    }

    public void setLastTransitionAt(Instant lastTransitionAt) {
        this.lastTransitionAt = lastTransitionAt;
    }

    public Integer getConsecutiveSuccesses() {
        return this.consecutiveSuccesses;
    }

    public void setConsecutiveSuccesses(Integer consecutiveSuccesses) {
        this.consecutiveSuccesses = consecutiveSuccesses;
    }

    public Integer getConsecutiveFailures() {
        return this.consecutiveFailures;
    }

    public void setConsecutiveFailures(Integer consecutiveFailures) {
        this.consecutiveFailures = consecutiveFailures;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<RequestLogResponse> getLast10Requests() {
        return this.last10Requests;
    }

    public void setLast10Requests(List<RequestLogResponse> last10Requests) {
        this.last10Requests = last10Requests;
    }
}