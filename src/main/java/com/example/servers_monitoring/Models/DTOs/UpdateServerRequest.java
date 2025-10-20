package com.example.servers_monitoring.Models.DTOs;

import com.example.servers_monitoring.Models.Enums.Protocol;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class UpdateServerRequest {
     @Size(max = 128)
    private String name;

    private Protocol protocol;

    @Size(max = 512)
    private String url;

    @Size(max = 255)
    private String host;

    @Min(1) @Max(65535)
    private Integer port;

    @Size(max = 128)
    private String username;

    @Size(max = 256)
    private String password;
}
