// AdminDashboard version 9

import { useState, useEffect, useRef } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
import {
  Shield,
  Users,
  Database,
  Settings,
  Activity,
  AlertTriangle,
  CheckCircle,
  XCircle,
  Clock,
  UserPlus,
  Edit,
  Trash2,
  Search,
  Stethoscope,
  BarChart,
  Calendar,
  FileText,
  DollarSign,
  KeyRound,
  RefreshCw,
} from "lucide-react";
import { api } from "../services/api";
import { useAuthGuard } from "@/hooks/useAuthGuard";

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
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // --- Data Fetching ---
  const fetchUsers = async () => {
    try {
      setLoading(true);
      const response = await api.getAllUsuarios();
      const mappedUsers = response.map(mapBackendUserToUser);
      setUsers(mappedUsers);
      // Atualiza estatísticas de usuários ativos
      SYSTEM_STATS[0].value = mappedUsers.filter((u) => u.status === "Ativo").length;
    } catch (error) {
      console.error("Erro ao buscar usuários:", error);
      toast({
        title: "Erro",
        description: "Falha ao carregar usuários. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  // --- Effects ---
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsAuthenticated(true);
      fetchUsers(); // Chama fetchUsers só se houver token
    }
    const savedActivities = localStorage.getItem("recentActivities");
    if (savedActivities) setRecentActivities(JSON.parse(savedActivities));
    setSystemStatus([]); // Dados fictícios ou fetch separado se dinâmico
  }, []);

  useEffect(() => {
    localStorage.setItem("recentActivities", JSON.stringify(recentActivities.slice(-10)));
  }, [recentActivities]);

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
            toast({ title: "Erro", description: "Cargo, Setor e Matrícula são obrigatórios.", variant: "destructive" });
            setLoading(false);
            return;
          }
          const funcionarioData = { idUsuario, cargo: formData.cargo || "", setor: formData.setor || "", matricula: formData.matricula || "", observacoes: formData.observacoes || "" };
          entityCriada = await api.criarFuncionario(funcionarioData);
        } else if (formData.role === "Profissional de Saúde") {
          if (!formData.cargo || !formData.matricula || !formData.unidade || !formData.specialty || !formData.conselho || !formData.registroConselho) {
            toast({ title: "Erro", description: "Campos obrigatórios faltando para Profissional de Saúde.", variant: "destructive" });
            setLoading(false);
            return;
          }
          const profissionalData = { idUsuario, cargo: formData.cargo || "", unidade: formData.unidade || "", especialidade: formData.specialty || "", conselho: formData.conselho || "", registroConselho: formData.registroConselho || "", matricula: formData.matricula || ""};
          entityCriada = await api.criarProfissional_saude(profissionalData);
        }

        const newUser: User = { ...(dataToSave as User), id: Number(idUsuario), status: "Ativo", lastLogin: "Nunca" };
        setUsers([...users, newUser]);
        toast({ title: "Usuário Criado", description: "Novo usuário criado com sucesso" });
        const newActivity: ActivityLog = { action: `Novo usuário criado: ${newUser.name}`, user: "Admin", time: new Date().toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" }), status: "success" };
        setRecentActivities([newActivity, ...recentActivities]);
        await fetchUsers();
      } catch (error) {
        toast({ title: "Erro no cadastro", description: "Tente novamente mais tarde", variant: "destructive" });
      }
    } else if (editingUser) {
      try {
        setUsers(users.map((u) => (u.id === editingUser.id ? { ...u, ...dataToSave } as User : u)));
        toast({ title: "Usuário Atualizado", description: "Usuário atualizado com sucesso" });
        const newActivity: ActivityLog = { action: `Dados atualizados: ${dataToSave.name}`, user: "Admin", time: new Date().toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" }), status: "success" };
        setRecentActivities([newActivity, ...recentActivities]);
        await fetchUsers();
      } catch (error) {
        toast({ title: "Erro", description: "Falha ao atualizar usuário.", variant: "destructive" });
      }
    } else {
      const newUser: User = { ...(dataToSave as User), id: Math.max(...users.map((u) => u.id), 0) + 1, status: "Ativo", lastLogin: "Nunca" };
      setUsers([...users, newUser]);
      toast({ title: "Usuário Criado", description: "Novo usuário criado com sucesso" });
      const newActivity: ActivityLog = { action: `Novo usuário criado: ${newUser.name}`, user: "Admin", time: new Date().toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" }), status: "success" };
      setRecentActivities([newActivity, ...recentActivities]);
      await fetchUsers();
    }

    setIsDialogOpen(false);
    setFormData({});
    setEditingUser(null);
    setLoading(false);
  };

  // --- Render Functions ---
  const renderUserForm = () => (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>{editingUser ? "Editar Usuário" : "Novo Usuário"}</DialogTitle>
        </DialogHeader>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 py-4 max-h-[70vh] overflow-y-auto pr-6">
          {/* Campos Gerais */}
          <div className="space-y-2"><Label htmlFor="name">Nome Completo *</Label><Input id="name" value={formData.name || ""} onChange={(e) => setFormData({ ...formData, name: e.target.value })} placeholder="Nome completo" /></div>
          <div className="space-y-2"><Label htmlFor="email">Email *</Label><Input id="email" type="email" value={formData.email || ""} onChange={(e) => setFormData({ ...formData, email: e.target.value })} placeholder="email@exemplo.com" /></div>
          <div className="space-y-2"><Label htmlFor="password">Senha {editingUser ? "(Opcional)" : "*"}</Label><Input id="password" type="password" value={formData.password || ""} onChange={(e) => setFormData({ ...formData, password: e.target.value })} placeholder="********" /></div>
          <div className="space-y-2"><Label htmlFor="confirmPassword">Confirmar Senha {editingUser ? "(Opcional)" : "*"}</Label><Input id="confirmPassword" type="password" value={formData.confirmPassword || ""} onChange={(e) => setFormData({ ...formData, confirmPassword: e.target.value })} placeholder="********" /></div>
          <div className="space-y-2 col-span-1 md:col-span-2"><Label htmlFor="address">Endereço</Label><Input id="address" value={formData.address || ""} onChange={(e) => setFormData({ ...formData, address: e.target.value })} placeholder="Rua, Número, Bairro, Cidade - UF" /></div>
          <div className="space-y-2"><Label htmlFor="birthDate">Data de Nascimento</Label><Input id="birthDate" type="date" value={formData.birthDate || ""} onChange={(e) => setFormData({ ...formData, birthDate: e.target.value })} /></div>
          <div className="space-y-2"><Label htmlFor="cpf">CPF</Label><Input id="cpf" value={formData.cpf || ""} onChange={(e) => setFormData({ ...formData, cpf: e.target.value })} placeholder="000.000.000-00" /></div>
          <div className="space-y-2"><Label htmlFor="gender">Sexo</Label><Select value={formData.gender || ""} onValueChange={(value) => setFormData({ ...formData, gender: value })}><SelectTrigger><SelectValue placeholder="Selecione o sexo" /></SelectTrigger><SelectContent><SelectItem value="M">Masculino</SelectItem><SelectItem value="F">Feminino</SelectItem><SelectItem value="Outro">Outro</SelectItem><SelectItem value="NaoInformar">Prefiro não informar</SelectItem></SelectContent></Select></div>
          <div className="space-y-2"><Label htmlFor="maritalStatus">Estado Civil</Label><Select value={formData.maritalStatus || ""} onValueChange={(value) => setFormData({ ...formData, maritalStatus: value })}><SelectTrigger><SelectValue placeholder="Selecione o estado civil" /></SelectTrigger><SelectContent><SelectItem value="Solteiro(a)">Solteiro(a)</SelectItem><SelectItem value="Casado(a)">Casado(a)</SelectItem><SelectItem value="Divorciado(a)">Divorciado(a)</SelectItem><SelectItem value="Viuvo(a)">Viúvo(a)</SelectItem></SelectContent></Select></div>
          <div className="space-y-2"><Label htmlFor="phone">Telefone</Label><Input id="phone" value={formData.phone || ""} onChange={(e) => setFormData({ ...formData, phone: e.target.value })} placeholder="(11) 99999-9999" /></div>
          <div className="space-y-2"><Label htmlFor="profession">Profissão</Label><Input id="profession" value={formData.profession || ""} onChange={(e) => setFormData({ ...formData, profession: e.target.value })} placeholder="Profissão" /></div>
          <div className="space-y-2 col-span-1 md:col-span-2"><Label htmlFor="role">Função no Sistema *</Label><Select value={formData.role || ""} onValueChange={(value) => setFormData({ ...formData, role: value })}><SelectTrigger><SelectValue placeholder="Selecione a função" /></SelectTrigger><SelectContent>{ROLES.map((role) => <SelectItem key={role} value={role}>{role}</SelectItem>)}</SelectContent></Select></div>

          {/* Campos Condicionais */}
          {formData.role === "Paciente" && (
            <div className="space-y-2 col-span-1 md:col-span-2">
              <Label htmlFor="preferencia_contato">Preferência de Contato</Label>
              <Select value={formData.preferencia_contato || ""} onValueChange={(value) => setFormData({ ...formData, preferencia_contato: value })}>
                <SelectTrigger><SelectValue placeholder="Selecione a preferência" /></SelectTrigger>
                <SelectContent>
                  <SelectItem value="Email">Email</SelectItem>
                  <SelectItem value="Telefone">Telefone</SelectItem>
                  <SelectItem value="WhatsApp">WhatsApp</SelectItem>
                </SelectContent>
              </Select>
            </div>
          )}

          {(formData.role === "Funcionário" || formData.role === "Profissional de Saúde") && (
            <>
              <div className="space-y-2"><Label htmlFor="matricula">Matrícula</Label><Input id="matricula" value={formData.matricula || ""} onChange={(e) => setFormData({ ...formData, matricula: e.target.value })} placeholder="Nº da Matrícula" /></div>
              <div className="space-y-2"><Label htmlFor="cargo">Cargo</Label><Input id="cargo" value={formData.cargo || ""} onChange={(e) => setFormData({ ...formData, cargo: e.target.value })} placeholder="Ex: Médico(a), Recepcionista" /></div>
            </>
          )}

          {formData.role === "Funcionário" && (
            <>
              <div className="space-y-2"><Label htmlFor="setor">Setor</Label><Input id="setor" value={formData.setor || ""} onChange={(e) => setFormData({ ...formData, setor: e.target.value })} placeholder="Setor de trabalho" /></div>
              <div className="space-y-2"><Label htmlFor="observacoes">Observações</Label><Input id="observacoes" value={formData.observacoes || ""} onChange={(e) => setFormData({ ...formData, observacoes: e.target.value })} placeholder="Observações adicionais" /></div>
            </>
          )}

          {formData.role === "Profissional de Saúde" && (
            <>
              <div className="space-y-2"><Label htmlFor="unidade">Unidade</Label><Input id="unidade" value={formData.unidade || ""} onChange={(e) => setFormData({ ...formData, unidade: e.target.value })} placeholder="Unidade de atuação" /></div>
              <div className="space-y-2"><Label htmlFor="specialty">Especialidade</Label><Input id="specialty" value={formData.specialty || ""} onChange={(e) => setFormData({ ...formData, specialty: e.target.value })} placeholder="Especialidade médica" /></div>
              <div className="space-y-2"><Label htmlFor="conselho">Conselho Profissional</Label><Input id="conselho" value={formData.conselho || ""} onChange={(e) => setFormData({ ...formData, conselho: e.target.value })} placeholder="Ex: CRM, COREN" /></div>
              <div className="space-y-2"><Label htmlFor="registroConselho">Nº do Conselho</Label><Input id="registroConselho" value={formData.registroConselho || ""} onChange={(e) => setFormData({ ...formData, registroConselho: e.target.value })} placeholder="123456-PE" /></div>
            </>
          )}
        </div>
        <div className="flex justify-end gap-2 pt-4 border-t">
          <Button variant="outline" onClick={() => setIsDialogOpen(false)}>Cancelar</Button>
          <Button disabled={loading} onClick={handleSaveUser} className="bg-red-600 hover:bg-red-700 text-white">
            {loading ? "Salvando..." : editingUser ? "Atualizar" : "Criar"}
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
                    .filter((user) =>
                      user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                      user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
                      user.role.toLowerCase().includes(searchTerm.toLowerCase())
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
                                : user.role === "SYSTEM.ADMIN"
                                ? "bg-purple-100 text-purple-800"
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

  const renderAccessControl = () => (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <KeyRound className="h-5 w-5" /> Controle de Acesso
        </CardTitle>
      </CardHeader>
      <CardContent>
        <p>Página para definir perfis e permissões de usuário.</p>
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