import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
import {
  User,
  Mail,
  Lock,
  Stethoscope,
  Phone,
  Calendar,
  MapPin,
  Briefcase,
  Building,
  ArrowLeft,
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const Cadastro = () => {
  const { toast } = useToast();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    cpf: "",
    telefone: "",
    endereco: "",
    dataNascimento: "",
    sexo: "",
    estadoCivil: "",
    profissao: "",
  });
  const [loading, setLoading] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData((prev) => ({ ...prev, [id]: value }));
  };

  const handleSelectChange = (field: string, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      toast({
        title: "Erro",
        description: "As senhas não coincidem",
        variant: "destructive",
      });
      return;
    }

    setLoading(true);
    try {
      console.log("📋 Dados do form:", formData);

      const role = await api.getRoleByName("PACIENTE");
      const idRole = role.idRole;
      console.log("🔍 Role PACIENTE encontrada:", { idRole });

      const nomeCompleto = formData.name.trim();
      const ultimoEspaco = nomeCompleto.lastIndexOf(" ");
      const nome =
        ultimoEspaco > 0
          ? nomeCompleto.substring(0, ultimoEspaco)
          : nomeCompleto;
      const sobrenome =
        ultimoEspaco > 0 ? nomeCompleto.substring(ultimoEspaco + 1) : "";

      const usuarioData = {
        nome,
        sobrenome,
        cpf: formData.cpf.replace(/\D/g, ""),
        email: formData.email,
        senha: formData.password,
        telefone: formData.telefone,
        ativo: true,
        mfaHabilitado: false,
        dataCriacao: new Date().toISOString().split("T")[0],
        idRole: idRole,
      };

      const usuarioCriado = await api.criarUsuario(usuarioData);
      const idUsuario = usuarioCriado.idUsuario;
      console.log("👤 Usuário criado:", { idUsuario });

      const pacienteData = {
        idUsuario: idUsuario,
        dataNascimento: formData.dataNascimento,
        sexo:
          formData.sexo === "M"
            ? "MASCULINO"
            : formData.sexo === "F"
            ? "FEMININO"
            : "OUTRO",
        estadoCivil: formData.estadoCivil,
        profissao: formData.profissao,
        endereco: formData.endereco,
        preferenciaContato: "EMAIL",
      };

      const pacienteCriado = await api.criarPaciente(pacienteData);
      console.log("🏥 Paciente criado:", {
        idPaciente: pacienteCriado.idPaciente,
      });

      toast({
        title: "Cadastro realizado com sucesso!",
        description: `Bem-vindo, ${usuarioCriado.nome}! Seu ID é ${pacienteCriado.idPaciente}`,
      });
      navigate("/login");
    } catch (error: any) {
      console.error("❌ Erro detalhado:", error);
      toast({
        title: "Erro no cadastro",
        description: error.message || "Tente novamente mais tarde",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-gray-50 to-blue-100 flex items-center justify-center p-4 sm:p-6">
      <div className="w-full max-w-md flex flex-col items-center gap-6">
        <div className="flex flex-col items-center text-center">
          <div className="bg-blue-600 rounded-full p-3 shadow-lg transform transition-transform hover:scale-105 mb-4">
            <Stethoscope className="h-10 w-10 text-white" />
          </div>
          <h1 className="text-5xl font-extrabold bg-gradient-to-r from-blue-600 to-teal-500 bg-clip-text text-transparent tracking-tight leading-tight">
            AVICIA
          </h1>
          <p className="text-gray-600 mt-1 text-lg font-medium leading-relaxed">
            Crie sua conta em minutos
          </p>
        </div>

        <Card className="shadow-2xl border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-2xl w-full">
          <CardHeader className="text-center py-4">
            <CardTitle className="text-3xl font-semibold text-blue-700 flex items-center justify-center gap-2">
              <User className="h-6 w-6" /> Criar Conta
            </CardTitle>
          </CardHeader>

          <CardContent className="px-8 py-4">
            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="space-y-1">
                <Label
                  htmlFor="name"
                  className="text-sm font-medium text-gray-700"
                >
                  Nome Completo
                </Label>
                <div className="relative group">
                  <User className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="name"
                    placeholder="Digite seu nome completo"
                    className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                    required
                    value={formData.name}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="space-y-1">
                <Label
                  htmlFor="email"
                  className="text-sm font-medium text-gray-700"
                >
                  E-mail
                </Label>
                <div className="relative group">
                  <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="email"
                    type="email"
                    placeholder="seu@email.com"
                    className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                    required
                    value={formData.email}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="password"
                    className="text-sm font-medium text-gray-700"
                  >
                    Senha
                  </Label>
                  <div className="relative group">
                    <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="password"
                      type="password"
                      placeholder="Digite sua senha"
                      className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      required
                      value={formData.password}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>

                <div className="space-y-1">
                  <Label
                    htmlFor="confirmPassword"
                    className="text-sm font-medium text-gray-700"
                  >
                    Confirmar Senha
                  </Label>
                  <div className="relative group">
                    <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="confirmPassword"
                      type="password"
                      placeholder="Confirme sua senha"
                      className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      required
                      value={formData.confirmPassword}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="cpf"
                    className="text-sm font-medium text-gray-700"
                  >
                    CPF
                  </Label>
                  <Input
                    id="cpf"
                    placeholder="000.000.000-00"
                    className="py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                    required
                    value={formData.cpf}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="space-y-1">
                  <Label
                    htmlFor="telefone"
                    className="text-sm font-medium text-gray-700"
                  >
                    Telefone
                  </Label>
                  <div className="relative group">
                    <Phone className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="telefone"
                      placeholder="(00) 00000-0000"
                      className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      required
                      value={formData.telefone}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>
              </div>

              <div className="space-y-1">
                <Label
                  htmlFor="endereco"
                  className="text-sm font-medium text-gray-700"
                >
                  Endereço
                </Label>
                <div className="relative group">
                  <MapPin className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="endereco"
                    placeholder="Digite seu endereço completo"
                    className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                    required
                    value={formData.endereco}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="dataNascimento"
                    className="text-sm font-medium text-gray-700"
                  >
                    Data de Nascimento
                  </Label>
                  <div className="relative group">
                    <Calendar className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="dataNascimento"
                      type="date"
                      className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      required
                      value={formData.dataNascimento}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>

                <div className="space-y-1">
                  <Label
                    htmlFor="sexo"
                    className="text-sm font-medium text-gray-700"
                  >
                    Sexo
                  </Label>
                  <Select
                    value={formData.sexo}
                    onValueChange={(value) => handleSelectChange("sexo", value)}
                    required
                  >
                    <SelectTrigger className="py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all">
                      <SelectValue placeholder="Selecione o sexo" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="M">Masculino</SelectItem>
                      <SelectItem value="F">Feminino</SelectItem>
                      <SelectItem value="Outro">Outro</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="estadoCivil"
                    className="text-sm font-medium text-gray-700"
                  >
                    Estado Civil
                  </Label>
                  <Select
                    value={formData.estadoCivil}
                    onValueChange={(value) =>
                      handleSelectChange("estadoCivil", value)
                    }
                    required
                  >
                    <SelectTrigger className="py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all">
                      <SelectValue placeholder="Selecione o estado civil" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="solteiro">Solteiro(a)</SelectItem>
                      <SelectItem value="casado">Casado(a)</SelectItem>
                      <SelectItem value="divorciado">Divorciado(a)</SelectItem>
                      <SelectItem value="viuvo">Viúvo(a)</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-1">
                  <Label
                    htmlFor="profissao"
                    className="text-sm font-medium text-gray-700"
                  >
                    Profissão
                  </Label>
                  <div className="relative group">
                    <Briefcase className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="profissao"
                      placeholder="Digite sua profissão"
                      className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      required
                      value={formData.profissao}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>
              </div>

              <div className="mt-8">
                <Button
                  type="submit"
                  disabled={loading}
                  className="w-full bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white py-3 rounded-lg font-semibold shadow-md hover:shadow-lg transition-all duration-300"
                >
                  {loading ? "Criando..." : "Criar Conta"}
                </Button>
              </div>

              <div className="text-center mt-6">
                <p className="text-gray-600 text-sm">
                  Já possui uma conta?{" "}
                  <button
                    onClick={() => navigate("/login")}
                    className="text-blue-600 font-medium hover:underline hover:text-blue-700 transition-colors"
                  >
                    Fazer login
                  </button>
                </p>
              </div>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Cadastro;
