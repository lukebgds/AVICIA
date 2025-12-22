// src/pages/AdminDashboard/forms/RoleForm.tsx
import React from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Checkbox } from "@/components/ui/checkbox";
import { Button } from "@/components/ui/button";
import { getRoleTypeLabel } from "../utils/helpers";
import type { Role } from "../types/types";
import {
  permissionGroups,
  actions,
  PermissionActions,
  initialFormData,
} from "../utils/constants";

interface RoleFormProps {
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  formData: typeof initialFormData;
  onFormDataChange: React.Dispatch<
    React.SetStateAction<typeof initialFormData>
  >;
  roles: Role[];
  loading: boolean;
  onPermissionChange: (
    permissionId: string,
    action: keyof PermissionActions
  ) => void;
  onSubmit: (e: React.FormEvent) => Promise<void>;
}

export const RoleForm: React.FC<RoleFormProps> = ({
  isOpen,
  onOpenChange,
  formData,
  onFormDataChange,
  roles,
  loading,
  onPermissionChange,
  onSubmit,
}) => {
  const setFormData = (updates: Partial<typeof initialFormData>) => {
    onFormDataChange({ ...formData, ...updates });
  };

  return (
    <Dialog open={isOpen} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-6xl">
        <DialogHeader>
          <DialogTitle className="text-xl font-semibold text-gray-900">
            Novo Perfil de Acesso
          </DialogTitle>
        </DialogHeader>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {/* === COLUNA ESQUERDA: INFORMAÇÕES BÁSICAS === */}
          <div className="space-y-6">
            <div className="p-4 border rounded-xl shadow-sm bg-gray-50/70 sticky top-0">
              <h2 className="text-lg font-semibold text-red-800 mb-3">
                Informações Básicas
              </h2>
              <div className="space-y-3">
                <div>
                  <Label htmlFor="name">Nome do Perfil</Label>
                  <Input
                    id="name"
                    placeholder="Ex: ADMIN.READ, Recepcionista"
                    value={formData.name}
                    onChange={(e) => setFormData({ name: e.target.value })}
                  />
                </div>

                <div>
                  <Label htmlFor="idTipoRole">Tipo de Role</Label>
                  <Select
                    value={formData.idTipoRole}
                    onValueChange={(value) =>
                      setFormData({ idTipoRole: value })
                    }
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Selecione o tipo de role" />
                    </SelectTrigger>
                    <SelectContent>
                      {roles.map((role) => (
                        <SelectItem
                          key={role.idRole}
                          value={role.idRole.toString()}
                        >
                          {getRoleTypeLabel(role.idRole)} ({role.idRole})
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
                    value={formData.description}
                    onChange={(e) =>
                      setFormData({ description: e.target.value })
                    }
                    rows={3}
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="md:col-span-2">
            <div className="max-h-[70vh] overflow-y-auto pr-2 space-y-6">
              {permissionGroups.map((group) => (
                <div
                  key={group.title}
                  className="p-5 border rounded-xl shadow-sm bg-white"
                >
                  <h3 className="text-lg font-semibold text-red-800 mb-3">
                    {group.title}
                  </h3>

                  <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4">
                    {group.permissions.map((permission) => (
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
                          {actions.map((action) => (
                            <div
                              key={action.id}
                              className="flex items-center space-x-2"
                            >
                              <Checkbox
                                id={`${permission.id}-${action.id}`}
                                checked={
                                  formData.permissions[permission.id][action.id]
                                }
                                onCheckedChange={() =>
                                  onPermissionChange(permission.id, action.id)
                                }
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
            onClick={() => onOpenChange(false)}
            className="px-6"
          >
            Cancelar
          </Button>

          <Button
            type="submit"
            disabled={loading}
            onClick={onSubmit}
            className="bg-red-600 hover:bg-red-700 text-white px-6"
          >
            {loading ? "Salvando..." : "Salvar Perfil"}
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
