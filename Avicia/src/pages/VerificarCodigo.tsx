import { useState, useEffect } from "react";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/hooks/use-toast";
import { Lock, AlertCircle, ArrowLeft } from "lucide-react";
import { useNavigate, useLocation, Link } from "react-router-dom";
import { api } from "../services/api";

// Assets
import AviciaLogo from "/logo.svg";
import VerifyCodeIllustration from "/VerifyCodeIllustration.svg";

const VerificarCodigo = () => {
  const [code, setCode] = useState("");
  const [loading, setLoading] = useState(false);
  const [resendLoading, setResendLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { toast } = useToast();
  const navigate = useNavigate();
  const location = useLocation();

  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => setIsMounted(true), 50);
    return () => clearTimeout(timer);
  }, []);

  // Recupera o email do state da navegação
  const email = location.state?.email as string | undefined;

  // Redireciona se o email não estiver presente
  useEffect(() => {
    if (!email) {
      toast({
        title: "Acesso inválido",
        description: "Por favor, informe seu e-mail novamente.",
        variant: "destructive",
      });
      navigate("/esqueceu-senha", { replace: true });
    }
  }, [email, navigate, toast]);

  const clearError = () => {
    setError(null);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    // Permite apenas números e limita a 6 dígitos
    if (/^\d*$/.test(value) && value.length <= 6) {
      setCode(value);
      clearError();
    }
  };

  const handleBlur = () => {
    if (!code.trim()) {
      setError("Preencha o código");
    } else if (code.length < 6) {
      setError("O código deve ter 6 dígitos");
    } else {
      clearError();
    }
  };

  const validateForm = (): boolean => {
    if (!code.trim()) {
      setError("Preencha o código");
      toast({
        title: "Campo obrigatório",
        description: "Digite o código de 6 dígitos enviado ao seu e-mail.",
        variant: "destructive",
      });
      return false;
    }
    if (code.length !== 6) {
      setError("O código deve ter 6 dígitos");
      toast({
        title: "Código inválido",
        description:
          "O código de verificação deve conter exatamente 6 números.",
        variant: "destructive",
      });
      return false;
    }
    return true;
  };

  const showSuccess = (title: string, description: string) => {
    toast({ title, description });
  };

  const showError = (title: string, description: string) => {
    toast({ title, description, variant: "destructive" });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm() || !email) return;

    setLoading(true);
    setError(null);

    try {
      await api.validarCodigo2FA({ email, codigo: code });

      showSuccess(
        "Código verificado!",
        "Você pode agora definir uma nova senha."
      );
      navigate("/nova-senha", { state: { codigo: code } });
    } catch (error: any) {
      const message = "Código inválido ou expirado";
      setCode("");
      setError(message);
      showError("Erro na verificação", message);
    } finally {
      setLoading(false);
    }
  };

  const handleResend = async () => {
    if (!email) return;

    setResendLoading(true);

    try {
      await api.enviarCodigo2FA({ email });
      showSuccess("Código reenviado!", "Verifique seu e-mail.");
    } catch (error: any) {
      const message = "Não foi possível reenviar o código.";
      showError("Erro ao reenviar", message);
    } finally {
      setResendLoading(false);
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
            src={VerifyCodeIllustration}
            alt="Ilustração de verificação de código"
            className="max-h-[700px] w-auto object-contain"
          />
        </div>
      </div>

      {/* Coluna da Direita: Formulário */}
      <div className="flex flex-col items-center justify-center w-[40%] p-10 bg-white max-md:w-full max-md:p-5 min-h-screen">
        {/* Logo para telas pequenas */}
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
            <Link
              to="/login"
              className="flex items-center gap-2 text-[#44494F] font-medium text-sm mb-6 hover:text-[#0061FE] transition-colors"
            >
              <ArrowLeft className="h-5 w-5" />
              Voltar ao login
            </Link>

            {/* Cabeçalho */}
            <header className="mb-6 text-left">
              <h1 className="text-[#1E255E] text-[26px] font-semibold mb-2">
                Verificar código
              </h1>
              <p className="text-[#44494F] text-lg font-normal">
                Um código de autenticação foi enviado para seu e-mail.
              </p>
            </header>

            <form onSubmit={handleSubmit} noValidate className="space-y-6">
              {/* Código */}

              {/* Código */}
              <div className="space-y-1">
                <Label
                  htmlFor="code"
                  className="text-sm font-medium text-gray-700"
                >
                  Digite o código:
                </Label>
                <div className="relative group">
                  <Lock className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="code"
                    type="text"
                    inputMode="numeric"
                    placeholder="000000"
                    className={`pl-10 h-11 text-sm border border-gray-300 rounded-3xl focus:border-blue-500 focus:ring-2 focus:ring-blue-500 focus:outline-none placeholder:text-gray-400 ${
                      error ? "border-red-500" : ""
                    }`}
                    value={code}
                    onChange={handleInputChange}
                    onBlur={handleBlur}
                    maxLength={6}
                  />
                </div>
                {error && (
                  <p className="flex items-center text-xs text-red-500 mt-1 animate-in fade-in-0 slide-in-from-top-1 duration-300">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {error}
                  </p>
                )}
              </div>

              {/* Botão Enviar */}
              <Button
                type="submit"
                disabled={loading || !email}
                className="w-full h-11 bg-[#0061FE] hover:bg-blue-700 text-white font-medium text-base rounded-full"
              >
                {loading ? "Verificando..." : "Verificar"}
              </Button>
            </form>

            {/* Reenviar Código */}
            <div className="text-center mt-6">
              <p className="text-sm text-gray-600">
                Não recebeu o código?{" "}
                <button
                  type="button"
                  onClick={handleResend}
                  disabled={resendLoading || !email}
                  className="font-medium text-[#0061FE] hover:underline focus:outline-none disabled:opacity-50"
                >
                  {resendLoading ? "Reenviando..." : "Reenviar"}
                </button>
              </p>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
};

export default VerificarCodigo;
