// // components/PatientsSection.tsx

// import React from 'react';
// // (Verifique se o caminho para BackgroundElements está correto)
// import { BlobShape } from './BackgroundElements'; 

// // --- Sub-componentes internos ---
// const CheckIcon = () => (
//   <svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-4 h-4 shrink-0 mt-1">
//     <g clipPath="url(#clip0_33_3272)"><path d="M14 0.5L4.7 10.7273L2 8.68225H0.5L4.7 15.5L15.5 0.5H14Z" fill="#0061FE"/></g>
//     <defs><clipPath id="clip0_33_3272"><rect width="16" height="16" fill="white"/></clipPath></defs>
//   </svg>
// );

// const FeatureListItem = ({ children, isWhiteText }: { children: React.ReactNode, isWhiteText?: boolean }) => (
//   <li className="flex items-start gap-3">
//     <CheckIcon />
//     <p className={`text-base font-normal ${isWhiteText ? 'text-blue-100' : 'text-[#2E2E2E]'}`}>{children}</p>
//   </li>
// );
// // --- Fim dos Sub-componentes ---

// interface PatientsSectionProps {
//   withBackgroundBlob?: boolean;
//   reverse?: boolean;
// }

// const PatientsSection: React.FC<PatientsSectionProps> = ({
//   withBackgroundBlob = false,
//   reverse = false,
// }) => {
  
//   const textColor = withBackgroundBlob ? 'text-white' : 'text-[#0B1F3B]';
//   const mutedTextColor = withBackgroundBlob ? 'text-blue-100' : 'text-[#44494F]';
//   const currentAccentColor = withBackgroundBlob ? 'white' : '#0061FE';

//   return (
//     <section 
//       className={`relative w-full flex justify-center py-20 lg:py-32 ${
//         withBackgroundBlob ? '' : 'bg-background'
//       } overflow-hidden`}
//     >
      
//       {withBackgroundBlob && (
//         <>
//           <BlobShape />
//           <div 
//             className="absolute inset-0 w-full h-full z-[-9]" 
//             style={{ backgroundImage: 'radial-gradient(circle at 1px 1px, rgba(255,255,255,0.08) 1px, transparent 0)', backgroundSize: '20px 20px' }}
//             aria-hidden="true"
//           />
//         </>
//       )}

//       {/* Container de Conteúdo (Grid de 2 colunas) */}
//       <div className={`relative z-10 w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 grid grid-cols-1 md:grid-cols-2 gap-12 lg:gap-24 items-center`}>
        
//         {/* Coluna 1: Texto e Features */}
//         <div className={`flex flex-col items-start gap-5 ${reverse ? "md:order-last" : ""}`}>
//           <div>
//             <svg width="57" height="6" viewBox="0 0 57 6" fill="none" xmlns="http://www.w3.org/2000/svg" 
//               className={`w-[57px] h-1.5`}
//             >
//               <path d="M0 0H57V6H0V0Z" fill={currentAccentColor}/>
//             </svg>
//           </div>
//           <h2 className={`text-3xl lg:text-4xl font-bold leading-tight ${textColor}`}>
//             Para pacientes
//           </h2>
//           <p className={`text-base lg:text-lg font-normal leading-relaxed ${mutedTextColor}`}>
//             Avicia é uma plataforma de saúde com inteligência artificial que ajuda pacientes a acompanhar exames, consultas e histórico de forma simples e segura.
//           </p>
          
//           <ul className={`flex flex-col gap-5 mt-4 w-full`}>
//             <FeatureListItem isWhiteText={withBackgroundBlob}>
//               Avicia é uma plataforma de saúde com inteligência artificial que facilita o acompanhamento de exames e consultas em um só lugar.
//             </FeatureListItem>
//             <FeatureListItem isWhiteText={withBackgroundBlob}>
//               Com Avicia, pacientes têm acesso seguro ao histórico médico, resultados de exames e informações de saúde de forma prática.
//             </FeatureListItem>
//             <FeatureListItem isWhiteText={withBackgroundBlob}>
//               Avicia conecta pacientes à sua saúde digital, permitindo organizar exames, consultas e registros de forma rápida e segura.
//             </FeatureListItem>
//             <FeatureListItem isWhiteText={withBackgroundBlob}>
//               Gerencie sua saúde de forma digital com proteção completa e confidencialidade das suas informações.
//             </FeatureListItem>
//           </ul>
//         </div>

