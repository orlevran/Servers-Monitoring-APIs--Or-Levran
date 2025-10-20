package com.example.servers_monitoring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.example.servers_monitoring.Models.Entities.ServerEntity;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<ServerEntity, Long>{
    
    Optional<ServerEntity> findByName(String name);

    Optional<ServerEntity> findById(Long id);
}
