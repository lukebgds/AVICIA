// DashboardHeader.tsx
import { Bell } from "lucide-react";
import { useState, useEffect, memo } from "react";
import { api } from "@/services/api";
import { useAuth } from "@/context/AuthContext";

type Tab = "dashboard" | "agenda" | "relatorios" | "pacientes";

interface DashboardHeaderProps {
  activeTab: Tab;
  onTabChange: (tab: Tab) => void;
}

export const DashboardHeader = memo(({ activeTab, onTabChange }: DashboardHeaderProps) => {
  const { logout } = useAuth();
  const [userInfo, setUserInfo] = useState<{
    idProfissional: number;
    idUsuario: number;
    nome: string;
    cargo: string;
  } | null>(null);
  const [nomeMedico, setNomeMedico] = useState<string>("Carregando...");
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);

  useEffect(() => {
    const carregarProfissional = async () => {
      const response = await api.getProfissionalSaudeLogado();
      const nomeFormatado = response.usuario.nome
        .trim()
        .toLowerCase()
        .split(" ")
        .map((palavra: string) => palavra.charAt(0).toUpperCase() + palavra.slice(1))
        .join(" ");

      setUserInfo({
        idProfissional: response.idProfissional,
        idUsuario: response.usuario.idUsuario,
        nome: nomeFormatado,
        cargo: response.cargo || "Médico",
      });
      setNomeMedico(`Dr. ${nomeFormatado}`);
    };

    carregarProfissional();
  }, []);

  return (
    <header className="flex items-center justify-between p-4 bg-white border-b border-gray-200">
      <div className="flex items-center gap-6">
        <div className="flex items-center gap-2">
          <img src="/logo.svg" alt="AVICia" className="h-8 w-auto" />
          <span className="text-2xl font-bold text-[#02298A]">AVICia</span>
        </div>

        <nav className="hidden md:flex gap-4">
          {(["dashboard", "relatorios", "pacientes", "agenda"] as const).map((tab) => (
            <button
              key={tab}
              onClick={() => onTabChange(tab)}  // ← Usa a função passada por props
              className={`px-3 py-2 rounded-lg text-sm font-semibold transition-colors ${
                activeTab === tab
                  ? "text-white bg-[#0061FE]"
                  : "text-gray-500 hover:bg-gray-100"
              }`}
            >
              {tab === "dashboard"
                ? "Dashboard"
                : tab === "relatorios"
                ? "Relatórios"
                : tab === "pacientes"
                ? "Pacientes"
                : "Agenda"}
            </button>
          ))}
        </nav>
      </div>

      {/* Resto do header (notificação, avatar, logout) permanece igual */}
      <div className="flex items-center gap-4">
        <button className="p-2 rounded-full hover:bg-gray-100 relative">
          <Bell className="w-6 h-6 text-gray-600" />
          <span className="absolute top-2 right-2 w-2 h-2 bg-red-500 rounded-full"></span>
        </button>

        <div className="flex items-center gap-3 pl-2 border-l border-gray-200">
          <img
            src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
              userInfo?.nome || "Profissional Saúde"
            )}&background=random`}
            alt={userInfo?.nome || "Profissional Saúde"}
            className="w-10 h-10 rounded-full border-4 border-gray-100 shadow"
          />
          <div className="hidden md:block">
            <div className="font-semibold text-[#1E255E] text-sm">
              {nomeMedico}
            </div>
            <div className="text-xs text-gray-500">
              {userInfo?.cargo || "Carregando..."}
            </div>
          </div>

          <div className="relative">
            <button
              onClick={() => setShowLogoutConfirm(true)}
              className="ml-4 px-4 py-2 bg-red-500 hover:bg-red-600 text-white font-medium text-sm rounded-xl transition-all shadow-md hover:shadow-lg flex items-center gap-2 whitespace-nowrap"
              title="Sair do sistema"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="w-4 h-4"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
                />
              </svg>
              <span className="hidden lg:inline">Sair</span>
            </button>

            {/* Modal de confirmação de logout (igual ao original) */}
            {showLogoutConfirm && (
              <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 px-4">
                <div className="bg-white rounded-2xl shadow-2xl max-w-sm w-full p-8">
                  <div className="flex flex-col items-center text-center">
                    <div className="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mb-5">
                      <svg className="w-10 h-10 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                      </svg>
                    </div>
                    <h3 className="text-xl font-bold text-[#1E255E] mb-3">Tem certeza que deseja sair?</h3>
                    <p className="text-gray-600 text-sm mb-8">
                      Você será desconectado da sua conta e redirecionado para a tela de login.
                    </p>
                    <div className="flex gap-4 w-full">
                      <button
                        onClick={() => setShowLogoutConfirm(false)}
                        className="flex-1 px-6 py-3 bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium rounded-xl transition-all"
                      >
                        Cancelar
                      </button>
                      <button
                        onClick={() => {
                          setShowLogoutConfirm(false);
                          logout();
                        }}
                        className="flex-1 px-6 py-3 bg-red-500 hover:bg-red-600 text-white font-medium rounded-xl transition-all shadow-md hover:shadow-lg"
                      >
                        Sim, sair!
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </header>
  );
});