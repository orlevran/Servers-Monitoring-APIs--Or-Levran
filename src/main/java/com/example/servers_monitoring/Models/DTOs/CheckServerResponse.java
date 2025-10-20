package com.example.servers_monitoring.Models.DTOs;

import com.example.servers_monitoring.Models.Enums.Protocol;
import java.time.Instant;
import com.example.servers_monitoring.Models.Enums.HealthStatus;

public class CheckServerResponse  {
    private Long serverId;
    private String name;
    private Protocol protocol;
    private String url;           // for HTTP/S
    private String host;          // for FTP/SSH
    private Integer port;

    private HealthStatus currentStatus;
    private Instant lastTransitionAt;
    private Integer consecutiveSuccesses;
    private Integer consecutiveFailures;

    private RequestLogResponse lastRequest; // the request we just created

    public CheckServerResponse(){}

    public CheckServerResponse(Long serverId, String name, Protocol protocol, String url, String host, Integer port,
        HealthStatus currentStatus, Instant lastTransitionAt, Integer consecutiveSuccesses, Integer consecutiveFailures,
        RequestLogResponse lastRequest){
            setServerId(serverId);
            setName(name);
            setProtocol(protocol);
            setUrl(url);
            setHost(host);
            setPort(port);
            setCurrentStatus(currentStatus);
            setLastTransitionAt(lastTransitionAt);
            setConsecutiveSuccesses(consecutiveSuccesses);
            setConsecutiveFailures(consecutiveFailures);
            setLastRequest(lastRequest);
        }

    public Long getServerId() {
        return this.serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
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

    public RequestLogResponse getLastRequest() {
        return this.lastRequest;
    }

    public void setLastRequest(RequestLogResponse lastRequest) {
        this.lastRequest = lastRequest;
    }
}
