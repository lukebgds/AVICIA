// src/pages/AdminDashboard/sections/SettingsSection.tsx
import React from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Settings } from "lucide-react";

export const SettingsSection: React.FC = () => (
  <Card>
    <CardHeader>
      <CardTitle className="flex items-center gap-2">
        <Settings className="h-5 w-5" /> Configurações Gerais
      </CardTitle>
    </CardHeader>
    <CardContent>
      <p>Página para as configurações gerais do sistema.</p>
    </CardContent>
  </Card>
);
