import React, { useState } from 'react';
import { Download } from 'lucide-react';
// Importa o Header
import { DashboardHeader } from '../components/auth/dashboard/DashboardHeader';
// Importa o componente de filtros
import { RelatorioFiltros } from '../components/auth/relatorios/RelatorioFiltros';
// 1. Importa os novos componentes de gráfico
import { VolumeConsultasChart } from '../components/auth/relatorios/VolumeConsultasChart';
import { DemografiaChart } from '../components/auth/relatorios/DemografiaChart';
import { Button } from '@/components/ui/Button';

const RelatoriosPage = () => {
  // 2. Estado para controlar a exibição dos gráficos
  const [relatorioGerado, setRelatorioGerado] = useState(false);

  // 3. Função que será chamada pelo componente de filtros
  const handleGerarRelatorio = (filtros: any) => {
    console.log("Gerando relatório com os filtros:", filtros);
    // Aqui você faria a busca de dados
    
    // Mostra os gráficos
    setRelatorioGerado(true); 
  };
  
  const handleExportarPDF = () => {
    alert("Exportando PDF...");
    // Lógica para exportar PDF (usando jsPDF, etc.)
  };

  return (
    <div className="min-h-screen bg-gray-50">
      
      <DashboardHeader activePage="relatorios" />
      
      <main className="p-8">
        {/* Título da Página */}
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-[#1E255E]">Relatórios e Análises</h1>
          <p className="text-gray-600">Gere e visualize dados importantes da sua clínica</p>
        </div>
        
        {/* 4. Passamos a função 'handleGerarRelatorio' para o componente de filtros */}
        <RelatorioFiltros onGerarRelatorio={handleGerarRelatorio} />

        {/* 5. Renderização condicional dos gráficos e botão de exportar */}
        {relatorioGerado && (
          <>
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mt-6">
              {/* Gráfico de Barras (ocupa 2 colunas) */}
              <div className="lg:col-span-2">
                <VolumeConsultasChart />
              </div>
              
              {/* Gráfico de Pizza (ocupa 1 coluna) */}
              <div className="lg:col-span-1">
                <DemografiaChart />
              </div>
            </div>

            {/* Botão de Exportar */}
            <div className="flex justify-center mt-6">
              <Button 
                onClick={handleExportarPDF} 
                className="max-w-xs px-10 flex items-center gap-2"
              >
                <Download className="w-4 h-4" />
                Exportar PDF
              </Button>
            </div>
          </>
        )}
        
      </main>
    </div>
  );
};

export default RelatoriosPage;