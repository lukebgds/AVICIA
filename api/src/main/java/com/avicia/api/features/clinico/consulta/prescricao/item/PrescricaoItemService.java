package com.avicia.api.features.clinico.consulta.prescricao.item;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avicia.api.features.clinico.consulta.prescricao.ConsultaPrescricao;
import com.avicia.api.features.clinico.consulta.prescricao.item.request.PrescricaoItemRequest;
import com.avicia.api.features.clinico.consulta.prescricao.item.response.PrescricaoItemResponse;
import com.avicia.api.features.sistema.systemLog.SystemLogService;
import com.avicia.api.util.UsuarioAutenticadoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescricaoItemService {

    private final PrescricaoItemRepository prescricaoItemRepository;
    private final SystemLogService systemLogService;
    private final VerificarPrescricaoItem verificarPrescricaoItem;
    private final UsuarioAutenticadoUtil usuarioAutenticadoUtil;

    @Transactional(readOnly = true)
    public List<PrescricaoItemResponse> listarTodos() {
        return prescricaoItemRepository.findAll()
                .stream()
                .map(PrescricaoItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrescricaoItemResponse> listarPorPrescricao(Integer idPrescricao) {
        verificarPrescricaoItem.validarIdPrescricaoNaoNulo(idPrescricao);
        return prescricaoItemRepository.findByConsultaPrescricao_IdPrescricao(idPrescricao)
                .stream()
                .map(PrescricaoItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrescricaoItemResponse> buscarPorMedicamento(String medicamento) {
        return prescricaoItemRepository.findByMedicamentoContainingIgnoreCase(medicamento)
                .stream()
                .map(PrescricaoItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrescricaoItemResponse> buscarPorPrescricaoEMedicamento(
            Integer idPrescricao, 
            String medicamento) {
        verificarPrescricaoItem.validarIdPrescricaoNaoNulo(idPrescricao);
        return prescricaoItemRepository.findByConsultaPrescricao_IdPrescricaoAndMedicamentoContainingIgnoreCase(
                idPrescricao, medicamento)
                .stream()
                .map(PrescricaoItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrescricaoItemResponse> listarPorFrequencia(String frequencia) {
        return prescricaoItemRepository.findByFrequencia(frequencia)
                .stream()
                .map(PrescricaoItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrescricaoItemResponse> listarPorPrescricaoEFrequencia(
            Integer idPrescricao, 
            String frequencia) {
        verificarPrescricaoItem.validarIdPrescricaoNaoNulo(idPrescricao);
        return prescricaoItemRepository.findByConsultaPrescricao_IdPrescricaoAndFrequencia(
                idPrescricao, frequencia)
                .stream()
                .map(PrescricaoItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PrescricaoItemResponse buscarPorId(Integer id) {
        PrescricaoItem item = verificarPrescricaoItem.buscarItemPorId(id);
        return PrescricaoItemMapper.toResponseDTO(item);
    }

    @Transactional
    public PrescricaoItemResponse criar(PrescricaoItemRequest dto) {
        // Validações
        verificarPrescricaoItem.validarIdPrescricaoNaoNulo(dto.getIdPrescricao());
        verificarPrescricaoItem.validarMedicamentoNaoVazio(dto.getMedicamento());
        verificarPrescricaoItem.validarDosagemNaoVazia(dto.getDosagem());
        
        PrescricaoItem item = PrescricaoItemMapper.toEntity(dto);

        // Recupera a prescrição
        ConsultaPrescricao prescricao = verificarPrescricaoItem.buscarPrescricaoPorId(dto.getIdPrescricao());
        
        item.setConsultaPrescricao(prescricao);

        PrescricaoItem salvo = prescricaoItemRepository.save(item);
        
        // Registro de log (Criação)
        systemLogService.registrarCriacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PrescricaoItem",
            "Item criado com ID " + salvo.getIdItem() + " para prescrição: " + prescricao.getIdPrescricao()
        );

        return PrescricaoItemMapper.toResponseDTO(salvo);
    }

    @Transactional
    public PrescricaoItemResponse atualizar(Integer id, PrescricaoItemRequest dto) {
        verificarPrescricaoItem.validarIdItemNaoNulo(id);
        verificarPrescricaoItem.validarIdPrescricaoNaoNulo(dto.getIdPrescricao());
        verificarPrescricaoItem.validarMedicamentoNaoVazio(dto.getMedicamento());
        verificarPrescricaoItem.validarDosagemNaoVazia(dto.getDosagem());
        
        PrescricaoItem existing = verificarPrescricaoItem.buscarItemPorId(id);
        
        // Verifica se a prescrição existe
        ConsultaPrescricao prescricao = verificarPrescricaoItem.buscarPrescricaoPorId(dto.getIdPrescricao());
        
        PrescricaoItem item = PrescricaoItemMapper.toEntity(dto);
        item.setIdItem(existing.getIdItem());
        item.setConsultaPrescricao(prescricao);
        
        PrescricaoItem atualizado = prescricaoItemRepository.save(item);
        
        // Registro de log (Atualização)
        systemLogService.registrarAtualizacao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PrescricaoItem",
            "Dados do item com ID " + id + " foram atualizados"
        );
        
        return PrescricaoItemMapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        verificarPrescricaoItem.validarIdItemNaoNulo(id);
        
        PrescricaoItem existing = verificarPrescricaoItem.buscarItemPorId(id);
        
        prescricaoItemRepository.delete(existing);
        
        // Registro de log (Exclusão)
        systemLogService.registrarExclusao(
            usuarioAutenticadoUtil.getIdUsuario(),
            "PrescricaoItem",
            "Item com ID " + id + " foi deletado"
        );
    }

}
