package com.avicia.api.features.funcionario;


import com.avicia.api.features.funcionario.request.FuncionarioRequest;
import com.avicia.api.features.funcionario.response.FuncionarioResponse;
import com.avicia.api.features.usuario.UsuarioMapper;


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
