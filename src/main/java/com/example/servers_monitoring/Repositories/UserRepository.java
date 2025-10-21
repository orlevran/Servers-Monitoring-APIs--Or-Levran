package com.example.servers_monitoring.Repositories;

import com.example.servers_monitoring.Models.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByEmail(String email);

    @Query("select distinct u.email from UserEntity u where u.email is not null")
    List<String> findAllDistinctEmails();
}
