// login version final
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { UserPlus, Lock, User, Stethoscope } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const Login = () => {
  const [loginData, setLoginData] = useState({ cpf: '', senha: '' });
  const [loading, setLoading] = useState(false);
  const { toast } = useToast();
  const navigate = useNavigate();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setLoginData(prev => ({ ...prev, [id]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    
    try {
      await api.loginPaciente({ 
        cpf: loginData.cpf, 
        senha: loginData.senha 
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
    <div className="min-h-screen bg-gradient-to-br from-medical-light via-background to-medical-secondary flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <div className="bg-primary rounded-full p-3">
              <Stethoscope className="h-8 w-8 text-primary-foreground" />
            </div>
          </div>
          <h1 className="text-4xl font-bold bg-gradient-to-r from-primary to-info bg-clip-text text-transparent">
            AVICIA
          </h1>
          <p className="text-muted-foreground mt-2">
            Prontuário Médico Eletrônico
          </p>
        </div>

        <Card className="shadow-[var(--medical-glow)] border-medical-secondary/30">
          <CardHeader className="text-center">
            <CardTitle className="text-2xl text-primary">
              Entrar
            </CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="cpf">CPF</Label>
                <div className="relative">
                  <User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    id="cpf"
                    type="text"
                    placeholder="000.000.000-00"
                    className="pl-10"
                    required
                    value={loginData.cpf}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="senha">Senha</Label>
                <div className="relative">
                  <Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    id="senha"
                    type="password"
                    placeholder="Sua senha"
                    className="pl-10"
                    required
                    value={loginData.senha}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <Button 
                type="submit" 
                disabled={loading}
                className="w-full bg-gradient-to-r from-primary to-info hover:from-primary/90 hover:to-info/90 transition-all duration-300"
              >
                {loading ? "Entrando..." : "Entrar"} 
              </Button>
            </form>

            <div className="mt-6 text-center">
              <Button
                variant="ghost"
                onClick={() => navigate("/cadastro")}
                className="text-primary hover:text-primary/80"
              >
                <UserPlus className="h-4 w-4 mr-2" />
                Não tem conta? Cadastre-se
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Login;
