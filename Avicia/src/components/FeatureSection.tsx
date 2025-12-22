// // components/FeatureSection.tsx
// import React from 'react';

// interface FeatureSectionProps {
//   title: string;
//   description: string;
//   children: React.ReactNode; 
//   reverse?: boolean;
//   blob?: React.ReactNode; // ✅ Nova prop para o blob
// }

// const FeatureSection: React.FC<FeatureSectionProps> = ({
//   title,
//   description,
//   children,
//   reverse = false,
//   blob, // ✅ Recebe o blob
// }) => {
  
//   // (Removemos a lógica 'withBackgroundBlob' pois não é mais necessária)
//   const textColor = 'text-[#0B1F3B]';
//   const mutedTextColor = 'text-[#44494F]';
//   const accentColor = 'fill-[#0061FE]';

//   return (
//     <section className="relative w-full flex justify-center py-20 lg:py-32 bg-background overflow-hidden">
//       <div className="relative z-10 w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 grid grid-cols-1 md:grid-cols-2 gap-12 lg:gap-24 items-center">
        
//         {/* Coluna de Texto */}
//         <div className={`flex flex-col items-start gap-5 ${reverse ? "md:order-last" : ""}`}>
//           <div>
//             <svg width="57" height="6" viewBox="0 0 57 6" fill="none" xmlns="http://www.w3.org/2000/svg" 
//               className={`w-[57px] h-1.5 ${accentColor}`}
//             >
//               <path d="M0 0H57V6H0V0Z" fill="currentColor"/>
//             </svg>
//           </div>
//           <h2 className={`text-3xl lg:text-4xl font-bold leading-tight ${textColor}`}>
//             {title}
//           </h2>
//           <p className={`text-base lg:text-lg font-normal leading-relaxed ${mutedTextColor}`}>
//             {description}
//           </p>
//         </div>

//         {/* ✅ Coluna de Imagem/Conteúdo agora é 'relative' */}
//         <div className="relative flex items-center justify-center w-full h-full p-8">
//           {/* ✅ Renderiza o Blob atrás do conteúdo */}
//           {blob} 
          
//           {/* O 'children' (imagem) agora fica na frente do blob */}
//           <div className="relative z-10">
//             {children}
//           </div>
//         </div>
//       </div>
//     </section>
//   );
// };

// export default FeatureSection;

import React from 'react';

interface FeatureSectionProps {
  title: string;
  description: string;
  children: React.ReactNode;
  reverse?: boolean;
  className?: string;
}

export const FeatureSection: React.FC<FeatureSectionProps> = ({ 
  title, 
  description, 
  children, 
  reverse = false,
  className = ""
}) => {
  return (
    <section className={`flex w-[1140px] justify-center items-start px-0 py-20 left-[150px] max-md:w-[90%] max-md:flex-col max-md:h-auto max-md:left-[5%] ${className}`}>
      <div className={`flex ${reverse ? 'flex-row-reverse' : 'flex-row'} w-full items-start gap-5 max-md:flex-col`}>
        <div className="flex flex-col items-start gap-5 flex-[1_0_0] relative p-2.5">
          <div className="w-[57px] h-1.5 relative bg-[#0061FE]" />
          <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] relative max-sm:text-xl">
            {title}
          </h2>
          <p className="w-[475px] text-[#44494F] text-base font-normal leading-[20.8px] relative max-sm:text-sm max-sm:w-full">
            {description}
          </p>
        </div>
        <div className="flex flex-col items-start gap-10 flex-[1_0_0] self-stretch relative p-2.5 max-md:w-full">
          {children}
        </div>
      </div>
    </section>
  );
};