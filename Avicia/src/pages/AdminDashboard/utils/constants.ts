// src/pages/AdminDashboard/utils/constants.ts
import { Users, AlertTriangle } from "lucide-react";

export const permissionGroups = [
  {
    title: "Administração do Sistema",
    permissions: [
      {
        id: "sistema",
        label: "Sistema",
        description: "Configurações gerais e parâmetros do sistema.",
      },
      {
        id: "role",
        label: "Roles (Perfis)",
        description: "Gerenciar perfis de acesso e suas permissões.",
      },
      {
        id: "usuario",
        label: "Usuários",
        description: "Gerenciar contas de usuários do sistema.",
      },
      {
        id: "log",
        label: "Logs de Atividade",
        description: "Visualizar os registros de auditoria do sistema.",
      },
    ],
  },
  {
    title: "Cadastros Principais",
    permissions: [
      {
        id: "paciente",
        label: "Pacientes",
        description: "Gerenciar o cadastro e dados dos pacientes.",
      },
      {
        id: "profissionalSaude",
        label: "Profissionais de Saúde",
        description: "Gerenciar o cadastro de profissionais de saúde.",
      },
      {
        id: "funcionario",
        label: "Funcionários",
        description: "Gerenciar o cadastro de funcionários administrativos.",
      },
      {
        id: "convenio",
        label: "Convênios",
        description: "Gerenciar os convênios médicos aceitos.",
      },
    ],
  },
  {
    title: "Gestão Clínica",
    permissions: [
      {
        id: "consulta",
        label: "Consultas",
        description: "Gerenciar agendamentos e registros de consultas.",
      },
      {
        id: "prescricao",
        label: "Prescrições",
        description: "Criar e gerenciar prescrições médicas.",
      },
      {
        id: "internacao",
        label: "Internações",
        description: "Gerenciar os registros de internações de pacientes.",
      },
    ],
  },
  {
    title: "Prontuário do Paciente",
    permissions: [
      {
        id: "alergia",
        label: "Alergias",
        description: "Visualizar e gerenciar registros de alergias.",
      },
      {
        id: "medicamento",
        label: "Medicamentos",
        description: "Gerenciar o cadastro de medicamentos.",
      },
      {
        id: "vacina",
        label: "Vacinas",
        description: "Visualizar e gerenciar o histórico de vacinas.",
      },
      {
        id: "antecedente",
        label: "Antecedentes",
        description: "Gerenciar os antecedentes médicos dos pacientes.",
      },
      {
        id: "anexo",
        label: "Anexos",
        description:
          "Gerenciar arquivos e documentos anexados aos prontuários.",
      },
      {
        id: "diagnostico",
        label: "Diagnósticos",
        description:
          "Gerenciar diagnósticos (CID) e registros nos prontuários.",
      },
    ],
  },
  {
    title: "Exames",
    permissions: [
      {
        id: "exame",
        label: "Tipos de Exame",
        description: "Gerenciar os tipos de exames disponíveis.",
      },
      {
        id: "exameSolicitar",
        label: "Solicitação de Exames",
        description: "Permite criar, ler e deletar solicitações de exames.",
      },
      {
        id: "exameResultado",
        label: "Resultados de Exames",
        description: "Visualizar e anexar resultados de exames.",
      },
    ],
  },
  {
    title: "Outros Módulos",
    permissions: [
      {
        id: "associacao",
        label: "Associações",
        description: "Gerenciar associações entre entidades do sistema.",
      },
      {
        id: "relatorio",
        label: "Relatórios",
        description: "Gerar e visualizar relatórios do sistema.",
      },
    ],
  },
];

export const actions: { id: keyof PermissionActions; label: string }[] = [
  { id: "C", label: "Criar" },
  { id: "R", label: "Ler" },
  { id: "U", label: "Atualizar" },
  { id: "D", label: "Deletar" },
];

export const allPermissions = permissionGroups.flatMap(
  (group) => group.permissions
);

export const initialPermissions = allPermissions.reduce((acc, perm) => {
  acc[perm.id] = { C: false, R: false, U: false, D: false };
  return acc;
}, {} as Record<string, PermissionActions>);

export interface PermissionActions {
  C: boolean;
  R: boolean;
  U: boolean;
  D: boolean;
}

export const ROLES = ["Paciente", "Funcionário", "Profissional de Saúde"];

export const SYSTEM_STATS = [
  { label: "Usuários Ativos", value: 0, icon: Users, color: "text-red-600" },
  {
    label: "Alertas Sistema",
    value: 0,
    icon: AlertTriangle,
    color: "text-yellow-500",
  },
];
export const initialFormData = {
  name: "",
  idTipoRole: "",
  description: "",
  permissions: initialPermissions,
};
