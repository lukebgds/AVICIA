import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { Shield, Lock, User } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const AdminLogin = () => {
  const [loginData, setLoginData] = useState({ nome: "", senha: "" });
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
      await api.loginAdmin({
        nome: loginData.nome,
        senha: loginData.senha,
      });

      toast({
        title: "Acesso administrativo autorizado",
        description: "Bem-vindo ao painel AVICIA Admin",
      });

      navigate("/admin/dashboard");
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
            <CardTitle className="text-3xl font-semibold text-destructive flex items-center justify-center gap-2">
              <Shield className="h-6 w-6" />
              Acesso Restrito
            </CardTitle>
          </CardHeader>

          <CardContent className="px-8 py-4">
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
                    required
                    value={loginData.nome}
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
                  <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400 group-focus-within:text-destructive transition-colors" />
                  <Input
                    id="senha"
                    type="password"
                    placeholder="Digite sua senha"
                    className="pl-10 py-2.5 border-gray-300 focus:ring-2 focus:ring-destructive focus:border-destructive rounded-lg transition-all"
                    required
                    value={loginData.senha}
                    onChange={handleInputChange}
                  />
                </div>
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
