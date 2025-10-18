import { useState, useEffect, useRef } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Checkbox } from "@/components/ui/checkbox";
import { useToast } from "@/hooks/use-toast";
import { Shield, Users, Database, Settings, Activity, AlertTriangle, CheckCircle, XCircle, Clock, UserPlus, Edit, Trash2, Search, Stethoscope, BarChart, Calendar, FileText, DollarSign, KeyRound, RefreshCw, ArrowLeft, Save } from "lucide-react";
import { api } from "../services/api";
import { useAuthGuard } from "@/hooks/useAuthGuard";
import { useAuth } from "@/context/AuthContext";

// --- Interfaces ---
interface BackendUser {
  idUsuario: number;
  nome: string;
  sobrenome: string;
  cpf: string;
  email: string;
  telefone: string;
  ativo: boolean;
  mfaHabilitado: boolean;
  dataCriacao: string;
  idRole: number;
}

interface User {
  id: number;
  name: string;
  email: string;
  role: string;
  status: string;
  lastLogin: string;
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

interface ActivityLog {
  action: string;
  user: string;
  time: string;
  status: string;
}

interface SystemStatusItem {
  name: string;
  status: "ok" | "warning" | "error";
  message: string;
}

interface PermissionActions {
  C: boolean;
  R: boolean;
  U: boolean;
  D: boolean;
}

// Constantes de Permissões (sem alteração)
const permissionGroups = [
  {
    title: "Administração do Sistema",
    permissions: [
      { id: "sistema", label: "Sistema", description: "Configurações gerais e parâmetros do sistema." },
      { id: "role", label: "Roles (Perfis)", description: "Gerenciar perfis de acesso e suas permissões." },
      { id: "usuario", label: "Usuários", description: "Gerenciar contas de usuários do sistema." },
      { id: "log", label: "Logs de Atividade", description: "Visualizar os registros de auditoria do sistema." },
    ]
  },
  {
    title: "Cadastros Principais",
    permissions: [
      { id: "paciente", label: "Pacientes", description: "Gerenciar o cadastro e dados dos pacientes." },
      { id: "profissionalSaude", label: "Profissionais de Saúde", description: "Gerenciar o cadastro de profissionais de saúde." },
      { id: "funcionario", label: "Funcionários", description: "Gerenciar o cadastro de funcionários administrativos." },
      { id: "convenio", label: "Convênios", description: "Gerenciar os convênios médicos aceitos." },
    ]
  },
  {
    title: "Gestão Clínica",
    permissions: [
      { id: "consulta", label: "Consultas", description: "Gerenciar agendamentos e registros de consultas." },
      { id: "prescricao", label: "Prescrições", description: "Criar e gerenciar prescrições médicas." },
      { id: "internacao", label: "Internações", description: "Gerenciar os registros de internações de pacientes." },
    ]
  },
  {
    title: "Prontuário do Paciente",
    permissions: [
      { id: "alergia", label: "Alergias", description: "Visualizar e gerenciar registros de alergias." },
      { id: "medicamento", label: "Medicamentos", description: "Gerenciar o cadastro de medicamentos." },
      { id: "vacina", label: "Vacinas", description: "Visualizar e gerenciar o histórico de vacinas." },
      { id: "antecedente", label: "Antecedentes", description: "Gerenciar os antecedentes médicos dos pacientes." },
      { id: "anexo", label: "Anexos", description: "Gerenciar arquivos e documentos anexados aos prontuários." },
      { id: "diagnostico", label: "Diagnósticos", description: "Gerenciar diagnósticos (CID) e registros nos prontuários." },
    ]
  },
  {
    title: "Exames",
    permissions: [
      { id: "exame", label: "Tipos de Exame", description: "Gerenciar os tipos de exames disponíveis." },
      { id: "exameSolicitar", label: "Solicitação de Exames", description: "Permite criar, ler e deletar solicitações de exames." },
      { id: "exameResultado", label: "Resultados de Exames", description: "Visualizar e anexar resultados de exames." },
    ]
  },
  {
    title: "Outros Módulos",
    permissions: [
      { id: "associacao", label: "Associações", description: "Gerenciar associações entre entidades do sistema." },
      { id: "relatorio", label: "Relatórios", description: "Gerar e visualizar relatórios do sistema." }
    ]
  }
];

const actions: { id: keyof PermissionActions; label: string }[] = [
  { id: "C", label: "Criar" },
  { id: "R", label: "Ler" },
  { id: "U", label: "Atualizar" },
  { id: "D", label: "Deletar" },
];

const allPermissions = permissionGroups.flatMap((group) => group.permissions);

const initialPermissions = allPermissions.reduce((acc, perm) => {
  acc[perm.id] = { C: false, R: false, U: false, D: false };
  return acc;
}, {} as Record<string, PermissionActions>);

interface RoleTypeOption {
  value: number;
  label: string;
}

const roleTypes: RoleTypeOption[] = [
  { value: 101, label: "Sistema Admin" },
  { value: 701, label: "Paciente" },
  { value: 501, label: "Funcionário" },
  { value: 601, label: "Profissional de Saúde" },
];

const initialFormData = {
  name: "",
  idTipoRole: "",
  description: "",
  permissions: initialPermissions,
};

// --- Constants ---
const ROLES = ["Paciente", "Funcionário", "Profissional de Saúde"];
const SYSTEM_STATS = [
  { label: "Usuários Ativos", value: 0, icon: Users, color: "text-red-600" },
  { label: "Alertas Sistema", value: 0, icon: AlertTriangle, color: "text-yellow-500" },
];

// --- Utility Functions ---
const getStatusIcon = (status: string) => {
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

const mapBackendUserToUser = (user: BackendUser): User => ({
  id: user.idUsuario,
  name: `${user.nome} ${user.sobrenome}`.trim(),
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
  lastLogin: "Nunca", // Expandir com outra query se necessário
  phone: user.telefone,
  cpf: user.cpf,
});

// --- Component ---
const AdminDashboard = () => {
  useAuthGuard();
  const { toast } = useToast();
  const { token } = useAuth();

  // --- State Management ---
  const [activeSection, setActiveSection] = useState("dashboard");
  const [users, setUsers] = useState<User[]>([]);
  const [recentActivities, setRecentActivities] = useState<ActivityLog[]>([]);
  const [systemStatus, setSystemStatus] = useState<SystemStatusItem[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [editingUser, setEditingUser] = useState<User | null>(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [formData, setFormData] = useState<Partial<User & { confirmPassword?: string }>>({});
  const [loading, setLoading] = useState(false);
  const hasLoadedUsers = useRef(false);

  // States for Role Management
  const [isRoleDialogOpen, setIsRoleDialogOpen] = useState(false);
  const [roleFormData, setRoleFormData] = useState(initialFormData);
  const [roles, setRoles] = useState<any[]>([]); // Empty for now, no mock data
  const [roleSearchTerm, setRoleSearchTerm] = useState("");
  const [rolesLoading, setRolesLoading] = useState(false);

  // --- Data Fetching ---
  const fetchUsers = async () => {
    try {
      setLoading(true);
      const response = await api.getAllUsuarios();
      const mappedUsers = response.map(mapBackendUserToUser);
      setUsers(mappedUsers);
      SYSTEM_STATS[0].value = mappedUsers.filter(
        (u) => u.status === "Ativo" && u.role !== "SYSTEM.ADMIN"
      ).length;
      console.log("✅ Usuários carregados:", mappedUsers.length); // OPCIONAL: Pra ver no console se rodou se rodou
    } catch (error) {
      console.error("Erro no fetchUsers:", error);
      toast({
        title: "Erro ao carregar usuários",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  const fetchRoles = async () => {
    try {
      setRolesLoading(true);
      const response = await api.getAllRoles();
      setRoles(response);
      console.log("✅ Roles carregadas:", response.length);
    } catch (error) {
      console.error("Erro no fetchRoles:", error);
      toast({
        title: "Erro ao carregar roles",
        variant: "destructive",
      });
    } finally {
      setRolesLoading(false);
    }
  };

  // --- Effects ---
  useEffect(() => {
    if (token) {
      // Só se tem token (após login)
      fetchUsers(); // Roda automático na abertura da página
      fetchRoles(); // Carrega roles na montagem inicial
    }

    // Parte das atividades (mantém se quiser, ou remova)
    const savedActivities = localStorage.getItem("recentActivities");
    if (savedActivities) {
      setRecentActivities(JSON.parse(savedActivities));
    }
    setSystemStatus([]); // Ou carrega de API se precisar
  }, [token]); // SÓ [token]: Roda no mount ou se token mudar (ex: logout recarrega)

  useEffect(() => {
    localStorage.setItem("recentActivities", JSON.stringify(recentActivities.slice(-10)));
  }, [recentActivities]);

  // Role form handlers
  const handlePermissionChange = (
    permissionId: string,
    action: keyof PermissionActions
  ) => {
    setRoleFormData((prev) => {
      const currentPermissions = prev.permissions[permissionId];
      const updatedPermissions = {
        ...currentPermissions,
        [action]: !currentPermissions[action],
      };

      return {
        ...prev,
        permissions: {
          ...prev.permissions,
          [permissionId]: updatedPermissions,
        },
      };
    });
  };

  const constructPermissionsJson = () => {
    const json: Record<string, string> = {};
    allPermissions.forEach(perm => {
      const actions = roleFormData.permissions[perm.id];
      const selectedActions = [];
      if (actions.C) selectedActions.push('C');
      if (actions.R) selectedActions.push('R');
      if (actions.U) selectedActions.push('U');
      if (actions.D) selectedActions.push('D');
      json[perm.id] = selectedActions.length > 0 ? selectedActions.join('') : 'N';
    });
    return json;
  };

  const handleSubmitRole = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!roleFormData.name || !roleFormData.idTipoRole || !roleFormData.description) {
      toast({
        title: "Erro de Validação",
        description: "Por favor, preencha todos os campos obrigatórios.",
        variant: "destructive",
      });
      return;
    }

    const permissionsJson = constructPermissionsJson();
    const dataToSave = {
      nome: roleFormData.name,
      idTipoRole: parseInt(roleFormData.idTipoRole as string),
      descricao: roleFormData.description,
      permissoes: permissionsJson,
    };

    setRolesLoading(true);

    try {
      await api.criarRole(dataToSave);
      console.log("Dados da Role salvos no backend.");

      toast({
        title: "Sucesso!",
        description: `A role "${roleFormData.name}" foi cadastrada com sucesso.`,
      });

      setIsRoleDialogOpen(false);
      setRoleFormData(initialFormData);
      await fetchRoles(); // Recarrega a lista para sincronizar com o backend, igual ao users
    } catch (error) {
      console.error("Erro ao criar role:", error);
      toast({
        title: "Erro ao Salvar",
        description: "Falha ao cadastrar a role. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setRolesLoading(false);
    }
  };

  const handleAddRole = () => {
    setRoleFormData(initialFormData);
    setIsRoleDialogOpen(true);
  };

  // --- Event Handlers ---
  const handleAddUser = () => {
    setEditingUser(null);
    setFormData({});
    setIsDialogOpen(true);
  };

  const handleEditUser = (user: User) => {
    setEditingUser(user);
    const { password, ...userData } = user;
    setFormData(userData);
    setIsDialogOpen(true);
  };

  const [isDeleting, setIsDeleting] = useState<number | null>(null); // Adicionar estado para feedback de carregamento

  const handleDeleteUser = async (userId: number) => {
    const userToDelete = users.find((u) => u.id === userId);
    if (!userToDelete) {
      toast({
        title: "Erro",
        description: "Usuário não encontrado.",
        variant: "destructive",
      });
      return;
    }

    const confirmDelete = window.confirm(`Tem certeza que deseja excluir o usuário ${userToDelete.name}?`);
    if (!confirmDelete) return;

    try {
      setIsDeleting(userId); // Indica que a exclusão está em andamento
      await api.deleteUsuario(userId); // Chama o método da API
      setUsers(users.filter((u) => u.id !== userId)); // Remove da lista local
      toast({
        title: "Usuário Removido",
        description: `Usuário ${userToDelete.name} removido com sucesso`,
        variant: "default",
      });

      const newActivity: ActivityLog = {
        action: `Usuário removido: ${userToDelete.name}`,
        user: "Admin",
        time: new Date().toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" }),
        status: "warning",
      };
      setRecentActivities([newActivity, ...recentActivities]);

      await fetchUsers(); // Recarrega a lista para sincronizar com o backend
    } catch (error) {
      console.error("Erro ao deletar usuário:", error);
      toast({
        title: "Erro",
        description: "Falha ao remover usuário. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setIsDeleting(null); // Reseta o estado de carregamento
    }
  };

  const handleSaveUser = async () => {
    if (!formData.name || !formData.email || !formData.role) {
      toast({
        title: "Erro",
        description: "Preencha todos os campos obrigatórios (Nome, Email, Função).",
        variant: "destructive",
      });
      return;
    }

    const dataToSave = { ...formData };

    if (!editingUser) {
      if (!dataToSave.password || dataToSave.password !== dataToSave.confirmPassword) {
        toast({ title: "Erro de Senha", description: "As senhas são obrigatórias e devem coincidir.", variant: "destructive" });
        return;
      }
    } else if (dataToSave.password || dataToSave.confirmPassword) {
      if (dataToSave.password !== dataToSave.confirmPassword) {
        toast({ title: "Erro de Senha", description: "As senhas não coincidem.", variant: "destructive" });
        return;
      }
      delete dataToSave.password;
    }

    delete dataToSave.confirmPassword;
    setLoading(true);

    if (!editingUser && (formData.role === "Paciente" || formData.role === "Funcionário" || formData.role === "Profissional de Saúde")) {
      try {
        const roleName =
          formData.role === "Paciente"
            ? "PACIENTE"
            : formData.role === "Funcionário"
              ? "FUNCIONARIO"
              : "PROFISSIONAL.SAUDE";
        const role = await api.getRoleByName(roleName);
        const idRole = role.idRole;

        const nomeCompleto = formData.name.trim();
        const ultimoEspaco = nomeCompleto.lastIndexOf(" ");
        const nome = ultimoEspaco > 0 ? nomeCompleto.substring(0, ultimoEspaco) : nomeCompleto;
        const sobrenome = ultimoEspaco > 0 ? nomeCompleto.substring(ultimoEspaco + 1) : "";

        const usuarioData = {
          nome,
          sobrenome,
          cpf: (formData.cpf || "").replace(/\D/g, ""),
          email: formData.email,
          senha: formData.password,
          telefone: formData.phone || "",
          ativo: true,
          mfaHabilitado: false,
          dataCriacao: new Date().toISOString().split("T")[0],
          idRole,
          dataNascimento: formData.birthDate || "",
          sexo: formData.gender ? (formData.gender === "M" ? "MASCULINO" : formData.gender === "F" ? "FEMININO" : "OUTRO") : "OUTRO",
          estadoCivil: formData.maritalStatus ? formData.maritalStatus.toLowerCase().replace("(a)", "") : "",
          profissao: formData.profession || "",
          endereco: formData.address || "",
          preferenciaContato: formData.preferencia_contato || "EMAIL",
        };

        const usuarioCriado = await api.criarUsuario(usuarioData);
        const idUsuario = usuarioCriado.idUsuario;

        let entityCriada;
        if (formData.role === "Paciente") {
          const pacienteData = { idUsuario, ...usuarioData };
          entityCriada = await api.criarPaciente(pacienteData);
        } else if (formData.role === "Funcionário") {
          if (!formData.cargo || !formData.setor || !formData.matricula) {
            toast({ title: "Erro", description: "Cargo, Setor e Matrícula são obrigatórios para Funcionário.", variant: "destructive" });
            return;
          }
          const funcionarioData = {
            idUsuario,
            cargo: formData.cargo!,
            setor: formData.setor!,
            matricula: formData.matricula!,
            observacoes: formData.observacoes || "",
          };
          entityCriada = await api.criarFuncionario(funcionarioData);
        } else if (formData.role === "Profissional de Saúde") {
          if (!formData.cargo || !formData.unidade || !formData.specialty || !formData.conselho || !formData.registroConselho || !formData.matricula) {
            toast({ title: "Erro", description: "Cargo, Unidade, Especialidade, Conselho, Registro do Conselho e Matrícula são obrigatórios para Profissional de Saúde.", variant: "destructive" });
            return;
          }
          const profissionalData = {
            idUsuario,
            cargo: formData.cargo!,
            unidade: formData.unidade!,
            especialidade: formData.specialty!,
            conselho: formData.conselho!,
            registroConselho: formData.registroConselho!,
            matricula: formData.matricula!,
            observacoes: formData.observacoes || "",
          };
          entityCriada = await api.criarProfissional_saude(profissionalData);
        }

        toast({
          title: "Sucesso!",
          description: `Usuário ${formData.name} criado com sucesso como ${formData.role}.`,
        });
        setIsDialogOpen(false);
        setFormData({});
        await fetchUsers(); // Recarrega a lista
      } catch (error) {
        console.error("Erro ao criar usuário:", error);
        toast({
          title: "Erro ao Criar",
          description: "Falha ao criar usuário. Verifique os dados e tente novamente.",
          variant: "destructive",
        });
      }
    } else {
      // Lógica para edição de usuário existente (implementar se necessário)
      toast({
        title: "Funcionalidade em Desenvolvimento",
        description: "Edição de usuários será implementada em breve.",
      });
      setLoading(false);
    }
  };

// --- Render Functions ---
  const renderUserForm = () => (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogContent className="max-w-4xl">
        <DialogHeader>
          <DialogTitle className="text-xl font-semibold text-gray-900">
            {editingUser ? "Editar Usuário" : "Novo Usuário"}
          </DialogTitle>
        </DialogHeader>

        <div className="max-h-[70vh] overflow-y-auto pr-2 space-y-6">
          {/* === INFORMAÇÕES PESSOAIS === */}
          <div className="p-5 border rounded-xl bg-gray-50/70 shadow-sm">
            <h2 className="text-lg font-semibold text-red-800 mb-3">Informações Pessoais</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {[
                { id: "name", label: "Nome Completo", type: "text", placeholder: "Nome completo" },
                { id: "email", label: "Email", type: "email", placeholder: "email@exemplo.com" },
                { id: "birthDate", label: "Data de Nascimento", type: "date" },
                { id: "cpf", label: "CPF", type: "text", placeholder: "000.000.000-00" },
              ].map(({ id, label, type, placeholder }) => (
                <div key={id} className="space-y-2">
                  <Label htmlFor={id}>{label}</Label>
                  <Input id={id} type={type} value={formData[id] || ""} onChange={e => setFormData({ ...formData, [id]: e.target.value })} placeholder={placeholder} />
                </div>
              ))}

              {/* Sexo */}
              <div className="space-y-2">
                <Label>Sexo</Label>
                <Select value={formData.gender || ""} onValueChange={v => setFormData({ ...formData, gender: v })}>
                  <SelectTrigger><SelectValue placeholder="Selecione o sexo" /></SelectTrigger>
                  <SelectContent>
                    {["Masculino", "Feminino", "Outro", "Prefiro não informar"].map((g, i) => (
                      <SelectItem key={i} value={g === "Masculino" ? "M" : g === "Feminino" ? "F" : g.replace(/\s/g, "")}>{g}</SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              {/* Estado Civil */}
              <div className="space-y-2">
                <Label>Estado Civil</Label>
                <Select value={formData.maritalStatus || ""} onValueChange={v => setFormData({ ...formData, maritalStatus: v })}>
                  <SelectTrigger><SelectValue placeholder="Selecione o estado civil" /></SelectTrigger>
                  <SelectContent>
                    {["solteiro(a)", "casado(a)", "divorciado(a)", "viuvo(a)"].map(s => (
                      <SelectItem key={s} value={s}>{s.charAt(0).toUpperCase() + s.slice(1)}</SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              <div className="space-y-2 col-span-1 md:col-span-2">
                <Label htmlFor="address">Endereço</Label>
                <Input id="address" value={formData.address || ""} onChange={e => setFormData({ ...formData, address: e.target.value })} placeholder="Rua, Número, Bairro, Cidade - UF" />
              </div>
            </div>
          </div>

          {/* === ACESSO AO SISTEMA === */}
          <div className="p-5 border rounded-xl bg-white shadow-sm">
            <h2 className="text-lg font-semibold text-red-800 mb-3">Acesso ao Sistema</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {[
                { id: "password", label: editingUser ? "Senha (Opcional)" : "Senha" },
                { id: "confirmPassword", label: editingUser ? "Confirmar Senha (Opcional)" : "Confirmar Senha" },
              ].map(({ id, label }) => (
                <div key={id} className="space-y-2">
                  <Label htmlFor={id}>{label}</Label>
                  <Input id={id} type="password" value={formData[id] || ""} onChange={e => setFormData({ ...formData, [id]: e.target.value })} placeholder="********" />
                </div>
              ))}

              <div className="space-y-2 col-span-1 md:col-span-2">
                <Label>Função no Sistema</Label>
                <Select value={formData.role || ""} onValueChange={v => setFormData({ ...formData, role: v })}>
                  <SelectTrigger><SelectValue placeholder="Selecione a função" /></SelectTrigger>
                  <SelectContent>{ROLES.map(r => <SelectItem key={r} value={r}>{r}</SelectItem>)}</SelectContent>
                </Select>
              </div>
            </div>

            {/* Campos Condicionais */}
            {formData.role === "Paciente" && (
              <div className="mt-4 space-y-2">
                <Label>Preferência de Contato</Label>
                <Select value={formData.preferencia_contato || ""} onValueChange={v => setFormData({ ...formData, preferencia_contato: v })}>
                  <SelectTrigger><SelectValue placeholder="Selecione a preferência" /></SelectTrigger>
                  <SelectContent>{["Email", "Telefone", "WhatsApp"].map(p => <SelectItem key={p} value={p}>{p}</SelectItem>)}</SelectContent>
                </Select>
              </div>
            )}

            {["Funcionário", "Profissional de Saúde"].includes(formData.role) && (
              <div className="mt-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                {[
                  { id: "matricula", label: "Matrícula", placeholder: "Nº da Matrícula" },
                  { id: "cargo", label: "Cargo", placeholder: "Ex: Médico(a), Recepcionista" },
                ].map(({ id, label, placeholder }) => (
                  <div key={id} className="space-y-2">
                    <Label htmlFor={id}>{label}</Label>
                    <Input id={id} value={formData[id] || ""} onChange={e => setFormData({ ...formData, [id]: e.target.value })} placeholder={placeholder} />
                  </div>
                ))}
              </div>
            )}

            {formData.role === "Funcionário" && (
              <div className="mt-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                {[
                  { id: "setor", label: "Setor", placeholder: "Setor de trabalho" },
                  { id: "observacoes", label: "Observações", placeholder: "Observações adicionais" },
                ].map(({ id, label, placeholder }) => (
                  <div key={id} className="space-y-2">
                    <Label htmlFor={id}>{label}</Label>
                    <Input id={id} value={formData[id] || ""} onChange={e => setFormData({ ...formData, [id]: e.target.value })} placeholder={placeholder} />
                  </div>
                ))}
              </div>
            )}

            {formData.role === "Profissional de Saúde" && (
              <div className="mt-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                {[
                  { id: "unidade", label: "Unidade", placeholder: "Unidade de atuação" },
                  { id: "specialty", label: "Especialidade", placeholder: "Especialidade médica" },
                  { id: "conselho", label: "Conselho Profissional", placeholder: "Ex: CRM, COREN" },
                  { id: "registroConselho", label: "Nº do Conselho", placeholder: "123456-PE" },
                ].map(({ id, label, placeholder }) => (
                  <div key={id} className="space-y-2">
                    <Label htmlFor={id}>{label}</Label>
                    <Input id={id} value={formData[id] || ""} onChange={e => setFormData({ ...formData, [id]: e.target.value })} placeholder={placeholder} />
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* BOTÕES */}
        <div className="flex justify-end gap-3 pt-4 border-t mt-4">
          <Button variant="outline" onClick={() => setIsDialogOpen(false)}>Cancelar</Button>
          <Button disabled={loading} onClick={handleSaveUser} className="bg-red-600 hover:bg-red-700 text-white">
            {loading ? "Salvando..." : editingUser ? "Atualizar" : "Criar"}
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );


  const renderRoleForm = () => (
    <Dialog open={isRoleDialogOpen} onOpenChange={setIsRoleDialogOpen}>
      <DialogContent className="max-w-6xl">
        <DialogHeader>
          <DialogTitle className="text-xl font-semibold text-gray-900">
            {editingUser ? "Editar Perfil de Acesso" : "Novo Perfil de Acesso"}
          </DialogTitle>
        </DialogHeader>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {/* === COLUNA ESQUERDA: INFORMAÇÕES BÁSICAS === */}
          <div className="space-y-6">
            <div className="p-4 border rounded-xl shadow-sm bg-gray-50/70 sticky top-0">
              <h2 className="text-lg font-semibold text-red-800 mb-3">Informações Básicas</h2>
              <div className="space-y-3">
                <div>
                  <Label htmlFor="name">Nome do Perfil</Label>
                  <Input
                    id="name"
                    placeholder="Ex: ADMIN.READ, Recepcionista"
                    value={roleFormData.name}
                    onChange={e => setRoleFormData({ ...roleFormData, name: e.target.value })}
                  />
                </div>

                <div>
                  <Label htmlFor="idTipoRole">Tipo de Role</Label>
                  <Select
                    value={roleFormData.idTipoRole}
                    onValueChange={value => setRoleFormData({ ...roleFormData, idTipoRole: value })}
                  >
                    <SelectTrigger><SelectValue placeholder="Selecione o tipo de role" /></SelectTrigger>
                    <SelectContent>
                      {roleTypes.map(option => (
                        <SelectItem key={option.value} value={option.value.toString()}>
                          {option.label}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                <div>
                  <Label htmlFor="description">Descrição</Label>
                  <Textarea
                    id="description"
                    placeholder="Descreva a finalidade e as responsabilidades deste perfil..."
                    value={roleFormData.description}
                    onChange={e => setRoleFormData({ ...roleFormData, description: e.target.value })}
                    rows={3}
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="md:col-span-2">
            <div className="max-h-[70vh] overflow-y-auto pr-2 space-y-6">
              {permissionGroups.map(group => (
                <div key={group.title} className="p-5 border rounded-xl shadow-sm bg-white">
                  <h3 className="text-lg font-semibold text-red-800 mb-3">{group.title}</h3>

                  <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4">
                    {group.permissions.map(permission => (
                      <div
                        key={permission.id}
                        className="p-4 border rounded-lg bg-gray-50/60 hover:shadow-md transition"
                      >
                        {/* Nome da Permissão */}
                        <p className="text-base font-semibold text-red-700 mb-1">
                          {permission.label}
                        </p>

                        {/* Descrição */}
                        <p className="text-sm text-gray-600 mb-3">
                          {permission.description}
                        </p>

                        {/* Ações */}
                        <div className="flex flex-wrap gap-4">
                          {actions.map(action => (
                            <div key={action.id} className="flex items-center space-x-2">
                              <Checkbox
                                id={`${permission.id}-${action.id}`}
                                checked={roleFormData.permissions[permission.id][action.id]}
                                onCheckedChange={() => handlePermissionChange(permission.id, action.id)}
                              />
                              <label
                                htmlFor={`${permission.id}-${action.id}`}
                                className="text-sm font-medium leading-none text-gray-800"
                              >
                                {action.label}
                              </label>
                            </div>
                          ))}
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* === BOTÕES === */}
        <div className="flex justify-end gap-3 pt-4 border-t mt-4">
          <Button
            variant="outline"
            onClick={() => setIsRoleDialogOpen(false)}
            className="px-6"
          >
            Cancelar
          </Button>

          <Button
            type="submit"
            disabled={rolesLoading}
            onClick={handleSubmitRole}
            className="bg-red-600 hover:bg-red-700 text-white px-6"
          >
            {rolesLoading ? "Salvando..." : "Salvar Perfil"}
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );

  const renderUserManagement = () => (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <CardTitle className="flex items-center gap-2 text-gray-700">
              <Users className="h-5 w-5" /> Gerenciamento de Usuários
            </CardTitle>
            <div className="flex gap-2">
              <Button onClick={handleAddUser} size="sm" className="bg-red-600 hover:bg-red-700 text-white" disabled={loading}>
                <UserPlus className="h-4 w-4 mr-2" /> Novo Usuário
              </Button>
              <Button variant="outline" size="sm" onClick={fetchUsers} disabled={loading}>
                <RefreshCw className="h-4 w-4 mr-2" /> Atualizar
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          {loading ? (
            <div className="flex justify-center py-8">
              <p>Carregando usuários...</p>
            </div>
          ) : (
            <>
              <div className="mb-4">
                <div className="relative">
                  <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
                  <Input
                    placeholder="Pesquisar usuários por nome, email ou função..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="pl-10"
                  />
                </div>
              </div>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Nome</TableHead>
                    <TableHead>Email</TableHead>
                    <TableHead>Função</TableHead>
                    <TableHead>Status</TableHead>
                    <TableHead>Último Login</TableHead>
                    <TableHead className="text-right">Ações</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {users
                    .filter(
                      (user) =>
                        user.role !== "SYSTEM.ADMIN" &&
                        (user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          user.role.toLowerCase().includes(searchTerm.toLowerCase()))
                    )
                    .map((user) => (
                      <TableRow key={user.id}>
                        <TableCell className="font-medium text-gray-800">{user.name}</TableCell>
                        <TableCell className="text-gray-600">{user.email}</TableCell>
                        <TableCell>
                          <Badge
                            className={
                              user.role === "Profissional de Saúde"
                                ? "bg-blue-100 text-blue-800"
                                : user.role === "Paciente"
                                  ? "bg-gray-100 text-gray-800"
                                  : user.role === "Funcionário"
                                    ? "bg-teal-100 text-teal-800"
                                    : "bg-gray-100 text-gray-800"
                            }
                          >
                            {user.role}
                          </Badge>
                        </TableCell>
                        <TableCell>
                          <Badge className={user.status === "Ativo" ? "bg-green-100 text-green-800" : "bg-gray-200 text-gray-600"}>
                            {user.status}
                          </Badge>
                        </TableCell>
                        <TableCell className="text-sm text-gray-500">{user.lastLogin}</TableCell>
                        <TableCell>
                          <div className="flex gap-2 justify-end">
                            <Button size="icon" variant="ghost" onClick={() => handleEditUser(user)}>
                              <Edit className="h-4 w-4 text-gray-500 hover:text-red-600" />
                            </Button>
                            <Button size="icon" variant="ghost" onClick={() => handleDeleteUser(user.id)} disabled={isDeleting === user.id}>
                              <Trash2 className="h-4 w-4 text-gray-500 hover:text-red-600" />
                            </Button>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                </TableBody>
              </Table>
            </>
          )}
        </CardContent>
      </Card>
      {renderUserForm()}
    </div>
  );

  const renderAccessControl = () => (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <CardTitle className="flex items-center gap-2 text-gray-700">
              <KeyRound className="h-5 w-5" /> Controle de Acesso - Gerenciamento de Roles
            </CardTitle>
            <div className="flex gap-2">
              <Button onClick={handleAddRole} size="sm" className="bg-red-600 hover:bg-red-700 text-white" disabled={rolesLoading}>
                <Shield className="h-4 w-4 mr-2" /> Novo Perfil
              </Button>
              <Button variant="outline" size="sm" onClick={fetchRoles} disabled={rolesLoading}>
                <RefreshCw className="h-4 w-4 mr-2" /> Atualizar
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <div className="mb-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
              <Input
                placeholder="Pesquisar roles por nome ou descrição..."
                value={roleSearchTerm}
                onChange={(e) => setRoleSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>
          </div>
          {rolesLoading ? (
            <div className="flex justify-center py-8">
              <p>Carregando roles...</p>
            </div>
          ) : (
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Nome</TableHead>
                  <TableHead>Tipo</TableHead>
                  <TableHead>Descrição</TableHead>
                  <TableHead>Permissões</TableHead>
                  <TableHead className="text-right">Ações</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {roles
                  .filter(
                    (role: any) =>
                      role.nome.toLowerCase().includes(roleSearchTerm.toLowerCase()) ||
                      role.descricao.toLowerCase().includes(roleSearchTerm.toLowerCase())
                  )
                  .map((role: any) => (
                    <TableRow key={role.id}>
                      <TableCell className="font-medium text-gray-800">{role.nome}</TableCell>
                      <TableCell className="text-gray-600">
                        {roleTypes.find(opt => opt.value === role.idTipoRole)?.label || 'Desconhecido'}
                      </TableCell>
                      <TableCell className="text-gray-600">{role.descricao}</TableCell>
                      <TableCell>
                        <Badge variant="outline" className="text-xs">
                          {JSON.stringify(role.permissoes).slice(0, 50)}...
                        </Badge>
                      </TableCell>
                      <TableCell>
                        <div className="flex gap-2 justify-end">
                          <Button size="icon" variant="ghost">
                            <Edit className="h-4 w-4 text-gray-500 hover:text-red-600" />
                          </Button>
                          <Button size="icon" variant="ghost">
                            <Trash2 className="h-4 w-4 text-gray-500 hover:text-red-600" />
                          </Button>
                        </div>
                      </TableCell>
                    </TableRow>
                  ))}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>
      {renderRoleForm()}
    </div>
  );

  const renderReports = () => (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <BarChart className="h-5 w-5" /> Relatórios e Análises
        </CardTitle>
      </CardHeader>
      <CardContent>
        <p>Página para visualização de relatórios e dados do sistema.</p>
      </CardContent>
    </Card>
  );

  const renderLogs = () => (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <FileText className="h-5 w-5" /> Logs do Sistema
        </CardTitle>
      </CardHeader>
      <CardContent>
        <p>Página para visualização dos logs de atividade do sistema.</p>
      </CardContent>
    </Card>
  );

  const renderSettings = () => (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Settings className="h-5 w-5" /> Configurações Gerais
        </CardTitle>
      </CardHeader>
      <CardContent>
        <p>Página para as configurações gerais do sistema.</p>
      </CardContent>
    </Card>
  );

  return (
    <div className="min-h-screen bg-red-50/50 p-4 sm:p-6 lg:p-8">
      <div className="max-w-7xl mx-auto space-y-6">
        <header className="flex flex-col sm:flex-row items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold text-red-800 flex items-center gap-3">
              <div className="bg-red-600 p-2 rounded-lg">
                <Stethoscope className="h-6 w-6 text-white" />
              </div>
              AVICIA - Painel Administrativo
            </h1>
            <p className="text-gray-500 mt-1">Controle total do sistema de prontuário eletrônico.</p>
          </div>
          <nav className="flex flex-wrap gap-2 mt-4 sm:mt-0">
            {[
              { label: "Dashboard", icon: Activity, section: "dashboard" },
              { label: "Usuários", icon: Users, section: "users" },
              { label: "Controle de Acesso", icon: KeyRound, section: "access-control" },
              { label: "Relatórios", icon: BarChart, section: "reports" },
              { label: "Logs", icon: FileText, section: "logs" },
              { label: "Configurações", icon: Settings, section: "settings" },
            ].map(({ label, icon: Icon, section }) => (
              <Button
                key={section}
                variant={activeSection === section ? "default" : "ghost"}
                onClick={() => setActiveSection(section)}
                className={activeSection === section ? "bg-red-600 hover:bg-red-700 text-white" : ""}
              >
                <Icon className="h-4 w-4 mr-2" /> {label}
              </Button>
            ))}
          </nav>
        </header>

        {activeSection === "dashboard" && (
          <div className="space-y-6">
            <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {SYSTEM_STATS.map((stat, index) => (
                <Card key={index} className="hover:shadow-md transition-shadow">
                  <CardContent className="p-6">
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="text-sm text-gray-500">{stat.label}</p>
                        <p className="text-3xl font-bold text-gray-800">{stat.value}</p>
                      </div>
                      <stat.icon className={`h-8 w-8 ${stat.color}`} />
                    </div>
                  </CardContent>
                </Card>
              ))}
            </section>
            <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
              <div className="lg:col-span-2">
                <Card>
                  <CardHeader>
                    <CardTitle className="flex items-center gap-2 text-gray-700">
                      <Activity className="h-5 w-5" /> Atividades Recentes do Sistema
                    </CardTitle>
                  </CardHeader>
                  <CardContent>
                    <div className="space-y-4">
                      {recentActivities.length > 0 ? (
                        recentActivities.map((activity, index) => (
                          <div key={index} className="flex items-center justify-between p-3 rounded-lg bg-gray-100/50">
                            <div className="flex items-center gap-3">
                              {getStatusIcon(activity.status)}
                              <div>
                                <p className="font-medium text-gray-800">{activity.action}</p>
                                <p className="text-sm text-gray-500">por {activity.user}</p>
                              </div>
                            </div>
                            <Badge variant="outline" className="text-xs text-gray-500">
                              {activity.time}
                            </Badge>
                          </div>
                        ))
                      ) : (
                        <p className="text-sm text-gray-500 text-center py-4">Nenhuma atividade recente.</p>
                      )}
                    </div>
                  </CardContent>
                </Card>
              </div>
              <div>
                <Card>
                  <CardHeader>
                    <CardTitle className="flex items-center gap-2 text-gray-700">
                      <Shield className="h-5 w-5" /> Status do Sistema
                    </CardTitle>
                  </CardHeader>
                  <CardContent className="space-y-3">
                    {systemStatus.length > 0 ? (
                      systemStatus.map((item, index) => {
                        const icon =
                          item.status === "ok" ? (
                            <CheckCircle className="h-5 w-5 text-green-500" />
                          ) : item.status === "warning" ? (
                            <Clock className="h-5 w-5 text-yellow-500" />
                          ) : (
                            <XCircle className="h-5 w-5 text-red-500" />
                          );
                        const bgColor =
                          item.status === "ok"
                            ? "bg-green-50 border-green-200"
                            : item.status === "warning"
                              ? "bg-yellow-50 border-yellow-200"
                              : "bg-red-50 border-red-200";
                        return (
                          <div key={index} className={`flex items-center gap-2 p-3 rounded-lg border ${bgColor}`}>
                            {icon}
                            <div>
                              <span className="font-medium text-gray-800">{item.name}</span>
                              <p className="text-sm text-gray-500">{item.message}</p>
                            </div>
                          </div>
                        );
                      })
                    ) : (
                      <p className="text-sm text-gray-500 text-center py-4">Nenhum status para exibir.</p>
                    )}
                  </CardContent>
                </Card>
              </div>
            </section>
          </div>
        )}

        {activeSection === "users" && renderUserManagement()}
        {activeSection === "access-control" && renderAccessControl()}
        {activeSection === "reports" && renderReports()}
        {activeSection === "logs" && renderLogs()}
        {activeSection === "settings" && renderSettings()}
      </div>
    </div>
  );
};

export default AdminDashboard;