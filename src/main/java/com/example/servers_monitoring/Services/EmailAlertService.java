package com.example.servers_monitoring.Services;

import org.springframework.stereotype.Service;

import com.example.servers_monitoring.Models.Entities.ServerEntity;
import com.example.servers_monitoring.Repositories.UserRepository;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@ConditionalOnProperty(name = "app.alerts.email.enabled", havingValue = "true", matchIfMissing = false)
public class EmailAlertService implements AlertService{
    private final JavaMailSender mailSender;
    private final UserRepository repository;

    public EmailAlertService(JavaMailSender mailSender, UserRepository repository) {
        this.mailSender = mailSender;
        this.repository = repository;
    }

    public void sendUnhealthyAlert(ServerEntity s, String lastError)
    {
        List<String> emails = repository.findAllDistinctEmails();
        if (emails == null || emails.isEmpty()) return;

        SimpleMailMessage msg = new SimpleMailMessage();
        for (String e : emails)
        {
            msg.setTo(e);
            msg.setSubject("[ALERT] Server Unhealthy: " + s.getName());
            msg.setText("Server '" + s.getName() + "' became UNHEALTHY.\nLast error: " + lastError);
            mailSender.send(msg);
        }
        
    }
}
