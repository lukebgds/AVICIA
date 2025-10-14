package com.avicia.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avicia.api.model.SystemLog;

public interface SystemLogRepository extends JpaRepository<SystemLog, Integer> {

    Optional<SystemLog> findByIdLog(Integer idLog);

}
