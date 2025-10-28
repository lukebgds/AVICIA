// src/pages/AdminDashboard/sections/ReportsSection.tsx
import React from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BarChart } from "lucide-react";

export const ReportsSection: React.FC = () => (
  <Card>
    <CardHeader>
      <CardTitle className="flex items-center gap-2">
        <BarChart className="h-5 w-5" /> Relatórios e Análises
      </CardTitle>
    </CardHeader>
    <CardContent>
      <p>Página para visualização de relatórios e dados do sistema.</p>
    </CardContent>
  </Card>
);
