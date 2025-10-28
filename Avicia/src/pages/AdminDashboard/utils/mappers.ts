// src/pages/AdminDashboard/utils/mappers.ts
import type { BackendUser, User } from "../types/types";

export const mapBackendUserToUser = (user: BackendUser): User => ({
  id: user.idUsuario,
  name: `${user.nome}`.trim(),
  email: user.email,
  role:
    user.idRole === 101
      ? "SYSTEM.ADMIN"
      : user.idRole === 501
      ? "Funcionário"
      : user.idRole === 701
      ? "Paciente"
      : user.idRole === 601
      ? "Profissional de Saúde"
      : "Desconhecido",
  status: user.ativo ? "Ativo" : "Inativo",
  phone: user.telefone,
  cpf: user.cpf,
});
