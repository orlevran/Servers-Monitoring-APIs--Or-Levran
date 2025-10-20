package com.example.servers_monitoring.Monitoring;

public class ProbeResult {
    public final boolean protocolOk;     // HTTP 2xx or successful login/command
    public final int statusCode;         // HTTP status or 0 for others
    public final int latencyMs;
    public final String error;           // null if none

    public ProbeResult(boolean okay, int statusCode, int latencyMs, String error)
    {
        this.protocolOk = okay;
        this.statusCode = statusCode;
        this.latencyMs = latencyMs;
        this.error = error;
    }

    public boolean successUnder45s()
    {
        return this.protocolOk && latencyMs < 45000;
    }
}