//         {/* Coluna 2: Imagens */}
//         <div className={`flex items-center justify-center w-full h-full ${reverse ? "md:order-first" : ""}`}>
//            <div className="w-full p-4 relative">
//              <span className={`font-bold absolute top-0 right-4 ${textColor}`}>
//                Melhorando seu controle
//              </span>
//             <img
//               src="https://api.builder.io/api/v1/image/assets/TEMP/756dcb8c575214486748fd7cbd6c64ef96544157?width=646"
//               alt="AVICIA patient interface"
//               className="w-2/3 h-auto rounded-xl shadow-lg"
//             />
//             <img
//               src="https://api.builder.io/api/v1/image/assets/TEMP/db997a1978525ec8b197e6535c0fe90cf775ba08?width=694"
//               alt="Patient using AVICIA mobile app"
//               className="w-2/3 h-auto rounded-xl shadow-md -mt-16 ml-auto"
//             />
//           </div>
//         </div>
//       </div>
//     </section>
//   );
// };

// export default PatientsSection;

// components/PatientsSection.tsx
import React from 'react';
import { ChecklistItem } from '@/components/ChecklistItem';

export const PatientSection = () => {
  return (
    <section>
      <div className="text-white text-base font-bold leading-[20.8px] absolute w-[205px] h-[21px] left-[1077px] top-[3170px]">
        Melhorando seu controle
      </div>

      <div className="flex w-[475px] h-[161px] justify-center items-center shrink-0 absolute left-[151px] top-[3150px] max-md:w-[90%] max-md:left-[5%] max-md:top-[2800px]">
        <div className="w-[475px] h-[161px] shrink-0 absolute left-0 top-0">
          <div className="w-[57px] h-1.5 shrink-0 absolute bg-[#0061FE] left-2.5 top-2.5" />
          <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] absolute w-48 h-8 left-2.5 top-9 max-sm:text-xl">
            Para pacientes
          </h2>
          <p className="w-[455px] text-[#44494F] text-base font-normal leading-[20.8px] absolute h-[63px] left-2.5 top-[84px] max-sm:text-sm max-sm:w-full">
            Avicia é uma plataforma de saúde com inteligência artificial que ajuda pacientes a acompanhar exames, consultas e histórico de forma simples e segura.
          </p>
        </div>
      </div>

      <div className="w-[636px] h-[420px] absolute left-[747px] top-[3170px]">
        <img
          src="https://api.builder.io/api/v1/image/assets/TEMP/db997a1978525ec8b197e6535c0fe90cf775ba08?width=694"
          alt="Patient mobile app interface"
          className="w-[347px] h-[336px] shadow-[0_0_48px_0_rgba(0,0,0,0.16)] absolute rounded-[36px] left-[289px] top-[37px]"
        />
        <img
          src="https://api.builder.io/api/v1/image/assets/TEMP/756dcb8c575214486748fd7cbd6c64ef96544157?width=646"
          alt="Patient dashboard interface"
          className="w-[323px] h-[420px] shadow-[0_0_44.665px_0_rgba(0,0,0,0.16)] absolute rounded-[33.498px] left-0 top-0"
        />
      </div>

      {/* Patient Benefits */}
      <div className="absolute left-40 top-[3320px]">
        <ChecklistItem 
          text="Avicia é uma plataforma de saúde com inteligência artificial que facilita o acompanhamento de exames e consultas em um só lugar." 
          iconColor="#0360D9"
        />
      </div>

      <div className="absolute left-40 top-[3390px]">
        <ChecklistItem 
          text="Avicia é uma plataforma de saúde com inteligência artificial que facilita o acompanhamento de exames e consultas em um só lugar." 
          iconColor="#0360D9"
        />
      </div>

      <div className="absolute left-40 top-[3460px]">
        <ChecklistItem 
          text="AVICIA centraliza exames, histórico e prescrições em um único ambiente, proporcionando atendimento mais preciso." 
          iconColor="#0360D9"
        />
      </div>

      <div className="absolute left-40 top-[3530px]">
        <ChecklistItem 
          text="A inteligência da AVICIA transforma informações médicas em insights clínicos que elevam a qualidade do diagnóstico." 
          iconColor="#0360D9"
        />
      </div>
    </section>
  );
};
