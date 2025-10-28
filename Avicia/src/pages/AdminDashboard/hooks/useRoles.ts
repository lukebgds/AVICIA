// src/pages/AdminDashboard/hooks/useRoles.ts
import { useState, useEffect } from "react";
import { useToast } from "@/hooks/use-toast";
import { api } from "@/services/api";
import { constructPermissionsJson, validateRoleForm } from "../utils/helpers";
import { initialFormData, PermissionActions } from "../utils/constants";
import type { Role } from "../types/types";

interface UseRolesReturn {
  roles: Role[];
  roleFormData: typeof initialFormData;
  isRoleDialogOpen: boolean;
  rolesLoading: boolean;
  handleAddRole: () => void;
  handlePermissionChange: (
    permissionId: string,
    action: keyof PermissionActions
  ) => void;
  handleSubmitRole: (e: React.FormEvent) => Promise<void>;
  setIsRoleDialogOpen: (open: boolean) => void;
  setRoleFormData: React.Dispatch<React.SetStateAction<typeof initialFormData>>;
  fetchRoles: () => Promise<void>;
}

export const useRoles = (token: string | null): UseRolesReturn => {
  const { toast } = useToast();
  const [roles, setRoles] = useState<Role[]>([]);
  const [roleFormData, setRoleFormData] = useState(initialFormData);
  const [isRoleDialogOpen, setIsRoleDialogOpen] = useState(false);
  const [rolesLoading, setRolesLoading] = useState(false);

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

  const handleSubmitRole = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateRoleForm(roleFormData)) {
      toast({
        title: "Erro de Validação",
        description: "Por favor, preencha todos os campos obrigatórios.",
        variant: "destructive",
      });
      return;
    }

    const permissionsJson = constructPermissionsJson(roleFormData.permissions);
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
      await fetchRoles();
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

  useEffect(() => {
    if (token) {
      fetchRoles();
    }
  }, [token]);

  return {
    roles,
    roleFormData,
    isRoleDialogOpen,
    rolesLoading,
    handleAddRole,
    handlePermissionChange,
    handleSubmitRole,
    setIsRoleDialogOpen,
    setRoleFormData,
    fetchRoles,
  };
};
