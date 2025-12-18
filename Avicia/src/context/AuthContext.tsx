// src/context/AuthContext.tsx
import {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from "react";

interface AuthContextType {
  token: string | null;
  setToken: (token: string | null) => void;
  logout: () => void;
  isIntentionalLogout: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  // Lê o token diretamente no useState → já está disponível no primeiro render
  const [token, setTokenState] = useState<string | null>(() =>
    localStorage.getItem("token")
  );
  const [isIntentionalLogout, setIsIntentionalLogout] = useState(false);

  // Sync com localStorage
  useEffect(() => {
    if (token) {
      localStorage.setItem("token", token);
      setIsIntentionalLogout(false);
    } else {
      localStorage.removeItem("token");
    }
  }, [token]);

  // Sync entre abas
  useEffect(() => {
    const handler = () => {
      setTokenState(localStorage.getItem("token"));
      setIsIntentionalLogout(false);
    };
    window.addEventListener("storage", handler);
    return () => window.removeEventListener("storage", handler);
  }, []);

  const setToken = (newToken: string | null) => {
    setTokenState(newToken);
    setIsIntentionalLogout(false);
  };

  const logout = () => {
    setTokenState(null);
    localStorage.removeItem("token");
    setIsIntentionalLogout(true);
  };

  return (
    <AuthContext.Provider
      value={{ token, setToken, logout, isIntentionalLogout }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context)
    throw new Error("useAuth deve ser usado dentro de AuthProvider");
  return context;
}
