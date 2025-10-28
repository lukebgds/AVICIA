// src/pages/AdminDashboard/utils/helpers.ts
import {
  CheckCircle,
  XCircle,
  AlertTriangle,
  Activity,
  Clock,
} from "lucide-react";
import {
  initialPermissions,
  PermissionActions,
  allPermissions,
} from "./constants";

import type { User } from "../types/types";

export const getStatusIcon = (status: string) => {
  switch (status) {
    case "success":
      return <CheckCircle className="h-4 w-4 text-green-500" />;
    case "error":
      return <XCircle className="h-4 w-4 text-red-500" />;
    case "warning":
      return <AlertTriangle className="h-4 w-4 text-yellow-500" />;
    default:
      return <Activity className="h-4 w-4 text-gray-500" />;
  }
};

export const getRoleTypeLabel = (idRole: number | string): string => {
  const firstDigit = String(idRole).charAt(0);
  switch (firstDigit) {
    case "1":
      return "Sistema Admin";
    case "7":
      return "Paciente";
    case "5":
      return "Funcionário";
    case "6":
      return "Profissional de Saúde";
    default:
      return "Desconhecido";
  }
};

export const constructPermissionsJson = (
  permissions: Record<string, PermissionActions> = initialPermissions
): Record<string, string> => {
  const json: Record<string, string> = {};
  allPermissions.forEach((perm) => {
    const actions = permissions[perm.id];
    const selectedActions = [];
    if (actions.C) selectedActions.push("C");
    if (actions.R) selectedActions.push("R");
    if (actions.U) selectedActions.push("U");
    if (actions.D) selectedActions.push("D");
    json[perm.id] = selectedActions.length > 0 ? selectedActions.join("") : "N";
  });
  return json;
};

export const validateRoleForm = (formData: {
  name: string;
  idTipoRole: string;
  description: string;
}): boolean => {
  return !!(formData.name && formData.idTipoRole && formData.description);
};

export const validateUserForm = (
  formData: Partial<User & { confirmPassword?: string }>,
  editingUser?: User | null,
  role?: string
): { isValid: boolean; error?: string } => {
  if (!formData.name || !formData.email || !formData.role) {
    return {
      isValid: false,
      error: "Preencha todos os campos obrigatórios (Nome, Email, Função).",
    };
  }

  if (!editingUser) {
    if (!formData.password || formData.password !== formData.confirmPassword) {
      return {
        isValid: false,
        error: "As senhas são obrigatórias e devem coincidir.",
      };
    }
  } else if (formData.password || formData.confirmPassword) {
    if (formData.password !== formData.confirmPassword) {
      return { isValid: false, error: "As senhas não coincidem." };
    }
  }

  if (
    role === "Funcionário" &&
    (!formData.cargo || !formData.setor || !formData.matricula)
  ) {
    return {
      isValid: false,
      error: "Cargo, Setor e Matrícula são obrigatórios para Funcionário.",
    };
  }

  if (
    role === "Profissional de Saúde" &&
    (!formData.cargo ||
      !formData.unidade ||
      !formData.specialty ||
      !formData.conselho ||
      !formData.registroConselho ||
      !formData.matricula)
  ) {
    return {
      isValid: false,
      error:
        "Cargo, Unidade, Especialidade, Conselho, Registro do Conselho e Matrícula são obrigatórios para Profissional de Saúde.",
    };
  }

  return { isValid: true };
};

export const buildUsuarioData = (formData: Partial<User>, idRole: string) => {
  return {
    nome: formData.name?.trim() || "",
    cpf: (formData.cpf || "").replace(/\D/g, ""),
    dataNascimento: formData.birthDate || null,
    sexo: formData.gender
      ? formData.gender === "M"
        ? "MASCULINO"
        : formData.gender === "F"
        ? "FEMININO"
        : "OUTRO"
      : "OUTRO",
    estadoCivil: formData.maritalStatus
      ? formData.maritalStatus.toLowerCase().replace("(a)", "")
      : "",
    email: formData.email || "",
    senha: formData.password || "",
    telefone: formData.phone || "",
    endereco: formData.address || "",
    ativo: true,
    mfaHabilitado: false,
    dataCriacao: new Date().toISOString().split("T")[0],
    idRole,
  };
};

export const createSpecificEntity = async (
  role: string,
  idUsuario: number,
  formData: Partial<User>,
  api: any
) => {
  if (role === "Paciente") {
    const pacienteData = {
      idUsuario,
      profissao: formData.profession || "",
      preferenciaContato: formData.preferencia_contato || "EMAIL",
    };
    await api.criarPaciente(pacienteData);
  } else if (role === "Funcionário") {
    const funcionarioData = {
      idUsuario,
      cargo: formData.cargo!,
      setor: formData.setor!,
      matricula: formData.matricula!,
      observacoes: formData.observacoes || "",
    };
    await api.criarFuncionario(funcionarioData);
  } else if (role === "Profissional de Saúde") {
    const profissionalData = {
      idUsuario,
      matricula: formData.matricula!,
      conselho: formData.conselho!,
      registroConselho: formData.registroConselho!,
      especialidade: formData.specialty!,
      cargo: formData.cargo!,
      unidade: formData.unidade!,
    };
    await api.criarProfissional_saude(profissionalData);
  }
};
