package com.avicia.api.features.sistema.systemLog;

import com.avicia.api.features.sistema.systemLog.request.SystemLogRequest;
import com.avicia.api.features.sistema.systemLog.response.SystemLogResponse;

public class SystemLogMapper {

    public static SystemLog toEntity(SystemLogRequest dto) {

		if (dto == null) return null;

        SystemLog systemLog = new SystemLog();

		systemLog.setTipoLog(dto.getTipoLog());
		systemLog.setAcao(dto.getAcao());
		systemLog.setDataHora(dto.getDataHora());
		systemLog.setEntidadeAfetada(dto.getEntidadeAfetada());
		systemLog.setDetalhes(dto.getDetalhes());

        return systemLog;
    }

	public static SystemLogResponse toResponseDTO(SystemLog systemLog) {

		if (systemLog == null) return null;

		SystemLogResponse dto = new SystemLogResponse();

		dto.setIdLog(systemLog.getIdLog());
		dto.setIdUsuario(systemLog.getIdUsuario().getIdUsuario());
		dto.setTipoLog(systemLog.getTipoLog());
		dto.setAcao(systemLog.getAcao());
		dto.setDataHora(systemLog.getDataHora());
		dto.setEntidadeAfetada(systemLog.getEntidadeAfetada());
		dto.setDetalhes(systemLog.getDetalhes());

		return dto;
	}

}
