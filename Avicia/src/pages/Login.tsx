import { useState, useEffect } from "react";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/hooks/use-toast";
import { User, Lock, Eye, EyeOff, AlertCircle } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";
import { useAuth } from "@/context/AuthContext";

// Assets
import AviciaLogo from "/logo.svg";
import LoginIllustration from "/LoginIllustration.svg";

const Login = () => {
  const [loginData, setLoginData] = useState({ cpf: "", senha: "" });
  const [loading, setLoading] = useState(false);
  const { toast } = useToast();
  const navigate = useNavigate();
  const { setToken } = useAuth();
  const [showPassword, setShowPassword] = useState(false);
  const [errors, setErrors] = useState<{ cpf?: string; senha?: string }>({});

  // --- ANIMAÇÃO: estado para entrada suave ---
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => setIsMounted(true), 50);
    return () => clearTimeout(timer);
  }, []);

  // --- Helpers ---
  const clearError = (field: "cpf" | "senha") => {
    setErrors((prev) => {
      const next = { ...prev };
      delete next[field];
      return next;
    });
  };

  const formatCPF = (value: string) => {
    const numeric = value.replace(/\D/g, "").slice(0, 11);
    if (numeric.length < 4) return numeric;
    if (numeric.length < 7) return numeric.replace(/^(\d{3})(\d+)/, "$1.$2");
    return numeric
      .replace(/^(\d{3})(\d{3})(\d+)/, "$1.$2.$3")
      .replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d{1,2})$/, "$1.$2.$3-$4");
  };

  const validateField = (
    field: "cpf" | "senha",
    value: string
  ): string | null => {
    if (!value.trim()) return "Preencha este campo";

    if (field === "cpf") {
      const digits = value.replace(/\D/g, "");
      if (digits.length !== 11) return "CPF deve conter exatamente 11 números";
    }

    if (field === "senha") {
      if (value.length < 8) return "A senha deve ter pelo menos 8 caracteres";
    }

    return null;
  };

  // --- Handlers ---
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;

    if (id === "cpf") {
      const formatted = formatCPF(value);
      setLoginData((prev) => ({ ...prev, cpf: formatted }));
    } else {
      setLoginData((prev) => ({ ...prev, [id]: value }));
    }

    // Limpa erro ao digitar
    clearError(id as "cpf" | "senha");
  };

  const handleBlur = (field: "cpf" | "senha") => {
    const value = loginData[field];
    const error = validateField(field, value);
    setErrors((prev) => ({ ...prev, [field]: error || undefined }));
  };

  const validateForm = (): boolean => {
    const cpfError = validateField("cpf", loginData.cpf);
    const senhaError = validateField("senha", loginData.senha);

    setErrors({
      ...(cpfError && { cpf: cpfError }),
      ...(senhaError && { senha: senhaError }),
    });

    if (cpfError || senhaError) {
      toast({
        title: "Campos obrigatórios",
        description: "Preencha todos os campos corretamente.",
        variant: "destructive",
      });
      return false;
    }
    return true;
  };

  // --- Toast Helpers ---
  const showSuccess = (title: string, description: string) => {
    toast({ title, description });
  };

  const showError = (title: string, description: string) => {
    toast({ title, description, variant: "destructive" });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);

    try {
      const cpfNumerico = loginData.cpf.replace(/\D/g, "");

      const resultado = await api.loginPaciente({
        cpf: cpfNumerico,
        senha: loginData.senha,
      });

      setToken(resultado.accessToken);
      showSuccess("Login realizado com sucesso!", "Bem-vindo ao AVICIA");

      setTimeout(() => {
        navigate("/paciente/home", { replace: true });
      }, 0);
    } catch (error: any) {
      // Limpa ambos os campos
      setLoginData({ cpf: "", senha: "" });
      setShowPassword(false);

      // Define erro genérico abaixo dos campos
      const message = "CPF ou senha incorretos";
      setErrors({
        cpf: message,
        senha: message,
      });

      showError("Erro no login", message);
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
          alt="AVICIA Logo"
          className="w-36 object-contain z-10"
        />

        <div className="flex-grow flex items-center justify-center px-8">
          <img
            src={LoginIllustration}
            alt="Ilustração de login"
            className="max-h-[700px] w-auto object-contain"
          />
        </div>
      </div>

      {/* Coluna da Direita: Formulário */}
      <div className="flex flex-col items-center justify-center w-[40%] p-10 bg-white max-md:w-full max-md:p-5 min-h-screen">
        <div className="hidden max-md:block mb-8">
          <img
            src={AviciaLogo}
            alt="AVICIA Logo"
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
            <header className="mb-6 text-left">
              <h1 className="text-[#1E255E] text-[26px] font-semibold mb-2">
                Faça seu login!
              </h1>
              <p className="text-[#44494F] text-lg font-normal">
                Ainda não tem uma conta?{" "}
                <button
                  type="button"
                  onClick={() => navigate("/cadastro")}
                  className="font-semibold text-[#0061FE] hover:underline"
                >
                  Cadastre-se
                </button>
              </p>
            </header>

            <form onSubmit={handleSubmit} noValidate className="space-y-6">
              {/* CPF */}
              <div className="space-y-1">
                <Label
                  htmlFor="cpf"
                  className="text-sm font-medium text-gray-700"
                >
                  CPF:
                </Label>
                <div className="relative group">
                  <User className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="cpf"
                    type="text"
                    placeholder="000.000.000-00"
                    className={`
                      pl-10 h-11 text-sm border rounded-3xl 
                      focus:border-blue-500 focus:ring-2 focus:ring-blue-500 
                      focus:outline-none placeholder:text-gray-400
                      ${errors.cpf ? "border-red-500" : "border-gray-300"}
                    `}
                    value={loginData.cpf}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("cpf")}
                  />
                </div>
                {errors.cpf && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {errors.cpf}
                  </p>
                )}
              </div>

              {/* SENHA */}
              <div className="space-y-1">
                <Label
                  htmlFor="senha"
                  className="text-sm font-medium text-gray-700"
                >
                  Senha:
                </Label>
                <div className="relative group">
                  <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="senha"
                    type={showPassword ? "text" : "password"}
                    placeholder="Digite sua senha"
                    className={`
                      pl-10 pr-10 h-11 text-sm border rounded-3xl 
                      focus:border-blue-500 focus:ring-2 focus:ring-blue-500 
                      focus:outline-none placeholder:text-gray-400
                      ${errors.senha ? "border-red-500" : "border-gray-300"}
                    `}
                    value={loginData.senha}
                    onChange={handleInputChange}
                    onBlur={() => handleBlur("senha")}
                  />
                  <button
                    type="button"
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? (
                      <Eye className="h-5 w-5" />
                    ) : (
                      <EyeOff className="h-5 w-5" />
                    )}
                  </button>
                </div>
                {errors.senha && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {errors.senha}
                  </p>
                )}
              </div>

              <Button
                type="submit"
                disabled={loading}
                className="w-full h-11 bg-[#0061FE] hover:bg-blue-700 text-white font-medium text-base rounded-full transition-colors"
              >
                {loading ? "Entrando..." : "Entrar"}
              </Button>

              <div className="text-center mt-6">
                <p className="text-sm text-gray-600">
                  Esqueceu a senha?{" "}
                  <button
                    type="button"
                    onClick={() => navigate("/esqueceu-senha")}
                    className="font-medium text-[#0061FE] hover:underline focus:outline-none"
                  >
                    Clique aqui
                  </button>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </main>
  );
};

export default Login;
