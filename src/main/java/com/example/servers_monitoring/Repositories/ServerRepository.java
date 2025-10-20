package com.example.servers_monitoring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.example.servers_monitoring.Models.Entities.ServerEntity;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<ServerEntity, Long>{
    //@Query("select se from ServerEntities se where se.name = name")
    Optional<ServerEntity> findByName(String name);

    //@Query("select se from ServerEntities se where se.id = id")
    Optional<ServerEntity> findById(Long id);
}
