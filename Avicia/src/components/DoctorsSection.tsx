// // components/DoctorsSection.tsx

// import React from 'react';
// // ⛔ Verifique este caminho!
// // Se 'SplitSection' está em 'components/layout/', o caminho está correto.
// import SplitSection from './layout/SplitSection'; 

// // --- Sub-componentes internos (CheckIcon, FeatureListItem) ---
// const CheckIcon = () => (
//   <svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-4 h-4 shrink-0 mt-1">
//     <g clipPath="url(#clip0_33_3228)"><path d="M14 0.5L4.7 10.7273L2 8.68225H0.5L4.7 15.5L15.5 0.5H14Z" fill="#0061FE"/></g>
//     <defs><clipPath id="clip0_33_3228"><rect width="16" height="16" fill="white"/></clipPath></defs>
//   </svg>
// );

// const FeatureListItem = ({ children, isWhiteText }: { children: React.ReactNode, isWhiteText?: boolean }) => (
//   <li className="flex items-start gap-3">
//     <CheckIcon />
//     <p className={`text-base font-normal ${isWhiteText ? 'text-blue-100' : 'text-[#2E2E2E]'}`}>{children}</p>
//   </li>
// );
// // --- Fim dos Sub-componentes ---

// interface DoctorsSectionProps {
//   withBackgroundBlob?: boolean;
//   reverse?: boolean;
// }

// const DoctorsSection: React.FC<DoctorsSectionProps> = ({
//   withBackgroundBlob = false,
//   reverse = false,
// }) => {
  
//   const textColor = withBackgroundBlob ? 'text-white' : 'text-[#0B1F3B]';
//   const mutedTextColor = withBackgroundBlob ? 'text-blue-100' : 'text-[#44494F]';
//   const currentAccentColor = withBackgroundBlob ? 'white' : '#0061FE';

//   // --- Conteúdo da Coluna da Esquerda (Imagens) ---
//   const ImageContent = (
//     <div className="w-full p-4 relative">
//        <span className={`font-bold absolute top-0 left-4 ${textColor}`}>
//          Melhorando Atendimentos
//        </span>
//       <img
//         src="https://api.builder.io/api/v1/image/assets/TEMP/15b0abc4f7727469948d1e914abb2c1e26f4c344?width=638"
//         alt="Medical professionals using AVICIA"
//         className="w-2/3 h-auto rounded-lg shadow-md"
//       />
//       <img
//         src="https://api.builder.io/api/v1/image/assets/TEMP/269ad80825c34807bede9b8654cb8f30941501ba?width=1020"
//         alt="AVICIA medical interface"
//         className="w-full h-auto rounded-xl shadow-lg -mt-16 ml-8"
//       />
//     </div>
//   );

//   // --- Conteúdo da Coluna da Direita (Texto + Lista) ---
//   const TextContent = (
//     <>
//       <div>
//         <svg width="57" height="6" viewBox="0 0 57 6" fill="none" xmlns="http://www.w3.org/2000/svg" 
//           className={`w-[57px] h-1.5`}
//         >
//           <path d="M0 0H57V6H0V0Z" fill={currentAccentColor}/>
//         </svg>
//       </div>
//       <h2 className={`text-3xl lg:text-4xl font-bold leading-tight ${textColor}`}>
//         Para médicos
//       </h2>
//       <p className={`text-base lg:text-lg font-normal leading-relaxed ${mutedTextColor}`}>
//         AVICIA é uma plataforma de saúde com inteligência artificial que ajuda profissionais a otimizar atendimentos e tomar decisões mais precisas.
//       </p>
      
