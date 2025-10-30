package com.avicia.api.features.profissional;

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

import com.avicia.api.features.profissional.request.ProfissionalSaudeRequest;
import com.avicia.api.features.profissional.response.ProfissionalSaudeResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profissionais-saude")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalService;

    @PostMapping // localhost:9081/api/profissionais-saude
    @PreAuthorize("hasAuthority('PROFISSIONALSAUDE_CREATE')")
    public ResponseEntity<ProfissionalSaudeResponse> criar(@RequestBody ProfissionalSaudeRequest dto) {
        return ResponseEntity.ok(profissionalService.criar(dto));
    }

    @GetMapping // localhost:9081/api/profissionais-saude
    @PreAuthorize("hasAuthority('PROFISSIONALSAUDE_READ')")
    public ResponseEntity<List<ProfissionalSaudeResponse>> listarTodos() {
        return ResponseEntity.ok(profissionalService.listarTodos());
    }

    @GetMapping("/{idProfissional}") // localhost:9081/api/profissionais-saude/{idProfissional}
    @PreAuthorize("hasAuthority('PROFISSIONALSAUDE_READ')")
    public ResponseEntity<ProfissionalSaudeResponse> buscarPorIdProfissional(@PathVariable Integer idProfissional) {

        return ResponseEntity.ok(profissionalService.buscarPorIdProfissional(idProfissional));
    }

    @GetMapping("/matricula/{matricula}")
    @PreAuthorize("hasAuthority('PROFISSIONALSAUDE_READ')")
    public ResponseEntity<ProfissionalSaudeResponse> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(profissionalService.buscarPorMatricula(matricula));
    }

    @GetMapping("/registro/{registroConselho}")
    @PreAuthorize("hasAuthority('PROFISSIONALSAUDE_READ')")
    public ResponseEntity<ProfissionalSaudeResponse> buscarPorRegistroConselho(@PathVariable String registroConselho) {
        return ResponseEntity.ok(profissionalService.buscarPorRegistroConselho(registroConselho));
    }

    @PutMapping("/{matricula}")
    @PreAuthorize("hasAuthority('PROFISSIONALSAUDE_UPDATE')")
    public ResponseEntity<ProfissionalSaudeResponse> atualizar(@PathVariable String matricula, @RequestBody ProfissionalSaudeRequest dto) {
        return ResponseEntity.ok(profissionalService.atualizar(matricula, dto));
    }

    @DeleteMapping("/{matricula}")
    @PreAuthorize("hasAuthority('PROFISSIONALSAUDE_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {
        profissionalService.deletar(matricula); 
        return ResponseEntity.noContent().build();                  
    }

}
