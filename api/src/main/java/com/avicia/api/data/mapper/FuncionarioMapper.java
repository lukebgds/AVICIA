package com.avicia.api.data.mapper;


import com.avicia.api.data.dto.request.funcionario.FuncionarioRequest;
import com.avicia.api.data.dto.response.funcionario.FuncionarioResponse;
import com.avicia.api.model.Funcionario;


public class FuncionarioMapper {

    public static Funcionario toEntity(FuncionarioRequest dto) {

        Funcionario funcionario = new Funcionario();
        funcionario.setCargo(dto.getCargo());
        funcionario.setSetor(dto.getSetor());
        funcionario.setMatricula(dto.getMatricula());
        funcionario.setObservacoes(dto.getObservacoes());

        return funcionario;
    }

    public static FuncionarioResponse toResponseDTO(Funcionario funcionario) {

        FuncionarioResponse dto = new FuncionarioResponse();
        
        dto.setIdFuncionario(funcionario.getIdFuncionario());
        dto.setUsuario(UsuarioMapper.toResponseDTO(funcionario.getUsuario()));
        dto.setCargo(funcionario.getCargo());
        dto.setSetor(funcionario.getSetor());
        dto.setMatricula(funcionario.getMatricula());
        dto.setObservacoes(funcionario.getObservacoes());
        
        return dto;
    }

}
