// src/pages/AdminDashboard/hooks/useUsers.ts
import { useState, useEffect } from "react";
import { useToast } from "@/hooks/use-toast";
import { api } from "@/services/api";
import { mapBackendUserToUser } from "../utils/mappers";
import {
  validateUserForm,
  buildUsuarioData,
  createSpecificEntity,
} from "../utils/helpers";
import { ROLES } from "../utils/constants";
import type { User } from "../types/types";

interface UseUsersReturn {
  users: User[];
  activeUserCount: number;
  loading: boolean;
  isDialogOpen: boolean;
  editingUser: User | null;
  formData: Partial<User & { confirmPassword?: string }>;
  isDeleting: number | null;
  handleAddUser: () => void;
  handleEditUser: (user: User) => void;
  handleDeleteUser: (userId: number) => Promise<void>;
  handleSaveUser: () => Promise<void>;
  setIsDialogOpen: (open: boolean) => void;
  setFormData: React.Dispatch<
    React.SetStateAction<Partial<User & { confirmPassword?: string }>>
  >;
  fetchUsers: () => Promise<void>;
}

export const useUsers = (token: string | null): UseUsersReturn => {
  const { toast } = useToast();
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(false);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [editingUser, setEditingUser] = useState<User | null>(null);
  const [formData, setFormData] = useState<
    Partial<User & { confirmPassword?: string }>
  >({});
  const [isDeleting, setIsDeleting] = useState<number | null>(null);

  const activeUserCount = users.filter(
    (u) => u.status === "Ativo" && u.role !== "SYSTEM.ADMIN"
  ).length;

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const response = await api.getAllUsuarios();
      const mappedUsers = response.map(mapBackendUserToUser);
      setUsers(mappedUsers);
      console.log("✅ Usuários carregados:", mappedUsers.length);
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

    const confirmDelete = window.confirm(
      `Tem certeza que deseja excluir o usuário ${userToDelete.name}?`
    );
    if (!confirmDelete) return;

    try {
      setIsDeleting(userId);
      await api.deleteUsuario(userId);
      setUsers(users.filter((u) => u.id !== userId));
      toast({
        title: "Usuário Removido",
        description: `Usuário ${userToDelete.name} removido com sucesso`,
        variant: "default",
      });

      const newActivity = {
        action: `Usuário removido: ${userToDelete.name}`,
        user: "Admin",
        time: new Date().toLocaleTimeString("pt-BR", {
          hour: "2-digit",
          minute: "2-digit",
        }),
        status: "warning",
      };
      // Note: recentActivities is dashboard-specific, so this might need adjustment or prop
      localStorage.setItem(
        "recentActivities",
        JSON.stringify(
          [
            newActivity,
            ...JSON.parse(localStorage.getItem("recentActivities") || "[]"),
          ].slice(-10)
        )
      );

      await fetchUsers();
    } catch (error) {
      console.error("Erro ao deletar usuário:", error);
      toast({
        title: "Erro",
        description: "Falha ao remover usuário. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setIsDeleting(null);
    }
  };

  const handleSaveUser = async () => {
    const validation = validateUserForm(formData, editingUser, formData.role);
    if (!validation.isValid) {
      toast({
        title: "Erro",
        description: validation.error,
        variant: "destructive",
      });
      return;
    }

    const dataToSave = { ...formData };
    if (editingUser && (dataToSave.password || dataToSave.confirmPassword)) {
      delete dataToSave.password;
    }
    delete dataToSave.confirmPassword;
    setLoading(true);

    if (!editingUser && ROLES.includes(formData.role as string)) {
      try {
        const roleName =
          formData.role === "Paciente"
            ? "PACIENTE"
            : formData.role === "Funcionário"
            ? "FUNCIONARIO"
            : "PROFISSIONAL.SAUDE";

        const role = await api.getRoleByName(roleName);
        const idRole = role.idRole;

        const usuarioData = buildUsuarioData(formData, idRole);

        const usuarioCriado = await api.criarUsuario(usuarioData);
        const idUsuario = usuarioCriado.idUsuario;

        await createSpecificEntity(
          formData.role as string,
          idUsuario,
          formData,
          api
        );

        toast({
          title: "Sucesso!",
          description: `Usuário ${formData.name} criado com sucesso como ${formData.role}.`,
        });

        setIsDialogOpen(false);
        setFormData({});
        await fetchUsers();
      } catch (error) {
        console.error("Erro ao criar usuário:", error);
        toast({
          title: "Erro ao Criar",
          description:
            "Falha ao criar usuário. Verifique os dados e tente novamente.",
          variant: "destructive",
        });
      }
    } else {
      toast({
        title: "Funcionalidade em Desenvolvimento",
        description: "Edição de usuários será implementada em breve.",
      });
    }
    setLoading(false);
  };

  useEffect(() => {
    if (token) {
      fetchUsers();
    }
  }, [token]);

  return {
    users,
    activeUserCount,
    loading,
    isDialogOpen,
    editingUser,
    formData,
    isDeleting,
    handleAddUser,
    handleEditUser,
    handleDeleteUser,
    handleSaveUser,
    setIsDialogOpen,
    setFormData,
    fetchUsers,
  };
};
