// src/pages/AdminDashboard/sections/RolesSection.tsx
import React from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import {
  KeyRound,
  Shield,
  RefreshCw,
  Search,
  Edit,
  Trash2,
} from "lucide-react";
import { RoleForm } from "../forms/RoleForm";
import { useRoles } from "../hooks/useRoles";
import { getRoleTypeLabel } from "../utils/helpers";

interface RolesSectionProps {
  token: string | null;
}

export const RolesSection: React.FC<RolesSectionProps> = ({ token }) => {
  const {
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
  } = useRoles(token);

  const [roleSearchTerm, setRoleSearchTerm] = React.useState("");

  const filteredRoles = roles.filter(
    (role: any) =>
      role.nome.toLowerCase().includes(roleSearchTerm.toLowerCase()) ||
      role.descricao.toLowerCase().includes(roleSearchTerm.toLowerCase())
  );

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <CardTitle className="flex items-center gap-2 text-gray-700">
              <KeyRound className="h-5 w-5" /> Controle de Acesso -
              Gerenciamento de Roles
            </CardTitle>
            <div className="flex gap-2">
              <Button
                onClick={handleAddRole}
                size="sm"
                className="bg-red-600 hover:bg-red-700 text-white"
                disabled={rolesLoading}
              >
                <Shield className="h-4 w-4 mr-2" /> Novo Perfil
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={fetchRoles}
                disabled={rolesLoading}
              >
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
                {filteredRoles.map((role: any) => (
                  <TableRow key={role.idRole}>
                    <TableCell className="font-medium text-gray-800">
                      {role.nome}
                    </TableCell>
                    <TableCell className="text-gray-600">
                      {getRoleTypeLabel(role.idRole)} ({role.idRole})
                    </TableCell>
                    <TableCell className="text-gray-600">
                      {role.descricao}
                    </TableCell>
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
      <RoleForm
        isOpen={isRoleDialogOpen}
        onOpenChange={setIsRoleDialogOpen}
        formData={roleFormData}
        onFormDataChange={setRoleFormData}
        roles={roles}
        loading={rolesLoading}
        onPermissionChange={handlePermissionChange}
        onSubmit={handleSubmitRole}
      />
    </div>
  );
};
