import { useState, useEffect } from "react";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
import {
  Eye,
  EyeOff,
  AlertCircle,
  User,
  FileText,
  Phone,
  Mail,
  Lock,
  MapPin,
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

// Caminhos para os assets na pasta /public
import AviciaLogo from "/logo.svg";
import CadastroIllustration from "/CadastroIllustration.svg";

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
    concordoTermos: false,
  });

  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  // --- ANIMAÇÃO: estado para entrada suave ---
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => setIsMounted(true), 50);
    return () => clearTimeout(timer);
  }, []);

  // --- Helpers ---
  const clearError = (field: string) => {
    setErrors((prev) => {
      const next = { ...prev };
      delete next[field];
      return next;
    });
  };

  const isValidPassword = (password: string): boolean => {
    if (password.length < 8) return false;
    if (!/[A-Z]/.test(password)) return false;
    if (!/[a-z]/.test(password)) return false;
    if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password)) return false;
    return true;
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

  const validateAllFields = () => {
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
      "concordoTermos",
    ] as const;

    const nextErrors: { [key: string]: string } = {};
    for (const field of required) {
      const value = formData[field as keyof typeof formData];
      if (field === "concordoTermos") {
        if (!value) {
          nextErrors[field] = "Você deve concordar com os termos e políticas";
          hasError = true;
        }
      } else if (!value?.toString().trim()) {
        nextErrors[field] = "Preencha este campo";
        hasError = true;
      }
    }

    if (formData.cpf.trim()) {
      const cpfDigits = formData.cpf.replace(/\D/g, "");
      if (cpfDigits.length !== 11) {
        nextErrors.cpf = "CPF deve conter exatamente 11 números";
        hasError = true;
      }
    }

    if (formData.telefone.trim()) {
      const telefoneDigits = formData.telefone.replace(/\D/g, "");
      if (telefoneDigits.length !== 11) {
        nextErrors.telefone = "Telefone deve conter exatamente 11 números";
        hasError = true;
      }
    }

    if (formData.password.trim()) {
      if (!isValidPassword(formData.password)) {
        nextErrors.password =
          "Senha: ≥8 caracteres (1 maiúscula, 1 minúscula, 1 especial)";
        hasError = true;
      }
    }

    if (formData.confirmPassword.trim()) {
      if (formData.confirmPassword !== formData.password) {
        nextErrors.confirmPassword = "As senhas não coincidem";
        hasError = true;
      }
    }

    if (Object.keys(nextErrors).length > 0) {
      setErrors(nextErrors);
    }

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

    if (!value?.toString().trim()) {
      setErrors((prev) => ({ ...prev, [field]: "Preencha este campo" }));
      return;
    }

    const next = { ...errors };

    if (field === "cpf") {
      const digits = value.toString().replace(/\D/g, "");
      if (digits.length !== 11)
        next.cpf = "CPF deve conter exatamente 11 números";
      else delete next.cpf;
    } else if (field === "telefone") {
      const digits = value.toString().replace(/\D/g, "");
      if (digits.length !== 11)
        next.telefone = "Telefone deve conter exatamente 11 números";
      else delete next.telefone;
    } else if (field === "password") {
      if (!isValidPassword(value.toString()))
        next.password =
          "Senha: ≥8 caracteres (1 maiúscula, 1 minúscula, 1 especial)";
      else delete next.password;
    } else if (field === "confirmPassword") {
      if (value.toString() !== formData.password)
        next.confirmPassword = "As senhas não coincidem";
      else delete next.confirmPassword;
    } else if (field === "dataNascimento") {
      const rawValue = formData.dataNascimento.toString();
      const parts = rawValue.split("/");
      if (parts.length === 3 && parts.every((p) => /^\d+$/.test(p))) {
        const day = parts[0].padStart(2, "0");
        const month = parts[1].padStart(2, "0");
        const year = parts[2].padStart(4, "0");
        const formatted = `${year}-${month}-${day}`;
        setFormData((prev) => ({ ...prev, dataNascimento: formatted }));
      }
      delete next.dataNascimento;
    } else {
      delete next[field];
    }

    setErrors(next);
  };

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData((prev) => ({ ...prev, concordoTermos: e.target.checked }));
    clearError("concordoTermos");
  };

  const validateForm = (): boolean => {
    const isValid = validateAllFields();
    if (!isValid) {
      toast({
        title: "Campos obrigatórios",
        description: "Preencha todos os campos obrigatórios e corrija os erros",
        variant: "destructive",
      });
    }
    return isValid;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);
    try {
      const role = await api.getRoleByName("PACIENTE");
      const idRole = role.idRole;

      const cpfNumerico = formData.cpf.replace(/\D/g, "");
      const telefoneNumerico = formData.telefone.replace(/\D/g, "");

      const usuarioData = {
        nome: formData.name.trim(),
        cpf: cpfNumerico,
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
        telefone: telefoneNumerico,
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

      toast({ title: "Cadastro realizado com sucesso!" });
      navigate("/login");
    } catch (error: any) {
      console.error("Erro no cadastro:", error);
      setFormData((prev) => ({
        ...prev,
        password: "",
        confirmPassword: "",
      }));
      setShowPassword(false);
      setShowConfirmPassword(false);
      toast({
        title: "Erro no cadastro",
        description: "Erro inesperado. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="flex min-h-screen">
      {/* Coluna da Esquerda: Imagem de Fundo e Logo */}
      <div
        className="relative flex flex-col w-[60%] p-10 max-md:hidden"
        style={{
          background:
            "linear-gradient(180deg, #0575E6 0%, #02298A 84.79%, #021B79 100%)",
        }}
      >
        <img
          src={AviciaLogo}
          alt="AVICia Logo"
          className="w-36 object-contain z-10"
        />
        <div className="flex-grow flex items-center justify-center px-8">
          <img
            src={CadastroIllustration}
            alt="Ilustração de Cadastro"
            className="max-h-[700px] w-auto object-contain"
          />
        </div>
      </div>

      {/* Coluna da Direita: Formulário */}
      <div className="flex flex-col items-center justify-center w-[40%] p-10 bg-white max-md:w-full max-md:p-5 min-h-screen">
        <div className="hidden max-md:block mb-8">
          <img
            src={AviciaLogo}
            alt="AVICia Logo"
            className="w-28 object-contain"
          />
        </div>

        <div className="max-w-[480px] w-full px-4 md:px-0">
          <div
            className={`
              transition-all duration-300 ease-out
              ${
                isMounted
                  ? "opacity-100 translate-y-0 filter blur-0"
                  : "opacity-0 translate-y-5 filter blur-[3px]"
              }
            `}
          >
            <header className="mb-6">
              <h1 className="text-[#1E255E] text-[26px] font-semibold mb-2">
                Faça seu cadastro:
              </h1>
              <p className="text-[#44494F] text-lg font-normal">
                Vamos preparar tudo para que você possa acessar sua conta
                pessoal.
              </p>
            </header>

            <form onSubmit={handleSubmit} noValidate className="space-y-5">
              {/* === NOME COMPLETO === */}
              <div className="space-y-1">
                <Label
                  htmlFor="name"
                  className="text-sm font-medium text-gray-700"
                >
                  Nome completo:
                </Label>
                <div className="relative group">
                  <User className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="name"
                    placeholder="Digite seu nome completo"
                    value={formData.name}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("name")}
                    className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                      errors.name ? "border-red-500" : ""
                    }`}
                  />
                </div>
                {errors.name && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {errors.name}
                  </p>
                )}
              </div>

              {/* === CPF + TELEFONE === */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="cpf"
                    className="text-sm font-medium text-gray-700"
                  >
                    CPF:
                  </Label>
                  <div className="relative group">
                    <FileText className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="cpf"
                      placeholder="000.000.000-00"
                      value={formData.cpf}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("cpf")}
                      className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                        errors.cpf ? "border-red-500" : ""
                      }`}
                    />
                  </div>
                  {errors.cpf && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.cpf}
                    </p>
                  )}
                </div>

                <div className="space-y-1">
                  <Label
                    htmlFor="telefone"
                    className="text-sm font-medium text-gray-700"
                  >
                    Telefone:
                  </Label>
                  <div className="relative group">
                    <Phone className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="telefone"
                      placeholder="(00) 00000-0000"
                      value={formData.telefone}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("telefone")}
                      className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                        errors.telefone ? "border-red-500" : ""
                      }`}
                    />
                  </div>
                  {errors.telefone && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.telefone}
                    </p>
                  )}
                </div>
              </div>

              {/* === E-MAIL === */}
              <div className="space-y-1">
                <Label
                  htmlFor="email"
                  className="text-sm font-medium text-gray-700"
                >
                  E-mail:
                </Label>
                <div className="relative group">
                  <Mail className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="email"
                    type="text"
                    placeholder="Insira seu e-mail"
                    value={formData.email}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("email")}
                    className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                      errors.email ? "border-red-500" : ""
                    }`}
                  />
                </div>
                {errors.email && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {errors.email}
                  </p>
                )}
              </div>

              {/* === SENHA + CONFIRMAR === */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="password"
                    className="text-sm font-medium text-gray-700"
                  >
                    Senha:
                  </Label>
                  <div className="relative group">
                    <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="password"
                      type={showPassword ? "text" : "password"}
                      placeholder="Digite sua senha"
                      value={formData.password}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("password")}
                      maxLength={30}
                      className={`pl-10 pr-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                        errors.password ? "border-red-500" : ""
                      }`}
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                    >
                      {showPassword ? (
                        <Eye className="h-5 w-5" />
                      ) : (
                        <EyeOff className="h-5 w-5" />
                      )}
                    </button>
                  </div>
                  {errors.password && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.password}
                    </p>
                  )}
                </div>

                <div className="space-y-1">
                  <Label
                    htmlFor="confirmPassword"
                    className="text-sm font-medium text-gray-700"
                  >
                    Confirmar senha:
                  </Label>
                  <div className="relative group">
                    <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="confirmPassword"
                      type={showConfirmPassword ? "text" : "password"}
                      placeholder="Confirme sua senha"
                      value={formData.confirmPassword}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("confirmPassword")}
                      maxLength={30}
                      className={`pl-10 pr-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                        errors.confirmPassword ? "border-red-500" : ""
                      }`}
                    />
                    <button
                      type="button"
                      onClick={() =>
                        setShowConfirmPassword(!showConfirmPassword)
                      }
                      className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                    >
                      {showConfirmPassword ? (
                        <Eye className="h-5 w-5" />
                      ) : (
                        <EyeOff className="h-5 w-5" />
                      )}
                    </button>
                  </div>
                  {errors.confirmPassword && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.confirmPassword}
                    </p>
                  )}
                </div>
              </div>

              {/* === ENDEREÇO === */}
              <div className="space-y-1">
                <Label
                  htmlFor="endereco"
                  className="text-sm font-medium text-gray-700"
                >
                  Endereço:
                </Label>
                <div className="relative group">
                  <MapPin className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="endereco"
                    placeholder="Digite seu endereço completo"
                    value={formData.endereco}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("endereco")}
                    className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                      errors.endereco ? "border-red-500" : ""
                    }`}
                  />
                </div>
                {errors.endereco && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {errors.endereco}
                  </p>
                )}
              </div>

              {/* === DATA + SEXO === */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="dataNascimento"
                    className="text-sm font-medium text-gray-700"
                  >
                    Data de nascimento:
                  </Label>
                  <div className="relative group">
                    <User className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors pointer-events-none" />
                    <Input
                      id="dataNascimento"
                      type="date"
                      value={formData.dataNascimento}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("dataNascimento")}
                      className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none ${
                        errors.dataNascimento ? "border-red-500" : ""
                      }`}
                    />
                  </div>
                  {errors.dataNascimento && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.dataNascimento}
                    </p>
                  )}
                </div>

                <div className="space-y-1">
                  <Label
                    htmlFor="sexo"
                    className="text-sm font-medium text-gray-700"
                  >
                    Sexo:
                  </Label>
                  <div className="relative group">
                    <User className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors pointer-events-none" />
                    <Select
                      value={formData.sexo}
                      onValueChange={(v) => handleSelectChange("sexo", v)}
                    >
                      <SelectTrigger
                        className={`pl-10 h-11 text-sm border rounded-3xl focus:border-blue-500 focus:outline-none focus:ring-0 data-[state=open]:border-blue-500 ${
                          errors.sexo ? "border-red-500" : "border-gray-300"
                        }`}
                      >
                        <SelectValue placeholder="Seu sexo" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="M">Masculino</SelectItem>
                        <SelectItem value="F">Feminino</SelectItem>
                        <SelectItem value="Outro">Outro</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  {errors.sexo && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.sexo}
                    </p>
                  )}
                </div>
              </div>

              {/* === ESTADO CIVIL + PROFISSÃO === */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="space-y-1">
                  <Label
                    htmlFor="estadoCivil"
                    className="text-sm font-medium text-gray-700"
                  >
                    Estado Civil:
                  </Label>
                  <div className="relative group">
                    <User className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors pointer-events-none" />
                    <Select
                      value={formData.estadoCivil}
                      onValueChange={(v) =>
                        handleSelectChange("estadoCivil", v)
                      }
                    >
                      <SelectTrigger
                        className={`pl-10 h-11 text-sm border rounded-3xl focus:border-blue-500 focus:outline-none focus:ring-0 data-[state=open]:border-blue-500 ${
                          errors.estadoCivil
                            ? "border-red-500"
                            : "border-gray-300"
                        }`}
                      >
                        <SelectValue placeholder="Selecione o estado civil" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="solteiro">Solteiro(a)</SelectItem>
                        <SelectItem value="casado">Casado(a)</SelectItem>
                        <SelectItem value="divorciado">
                          Divorciado(a)
                        </SelectItem>
                        <SelectItem value="viuvo">Viúvo(a)</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  {errors.estadoCivil && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.estadoCivil}
                    </p>
                  )}
                </div>

                <div className="space-y-1">
                  <Label
                    htmlFor="profissao"
                    className="text-sm font-medium text-gray-700"
                  >
                    Digite sua profissão:
                  </Label>
                  <div className="relative group">
                    <User className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                    <Input
                      id="profissao"
                      placeholder="Sua profissão"
                      value={formData.profissao}
                      onChange={handleInputChange}
                      onBlur={() => handleBlur("profissao")}
                      className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                        errors.profissao ? "border-red-500" : ""
                      }`}
                    />
                  </div>
                  {errors.profissao && (
                    <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                      <AlertCircle className="h-3 w-3 mr-1" />
                      {errors.profissao}
                    </p>
                  )}
                </div>
              </div>

              {/* === CHECKBOX === */}
              <div className="flex items-start gap-2 pt-2">
                <input
                  type="checkbox"
                  id="concordoTermos"
                  checked={formData.concordoTermos}
                  onChange={handleCheckboxChange}
                  className="mt-1 h-4 w-4 rounded border-gray-300 text-[#0061FE] focus:ring-[#0061FE]"
                />
                <label
                  htmlFor="concordoTermos"
                  className="text-sm text-[#44494F]"
                >
                  Concordo com todos os{" "}
                  <a href="#" className="font-medium text-[#0061FE]">
                    Termos
                  </a>{" "}
                  e{" "}
                  <a href="#" className="font-medium text-[#0061FE]">
                    Políticas de Privacidade
                  </a>
                </label>
              </div>
              {errors.concordoTermos && (
                <p className="flex items-center text-xs text-red-500 -mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                  <AlertCircle className="h-3 w-3 mr-1" />
                  {errors.concordoTermos}
                </p>
              )}

              {/* === BOTÃO === */}
              <Button
                type="submit"
                disabled={loading}
                className="w-full h-11 mt-6 bg-[#0061FE] hover:bg-blue-700 text-white font-medium text-base rounded-full"
              >
                {loading ? "Criando..." : "Criar conta"}
              </Button>

              {/* === LOGIN === */}
              <p className="text-center text-sm text-gray-600 mt-6">
                Já possui uma conta?{" "}
                <button
                  type="button"
                  onClick={() => navigate("/login")}
                  className="text-[#0061FE] font-medium hover:underline"
                >
                  Fazer login
                </button>
              </p>
            </form>
          </div>
        </div>
      </div>
    </main>
  );
};

export default Cadastro;
