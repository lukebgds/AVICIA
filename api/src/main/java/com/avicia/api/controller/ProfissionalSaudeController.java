package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.profissional.ProfissionalSaudeRequest;
import com.avicia.api.data.dto.response.profissional.ProfissionalSaudeResponse;
import com.avicia.api.service.ProfissionalSaudeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profissionais-saude")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalService;

    @PostMapping // localhost:9081/api/profissionais-saude
    @PreAuthorize("hasAuthority('PROFISSIONAL.SAUDE_CREATE')")
    public ResponseEntity<ProfissionalSaudeResponse> criar(@RequestBody ProfissionalSaudeRequest dto) {
        return ResponseEntity.ok(profissionalService.criar(dto));
    }

    @GetMapping // localhost:9081/api/profissionais-saude
    @PreAuthorize("hasAuthority('PROFISSIONAL.SAUDE_READ')")
    public ResponseEntity<List<ProfissionalSaudeResponse>> listarTodos() {
        return ResponseEntity.ok(profissionalService.listarTodos());
    }

    @GetMapping("/{idProfissional}") // localhost:9081/api/profissionais-saude/{idProfissional}
    @PreAuthorize("hasAuthority('PROFISSIONAL.SAUDE_READ')")
    public ResponseEntity<ProfissionalSaudeResponse> buscarPorIdProfissional(@PathVariable Integer idProfissional) {

        return profissionalService.buscarPorIdProfissional(idProfissional)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matricula}")
    @PreAuthorize("hasAuthority('PROFISSIONAL.SAUDE_READ')")
    public ResponseEntity<ProfissionalSaudeResponse> buscarPorMatricula(@PathVariable String matricula) {

        return profissionalService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/registro/{registroConselho}")
    @PreAuthorize("hasAuthority('PROFISSIONAL.SAUDE_READ')")
    public ResponseEntity<ProfissionalSaudeResponse> buscarPorRegistroConselho(@PathVariable String registroConselho) {

        return profissionalService.buscarPorRegistroConselho(registroConselho)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    @PreAuthorize("hasAuthority('PROFISSIONAL.SAUDE_UPDATE')")
    public ResponseEntity<ProfissionalSaudeResponse> atualizar(@PathVariable String matricula, @RequestBody ProfissionalSaudeRequest dto) {

        return profissionalService.atualizar(matricula, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    @PreAuthorize("hasAuthority('PROFISSIONAL.SAUDE_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {
        
        boolean deletado = profissionalService.deletar(matricula);
        
        return deletado ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }

}
