// components/ResourcesSection.tsx
import React from "react";
import { ResourceCard } from "@/components/ResourceCard";

// Ícones para os cards (você pode passá-los como SVGs ou componentes)
// Estes são placeholders, substitua pelos seus ícones reais.
const IconEstrategia = () => (
  <div className="w-12 h-12 bg-blue-100 rounded-lg" />
);
const IconTime = () => <div className="w-12 h-12 bg-blue-100 rounded-lg" />;
const IconSolucoes = () => <div className="w-12 h-12 bg-cyan-100 rounded-lg" />;
const IconAnalises = () => <div className="w-12 h-12 bg-cyan-100 rounded-lg" />;

const ResourcesSection = () => {
  return (
    <section
      id="recursos"
      className="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20 md:py-32"
    >
      {/* Container flexível para texto e grid */}
      <div className="flex flex-col lg:flex-row gap-16">
        {/* Coluna de Texto */}
        <div className="lg:w-1/3 flex-shrink-0">
          <div className="w-14 h-1.5 bg-[#0061FE]" />
          <h2 className="text-[#0B1F3B] text-3xl font-bold leading-tight mt-4">
            Gestão Inteligente de negócios para você
          </h2>
          <p className="text-[#44494F] text-base leading-relaxed mt-4">
            Tenha todas as ferramentas que sua clínica precisa em um único
            sistema. Organize processos de forma estratégica, gerencie
            diferentes perfis de colaboradores como médicos, secretários e
            administradores, e acompanhe análises detalhadas e estatísticas
            inteligentes. Transforme dados em decisões estratégicas, aumente a
            eficiência da equipe e ofereça um atendimento de qualidade superior
            aos pacientes.
          </p>
        </div>

        {/* Grid de Cards */}
        {/* ✅ Usa grid para responsividade automática */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 lg:w-2/3">
          <ResourceCard
            icon={<IconEstrategia />} // Substitua pelo seu SVG
            title="Feito estrategicamente"
            description="Plataforma desenvolvida para organizar processos, otimizar fluxos e facilitar a gestão de clínicas e consultórios."
          />
          <ResourceCard
            icon={<IconTime />} // Substitua pelo seu SVG
            title="Para todo o time"
            description="Oferece análises, relatórios e estatísticas inteligentes que ajudam na tomada de decisão e na melhora do atendimento."
          />
          <ResourceCard
            icon={<IconSolucoes />} // Substitua pelo seu SVG
            title="Soluções criativas"
            description="Descubra insights valiosos por meio de análises avançadas e estatísticas que impulsionam decisões estratégicas."
            accentColor="#00BBD4" // Cor do título (como no seu design)
          />
          <ResourceCard
            icon={<IconAnalises />} // Substitua pelo seu SVG
            title="Análises e estatísticas"
            description="Oferece análises, relatórios e estatísticas inteligentes que ajudam na tomada de decisão e no aprimoramento do atendimento."
            accentColor="#00BBD4"
          />
        </div>
      </div>
    </section>
  );
};

export default ResourcesSection;
