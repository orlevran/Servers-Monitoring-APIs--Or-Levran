package com.example.servers_monitoring.Monitoring;

import com.example.servers_monitoring.Models.Entities.ServerEntity;
import com.jcraft.jsch.*;

public class SshChecker implements IProtocolCheck {

    @Override
    public ProbeResult probe(ServerEntity server) {
        long start = System.nanoTime();
        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channel = null;

        try
        {
            session = jsch.getSession(
                server.getUsername() == null ? "root" : server.getUsername(),
                server.getHost(),
                server.effectivePort());

            session.setPassword(server.getPassword() == null ? "" : server.getPassword());
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect(45_000);

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("echo ok");
            channel.setInputStream(null);
            channel.connect(45_000);

            while (!channel.isClosed()) Thread.sleep(50);
            int exit = channel.getExitStatus();
            int ms = (int)((System.nanoTime()-start)/1_000_000);
            boolean ok = (exit == 0);
            return new ProbeResult(ok, 0, ms, ok ? null : "SSH command exit " + exit);
        }
        catch(Exception ex)
        {
            int ms = (int)((System.nanoTime()-start)/1_000_000);
            return new ProbeResult(false, 0, ms, ex.getMessage());
        }
        finally
        {
            if (channel != null) try { channel.disconnect(); } catch (Exception ignored) {}
            if (session != null) try { session.disconnect(); } catch (Exception ignored) {}
        }
    }
    
}
