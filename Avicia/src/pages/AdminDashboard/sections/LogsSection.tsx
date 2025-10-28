// src/pages/AdminDashboard/sections/LogsSection.tsx
import React from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { FileText } from "lucide-react";

export const LogsSection: React.FC = () => (
  <Card>
    <CardHeader>
      <CardTitle className="flex items-center gap-2">
        <FileText className="h-5 w-5" /> Logs do Sistema
      </CardTitle>
    </CardHeader>
    <CardContent>
      <p>Página para visualização dos logs de atividade do sistema.</p>
    </CardContent>
  </Card>
);
