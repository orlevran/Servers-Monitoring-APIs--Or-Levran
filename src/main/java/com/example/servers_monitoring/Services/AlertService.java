package com.example.servers_monitoring.Services;

import com.example.servers_monitoring.Models.Entities.ServerEntity;

public interface AlertService {
    void sendUnhealthyAlert(ServerEntity server, String lastError);
}
