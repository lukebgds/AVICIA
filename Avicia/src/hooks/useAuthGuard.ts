import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useToast } from "@/hooks/use-toast";

export function useAuthGuard() {
  const navigate = useNavigate();
  const { toast } = useToast();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      const loginPath = window.location.pathname.includes("/admin")
        ? "/admin"
        : "/login";
      toast({
        title: "Acesso restrito",
        description: "Faça login para continuar",
        variant: "destructive",
      });
      navigate(loginPath);
    }
  }, [navigate]);
}
