package com.example.servers_monitoring.Models.DTOs;

import java.time.Instant;

public class HealthyAtResponse {
    private Long serverId;
    private Instant evaluatedAt;
    private boolean healthy;

    public HealthyAtResponse() {
    }

    public HealthyAtResponse(Long serverId, Instant evaluatedAt, boolean healthy) {
        setServerId(serverId);
        setEvaluatedAt(evaluatedAt);
        setHealthy(healthy);
    }

    public Long getServerId() {
        return this.serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Instant getEvaluatedAt() {
        return this.evaluatedAt;
    }

    public void setEvaluatedAt(Instant evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }

    public boolean isHealthy() {
        return this.healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
