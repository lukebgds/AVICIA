import { useState, useEffect } from "react";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/hooks/use-toast";
import { AlertCircle, Lock, FileText, Eye, EyeOff } from "lucide-react";
import { useNavigate, useLocation } from "react-router-dom";
import { api } from "../services/api";

// Assets
import AviciaLogo from "/logo.svg";
import RecuperarSenhaIllustration from "/NovaSenha.svg";

interface FormData {
  cpf: string;
  novaSenha: string;
  confirmarSenha: string;
}

interface FormErrors {
  cpf?: string;
  novaSenha?: string;
  confirmarSenha?: string;
}

const RecuperarSenha = () => {
  const { toast } = useToast();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const codigo = location.state?.codigo as string | undefined;

  // --- ANIMAÇÃO: estado para entrada suave ---
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => setIsMounted(true), 50);
    return () => clearTimeout(timer);
  }, []);

  useEffect(() => {
    if (!codigo) {
      toast({
        title: "Acesso inválido",
        description: "Por favor, verifique o código enviado ao seu e-mail.",
        variant: "destructive",
      });
      navigate("/esqueceu-senha", { replace: true });
    }
  }, [codigo, navigate, toast]);

  // Não renderiza se não tiver código
  if (!codigo) return null;

  const [formData, setFormData] = useState<FormData>({
    cpf: "",
    novaSenha: "",
    confirmarSenha: "",
  });

  const [errors, setErrors] = useState<FormErrors>({});

  // --- Toast Helpers (PADRÃO UNIFICADO) ---
  const showSuccess = (title: string, description: string) => {
    toast({ title, description });
  };

  const showError = (title: string, description: string) => {
    toast({ title, description, variant: "destructive" });
  };

  // --- Validações puras ---
  const validateCPFField = (value: string): string | null => {
    if (!value.trim()) return "Preencha este campo";
    const digits = value.replace(/\D/g, "");
    if (digits.length !== 11) return "CPF deve conter exatamente 11 números";
    return null;
  };

  const validatePasswordField = (value: string): string | null => {
    if (!value.trim()) return "Preencha este campo";
    if (value.length < 8) return "A senha deve ter pelo menos 8 caracteres";
    if (!/[A-Z]/.test(value))
      return "A senha deve conter pelo menos 1 letra maiúscula";
    if (!/[a-z]/.test(value))
      return "A senha deve conter pelo menos 1 letra minúscula";
    if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(value))
      return "A senha deve conter pelo menos 1 caractere especial";
    return null;
  };

  const validateConfirmPasswordField = (
    value: string,
    password: string
  ): string | null => {
    if (!value.trim()) return "Preencha este campo";
    if (value !== password) return "As senhas não coincidem";
    return null;
  };

  const formatCPF = (value: string) => {
    const numeric = value.replace(/\D/g, "").slice(0, 11);
    if (numeric.length < 4) return numeric;
    if (numeric.length < 7) return numeric.replace(/^(\d{3})(\d+)/, "$1.$2");
    return numeric
      .replace(/^(\d{3})(\d{3})(\d+)/, "$1.$2.$3")
      .replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d{1,2})$/, "$1.$2.$3-$4");
  };

  // --- Handlers ---
  const handleCPFChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatCPF(e.target.value);
    setFormData((prev) => ({ ...prev, cpf: formatted }));
    if (errors.cpf) setErrors((prev) => ({ ...prev, cpf: undefined }));
  };

  const handleInputChange =
    (field: keyof FormData) => (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value;
      setFormData((prev) => ({ ...prev, [field]: value }));
      if (errors[field]) setErrors((prev) => ({ ...prev, [field]: undefined }));
      if (field === "novaSenha" && errors.confirmarSenha) {
        setErrors((prev) => ({ ...prev, confirmarSenha: undefined }));
      }
    };

  const handleBlur = (field: keyof FormData) => {
    let error: string | null = null;

    if (field === "cpf") {
      error = validateCPFField(formData.cpf);
    } else if (field === "novaSenha") {
      error = validatePasswordField(formData.novaSenha);
    } else if (field === "confirmarSenha") {
      error = validateConfirmPasswordField(
        formData.confirmarSenha,
        formData.novaSenha
      );
    }

    setErrors((prev) => ({ ...prev, [field]: error || undefined }));
  };

  const validateForm = (): boolean => {
    const cpfError = validateCPFField(formData.cpf);
    const senhaError = validatePasswordField(formData.novaSenha);
    const confirmError = validateConfirmPasswordField(
      formData.confirmarSenha,
      formData.novaSenha
    );

    setErrors({
      ...(cpfError && { cpf: cpfError }),
      ...(senhaError && { novaSenha: senhaError }),
      ...(confirmError && { confirmarSenha: confirmError }),
    });

    if (cpfError || senhaError || confirmError) {
      showError(
        "Campos obrigatórios",
        "Preencha todos os campos corretamente."
      );
      return false;
    }
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);
    try {
      const cpfNumerico = formData.cpf.replace(/\D/g, "");

      await api.recuperarSenha({
        cpf: cpfNumerico,
        senhaNova: formData.novaSenha,
      });

      showSuccess(
        "Senha redefinida!",
        "Você pode agora fazer login com a nova senha."
      );

      navigate("/login", { replace: true });
    } catch (error: any) {
      // Limpa todos os campos
      setFormData({ cpf: "", novaSenha: "", confirmarSenha: "" });
      setShowPassword(false);
      setShowConfirmPassword(false);

      const message = "Erro ao redefinir senha. Tente novamente.";
      setErrors({
        cpf: message,
        novaSenha: message,
        confirmarSenha: message,
      });

      showError("Erro", message);
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
            src={RecuperarSenhaIllustration}
            alt="Ilustração de Recuperação de Senha"
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
                Redefina sua senha
              </h1>
              <p className="text-[#44494F] text-lg font-normal">
                Insira seu CPF e crie uma nova senha segura.
              </p>
            </header>

            <form onSubmit={handleSubmit} noValidate className="space-y-5">
              {/* === CPF === */}
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
                    onChange={handleCPFChange}
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

              {/* === NOVA SENHA === */}
              <div className="space-y-1">
                <Label
                  htmlFor="novaSenha"
                  className="text-sm font-medium text-gray-700"
                >
                  Nova senha:
                </Label>
                <div className="relative group">
                  <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="novaSenha"
                    type={showPassword ? "text" : "password"}
                    placeholder="Crie uma senha forte"
                    value={formData.novaSenha}
                    onChange={handleInputChange("novaSenha")}
                    onBlur={() => handleBlur("novaSenha")}
                    maxLength={30}
                    className={`pl-10 pr-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                      errors.novaSenha ? "border-red-500" : ""
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
                {errors.novaSenha && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {errors.novaSenha}
                  </p>
                )}
              </div>

              {/* === CONFIRMAR SENHA === */}
              <div className="space-y-1">
                <Label
                  htmlFor="confirmarSenha"
                  className="text-sm font-medium text-gray-700"
                >
                  Confirmar nova senha:
                </Label>
                <div className="relative group">
                  <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="confirmarSenha"
                    type={showConfirmPassword ? "text" : "password"}
                    placeholder="Repita a senha"
                    value={formData.confirmarSenha}
                    onChange={handleInputChange("confirmarSenha")}
                    onBlur={() => handleBlur("confirmarSenha")}
                    maxLength={30}
                    className={`pl-10 pr-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                      errors.confirmarSenha ? "border-red-500" : ""
                    }`}
                  />
                  <button
                    type="button"
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                  >
                    {showConfirmPassword ? (
                      <Eye className="h-5 w-5" />
                    ) : (
                      <EyeOff className="h-5 w-5" />
                    )}
                  </button>
                </div>
                {errors.confirmarSenha && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {errors.confirmarSenha}
                  </p>
                )}
              </div>

              {/* === BOTÃO === */}
              <Button
                type="submit"
                disabled={loading}
                className="w-full h-11 mt-6 bg-[#0061FE] hover:bg-blue-700 text-white font-medium text-base rounded-full"
              >
                {loading ? "Redefinindo..." : "Redefinir senha"}
              </Button>

              {/* === VOLTAR AO LOGIN === */}
              <p className="text-center text-sm text-gray-600 mt-6">
                Lembrou da senha?{" "}
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

export default RecuperarSenha;
