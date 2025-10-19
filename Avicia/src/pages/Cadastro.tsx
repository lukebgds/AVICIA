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
  Eye,
  EyeOff,
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";
const Cadastro = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

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

  // Erros inline (genérico para todos os campos)
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  // --- Helpers ---
  const clearError = (field: string) => {
    setErrors((prev) => {
      const next = { ...prev };
      delete next[field];
      return next;
    });
  };

  const setTemporaryError = (
    field: string,
    message: string,
    duration = 4000
  ) => {
    setErrors((prev) => ({ ...prev, [field]: message }));
    setTimeout(() => clearError(field), duration);
  };

  const formatCPF = (value: string) => {
    const numeric = value.replace(/\D/g, "").slice(0, 11);
    if (numeric.length < 4) return numeric;
    if (numeric.length < 7) return numeric.replace(/^(\d{3})(\d+)/, "$1.$2");
    if (numeric.length <= 11)
      return numeric
        .replace(/^(\d{3})(\d{3})(\d+)/, "$1.$2.$3")
        .replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d{1,2})$/, "$1.$2.$3-$4");
    return numeric;
  };

  // Função para validar todos os campos e definir erros inline (chamada no submit)
  const validateAllFields = () => {
    // Limpa erros existentes antes de validar
    setErrors({});

    let hasError = false;

    const required = [
      "name",
      "email",
      "password",
      "confirmPassword",
      "cpf",
      "telefone",
      "endereco",
      "dataNascimento",
      "sexo",
      "estadoCivil",
      "profissao",
    ] as const;

    // Verifica campos vazios e define erros temporários
    for (const field of required) {
      const value = formData[field as keyof typeof formData];
      if (!value?.toString().trim()) {
        setTemporaryError(field, "Preencha este campo.");
        hasError = true;
      }
    }

    // Validações específicas (apenas se não vazio)
    const nextErrors: { [key: string]: string } = {};

    if (formData.cpf.trim()) {
      const cpfDigits = formData.cpf.replace(/\D/g, "");
      if (cpfDigits.length !== 11) {
        nextErrors.cpf = "CPF deve conter exatamente 11 números.";
        hasError = true;
      }
    }

    if (formData.telefone.trim()) {
      const telefoneDigits = formData.telefone.replace(/\D/g, "");
      if (telefoneDigits.length !== 11) {
        nextErrors.telefone = "Telefone deve conter exatamente 11 números.";
        hasError = true;
      }
    }

    if (formData.password.trim()) {
      if (formData.password.length < 8) {
        nextErrors.password = "A senha deve ter pelo menos 8 caracteres.";
        hasError = true;
      }
    }

    if (formData.confirmPassword.trim()) {
      if (formData.confirmPassword !== formData.password) {
        nextErrors.confirmPassword = "As senhas não coincidem.";
        hasError = true;
      }
    }

    // Aplica os erros específicos (permanentes)
    if (Object.keys(nextErrors).length > 0) {
      setErrors((prev) => ({ ...prev, ...nextErrors }));
    }

    // Retorna se válido
    return !hasError;
  };

  // --- Handlers ---
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;

    if (id === "cpf") {
      const formatted = formatCPF(value);
      setFormData((prev) => ({ ...prev, cpf: formatted }));
      clearError("cpf");
      return;
    }

    if (id === "telefone") {
      const numeric = value.replace(/\D/g, "").slice(0, 11);
      const formatted = numeric
        .replace(/^(\d{2})(\d)/g, "($1) $2")
        .replace(/(\d{5})(\d{4})$/, "$1-$2");
      setFormData((prev) => ({ ...prev, telefone: formatted }));
      clearError("telefone");
      return;
    }

    setFormData((prev) => ({ ...prev, [id]: value }));
    clearError(id);

    // Limpa erro de confirmPassword se senha foi alterada
    if (id === "password") {
      clearError("confirmPassword");
    }
  };

  const handleSelectChange = (field: string, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
    clearError(field);
  };

  const handleBlur = (field: string) => {
    const value = formData[field as keyof typeof formData];

    // SEMPRE checa se vazio PRIMEIRO (para TODOS os campos)
    if (!value?.toString().trim()) {
      setTemporaryError(field, "Preencha este campo.");
      return;
    }

    // Se não vazio, checa validações específicas (só para campos críticos)
    const next = { ...errors };

    if (field === "cpf") {
      const digits = value.toString().replace(/\D/g, "");
      if (digits.length !== 11)
        next.cpf = "CPF deve conter exatamente 11 números.";
      else delete next.cpf;
    } else if (field === "telefone") {
      const digits = value.toString().replace(/\D/g, "");
      if (digits.length !== 11)
        next.telefone = "Telefone deve conter exatamente 11 números.";
      else delete next.telefone;
    } else if (field === "password") {
      if (value.toString().length < 8)
        next.password = "A senha deve ter pelo menos 8 caracteres.";
      else delete next.password;
    } else if (field === "confirmPassword") {
      if (value.toString() !== formData.password)
        next.confirmPassword = "As senhas não coincidem.";
      else delete next.confirmPassword;
    } else {
      // Para outros campos não vazios, limpa (sem validação extra)
      delete next[field];
    }

    setErrors(next);
  };

  // --- Validação completa antes do submit (com toast se inválido) ---
  const validateForm = (): boolean => {
    const isValid = validateAllFields();
    if (!isValid) {
      toast({
        title: "Campos obrigatórios",
        description:
          "Preencha todos os campos obrigatórios e corrija os erros.",
        variant: "destructive",
      });
    }
    return isValid;
  };

  // --- Submit para API ---
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return; // Impede cadastro se inválido, com toast

    setLoading(true);
    try {
      const role = await api.getRoleByName("PACIENTE");
      const idRole = role.idRole;

      // CPF como número inteiro (sem formatação)
      const cpfNumerico = formData.cpf.replace(/\D/g, "");

      // Telefone como número inteiro (sem formatação)
      const telefoneNumerico = formData.telefone.replace(/\D/g, "");

      const usuarioData = {
        nome: formData.name.trim(),
        cpf: cpfNumerico, // Enviado como "12345678901"
        dataNascimento: formData.dataNascimento,
        sexo:
          formData.sexo === "M"
            ? "MASCULINO"
            : formData.sexo === "F"
            ? "FEMININO"
            : "OUTRO",
        estadoCivil: formData.estadoCivil
          ? formData.estadoCivil.toLowerCase().replace("(a)", "")
          : "",
        email: formData.email,
        senha: formData.password,
        telefone: telefoneNumerico, // Enviado como "81996378721"
        endereco: formData.endereco,
        ativo: true,
        mfaHabilitado: false,
        dataCriacao: new Date().toISOString().split("T")[0],
        idRole,
      };

      const usuarioCriado = await api.criarUsuario(usuarioData);

      await api.criarPaciente({
        idUsuario: usuarioCriado.idUsuario,
        profissao: formData.profissao,
        preferenciaContato: "EMAIL",
      });

      toast({
        title: "Cadastro realizado com sucesso!",
      });

      navigate("/login");
    } catch (error: any) {
      console.error("Erro no cadastro:", error);
      toast({
        title: "Erro no cadastro",
        description:
          error?.response?.data?.message || "Erro inesperado. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-gray-50 to-blue-100 flex items-center justify-center p-4 sm:p-6">
      <div className="w-full max-w-lg flex flex-col items-center gap-6">
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
            <CardTitle className="text-3xl font-semibold text-blue-700 flex items-center justify-center gap-2 relative right-4">
              <User className="h-7 w-7" /> Criar Conta
            </CardTitle>
          </CardHeader>

          <CardContent className="px-9 py-4">
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Nome */}
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
                    value={formData.name}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("name")}
                  />
                </div>
                {errors.name && (
                  <p className="text-xs text-red-500 mt-1">{errors.name}</p>
                )}
              </div>

              {/* Email */}
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
                    value={formData.email}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("email")}
                  />
                </div>
                {errors.email && (
                  <p className="text-xs text-red-500 mt-1">{errors.email}</p>
                )}
              </div>

              {/* Senhas */}
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
                      type={showPassword ? "text" : "password"}
                      placeholder="Digite sua senha"
                      className="pl-10 pr-10 py-3 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      value={formData.password}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("password")}
                    />
                    <button
                      type="button"
                      className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                      onClick={() => setShowPassword(!showPassword)}
                    >
                      {showPassword ? (
                        <Eye className="h-4 w-4" />
                      ) : (
                        <EyeOff className="h-4 w-4" />
                      )}
                    </button>
                  </div>
                  {errors.password && (
                    <p className="text-xs text-red-500 mt-1">
                      {errors.password}
                    </p>
                  )}
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
                      type={showConfirmPassword ? "text" : "password"}
                      placeholder="Confirme sua senha"
                      className="pl-10 pr-10 py-3 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      value={formData.confirmPassword}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("confirmPassword")}
                    />
                    <button
                      type="button"
                      className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                      onClick={() =>
                        setShowConfirmPassword(!showConfirmPassword)
                      }
                    >
                      {showConfirmPassword ? (
                        <Eye className="h-4 w-4" />
                      ) : (
                        <EyeOff className="h-4 w-4" />
                      )}
                    </button>
                  </div>
                  {errors.confirmPassword && (
                    <p className="text-xs text-red-500 mt-1">
                      {errors.confirmPassword}
                    </p>
                  )}
                </div>
              </div>

              {/* CPF e Telefone */}
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
                    value={formData.cpf}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("cpf")}
                  />
                  {errors.cpf && (
                    <p className="text-xs text-red-500 mt-1">{errors.cpf}</p>
                  )}
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
                      value={formData.telefone}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("telefone")}
                    />
                  </div>
                  {errors.telefone && (
                    <p className="text-xs text-red-500 mt-1">
                      {errors.telefone}
                    </p>
                  )}
                </div>
              </div>

              {/* Endereço */}
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
                    value={formData.endereco}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("endereco")}
                  />
                </div>
                {errors.endereco && (
                  <p className="text-xs text-red-500 mt-1">{errors.endereco}</p>
                )}
              </div>

              {/* Data de Nascimento e Sexo */}
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
                      value={formData.dataNascimento}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("dataNascimento")}
                    />
                  </div>
                  {errors.dataNascimento && (
                    <p className="text-xs text-red-500 mt-1">
                      {errors.dataNascimento}
                    </p>
                  )}
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
                  >
                    <SelectTrigger
                      className="py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      onBlur={() => handleBlur("sexo")}
                    >
                      <SelectValue placeholder="Selecione o sexo" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="M">Masculino</SelectItem>
                      <SelectItem value="F">Feminino</SelectItem>
                      <SelectItem value="Outro">Outro</SelectItem>
                    </SelectContent>
                  </Select>
                  {errors.sexo && (
                    <p className="text-xs text-red-500 mt-1">{errors.sexo}</p>
                  )}
                </div>
              </div>

              {/* Estado Civil e Profissão */}
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
                  >
                    <SelectTrigger
                      className="py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                      onBlur={() => handleBlur("estadoCivil")}
                    >
                      <SelectValue placeholder="Selecione o estado civil" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="solteiro">Solteiro(a)</SelectItem>
                      <SelectItem value="casado">Casado(a)</SelectItem>
                      <SelectItem value="divorciado">Divorciado(a)</SelectItem>
                      <SelectItem value="viuvo">Viúvo(a)</SelectItem>
                    </SelectContent>
                  </Select>
                  {errors.estadoCivil && (
                    <p className="text-xs text-red-500 mt-1">
                      {errors.estadoCivil}
                    </p>
                  )}
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
                      value={formData.profissao}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("profissao")}
                    />
                  </div>
                  {errors.profissao && (
                    <p className="text-xs text-red-500 mt-1">
                      {errors.profissao}
                    </p>
                  )}
                </div>
              </div>

              {/* Botão */}
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