//       <ul className={`flex flex-col gap-5 mt-4 w-full`}>
//         <FeatureListItem isWhiteText={withBackgroundBlob}>
//           AVICIA otimiza o tempo médico com automação inteligente...
//         </FeatureListItem>
//         <FeatureListItem isWhiteText={withBackgroundBlob}>
//           Com inteligência artificial integrada, AVICIA reduz tarefas...
//         </FeatureListItem>
//         <FeatureListItem isWhiteText={withBackgroundBlob}>
//           AVICIA centraliza exames, histórico e prescrições...
//         </FeatureListItem>
//         <FeatureListItem isWhiteText={withBackgroundBlob}>
//           A inteligência da AVICIA transforma informações médicas...
//         </FeatureListItem>
//       </ul>
//     </>
//   );

//   return (
//     <SplitSection
//       // Define a ordem baseado na prop 'reverse'
//       slotLeft={reverse ? ImageContent : TextContent}
//       slotRight={reverse ? TextContent : ImageContent}
//       reverse={reverse}
//       withBackgroundBlob={withBackgroundBlob}
//     />
//   );
// };

// // ✅ ESTA É A LINHA QUE FALTAVA:
// export default DoctorsSection;


// components/DoctorsSection.tsx
import React from 'react';
import { ChecklistItem } from '@/components/ChecklistItem';

export const DoctorSection = () => {
  return (
    <section>
      <div className="text-white text-base font-bold leading-[20.8px] absolute w-[221px] h-[21px] left-40 top-[2466px]">
        Melhorando Atendimentos
      </div>

      <div className="flex w-[475px] h-[161px] justify-center items-center shrink-0 absolute left-[815px] top-[2496px] max-md:w-[90%] max-md:left-[5%] max-md:top-[2200px]">
        <div className="w-[475px] h-[161px] shrink-0 absolute left-0 top-0">
          <div className="w-[57px] h-1.5 shrink-0 absolute bg-[#0061FE] left-2.5 top-2.5" />
          <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] absolute w-44 h-8 left-2.5 top-9 max-sm:text-xl">
            Para médicos
          </h2>
          <p className="w-[455px] text-[#44494F] text-base font-normal leading-[20.8px] absolute h-[63px] left-2.5 top-[84px] max-sm:text-sm max-sm:w-full">
            AVICIA é uma plataforma de saúde com inteligência artificial que ajuda profissionais a otimizar atendimentos e tomar decisões mais precisas.
          </p>
        </div>
      </div>

      <img
        src="https://api.builder.io/api/v1/image/assets/TEMP/15b0abc4f7727469948d1e914abb2c1e26f4c344?width=638"
        alt="Medical professionals using platform"
        className="w-[319px] h-[220px] absolute rounded-[9.58px] left-[167px] top-[2496px]"
      />

      <img
        src="https://api.builder.io/api/v1/image/assets/TEMP/269ad80825c34807bede9b8654cb8f30941501ba?width=1020"
        alt="Healthcare dashboard interface"
        className="w-[510px] h-[291px] absolute rounded-[19.019px] left-[200px] top-[2614px]"
      />

      {/* Doctor Benefits */}
      <div className="absolute left-[825px] top-[2657px]">
        <ChecklistItem text="AVICIA otimiza o tempo médico com automação inteligente, facilitando decisões clínicas com mais segurança." />
      </div>

      <div className="absolute left-[825px] top-[2727px]">
        <ChecklistItem 
          text="Com inteligência artificial integrada, AVICIA reduz tarefas administrativas e aumenta o foco no cuidado ao paciente." 
          iconColor="#0360D9"
        />
      </div>

      <div className="absolute left-[825px] top-[2797px]">
        <ChecklistItem 
          text="Com Avicia, pacientes têm acesso seguro ao histórico médico, resultados de exames e informações de saúde de forma prática." 
          iconColor="#0360D9"
        />
      </div>

      <div className="absolute left-[825px] top-[2867px]">
        <ChecklistItem 
          text="Avicia conecta pacientes à sua saúde digital, permitindo organizar exames, consultas e registros de forma rápida e segura." 
          iconColor="#0360D9"
        />
      </div>
    </section>
  );
};
