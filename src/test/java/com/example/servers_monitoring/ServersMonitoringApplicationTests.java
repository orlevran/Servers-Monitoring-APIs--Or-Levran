package com.example.servers_monitoring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.servers_monitoring.Monitoring.ProtocolCheckFactory;
import com.example.servers_monitoring.Services.EmailAlertService;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)// use embedded H2 for tests
class ServersMonitoringApplicationTests {

	@MockitoBean private JavaMailSender javaMailSender;         // already needed earlier
  	@MockitoBean private ProtocolCheckFactory protocolCheckFactory;
  	@MockitoBean private EmailAlertService emailAlertService;

    @Test
	void contextLoads() { }
}
