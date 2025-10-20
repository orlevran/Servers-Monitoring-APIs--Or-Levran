package com.example.servers_monitoring.Models.DTOs;

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
}
