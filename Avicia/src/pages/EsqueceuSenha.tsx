import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/hooks/use-toast";
import { Mail, AlertCircle, ArrowLeft } from "lucide-react";
import { api } from "@/services/api";

// Assets
import AviciaLogo from "/logo.svg";
import ForgotPasswordIllustration from "/ForgotPasswordIllustration.svg";

const EsqueceuSenha = () => {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState<{ email?: string }>({});
  const { toast } = useToast();
  const navigate = useNavigate();

  // --- ANIMAÇÃO: estado para entrada suave ---
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => setIsMounted(true), 50);
    return () => clearTimeout(timer);
  }, []);

  // --- Toast Helpers (PADRÃO UNIFICADO) ---
  const showSuccess = (title: string, description: string) => {
    toast({ title, description });
  };

  const showError = (title: string, description: string) => {
    toast({ title, description, variant: "destructive" });
  };

  // --- Validação pura (sem side effects) ---
  const validateEmailField = (value: string): string | null => {
    if (!value.trim()) return "Preencha este campo";
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(value)) return "E-mail inválido";
    return null;
  };

  // --- Handlers ---
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setEmail(value);
    // Limpa erro ao digitar
    if (errors.email) {
      setErrors({});
    }
  };

  const handleBlur = () => {
    const error = validateEmailField(email);
    setErrors(error ? { email: error } : {});
  };

  const validateForm = (): boolean => {
    const error = validateEmailField(email);
    if (error) {
      setErrors({ email: error });
      showError("Campo obrigatório", "Insira um e-mail válido.");
      return false;
    }
    return true;
  };

  // --- Submissão ---
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);
    try {
      await api.enviarCodigo2FA({ email });

      showSuccess(
        "Código enviado!",
        "Verifique sua caixa de e-mail para o código de recuperação."
      );

      navigate("/verificar-codigo", { state: { email } });
    } catch (error: any) {
      setEmail("");
      const message =
        "Não foi possível enviar o código. Verifique o e-mail e insira novamente.";
      setErrors({ email: message });
      showError("Erro ao enviar", message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="flex min-h-screen">
      {/* Coluna da Esquerda: Ilustração */}
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
            src={ForgotPasswordIllustration}
            alt="Ilustração de recuperação de senha"
            className="max-h-[700px] w-auto object-contain"
          />
        </div>
      </div>

      {/* Coluna da Direita: Formulário */}
      <div className="flex flex-col items-center justify-center w-[40%] p-10 bg-white max-md:w-full max-md:p-5 min-h-screen">
        {/* Logo Mobile */}
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
            {/* Link Voltar */}
            <button
              onClick={() => navigate("/login")}
              className="flex items-center gap-2 text-[#44494F] font-medium text-sm mb-6 hover:text-[#0061FE] transition-colors"
            >
              <ArrowLeft className="w-5 h-5" />
              Voltar ao login
            </button>

            {/* Cabeçalho */}
            <header className="mb-6 text-left">
              <h1 className="text-[#1E255E] text-[26px] font-semibold mb-2">
                Esqueceu sua senha?
              </h1>
              <p className="text-[#44494F] text-lg font-normal">
                Insira seu e-mail abaixo para recuperar sua senha.
              </p>
            </header>

            <form onSubmit={handleSubmit} noValidate className="space-y-6">
              {/* E-mail */}
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
                    type="email"
                    placeholder="Insira seu e-mail"
                    value={email}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={`pl-10 h-11 text-sm border rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                      errors.email ? "border-red-500" : "border-gray-300"
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

              {/* Botão */}
              <Button
                type="submit"
                disabled={loading}
                className="w-full h-11 bg-[#0061FE] hover:bg-blue-700 text-white font-medium text-base rounded-full"
              >
                {loading ? "Enviando..." : "Enviar código"}
              </Button>
            </form>

            {/* Link Login */}
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
          </div>
        </div>
      </div>
    </main>
  );
};

export default EsqueceuSenha;
