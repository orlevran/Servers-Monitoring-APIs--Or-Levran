package com.example.servers_monitoring.Monitoring;

import org.springframework.stereotype.Component;

import com.example.servers_monitoring.Models.Enums.Protocol;

@Component
public class ProtocolCheckFactory {
    public IProtocolCheck get(Protocol p)
    {
        return switch(p)
        {
            case HTTP, HTTPS -> new HttpChecker();
            case FTP -> new FTPChecker();
            case SSH -> new SshChecker();
        };
    }
}
