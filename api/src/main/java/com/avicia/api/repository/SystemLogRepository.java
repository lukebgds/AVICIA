package com.avicia.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avicia.api.data.enumerate.TipoSystemLog;
import com.avicia.api.model.SystemLog;

public interface SystemLogRepository extends JpaRepository<SystemLog, Integer> {

    Optional<SystemLog> findByIdLog(Integer idLog);

    List<SystemLog> findByTipoLog(TipoSystemLog tipoLog);
    
    List<SystemLog> findByIdUsuario_IdUsuario(Integer idUsuario);
    
    List<SystemLog> findByEntidadeAfetada(String entidadeAfetada);
    
    List<SystemLog> findByDataHoraBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<SystemLog> findByDataHoraBefore(LocalDateTime dataLimite);

}
