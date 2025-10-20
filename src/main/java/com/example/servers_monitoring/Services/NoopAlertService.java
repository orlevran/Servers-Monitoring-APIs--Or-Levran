package com.example.servers_monitoring.Services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.example.servers_monitoring.Models.Entities.ServerEntity;

@Service
@ConditionalOnProperty(name = "app.alerts.email.enabled", havingValue = "false", matchIfMissing = true)
public class NoopAlertService implements AlertService {
    @Override
    public void sendUnhealthyAlert(ServerEntity server, String lastError) {
        // no-op in dev
    }
}
