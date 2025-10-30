package com.avicia.api.features.clinico.consulta.prescricao.item;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.features.clinico.consulta.prescricao.item.request.PrescricaoItemRequest;
import com.avicia.api.features.clinico.consulta.prescricao.item.response.PrescricaoItemResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consultas/prescricoes/itens")
@RequiredArgsConstructor
public class PrescricaoItemController {

    private final PrescricaoItemService prescricaoItemService;

    @GetMapping
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<PrescricaoItemResponse> listarTodos() {
        return prescricaoItemService.listarTodos();
    }

    @GetMapping("/prescricao/{idPrescricao}")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<PrescricaoItemResponse> listarPorPrescricao(@PathVariable Integer idPrescricao) {
        return prescricaoItemService.listarPorPrescricao(idPrescricao);
    }

    @GetMapping("/medicamento")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<PrescricaoItemResponse> buscarPorMedicamento(@RequestParam String medicamento) {
        return prescricaoItemService.buscarPorMedicamento(medicamento);
    }

    @GetMapping("/prescricao/{idPrescricao}/medicamento")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<PrescricaoItemResponse> buscarPorPrescricaoEMedicamento(
            @PathVariable Integer idPrescricao,
            @RequestParam String medicamento) {
        return prescricaoItemService.buscarPorPrescricaoEMedicamento(idPrescricao, medicamento);
    }

    @GetMapping("/frequencia")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<PrescricaoItemResponse> listarPorFrequencia(@RequestParam String frequencia) {
        return prescricaoItemService.listarPorFrequencia(frequencia);
    }

    @GetMapping("/prescricao/{idPrescricao}/frequencia")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<PrescricaoItemResponse> listarPorPrescricaoEFrequencia(
            @PathVariable Integer idPrescricao,
            @RequestParam String frequencia) {
        return prescricaoItemService.listarPorPrescricaoEFrequencia(idPrescricao, frequencia);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public ResponseEntity<PrescricaoItemResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(prescricaoItemService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRESCRICAO_CREATE')")
    public PrescricaoItemResponse criar(@RequestBody PrescricaoItemRequest dto) {
        return prescricaoItemService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESCRICAO_UPDATE')")
    public ResponseEntity<PrescricaoItemResponse> atualizar(@PathVariable Integer id, @RequestBody PrescricaoItemRequest dto) { 
        return ResponseEntity.ok(prescricaoItemService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESCRICAO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        prescricaoItemService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
