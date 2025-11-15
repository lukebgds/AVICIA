import React from 'react';

interface ChecklistItemProps {
  text: string;
  iconColor?: string;
}

export const ChecklistItem: React.FC<ChecklistItemProps> = ({ 
  text, 
  iconColor = "#0061FE" 
}) => {
  return (
    <div className="w-[478px] h-[60px] relative">
      <div className="w-[453px] text-[#2E2E2E] text-base font-normal absolute h-[60px] left-[25px] top-0">
        {text}
      </div>
      <div
        dangerouslySetInnerHTML={{
          __html: `<svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" style="width: 16px; height: 16px; position: absolute; left: 0px; top: 22px"> <g clip-path="url(#clip0_33_3228)"> <path d="M14 0.5L4.7 10.7273L2 8.68225H0.5L4.7 15.5L15.5 0.5H14Z" fill="${iconColor}"></path> </g> <defs> <clipPath id="clip0_33_3228"> <rect width="16" height="16" fill="white"></rect> </clipPath> </defs> </svg>`,
        }}
      />
    </div>
  );
};
