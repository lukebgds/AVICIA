package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.object.ProfissionalSaudeDTO;
import com.avicia.api.service.ProfissionalSaudeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profissionais-saude")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalService;

    @PostMapping // localhost:9081/api/profissionais-saude
    public ResponseEntity<ProfissionalSaudeDTO> criar(@RequestBody ProfissionalSaudeDTO dto) {
        return ResponseEntity.ok(profissionalService.criar(dto));
    }

    @GetMapping // localhost:9081/api/profissionais-saude
    public ResponseEntity<List<ProfissionalSaudeDTO>> listarTodos() {
        return ResponseEntity.ok(profissionalService.listarTodos());
    }

    @GetMapping("/{idProfissional}") // localhost:9081/api/profissionais-saude/{idProfissional}
    public ResponseEntity<ProfissionalSaudeDTO> buscarPorId(@PathVariable Integer idProfissional) {

        return profissionalService.buscarPorId(idProfissional)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<ProfissionalSaudeDTO> buscarPorMatricula(@PathVariable String matricula) {

        return profissionalService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/registro/{registroConselho}")
    public ResponseEntity<ProfissionalSaudeDTO> buscarPorRegistroConselho(@PathVariable String registroConselho) {

        return profissionalService.buscarPorRegistroConselho(registroConselho)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<ProfissionalSaudeDTO> atualizar(@PathVariable String matricula, @RequestBody ProfissionalSaudeDTO dto) {

        return profissionalService.atualizar(matricula, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {
        
        boolean deletado = profissionalService.deletar(matricula);
        
        return deletado ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }

}
