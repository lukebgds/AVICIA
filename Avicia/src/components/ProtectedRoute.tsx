// src/components/ProtectedRoute.tsx
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "@/context/AuthContext";
import { useToast } from "@/hooks/use-toast";
import { useEffect } from "react";

export function ProtectedRoute() {
  const { token, isIntentionalLogout } = useAuth();
  const location = useLocation();
  const { toast } = useToast();

  useEffect(() => {
    if (!token) {
      if (isIntentionalLogout) {
        toast({
          title: "Sessão encerrada",
          description: "Você saiu com sucesso.",
        });
      } else {
        toast({
          title: "Acesso restrito",
          description: "Faça login para continuar.",
          variant: "destructive",
        });
      }
    }
  }, [token, isIntentionalLogout, toast]);

  if (!token) {
    const redirectTo = location.pathname.startsWith("/admin")
      ? "/login-admin"
      : "/login";
    return <Navigate to={redirectTo} replace />;
  }

  return <Outlet />;
}
