package com.example.servers_monitoring.Models.DTOs;

import java.time.Instant;

public class RequestLogResponse {
    private Instant timestamp;
    private boolean success;
    private Integer statusCode;   // HTTP 2xx or 0 for FTP/SSH
    private int latencyMs;
    private String error;         // nullable

    public RequestLogResponse(){}

    public RequestLogResponse(Instant timestamp, boolean success, Integer statusCode, int latencyMs, String error){
        setTimestamp(timestamp);
        setSuccess(success);
        setStatusCode(statusCode);
        setLatencyMs(latencyMs);
        setError(error);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public int getLatencyMs() {
        return latencyMs;
    }

    public void setLatencyMs(int latencyMs) {
        this.latencyMs = latencyMs;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
