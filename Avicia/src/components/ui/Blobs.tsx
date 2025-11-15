// components/ui/ImageBlob.tsx
import React from 'react';

interface ImageBlobProps {
  blobSrc: string; // Caminho para o blob (ex: /elemento1.png)
  children: React.ReactNode; // A imagem <img>
}

const ImageBlob: React.FC<ImageBlobProps> = ({ blobSrc, children }) => {
  return (
    <div className="relative w-full h-full flex items-center justify-center">
      {/* 1. O Blob (no fundo) */}
      <img
        src={blobSrc}
        alt=""
        aria-hidden="true"
        className="absolute w-full h-full object-contain -z-10"
      />
      {/* 2. O Conteúdo (na frente) */}
      <div className="relative z-10">
        {children}
      </div>
    </div>
  );
};

export default ImageBlob;