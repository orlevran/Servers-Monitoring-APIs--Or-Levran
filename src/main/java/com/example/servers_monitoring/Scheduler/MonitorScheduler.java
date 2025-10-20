package com.example.servers_monitoring.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.servers_monitoring.Repositories.ServerRepository;
import com.example.servers_monitoring.Services.MonitorService;

@Component
public class MonitorScheduler {
    private final ServerRepository serverRepo;
    private final MonitorService monitorService;

    public MonitorScheduler(ServerRepository serverRepo, MonitorService monitorService) {
        this.serverRepo = serverRepo;
        this.monitorService = monitorService;
    }

    @Scheduled(cron = "0 * * * * *")// 0 * * * * * → at second 0 of every minute, every hour/day/month/weekday
    public void run() {
        serverRepo.findAll().forEach(s -> {
            try {
                monitorService.checkNowAndPersist(s.getId());
            }
            catch (Exception ignored) { /* log error; don't stop the loop */ }
        });
    }
}
