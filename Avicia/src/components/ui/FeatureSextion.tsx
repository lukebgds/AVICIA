import React from 'react';

interface FeatureSectionProps {
  title: string;
  description: string;
  children?: React.ReactNode;
  reverse?: boolean;
  className?: string;
}

const FeatureSection: React.FC<FeatureSectionProps> = ({ 
  title, 
  description, 
  children, 
  reverse = false,
  className = ""
}) => {
  return (
    <section className={`flex w-[1140px] justify-center items-start absolute px-0 py-20 left-[150px] max-md:w-[90%] max-md:left-[5%] ${reverse ? 'max-md:flex-col-reverse' : 'max-md:flex-col'} ${className}`}>
      <div className={`flex flex-col items-start gap-5 ${reverse ? 'flex-[1_0_0] self-stretch relative p-2.5' : 'w-[475px] shrink-0 relative p-2.5'}`}>
        <div className="w-[57px] h-1.5 relative bg-[#0061FE]" />
        <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] relative max-sm:text-xl">
          {title}
        </h2>
        <p className={`text-[#44494F] text-base font-normal leading-[20.8px] relative max-sm:text-sm ${reverse ? 'self-stretch' : ''}`}>
          {description}
        </p>
      </div>
      {children && (
        <div className={`${reverse ? 'flex w-[475px] flex-col justify-center items-start gap-5 shrink-0 self-stretch relative p-2.5' : 'flex flex-col items-end gap-2.5 flex-[1_0_0] self-stretch relative'}`}>
          {children}
        </div>
      )}
    </section>
  );
};

export default FeatureSection;
