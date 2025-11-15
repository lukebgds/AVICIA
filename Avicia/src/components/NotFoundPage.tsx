import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Logo } from './icons/Logo';
import { BackgroundCircle } from './icons/BackgroundCircle';
import { IllustrationSvg } from './icons/IllustrationSvg';
import { WarningIcon } from './icons/WarningIcon';
// Certifique-se que esta importação está correta
import { Button } from './ui/Button';

// [CORREÇÃO 1] Removemos o 'export' daqui
const NotFoundPage: React.FC = () => {
  const navigate = useNavigate();

  const handleGoHome = () => {
    navigate('/');
  };

  return (
    <main className="w-screen h-screen relative overflow-hidden max-md:h-screen max-md:overflow-y-auto max-sm:h-auto max-sm:min-h-screen max-sm:px-0 max-sm:py-5">
      {/* Background Circle */}
      <div className="hidden lg:block">
        <BackgroundCircle />
      </div>

      {/* Main Content */}
      <section className="inline-flex flex-col items-start gap-6 absolute w-[606px] h-[491px] left-[85px] top-[200px] max-md:w-[500px] max-md:left-[50px] max-md:top-[120px] max-sm:w-[calc(100vw_-_40px)] max-sm:max-w-[400px] max-sm:relative max-sm:mx-auto max-sm:my-0 max-sm:left-5 max-sm:top-20">
        {/* 404 Number */}
        <header className="text-[#0061FE] text-[200px] font-semibold relative max-md:text-[150px] max-sm:text-[100px] max-sm:text-center max-sm:w-full">
          404
        </header>

        {/* Content Section */}
        <div className="flex flex-col items-start gap-8 relative max-sm:w-full">
          {/* Text Content */}
          <div className="flex flex-col items-start gap-5 relative max-sm:w-full">
            <h1 className="text-[#0A0015] text-[40px] font-bold relative max-md:text-[32px] max-sm:text-2xl max-sm:text-center max-sm:w-full">
              Página não encontrada
            </h1>
            <p className="w-[606px] text-[#77707F] text-2xl font-normal relative max-md:w-[500px] max-md:text-xl max-sm:w-full max-sm:text-base max-sm:text-center">
              Desculpe, a página que você está procurando não existe ou foi
              movida. Volte para a página inicial.
            </p>
          </div>

          {/* [CORREÇÃO 2] Esta é a seção do botão corrigida */}
          <div className="relative max-sm:w-full max-sm:flex max-sm:justify-center">
            <Button
              onClick={handleGoHome}
              aria-label="Voltar para página inicial"
              // Removemos o 'className="relative"' que estava aqui
            >
              Voltar para pagina inicial
            </Button>
          </div>
          
        </div>
      </section>

      {/* Illustration SVG */}
      <div className="hidden lg:block">
        <IllustrationSvg />
      </div>

      {/* Warning Icon */}
      <div className="hidden lg:block">
        <WarningIcon />
      </div>

      {/* Logo */}
      <header className="absolute left-[76px] top-[52px]">
        <Logo />
      </header>
    </main>
  );
};

// [CORREÇÃO 3] Adicionamos o 'export default' aqui no final
export default NotFoundPage;