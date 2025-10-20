package com.example.servers_monitoring.Monitoring;

import com.example.servers_monitoring.Models.Entities.ServerEntity;

import org.apache.commons.net.ftp.FTPClient;

public class FTPChecker implements IProtocolCheck{

    @Override
    public ProbeResult probe(ServerEntity server) {
        long start = System.nanoTime();
        FTPClient client = new FTPClient();

        try
        {
            String host = server.getHost();
            int port = server.effectivePort();
            client.setConnectTimeout(45_000);
            client.setDefaultTimeout(45_000);
            client.connect(host, port);

            boolean logged = (server.getUsername() != null)
                    ? client.login(server.getUsername(), server.getPassword() == null ? "" : server.getPassword())
                    : client.login("anonymous", "anonymous");

            if (!logged) {
                int ms = (int)((System.nanoTime()-start)/1_000_000);
                return new ProbeResult(false, 0, ms, "FTP login failed");
            }

            client.noop();
            int ms = (int)((System.nanoTime()-start)/1_000_000);
            return new ProbeResult(true, 0, ms, null);
        }
        catch(Exception ex)
        {
            int ms = (int)((System.nanoTime()-start)/1_000_000);
            return new ProbeResult(false, 0, ms, ex.getMessage());
        }
        finally
        {
            try { client.disconnect(); } catch (Exception ignore) {}
        }
    }
    
}
