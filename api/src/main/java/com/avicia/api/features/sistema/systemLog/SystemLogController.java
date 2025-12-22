package com.avicia.api.features.sistema.systemLog;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.enumerate.TipoSystemLog;
import com.avicia.api.features.sistema.systemLog.response.EstatisticasLogsResponse;
import com.avicia.api.features.sistema.systemLog.response.SystemLogResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class SystemLogController {
    
    private final SystemLogService systemLogService;
    
    @GetMapping
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<List<SystemLogResponse>> listarTodos() {
        List<SystemLogResponse> logs = systemLogService.listarTodos();
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<SystemLogResponse> buscarPorId(@PathVariable Integer id) {
        SystemLogResponse log = systemLogService.buscarPorId(id);
        return ResponseEntity.ok(log);
    }
    
    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<List<SystemLogResponse>> buscarPorTipo(@PathVariable TipoSystemLog tipo) {
        List<SystemLogResponse> logs = systemLogService.buscarPorTipo(tipo);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<List<SystemLogResponse>> buscarPorUsuario(@PathVariable Integer idUsuario) {
        List<SystemLogResponse> logs = systemLogService.buscarPorUsuario(idUsuario);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/entidade/{entidade}")
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<List<SystemLogResponse>> buscarPorEntidade(@PathVariable String entidade) {
        List<SystemLogResponse> logs = systemLogService.buscarPorEntidade(entidade);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<List<SystemLogResponse>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<SystemLogResponse> logs = systemLogService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(logs);
    }
    
    @DeleteMapping("/antigos")
    @PreAuthorize("hasAuthority('LOG_DELETE')")
    public ResponseEntity<String> deletarLogsAntigos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataLimite) {
        int quantidadeDeletada = systemLogService.deletarLogsAntigos(dataLimite);
        return ResponseEntity.ok(String.format("%d log(s) deletado(s) com sucesso", quantidadeDeletada));
    }
    
    @GetMapping("/estatisticas")
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<EstatisticasLogsResponse> obterEstatisticas() {
        EstatisticasLogsResponse estatisticas = systemLogService.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
}