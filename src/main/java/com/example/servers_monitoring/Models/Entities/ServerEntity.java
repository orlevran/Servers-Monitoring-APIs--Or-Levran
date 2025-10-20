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

    void onCreate() {
        var now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    void onUpdate() {
        updatedAt = Instant.now();
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

    public Long getId()
    {
        return this.id;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getHost()
    {
        return this.host;
    }

    public Integer getPort()
    {
        return this.port;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getName()
    {
        return this.name;
    }

    public Protocol getProtocol()
    {
        return this.protocol;
    }

    public HealthStatus getCurrentStatus()
    {
        return this.currentStatus;
    }

    public Integer getConsecutiveSuccesses()
    {
        return this.consecutiveSuccesses;
    }

    public Integer getConsecutiveFailures()
    {
        return this.consecutiveFailures;
    }

    public Instant getLastTransitionAt()
    {
        return this.lastTransitionAt;
    }

    public Instant getCreatedAt()
    {
        return this.createdAt;
    }

    public Instant getUpdatedAt()
    {
        return this.updatedAt;
    }

    public void setConsecutiveSuccesses(Integer consecutiveSuccesses)
    {
        this.consecutiveSuccesses = consecutiveSuccesses;
    }

    public void setConsecutiveFailures(Integer consecutiveFailures)
    {
        this.consecutiveFailures = consecutiveFailures;
    }

    public void setCurrentStatus(HealthStatus status)
    {
        this.currentStatus = status;
    }

    public void setLastTransitionAt(Instant lastTransitionAt)
    {
        this.lastTransitionAt = lastTransitionAt; 
    }
}
