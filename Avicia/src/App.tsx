import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { ProtectedRoute } from "@/components/ProtectedRoute";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Suspense, lazy } from "react";
import { DashboardSkeleton } from "@/components/loaders/DashboardSkeleton";
import { LoginSkeleton } from "./components/loaders/LoginSkeleton";
import { CadastroSkeleton } from "./components/loaders/CadastroSkeleton";
import { EsqueceuSenhaSkeleton } from "./components/loaders/EsqueceuSenhaSkeleton";
import { VerificarCodigoSkeleton } from "./components/loaders/VerificarCodigoSkeleton";
import { RecuperarSenhaSkeleton } from "./components/loaders/RecuperarSenhaSkeleton";

const ProntuarioPage = lazy(() => import("./pages/ProntuarioPage"));
const OtimizarExamePage = lazy(() => import("./pages/OtimizarExamePage"));
const Index = lazy(() => import("./pages/Index"));
const NotFoundPage = lazy(() => import("./components/NotFoundPage"));
const Login = lazy(() => import("@/pages/Login"));
const EsqueceuSenha = lazy(() => import("@/pages/EsqueceuSenha"));
const VerificarCodigo = lazy(() => import("@/pages/VerificarCodigo"));
const RecuperarSenha = lazy(() => import("@/pages/RecuperarSenha"));
const Cadastro = lazy(() => import("@/pages/Cadastro"));
const AdminLogin = lazy(() => import("./pages/AdminLogin"));
const DashboardMedico = lazy(() => import("@/pages/DashboardMedico"));
const AdminDashboardMain = lazy(
  () => import("./pages/AdminDashboard/AdminDashboardMain")
);

const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <Toaster />
        <Sonner />
        <BrowserRouter>
          <Routes>
            {/* ******************************************************************
             *                         Rotas Públicas
             * ******************************************************************/}

            <Route path="/" element={<Index />} />
            <Route path="*" element={<NotFoundPage />} />

            <Route
              path="/login"
              element={
                <Suspense fallback={<LoginSkeleton />}>
                  <Login />
                </Suspense>
              }
            />

            <Route
              path="/esqueceu-senha"
              element={
                <Suspense fallback={<EsqueceuSenhaSkeleton />}>
                  <EsqueceuSenha />
                </Suspense>
              }
            />

            <Route
              path="/cadastro"
              element={
                <Suspense fallback={<CadastroSkeleton />}>
                  <Cadastro />
                </Suspense>
              }
            />

            <Route
              path="/verificar-codigo"
              element={
                <Suspense fallback={<VerificarCodigoSkeleton />}>
                  <VerificarCodigo />
                </Suspense>
              }
            />

            <Route
              path="/nova-senha"
              element={
                <Suspense fallback={<RecuperarSenhaSkeleton />}>
                  <RecuperarSenha />
                </Suspense>
              }
            />

            <Route path="/login-admin" element={<AdminLogin />} />

            {/* ******************************************************************
             *                         Rotas Protegidas - Médico
             * ******************************************************************/}
            <Route element={<ProtectedRoute />}>
              <Route
                path="/dashboard-medico"
                element={
                  <Suspense fallback={<DashboardSkeleton />}>
                    <DashboardMedico />
                  </Suspense>
                }
              />

              <Route
                path="/dashboard-medico/prontuario"
                element={<ProntuarioPage />}
              />
              <Route
                path="/dashboard-medico/prontuario/exame-otimizar"
                element={<OtimizarExamePage />}
              />
            </Route>

            {/* ******************************************************************
             *                         Rotas Protegidas - Admin
             * ******************************************************************/}
            <Route element={<ProtectedRoute />}>
              <Route path="/admin/dashboard" element={<AdminDashboardMain />} />
            </Route>
          </Routes>
        </BrowserRouter>
      </TooltipProvider>
    </QueryClientProvider>
  );
}
