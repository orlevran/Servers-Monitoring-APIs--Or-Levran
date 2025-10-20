package com.example.servers_monitoring.Monitoring;

import com.example.servers_monitoring.Models.Entities.ServerEntity;

public interface IProtocolCheck {
    ProbeResult probe(ServerEntity server);
}
