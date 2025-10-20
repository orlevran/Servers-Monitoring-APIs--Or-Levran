package com.example.servers_monitoring.Repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servers_monitoring.Models.Entities.RequestLogEntity;
import com.example.servers_monitoring.Models.Entities.ServerEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestLogRepository extends JpaRepository<RequestLogEntity, Long>{
    List<RequestLogEntity> findTop10ByServerOrderByTimestampDesc(ServerEntity server);
    
    Page<RequestLogEntity> findByServerOrderByTimestampDesc(ServerEntity server, Pageable pageable);

    List<RequestLogEntity> findTop200ByServerAndTimestampLessThanEqualOrderByTimestampDesc(
            ServerEntity server, Instant timestamp);
}
