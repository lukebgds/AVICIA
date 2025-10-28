// src/pages/AdminDashboard/index.tsx
import React, { useState, Suspense } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { useAuthGuard } from "@/hooks/useAuthGuard";
import { useAuth } from "@/context/AuthContext";
import { Button } from "@/components/ui/button";
import {
  Activity,
  Users,
  KeyRound,
  BarChart,
  FileText,
  Settings,
  Stethoscope,
} from "lucide-react";
import { useUsers } from "./hooks/useUsers";

// Importações das seções (com React.memo para cache)
import { DashboardSection as DashboardSectionBase } from "./sections/DashboardSection";
import { UsersSection as UsersSectionBase } from "./sections/UsersSection";
import { RolesSection as RolesSectionBase } from "./sections/RolesSection";
import { ReportsSection as ReportsSectionBase } from "./sections/ReportsSection";
import { LogsSection as LogsSectionBase } from "./sections/LogsSection";
import { SettingsSection as SettingsSectionBase } from "./sections/SettingsSection";

// Memorizar os componentes (mantém o cache entre trocas de abas)
const DashboardSection = React.memo(DashboardSectionBase);
const UsersSection = React.memo(UsersSectionBase);
const RolesSection = React.memo(RolesSectionBase);
const ReportsSection = React.memo(ReportsSectionBase);
const LogsSection = React.memo(LogsSectionBase);
const SettingsSection = React.memo(SettingsSectionBase);

const AdminDashboardMain: React.FC = () => {
  useAuthGuard();
  const { token } = useAuth();
  const { activeUserCount } = useUsers(token);

  const [activeSection, setActiveSection] = useState("dashboard");

  const navItems = [
    { label: "Dashboard", icon: Activity, section: "dashboard" },
    { label: "Usuários", icon: Users, section: "users" },
    { label: "Controle de Acesso", icon: KeyRound, section: "access-control" },
    { label: "Relatórios", icon: BarChart, section: "reports" },
    { label: "Logs", icon: FileText, section: "logs" },
    { label: "Configurações", icon: Settings, section: "settings" },
  ];

  const renderSection = () => {
    switch (activeSection) {
      case "dashboard":
        return (
          <DashboardSection token={token} activeUserCount={activeUserCount} />
        );
      case "users":
        return <UsersSection token={token} />;
      case "access-control":
        return <RolesSection token={token} />;
      case "reports":
        return <ReportsSection />;
      case "logs":
        return <LogsSection />;
      case "settings":
        return <SettingsSection />;
      default:
        return null;
    }
  };

  return (
    <div className="min-h-screen bg-red-50/50 p-4 sm:p-6 lg:p-8">
      <div className="max-w-7xl mx-auto space-y-6">
        {/* Cabeçalho */}
        <header className="flex flex-col sm:flex-row items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold text-red-800 flex items-center gap-3">
              <div className="bg-red-600 p-2 rounded-lg">
                <Stethoscope className="h-6 w-6 text-white" />
              </div>
              AVICIA - Painel Administrativo
            </h1>
            <p className="text-gray-500 mt-1">
              Controle total do sistema de prontuário eletrônico.
            </p>
          </div>

          {/* Botões de navegação */}
          <nav className="flex flex-wrap gap-2 mt-4 sm:mt-0">
            {navItems.map(({ label, icon: Icon, section }) => (
              <Button
                key={section}
                variant={activeSection === section ? "default" : "ghost"}
                onClick={() => setActiveSection(section)}
                className={
                  activeSection === section
                    ? "bg-red-600 hover:bg-red-700 text-white"
                    : ""
                }
              >
                <Icon className="h-4 w-4 mr-2" /> {label}
              </Button>
            ))}
          </nav>
        </header>

        {/* Transição mais rápida e fluida entre seções */}
        <div className="relative">
          <AnimatePresence mode="wait">
            <motion.div
              key={activeSection}
              initial={{ opacity: 0, y: 8 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0, y: -8 }}
              transition={{
                duration: 0.08,
                ease: "easeOut",
              }}
              className="mt-6"
            >
              <Suspense
                fallback={
                  <div className="text-center text-gray-500 py-8">
                    Carregando seção...
                  </div>
                }
              >
                {renderSection()}
              </Suspense>
            </motion.div>
          </AnimatePresence>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboardMain;
