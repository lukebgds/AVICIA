// // components/ResourceCard.tsx

// import React from 'react';

// interface ResourceCardProps {
//   icon: React.ReactNode;
//   title: string;
//   description: string;
//   accentColor?: string; // Prop opcional para a cor
// }

// const ResourceCard: React.FC<ResourceCardProps> = ({
//   icon,
//   title,
//   description,
//   accentColor
// }) => {
//   return (
//     <div className="flex flex-col items-start gap-4 p-2">
//       <div className="flex-shrink-0">
//         {icon}
//       </div>
//       <div className="flex flex-col gap-2">
//         <h3 className="text-[#0B1F3B] text-lg font-bold">
//           {title}
//         </h3>
//         {/* Renderiza a barra colorida se a cor for passada */}
//         {accentColor && (
//           <div
//             className="w-[78px] h-1.5"
//             style={{ backgroundColor: accentColor }}
//           />
//         )}
//       </div>
//       <p className="text-[#44494F] text-base font-normal leading-relaxed">
//         {description}
//       </p>
//     </div>
//   );
// };

// export default ResourceCard;

import React from "react";

interface ResourceCardProps {
  icon: React.ReactNode;
  title: string;
  description: string;
  accentColor?: string;
}

export const ResourceCard: React.FC<ResourceCardProps> = ({
  icon,
  title,
  description,
  accentColor = "#0061FE",
}) => {
  return (
    <div className="flex flex-col items-start gap-5 flex-[1_0_0] self-stretch relative">
      {icon}
      <div className="w-[191px] h-[37px] relative">
        <div className="text-[#0B1F3B] text-base font-bold leading-[20.8px] absolute w-[191px] h-[21px] left-0 top-0 max-sm:text-sm">
          {title}
        </div>
        <div
          className="w-[78px] h-1.5 absolute left-0 top-[31px]"
          style={{ backgroundColor: accentColor }}
        />
      </div>
      <div className="w-[229px] text-[#44494F] text-base font-normal leading-[20.8px] relative max-sm:text-sm max-sm:w-full">
        {description}
      </div>
    </div>
  );
};
