import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Checkbox } from "@/components/ui/checkbox";
import { useToast } from "@/hooks/use-toast";
import { Shield, ArrowLeft, Save } from "lucide-react";

// Interfaces
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

const initialFormData = {
  name: "",
  type: "",
  description: "",
  permissions: initialPermissions,
};

const RoleRegistration = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [formData, setFormData] = useState(initialFormData);

  const handlePermissionChange = (
    permissionId: string,
    action: keyof PermissionActions
  ) => {
    setFormData((prev) => {
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

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!formData.name || !formData.description) {
      toast({
        title: "Erro de Validação",
        description: "Por favor, preencha todos os campos obrigatórios.",
        variant: "destructive",
      });
      return;
    }

    console.log("Dados da Role para salvar:", JSON.stringify(formData, null, 2));
    toast({
      title: "Sucesso!",
      description: `A role "${formData.name}" foi cadastrada com sucesso.`,
    });
  };

  return (
    <div className="min-h-screen bg-red-50/50 p-4 sm:p-6 lg:p-8">
      <div className="max-w-4xl mx-auto space-y-6">
        {/* Cabeçalho */}
        <div className="flex items-center gap-4">
          <Button
            variant="outline"
            size="icon"
            onClick={() => navigate(-1)}
            className="h-10 w-10"
          >
            <ArrowLeft className="h-5 w-5" />
          </Button>
          <div>
            <h1 className="text-3xl font-bold flex items-center gap-3 text-red-800">
              <div className="bg-red-600 p-2 rounded-lg">
                <Shield className="h-6 w-6 text-white" />
              </div>
              Cadastro de Perfis (Roles)
            </h1>
            <p className="text-gray-500 mt-1">
              Crie um novo perfil de acesso e defina suas permissões no sistema.
            </p>
          </div>
        </div>

        <form onSubmit={handleSubmit}>
          <Card>
            <CardHeader>
              <CardTitle className="text-gray-700">Informações do Perfil</CardTitle>
              <CardDescription>
                Defina os detalhes e as permissões para o novo perfil de acesso.
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="space-y-2">
                <Label htmlFor="name" className="font-medium">
                  Nome do Perfil *
                </Label>
                <Input
                  id="name"
                  placeholder="Ex: Administrador, Recepcionista"
                  value={formData.name}
                  onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="description" className="font-medium">
                  Descrição *
                </Label>
                <Textarea
                  id="description"
                  placeholder="Descreva a finalidade e as responsabilidades deste perfil..."
                  value={formData.description}
                  onChange={(e) =>
                    setFormData({ ...formData, description: e.target.value })
                  }
                  rows={3}
                />
              </div>

              <div className="space-y-4">
                <Label className="text-base font-semibold text-gray-800">
                  Permissões *
                </Label>
                <div className="space-y-6">
                  {permissionGroups.map((group) => (
                    <Card key={group.title} className="p-4 bg-gray-50/50">
                      <h3 className="text-lg font-semibold text-red-800 mb-2">
                        {group.title}
                      </h3>
                      <div className="space-y-2">
                        {group.permissions.map((permission) => (
                          <div
                            key={permission.id}
                            className="p-3 border-t first:border-t-0 md:flex md:items-center md:justify-between gap-4"
                          >
                            <div className="flex-1 mb-3 md:mb-0">
                              <p className="font-medium text-gray-800">
                                {permission.label}
                              </p>
                              <p className="text-xs text-gray-500 mt-1">
                                {permission.description}
                              </p>
                            </div>

                            <div className="flex items-center gap-4">
                              {actions.map((action) => (
                                <div key={action.id} className="flex items-center space-x-2">
                                  <Checkbox
                                    id={`${permission.id}-${action.id}`}
                                    checked={formData.permissions[permission.id][action.id]}
                                    onCheckedChange={() => handlePermissionChange(permission.id, action.id)}
                                  />
                                  <label
                                    htmlFor={`${permission.id}-${action.id}`}
                                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                  >
                                    {action.label}
                                  </label>
                                </div>
                              ))}
                            </div>
                          </div>
                        ))}
                      </div>
                    </Card>
                  ))}
                </div>
              </div>

              <div className="flex gap-3 justify-end pt-4 border-t">
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => navigate(-1)}
                >
                  Cancelar
                </Button>
                <Button
                  type="submit"
                  className="bg-red-600 text-white hover:bg-red-700 flex items-center gap-2"
                >
                  <Save className="h-4 w-4" />
                  Salvar Perfil
                </Button>
              </div>
            </CardContent>
          </Card>
        </form>
      </div>
    </div>
  );
};

export default RoleRegistration;