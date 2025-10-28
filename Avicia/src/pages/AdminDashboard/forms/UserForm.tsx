// src/pages/AdminDashboard/forms/UserForm.tsx
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
import { Button } from "@/components/ui/button";
import { ROLES } from "../utils/constants";
import type { User } from "../types/types";

interface UserFormProps {
  isOpen: boolean;
  onOpenChange: (open: boolean) => void;
  formData: Partial<User & { confirmPassword?: string }>;
  onFormDataChange: React.Dispatch<
    React.SetStateAction<Partial<User & { confirmPassword?: string }>>
  >;
  editingUser: User | null;
  loading: boolean;
  onSave: () => Promise<void>;
}

export const UserForm: React.FC<UserFormProps> = ({
  isOpen,
  onOpenChange,
  formData,
  onFormDataChange,
  editingUser,
  loading,
  onSave,
}) => {
  const setFormData = (
    updates: Partial<User & { confirmPassword?: string }>
  ) => {
    onFormDataChange({ ...formData, ...updates });
  };

  return (
    <Dialog open={isOpen} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-4xl">
        <DialogHeader>
          <DialogTitle className="text-xl font-semibold text-gray-900">
            {editingUser ? "Editar Usuário" : "Novo Usuário"}
          </DialogTitle>
        </DialogHeader>

        <div className="max-h-[70vh] overflow-y-auto pr-2 space-y-6">
          {/* === INFORMAÇÕES PESSOAIS === */}
          <div className="p-5 border rounded-xl bg-gray-50/70 shadow-sm">
            <h2 className="text-lg font-semibold text-red-800 mb-3">
              Informações Pessoais
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {[
                {
                  id: "name",
                  label: "Nome Completo",
                  type: "text",
                  placeholder: "Nome completo",
                },
                {
                  id: "email",
                  label: "Email",
                  type: "email",
                  placeholder: "email@exemplo.com",
                },
                { id: "birthDate", label: "Data de Nascimento", type: "date" },
                {
                  id: "cpf",
                  label: "CPF",
                  type: "text",
                  placeholder: "000.000.000-00",
                },
                {
                  id: "phone",
                  label: "Telefone",
                  type: "tel",
                  placeholder: "(00) 00000-0000",
                },
              ].map(({ id, label, type, placeholder }) => (
                <div key={id} className="space-y-2">
                  <Label htmlFor={id}>{label}</Label>
                  <Input
                    id={id}
                    type={type}
                    value={formData[id as keyof typeof formData] || ""}
                    onChange={(e) => setFormData({ [id]: e.target.value })}
                    placeholder={placeholder}
                  />
                </div>
              ))}

              {/* Sexo */}
              <div className="space-y-2">
                <Label>Sexo</Label>
                <Select
                  value={formData.gender || ""}
                  onValueChange={(v) => setFormData({ gender: v })}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione o sexo" />
                  </SelectTrigger>
                  <SelectContent>
                    {[
                      "Masculino",
                      "Feminino",
                      "Outro",
                      "Prefiro não informar",
                    ].map((g, i) => {
                      const value =
                        g === "Masculino"
                          ? "M"
                          : g === "Feminino"
                          ? "F"
                          : g.replace(/\s/g, "");
                      return (
                        <SelectItem key={i} value={value}>
                          {g}
                        </SelectItem>
                      );
                    })}
                  </SelectContent>
                </Select>
              </div>

              {/* Estado Civil */}
              <div className="space-y-2">
                <Label>Estado Civil</Label>
                <Select
                  value={formData.maritalStatus || ""}
                  onValueChange={(v) => setFormData({ maritalStatus: v })}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione o estado civil" />
                  </SelectTrigger>
                  <SelectContent>
                    {[
                      "solteiro(a)",
                      "casado(a)",
                      "divorciado(a)",
                      "viuvo(a)",
                    ].map((s) => (
                      <SelectItem key={s} value={s}>
                        {s.charAt(0).toUpperCase() + s.slice(1)}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              <div className="space-y-2 col-span-1 md:col-span-2">
                <Label htmlFor="address">Endereço</Label>
                <Input
                  id="address"
                  value={formData.address || ""}
                  onChange={(e) => setFormData({ address: e.target.value })}
                  placeholder="Rua, Número, Bairro, Cidade - UF"
                />
              </div>
            </div>
          </div>

          {/* === ACESSO AO SISTEMA === */}
          <div className="p-5 border rounded-xl bg-white shadow-sm">
            <h2 className="text-lg font-semibold text-red-800 mb-3">
              Acesso ao Sistema
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {[
                {
                  id: "password",
                  label: editingUser ? "Senha (Opcional)" : "Senha",
                },
                {
                  id: "confirmPassword",
                  label: editingUser
                    ? "Confirmar Senha (Opcional)"
                    : "Confirmar Senha",
                },
              ].map(({ id, label }) => (
                <div key={id} className="space-y-2">
                  <Label htmlFor={id}>{label}</Label>
                  <Input
                    id={id}
                    type="password"
                    value={formData[id as keyof typeof formData] || ""}
                    onChange={(e) => setFormData({ [id]: e.target.value })}
                    placeholder="********"
                  />
                </div>
              ))}

              <div className="space-y-2 col-span-1 md:col-span-2">
                <Label>Função no Sistema</Label>
                <Select
                  value={formData.role || ""}
                  onValueChange={(v) => setFormData({ role: v })}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione a função" />
                  </SelectTrigger>
                  <SelectContent>
                    {ROLES.map((r) => (
                      <SelectItem key={r} value={r}>
                        {r}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
            </div>

            {/* Campos Condicionais */}
            {formData.role === "Paciente" && (
              <div className="mt-4 space-y-2">
                <div className="space-y-2">
                  <Label htmlFor="profession">Profissão</Label>
                  <Input
                    id="profession"
                    value={formData.profession || ""}
                    onChange={(e) =>
                      setFormData({ profession: e.target.value })
                    }
                    placeholder="Profissão do paciente"
                  />
                </div>
                <div className="space-y-2">
                  <Label>Preferência de Contato</Label>
                  <Select
                    value={formData.preferencia_contato || ""}
                    onValueChange={(v) =>
                      setFormData({ preferencia_contato: v })
                    }
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Selecione a preferência" />
                    </SelectTrigger>
                    <SelectContent>
                      {["Email", "Telefone", "WhatsApp"].map((p) => (
                        <SelectItem key={p} value={p}>
                          {p}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>
            )}

            {["Funcionário", "Profissional de Saúde"].includes(
              formData.role || ""
            ) && (
              <div className="mt-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                {[
                  {
                    id: "matricula",
                    label: "Matrícula",
                    placeholder: "Nº da Matrícula",
                  },
                  {
                    id: "cargo",
                    label: "Cargo",
                    placeholder: "Ex: Médico(a), Recepcionista",
                  },
                ].map(({ id, label, placeholder }) => (
                  <div key={id} className="space-y-2">
                    <Label htmlFor={id}>{label}</Label>
                    <Input
                      id={id}
                      value={formData[id as keyof typeof formData] || ""}
                      onChange={(e) => setFormData({ [id]: e.target.value })}
                      placeholder={placeholder}
                    />
                  </div>
                ))}
              </div>
            )}

            {formData.role === "Funcionário" && (
              <div className="mt-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                {[
                  {
                    id: "setor",
                    label: "Setor",
                    placeholder: "Setor de trabalho",
                  },
                  {
                    id: "observacoes",
                    label: "Observações",
                    placeholder: "Observações adicionais",
                  },
                ].map(({ id, label, placeholder }) => (
                  <div key={id} className="space-y-2">
                    <Label htmlFor={id}>{label}</Label>
                    <Input
                      id={id}
                      value={formData[id as keyof typeof formData] || ""}
                      onChange={(e) => setFormData({ [id]: e.target.value })}
                      placeholder={placeholder}
                    />
                  </div>
                ))}
              </div>
            )}

            {formData.role === "Profissional de Saúde" && (
              <div className="mt-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                {[
                  {
                    id: "unidade",
                    label: "Unidade",
                    placeholder: "Unidade de atuação",
                  },
                  {
                    id: "specialty",
                    label: "Especialidade",
                    placeholder: "Especialidade médica",
                  },
                  {
                    id: "conselho",
                    label: "Conselho Profissional",
                    placeholder: "Ex: CRM, COREN",
                  },
                  {
                    id: "registroConselho",
                    label: "Nº do Conselho",
                    placeholder: "123456-PE",
                  },
                ].map(({ id, label, placeholder }) => (
                  <div key={id} className="space-y-2">
                    <Label htmlFor={id}>{label}</Label>
                    <Input
                      id={id}
                      value={formData[id as keyof typeof formData] || ""}
                      onChange={(e) => setFormData({ [id]: e.target.value })}
                      placeholder={placeholder}
                    />
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* BOTÕES */}
        <div className="flex justify-end gap-3 pt-4 border-t mt-4">
          <Button variant="outline" onClick={() => onOpenChange(false)}>
            Cancelar
          </Button>
          <Button
            disabled={loading}
            onClick={onSave}
            className="bg-red-600 hover:bg-red-700 text-white"
          >
            {loading ? "Salvando..." : editingUser ? "Atualizar" : "Criar"}
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
