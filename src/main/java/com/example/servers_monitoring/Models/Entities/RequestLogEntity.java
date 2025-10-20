package com.example.servers_monitoring.Models.Entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RequestLogs")
public class RequestLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "server_id", nullable = false)
    private ServerEntity server;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(name = "success", nullable = false)
    private Boolean success;

    // HTTP: 2xx; FTP/SSH: you can store 0 or library code
    // @Column(name = "protocolStatusCode", nullable = false)
    private Integer protocolStatusCode;

    @Column(name = "latencyMs", nullable = false)
    private Integer latencyMs;

    @Column(name = "errorMessage", length = 1024)
    private String errorMessage;

    public RequestLogEntity() {
    }

    public RequestLogEntity(ServerEntity server, Instant timestamp, Boolean success, Integer protocolStatusCode, Integer latencyMs, String errorMessage) {
        setServer(server);
        setTimestamp(timestamp);
        setSuccess(success);
        setProtocolStatusCode(protocolStatusCode);
        setLatencyMs(latencyMs);
        setErrorMessage(errorMessage);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServer(ServerEntity server) {
        this.server = server;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setProtocolStatusCode(Integer protocolStatusCode) {
        this.protocolStatusCode = protocolStatusCode;
    }

    public void setLatencyMs(Integer latencyMs) {
        this.latencyMs = latencyMs;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public Integer getProtocolStatusCode() {
        return this.protocolStatusCode;
    }

    public Integer getLatencyMs() {
        return this.latencyMs;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ServerEntity getServer(){
        return this.server;
    }
}
