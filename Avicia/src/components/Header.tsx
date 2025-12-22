import React from "react";
import { Link } from "react-router-dom";
import logo from "../assets/ICONE_AVICIA_EM_BRANCO.svg";

interface HeaderProps {
  isOnBlueBackground?: boolean;
}

const Header: React.FC<HeaderProps> = ({ isOnBlueBackground = false }) => {
  const linkTextColorClass = "text-white";

  return (
    <header className="relative z-20 w-full max-w-none px-0 py-6 flex items-center justify-between">
      <Link to="/" className="flex items-center gap-2 pl-10 md:pl-16 lg:pl-20">
        <img src={logo} alt="" />
        <span className="text-2xl font-semibold text-white">AVICia</span>
      </Link>

      <nav className="hidden md:flex flex-grow justify-center items-center gap-8 lg:gap-12">
        <a
          href="#produto"
          className={`${linkTextColorClass} text-sm font-medium hover:opacity-80 transition-opacity`}
        >
          Sobre o produto
        </a>
        <a
          href="#empresas"
          className={`${linkTextColorClass} text-sm font-medium hover:opacity-80 transition-opacity`}
        >
          Para empresas
        </a>
        <a
          href="#medicos"
          className={`${linkTextColorClass} text-sm font-medium hover:opacity-80 transition-opacity`}
        >
          Para médicos
        </a>
        <a
          href="#pacientes"
          className={`${linkTextColorClass} text-sm font-medium hover:opacity-80 transition-opacity`}
        >
          Para pacientes
        </a>
      </nav>

      <div className="hidden md:flex items-center gap-2 pr-10 md:pr-16 lg:pr-20">
        {/* Botão Entrar */}
        <Link
          to="/login"
          className="py-2 px-5 rounded-full text-sm font-medium transition-colors border border-[#0061FE] text-[#0061FE] bg-white hover:bg-gray-100 inline-block text-center"
        >
          Entrar
        </Link>

        {/* Botão Cadastre-se */}
        <Link
          to="/cadastro"
          className="py-2 px-5 rounded-full text-sm font-medium transition-colors bg-[#0061FE] text-white hover:bg-[#0052d4] inline-block text-center"
        >
          Cadastre-se
        </Link>
      </div>
    </header>
  );
};

export default Header;
