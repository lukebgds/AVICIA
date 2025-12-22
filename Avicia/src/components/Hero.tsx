
import React, { useId, useState } from "react";
import Header from "./Header";
import ilustracao1 from "../assets/ILUSTRAÇÂO_1.svg"

const Hero = () => {
  const clipId = useId();
  const [activeTab, setActiveTab] = useState("cadastrar");

  return (
    <section className="relative w-full h-auto overflow-hidden flex flex-col">
      {/* --- 1. FUNDO COM IMAGEM --- */}
      <svg className="absolute w-0 h-0">
        <defs>
          <clipPath id={clipId} clipPathUnits="objectBoundingBox">
            <path d="M 0 0 L 1 0 L 1 0.88 C 0.75 0.98, 0.45 0.98, 0 0.95 L 0 0 Z" />
          </clipPath>
        </defs>
      </svg>

      <div
        className="absolute top-10 left-4 h-full w-full"
        style={{
          clipPath: `url(#${clipId})`,
          backgroundImage:
            `url(${ilustracao1})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
      </div>

      <div className="relative z-10 w-full flex flex-col">
        <Header isOnBlueBackground={true} />

        <div className="relative z-10 w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 grid grid-cols-1 md:grid-cols-2 gap-10 items-center 
            pt-10 md:pt-16 
            pb-16 md:pb-24">
          
          <div className="flex flex-col gap-5 max-md:order-last max-md:text-center max-md:items-center">
            <span className="text-white/80 text-base font-medium">
              Uma nova forma de cuidar da saúde
            </span>

            <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold text-white leading-tight">
              A plataforma para <br />
              <span className="text-[#002055]">gestão da saúde</span> <br />
              feita para você
            </h1>

            <p className="text-white/80 max-w-lg text-base leading-relaxed">
              AVICIA integra IA, gestão e prontuário eletrônico para conectar a
              sua saúde em um só lugar.
            </p>
            <div className="flex items-center gap-4 mt-6 max-md:flex-col max-md:w-full max-md:max-w-sm">
              <button
                onClick={() => setActiveTab("cadastrar")}
                className={`font-bold py-3 px-8 rounded-full shadow-lg transition-all text-sm w-auto max-md:w-full ${
                  activeTab === "cadastrar"
                    ? "bg-white text-[#0061FE]"
                    : "bg-white/10 text-white hover:bg-white/20"
                }`}
              >
                Quero me cadastrar
              </button>

              <button
                onClick={() => setActiveTab("login")}
                className={`border border-white/50 font-bold py-3 px-8 rounded-full transition-all text-sm w-auto max-md:w-full ${
                  activeTab === "login"
                    ? "bg-transparent text-white border-white"
                    : "text-white hover:bg-white/10 hover:border-white"
                }`}
              >
                Entrar
              </button>
            </div>
          </div>

        </div>
      </div>
    </section>
  );
};

export default Hero;
