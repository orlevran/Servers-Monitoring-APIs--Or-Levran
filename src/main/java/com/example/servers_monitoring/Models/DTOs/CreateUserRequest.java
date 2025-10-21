package com.example.servers_monitoring.Models.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
