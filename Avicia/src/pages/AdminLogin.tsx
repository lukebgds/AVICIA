import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { Shield, Lock, User, Eye, EyeOff } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";
import { useAuth } from "@/context/AuthContext";

const AdminLogin = () => {
  const [loginData, setLoginData] = useState({ nome: "", senha: "" });
  const [loading, setLoading] = useState(false);
  const { toast } = useToast();
  const navigate = useNavigate();
  const { setToken } = useAuth();
  const [showPassword, setShowPassword] = useState(false);

  // Erros inline (genérico para nome e senha)
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

  // Função para validar todos os campos e definir erros inline (chamada no submit)
  const validateAllFields = () => {
    // Limpa erros existentes antes de validar
    setErrors({});

    let hasError = false;

    const required = ["nome", "senha"] as const;

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

    if (field === "senha") {
      if (value.toString().length < 8)
        next.senha = "A senha deve ter pelo menos 8 caracteres.";
      else delete next.senha;
    } else {
      // Para nome, limpa se não vazio (sem validação extra)
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

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return; // Impede login se inválido, com toast

    setLoading(true);

    try {
      const resultado = await api.loginAdmin({
        nome: loginData.nome,
        senha: loginData.senha,
      });

      setToken(resultado.accessToken);
      toast({
        title: "Login realizado com sucesso!",
        description: "Bem-vindo ao AVICIA",
      });

      setTimeout(() => {
        navigate("/admin/dashboard", { replace: true });
      }, 0);
    } catch (error: any) {
      toast({
        title: "Erro no login",
        description: error.message || "Usuário ou senha incorretos",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-destructive/5 via-background to-destructive/10 flex items-center justify-center p-4 sm:p-6">
      <div className="w-full max-w-md flex flex-col items-center gap-6">
        {/* Hero */}
        <div className="flex flex-col items-center text-center">
          <div className="bg-destructive rounded-full p-3 shadow-lg transform transition-transform hover:scale-105 mb-4">
            <Shield className="h-10 w-10 text-destructive-foreground" />
          </div>
          <h1 className="text-5xl font-extrabold bg-gradient-to-r from-destructive to-destructive/80 bg-clip-text text-transparent tracking-tight leading-tight">
            AVICIA ADMIN
          </h1>
          <p className="text-gray-600 mt-1 text-lg font-medium leading-relaxed">
            Faça login na sua conta administrativa
          </p>
        </div>

        <Card className="shadow-2xl border border-destructive/20 bg-white/95 backdrop-blur-sm rounded-2xl w-full">
          <CardHeader className="text-center py-4">
            <CardTitle className="text-3xl font-semibold text-destructive flex items-center justify-center gap-2 relative right-3">
              <Shield className="h-7 w-7" />
              Acesso Restrito
            </CardTitle>
          </CardHeader>

          <CardContent className="px-9 py-4">
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Usuário */}
              <div className="space-y-1">
                <Label
                  htmlFor="nome"
                  className="text-sm font-medium text-gray-700"
                >
                  Usuário
                </Label>
                <div className="relative group">
                  <User className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-destructive transition-colors" />
                  <Input
                    id="nome"
                    type="text"
                    placeholder="Digite seu usuário"
                    className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-destructive focus:border-destructive rounded-lg transition-all"
                    value={loginData.nome}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("nome")}
                  />
                </div>
                {errors.nome && (
                  <p className="text-xs text-red-500 mt-1">{errors.nome}</p>
                )}
              </div>

              {/* Senha */}
              <div className="space-y-1">
                <Label
                  htmlFor="senha"
                  className="text-sm font-medium text-gray-700"
                >
                  Senha
                </Label>
                <div className="relative group">
                  <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-destructive transition-colors" />
                  <Input
                    id="senha"
                    type={showPassword ? "text" : "password"}
                    placeholder="Digite sua senha"
                    className="pl-10 pr-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-destructive focus:border-destructive rounded-lg transition-all"
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

              <Button
                type="submit"
                disabled={loading}
                className="w-full bg-gradient-to-r from-destructive to-destructive/90 hover:from-destructive/90 hover:to-destructive/80 text-white py-3 rounded-lg font-semibold shadow-md hover:shadow-lg transition-all duration-300"
              >
                {loading ? "Acessando..." : "Acessar Painel Administrativo"}
              </Button>

              <div className="mt-6 p-4 bg-destructive/5 rounded-lg border border-destructive/20">
                <p className="text-sm text-gray-600 text-center">
                  <Shield className="h-4 w-4 inline mr-1" />
                  Área restrita para administradores do sistema
                </p>
              </div>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default AdminLogin;
