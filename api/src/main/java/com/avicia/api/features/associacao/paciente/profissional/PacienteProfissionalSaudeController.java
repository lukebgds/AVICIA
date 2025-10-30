package com.avicia.api.features.associacao.paciente.profissional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes-profissionais")
@RequiredArgsConstructor
public class PacienteProfissionalSaudeController {

    private final PacienteProfissionalSaudeService vinculoService;

    @PostMapping("/{idProfissionalSaude}/{idPaciente}")
    @PreAuthorize("hasAuthority('VINCULO_CREATE')")
    public ResponseEntity<Void> criarVinculo(
            @PathVariable Integer idProfissionalSaude,
            @PathVariable Integer idPaciente) {
        vinculoService.criarVinculo(idProfissionalSaude, idPaciente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idProfissionalSaude}/{idPaciente}")
    @PreAuthorize("hasAuthority('VINCULO_DELETE')")
    public ResponseEntity<Void> deletarVinculo(
            @PathVariable Integer idProfissionalSaude,
            @PathVariable Integer idPaciente) {
        vinculoService.deletarVinculo(idProfissionalSaude, idPaciente);
        return ResponseEntity.noContent().build();
    }

}
