package com.example.servers_monitoring.Models.DTOs;

import com.example.servers_monitoring.Models.Entities.ServerEntity;
import com.example.servers_monitoring.Models.Enums.Protocol;
import jakarta.validation.constraints.*;

public class CreateServerRequest {
    @NotBlank
    @Size(max = 128)
    private String name;

    @NotNull
    private Protocol protocol;

    // For HTTP/HTTPS
    @Size(max = 512)
    private String url;

    // For FTP/SSH (optional for HTTP/S)
    @Size(max = 255)
    private String host;

    @Min(1) @Max(65535)
    private Integer port;

    // Optional (FTP/SSH, or basic auth if you ever use it on HTTP)
    @Size(max = 128)
    private String username;

    @Size(max = 256)
    private String password;

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

    public ServerEntity toEntity() {
        ServerEntity e = new ServerEntity();
        e.setName(name);
        e.setProtocol(protocol);
        e.setUrl(url);
        e.setHost(host);
        e.setPort(port);
        e.setUsername(username);
        e.setPassword(password);
        e.setConsecutiveFailures(0);
        e.setConsecutiveSuccesses(0);
        return e;
    }
}
