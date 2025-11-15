import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Index from "./pages/Index";
import NotFoundPage from "./components/NotFoundPage";
import Login from "@/pages/Login";
import EsqueceuSenha from "@/pages/EsqueceuSenha";
import VerificarCodigo from "@/pages/VerificarCodigo";
import RecuperarSenha from "@/pages/RecuperarSenha";
import Cadastro from "@/pages/Cadastro";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          {/* Rotas principais */}
          <Route path="/" element={<Index />} />

          {/* Rotas de Autenticação */}
          <Route path="/login" element={<Login />} />
          <Route path="/esqueceu-senha" element={<EsqueceuSenha />} />
          <Route path="/verificar-codigo" element={<VerificarCodigo />} />
          <Route path="/nova-senha" element={<RecuperarSenha />} />
          <Route path="*" element={<NotFoundPage />} />
          <Route path="/cadastro" element={<Cadastro />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
