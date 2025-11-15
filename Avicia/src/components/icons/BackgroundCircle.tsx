import React from 'react';

export const BackgroundCircle: React.FC = () => {
  return (
    <svg
      width="360"
      height="360"
      viewBox="0 0 360 360"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className="w-[360px] h-[360px] flex-shrink-0 rounded-full bg-[#F7F7F7] absolute left-[914px] top-[245px]"
    >
      <circle cx="180" cy="180" r="180" fill="#F7F7F7" />
    </svg>
  );
};
