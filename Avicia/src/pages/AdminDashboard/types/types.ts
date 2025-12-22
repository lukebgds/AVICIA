// src/pages/AdminDashboard/types.ts (Local types file for shared interfaces, since no global)
export interface User {
  id: number;
  name: string;
  email: string;
  role: string;
  status: string;
  phone?: string;
  cpf?: string;
  specialty?: string;
  department?: string;
  password?: string;
  address?: string;
  birthDate?: string;
  gender?: string;
  maritalStatus?: string;
  profession?: string;
  matricula?: string;
  cargo?: string;
  conselho?: string;
  registroConselho?: string;
  unidade?: string;
  setor?: string;
  observacoes?: string;
  preferencia_contato?: string;
}

export interface Role {
  idRole: string;
  nome: string;
  descricao: string;
  idTipoRole: number;
  permissoes: Record<string, string>;
}

export interface ActivityLog {
  action: string;
  user: string;
  time: string;
  status: string;
}

export interface SystemStatusItem {
  name: string;
  status: "ok" | "warning" | "error";
  message: string;
}

export interface PermissionActions {
  C: boolean;
  R: boolean;
  U: boolean;
  D: boolean;
}

export interface BackendUser {
  idUsuario: number;
  nome: string;
  cpf: string;
  email: string;
  telefone: string;
  ativo: boolean;
  mfaHabilitado: boolean;
  dataCriacao: string;
  idRole: number;
}
