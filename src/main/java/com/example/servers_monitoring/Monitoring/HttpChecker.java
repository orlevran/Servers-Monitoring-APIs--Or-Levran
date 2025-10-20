package com.example.servers_monitoring.Monitoring;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.example.servers_monitoring.Models.Entities.ServerEntity;

public class HttpChecker implements IProtocolCheck
{
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)) // connect timeout
            .build();

    @Override
    public ProbeResult probe(ServerEntity server)
    {
        long start = System.nanoTime();

        try
        {
            if(server.getUrl() == null || server.getUrl().isEmpty())
            {
                return new ProbeResult(false, 0, 0, "Missing URL");
            }

            HttpRequest request = HttpRequest.newBuilder(URI.create(server.getUrl()))
                .timeout(Duration.ofSeconds(45)).GET().build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            int ms = (int)((System.nanoTime()-start)/1_000_000);
            boolean ok = response.statusCode() >= 200 && response.statusCode() < 300;
            return new ProbeResult(ok, response.statusCode(), ms, ok ? null : ("HTTP " + response.statusCode()));
        }
        catch(Exception ex)
        {
            int ms = (int)((System.nanoTime()-start)/1_000_000);
            return new ProbeResult(false, 0, ms, ex.getMessage());
        }
    }
}
