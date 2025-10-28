// src/pages/AdminDashboard/sections/UsersSection.tsx
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
import { Users, UserPlus, RefreshCw, Search, Edit, Trash2 } from "lucide-react";
import { UserForm } from "../forms/UserForm";
import { useUsers } from "../hooks/useUsers";

interface UsersSectionProps {
  token: string | null;
}

export const UsersSection: React.FC<UsersSectionProps> = ({ token }) => {
  const {
    users,
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
  } = useUsers(token);

  const [searchTerm, setSearchTerm] = React.useState("");

  const filteredUsers = users.filter(
    (user) =>
      user.role !== "SYSTEM.ADMIN" &&
      (user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
        user.role.toLowerCase().includes(searchTerm.toLowerCase()))
  );

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <CardTitle className="flex items-center gap-2 text-gray-700">
              <Users className="h-5 w-5" /> Gerenciamento de Usuários
            </CardTitle>
            <div className="flex gap-2">
              <Button
                onClick={handleAddUser}
                size="sm"
                className="bg-red-600 hover:bg-red-700 text-white"
                disabled={loading}
              >
                <UserPlus className="h-4 w-4 mr-2" /> Novo Usuário
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={fetchUsers}
                disabled={loading}
              >
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
                    <TableHead className="text-right">Ações</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {filteredUsers.map((user) => (
                    <TableRow key={user.id}>
                      <TableCell className="font-medium text-gray-800">
                        {user.name}
                      </TableCell>
                      <TableCell className="text-gray-600">
                        {user.email}
                      </TableCell>
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
                        <Badge
                          className={
                            user.status === "Ativo"
                              ? "bg-green-100 text-green-800"
                              : "bg-gray-200 text-gray-600"
                          }
                        >
                          {user.status}
                        </Badge>
                      </TableCell>
                      <TableCell>
                        <div className="flex gap-2 justify-end">
                          <Button
                            size="icon"
                            variant="ghost"
                            onClick={() => handleEditUser(user)}
                          >
                            <Edit className="h-4 w-4 text-gray-500 hover:text-red-600" />
                          </Button>
                          <Button
                            size="icon"
                            variant="ghost"
                            onClick={() => handleDeleteUser(user.id)}
                            disabled={isDeleting === user.id}
                          >
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
      <UserForm
        isOpen={isDialogOpen}
        onOpenChange={setIsDialogOpen}
        formData={formData}
        onFormDataChange={setFormData}
        editingUser={editingUser}
        loading={loading}
        onSave={handleSaveUser}
      />
    </div>
  );
};
