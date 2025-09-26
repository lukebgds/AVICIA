import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { Shield, Lock, User } from "lucide-react";
import { useNavigate } from "react-router-dom";

const AdminLogin = () => {
  const { toast } = useToast();
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    toast({
      title: "Acesso administrativo autorizado",
      description: "Bem-vindo ao painel AVICIA Admin",
    });
    navigate("/admin/dashboard");
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-destructive/5 via-background to-destructive/10 flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <div className="bg-destructive rounded-full p-3">
              <Shield className="h-8 w-8 text-destructive-foreground" />
            </div>
          </div>
          <h1 className="text-4xl font-bold bg-gradient-to-r from-destructive to-destructive/80 bg-clip-text text-transparent">
            AVICIA ADMIN
          </h1>
          <p className="text-muted-foreground mt-2">
            Painel Administrativo
          </p>
        </div>

        <Card className="shadow-xl border-destructive/20">
          <CardHeader className="text-center">
            <CardTitle className="text-2xl text-destructive flex items-center justify-center gap-2">
              <Shield className="h-6 w-6" />
              Acesso Restrito
            </CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="adminUser">Usuário</Label>
                <div className="relative">
                  <User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    id="adminUser"
                    placeholder="Usuário"
                    className="pl-10"
                    required
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="adminPassword">Senha </Label>
                <div className="relative">
                  <Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    id="adminPassword"
                    type="password"
                    placeholder="Senha"
                    className="pl-10"
                    required
                  />
                </div>
              </div>

              <Button 
                type="submit" 
                className="w-full bg-gradient-to-r from-destructive to-destructive/90 hover:from-destructive/90 hover:to-destructive/80 transition-all duration-300"
              >
                <Shield className="h-4 w-4 mr-2" />
                Acessar Painel Administrativo
              </Button>
            </form>

            <div className="mt-6 p-4 bg-destructive/5 rounded-lg border border-destructive/20">
              <p className="text-sm text-muted-foreground text-center">
                <Shield className="h-4 w-4 inline mr-1" />
                Área restrita para administradores do sistema
              </p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default AdminLogin;