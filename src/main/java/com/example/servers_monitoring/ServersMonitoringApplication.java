package com.example.servers_monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServersMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServersMonitoringApplication.class, args);
	}

}
