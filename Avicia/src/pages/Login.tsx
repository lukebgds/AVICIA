import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { User, Lock, Stethoscope, Eye, EyeOff } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";
import { useAuth } from "@/context/AuthContext";

const Login = () => {
  const [loginData, setLoginData] = useState({ cpf: "", senha: "" });
  const [loading, setLoading] = useState(false);
  const { toast } = useToast();
  const navigate = useNavigate();
  const { setToken } = useAuth();
  const [showPassword, setShowPassword] = useState(false);

  // Erros inline (genérico para CPF e senha)
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

    const required = ["cpf", "senha"] as const;

    // Verifica campos vazios e define erros temporários
    for (const field of required) {
      const value = loginData[field as keyof typeof loginData];
      if (!value?.toString().trim()) {
        setTemporaryError(field, "Preencha este campo.");
        hasError = true;
      }
    }

    // Validações específicas (apenas se não vazio)
    const nextErrors: { [key: string]: string } = {};

    if (loginData.cpf.trim()) {
      const cpfDigits = loginData.cpf.replace(/\D/g, "");
      if (cpfDigits.length !== 11) {
        nextErrors.cpf = "CPF deve conter exatamente 11 números.";
        hasError = true;
      }
    }

    if (loginData.senha.trim()) {
      if (loginData.senha.length < 8) {
        nextErrors.senha = "A senha deve ter pelo menos 8 caracteres.";
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
      setLoginData((prev) => ({ ...prev, cpf: formatted }));
      clearError("cpf");
      return;
    }

    setLoginData((prev) => ({ ...prev, [id]: value }));
    clearError(id);
  };

  const handleBlur = (field: string) => {
    const value = loginData[field as keyof typeof loginData];

    // SEMPRE checa vazio PRIMEIRO (para TODOS os campos)
    if (!value?.toString().trim()) {
      setTemporaryError(field, "Preencha este campo.");
      return;
    }

    // Se não vazio, checa validações específicas
    const next = { ...errors };

    if (field === "cpf") {
      const digits = value.toString().replace(/\D/g, "");
      if (digits.length !== 11)
        next.cpf = "CPF deve conter exatamente 11 números.";
      else delete next.cpf;
    } else if (field === "senha") {
      if (value.toString().length < 8)
        next.senha = "A senha deve ter pelo menos 8 caracteres.";
      else delete next.senha;
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

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return; // Impede login se inválido, com toast

    setLoading(true);

    try {
      // CPF como string pura de números
      const cpfNumerico = loginData.cpf.replace(/\D/g, "");

      const resultado = await api.loginPaciente({
        cpf: cpfNumerico,
        senha: loginData.senha,
      });

      setToken(resultado.accessToken);
      toast({
        title: "Login realizado com sucesso!",
        description: "Bem-vindo ao AVICIA",
      });

      setTimeout(() => {
        navigate("/paciente/home", { replace: true });
      }, 0);
    } catch (error: any) {
      toast({
        title: "Erro no login",
        description: error.message || "CPF ou senha incorretos",
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
            Faça login na sua conta
          </p>
        </div>

        <Card className="shadow-2xl border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-2xl w-full">
          <CardHeader className="text-center py-4">
            <CardTitle className="text-3xl font-semibold text-blue-700 flex items-center justify-center gap-2 relative right-5">
              <User className="h-7 w-7" /> Entrar
            </CardTitle>
          </CardHeader>

          <CardContent className="px-9 py-4">
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* CPF */}
              <div className="space-y-1">
                <Label
                  htmlFor="cpf"
                  className="text-sm font-medium text-gray-700"
                >
                  CPF
                </Label>
                <div className="relative group">
                  <User className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="cpf"
                    type="text"
                    placeholder="000.000.000-00"
                    className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                    value={loginData.cpf}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("cpf")}
                  />
                </div>
                {errors.cpf && (
                  <p className="text-xs text-red-500 mt-1">{errors.cpf}</p>
                )}
              </div>

              {/* SENHA */}
              <div className="space-y-1">
                <Label
                  htmlFor="senha"
                  className="text-sm font-medium text-gray-700"
                >
                  Senha
                </Label>
                <div className="relative group">
                  <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="senha"
                    type={showPassword ? "text" : "password"}
                    placeholder="Digite sua senha"
                    className="pl-10 pr-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                    value={loginData.senha}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("senha")}
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
                {errors.senha && (
                  <p className="text-xs text-red-500 mt-1">{errors.senha}</p>
                )}
              </div>

              {/* BOTÃO */}
              <Button
                type="submit"
                disabled={loading}
                className="w-full bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white py-3 rounded-lg font-semibold shadow-md hover:shadow-lg transition-all duration-300"
              >
                {loading ? "Entrando..." : "Entrar"}
              </Button>

              <div className="text-center mt-6">
                <p className="text-gray-600 text-sm">
                  Não tem conta?{" "}
                  <button
                    type="button"
                    onClick={() => navigate("/cadastro")}
                    className="text-blue-600 font-medium hover:underline hover:text-blue-700 transition-colors"
                  >
                    Cadastre-se
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

export default Login;
