package com.example.servers_monitoring.Models.Entities;

import java.time.Instant;

import com.example.servers_monitoring.Models.Enums.HealthStatus;
import com.example.servers_monitoring.Models.Enums.Protocol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "ServerEntities")
public class ServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "protocol")
    private Protocol protocol;

    @Column(name = "url")
    private String url;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private Integer port;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "currentStatus")
    private HealthStatus currentStatus = HealthStatus.UNKNOWN;

    @Column(name = "consecutiveSuccesses")
    private Integer consecutiveSuccesses = 0;

    @Column(name = "consecutiveFailures")
    private Integer consecutiveFailures = 0;

    @Column(name = "lastTransitionAt")
    private Instant lastTransitionAt;

    @Column(name = "createdAt")
    private Instant createdAt;

    @Column(name = "updatedAt")
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public int effectivePort() {
        if (port != null) return port;
        return switch (protocol) {
            case HTTP -> 80;
            case HTTPS -> 443;
            case FTP -> 21;
            case SSH -> 22;
        };
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HealthStatus getCurrentStatus() {
        return this.currentStatus;
    }

    public void setCurrentStatus(HealthStatus currentStatus) {
        this.currentStatus = currentStatus;
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

    public Instant getLastTransitionAt() {
        return this.lastTransitionAt;
    }

    public void setLastTransitionAt(Instant lastTransitionAt) {
        this.lastTransitionAt = lastTransitionAt;
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
}
