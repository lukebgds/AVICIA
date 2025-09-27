import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { User, Lock, Stethoscope } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const Login = () => {
  const [loginData, setLoginData] = useState({ cpf: "", senha: "" });
  const [loading, setLoading] = useState(false);
  const { toast } = useToast();
  const navigate = useNavigate();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setLoginData((prev) => ({ ...prev, [id]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      await api.loginPaciente({
        cpf: loginData.cpf,
        senha: loginData.senha,
      });

      toast({
        title: "Login realizado com sucesso!",
        description: "Bem-vindo ao AVICIA",
      });

      navigate("/paciente/home");
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
        {/* Hero */}
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
            <CardTitle className="text-3xl font-semibold text-blue-700 flex items-center justify-center gap-2">
              <User className="h-6 w-6" />
              Entrar
            </CardTitle>
          </CardHeader>

          <CardContent className="px-8 py-4">
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
                    required
                    value={loginData.cpf}
                    onChange={handleInputChange}
                  />
                </div>
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
                  <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-blue-500 transition-colors" />
                  <Input
                    id="senha"
                    type="password"
                    placeholder="Digite sua senha"
                    className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-lg transition-all"
                    required
                    value={loginData.senha}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

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
