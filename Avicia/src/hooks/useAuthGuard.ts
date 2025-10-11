import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useToast } from "@/hooks/use-toast";
import { useAuth } from "@/context/AuthContext";

/**
 * Hook de proteção de rotas.
 * 
 * Impede o acesso a páginas sem token de autenticação,
 * redirecionando automaticamente para a tela de login apropriada.
 */
export function useAuthGuard() {
  const { token } = useAuth();            // Pega o token do contexto global
  const navigate = useNavigate();
  const { toast } = useToast();

  useEffect(() => {
    if (!token) {
      const isAdminRoute = window.location.pathname.includes("/admin");
      const redirectPath = isAdminRoute ? "/admin" : "/login";

      toast({
        title: "Acesso restrito",
        description: "Faça login para continuar.",
        variant: "destructive",
      });

      // Redireciona o usuário e substitui o histórico
      navigate(redirectPath, { replace: true });
    }
  }, [token, navigate, toast]);
}
