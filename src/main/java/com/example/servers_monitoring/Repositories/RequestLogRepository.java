package com.example.servers_monitoring.Repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.servers_monitoring.Models.Entities.RequestLogEntity;
import com.example.servers_monitoring.Models.Entities.ServerEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestLogRepository extends JpaRepository<RequestLogEntity, Long>{
    List<RequestLogEntity> findTop10ByServerOrderByTimestampDesc(ServerEntity server);
    
    Page<RequestLogEntity> findByServerOrderByTimestampDesc(ServerEntity server, Pageable pageable);

    List<RequestLogEntity> findTop200ByServerAndTimestampLessThanEqualOrderByTimestampDesc(
            ServerEntity server, Instant timestamp);

    @Modifying
    @Transactional
    @Query("delete from RequestLogEntity r where r.server.id = :serverId")
    void deleteByServerId(@Param("serverId") Long serverId);
}
