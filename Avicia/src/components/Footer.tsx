// // components/Footer.tsx

// import React from 'react';

// // --- Ícones (Placeholders, substitua pelos seus SVGs) ---
// const IconTwitter = () => (
//   <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
//     {/* Substitua pelo path do seu ícone */}
//     <path d="M8.29 20.251c7.547 0 11.675-6.253 11.675-11.675 0-.178 0-.355-.012-.53A8.348 8.348 0 0022 5.92a8.19 8.19 0 01-2.357.646 4.118 4.118 0 001.804-2.27 8.224 8.224 0 01-2.605.996 4.107 4.107 0 00-6.993 3.743 11.65 11.65 0 01-8.457-4.287 4.106 4.106 0 001.27 5.477A4.072 4.072 0 012.8 9.713v.052a4.105 4.105 0 003.292 4.022 4.095 4.095 0 01-1.853.07 4.108 4.108 0 003.834 2.85A8.233 8.233 0 012 18.407a11.616 11.616 0 006.29 1.84" />
//   </svg>
// );
// const IconInstagram = () => (
//   <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
//     {/* Substitua pelo path do seu ícone */}
//     <path fillRule="evenodd" d="M12.315 2c2.43 0 2.784.013 3.808.06 1.064.049 1.791.218 2.427.465a4.902 4.902 0 011.772 1.153 4.902 4.902 0 011.153 1.772c.247.636.416 1.363.465 2.427.048 1.024.06 1.378.06 3.808s-.012 2.784-.06 3.808c-.049 1.064-.218 1.791-.465 2.427a4.902 4.902 0 01-1.153 1.772 4.902 4.902 0 01-1.772 1.153c-.636.247-1.363.416-2.427.465-1.024.048-1.378.06-3.808.06s-2.784-.012-3.808-.06c-1.064-.049-1.791-.218-2.427-.465a4.902 4.902 0 01-1.772-1.153 4.902 4.902 0 01-1.153-1.772c-.247-.636-.416-1.363-.465-2.427-.048-1.024-.06-1.378-.06-3.808s.012-2.784.06-3.808c.049-1.064.218-1.791.465-2.427a4.902 4.902 0 011.153-1.772A4.902 4.902 0 015.47 2.525c.636-.247 1.363-.416 2.427-.465C8.93 2.013 9.284 2 12.315 2zM12 7a5 5 0 100 10 5 5 0 000-10zm0-2a7 7 0 100 14 7 7 0 000-14zm4.5-1.5a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" clipRule="evenodd" />
//   </svg>
// );
// // --- Fim dos Ícones ---


// const Footer = () => {
//   return (
//     // ✅ 1. O Container Principal
//     // Sem 'absolute'. É um componente de fluxo normal.
//     // Ocupa 100% da largura e tem o fundo azul.
//     <footer className="w-full bg-[#0061FE] text-white pt-20 lg:pt-24 pb-12">
//       <div className="w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
//         {/* 2. Grid de Conteúdo */}
//         {/* Organiza o conteúdo em colunas responsivas */}
//         <div className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-12">
          
//           {/* Coluna 1: AVICia & Newsletter (ocupa 2 colunas no desktop) */}
//           <div className="md:col-span-2 flex flex-col gap-6">
//             {/* Logo */}
//             <img 
//               // 🚨 ATENÇÃO: Verifique se este caminho está correto!
//               // (Você deve ter o 'ICONE_E_TEXTO_BRANCO.svg' na pasta /public)
//               src="/ICONE_E_TEXTO_BRANCO.svg" 
//               alt="AVICIA logo" 
//               className="h-8 w-auto self-start" 
//             />
//             <p className="text-base text-white/80 max-w-md">
//               Somos uma plataforma de saúde inteligente que integra dados, otimiza atendimentos e facilita decisões precisas. Com inteligência artificial, melhore a eficiência de clínicas, equipes e a experiência de pacientes.
//             </p>
//             {/* Ícones Sociais */}
//             <div className="flex gap-4 text-white/80">
//               <a href="#" className="hover:text-white"><IconTwitter /></a>
//               <a href="#" className="hover:text-white"><IconInstagram /></a>
//             </div>
//             {/* Newsletter Form */}
//             <form className="flex w-full max-w-sm mt-4">
//               <input 
//                 type="email" 
//                 placeholder="Seu email" 
//                 className="w-full px-4 py-3 rounded-l-lg border-0 text-gray-800 focus:ring-2 focus:ring-blue-400 outline-none" 
//               />
//               <button type="submit" className="bg-blue-700 text-white font-bold px-6 py-3 rounded-r-lg hover:bg-blue-800 transition-all">
//                 Inscrever
//               </button>
//             </form>
//           </div>

//           {/* Coluna 2: Links Úteis */}
//           <div className="flex flex-col gap-4">
//             <h3 className="text-lg font-bold text-white mb-2">Links úteis</h3>
//             <ul className="flex flex-col gap-3">
//               <li><a href="#" className="text-white/80 hover:text-white">Sobre nós</a></li>
//               <li><a href="#" className="text-white/80 hover:text-white">Política de privacidade</a></li>
//               <li><a href="#" className="text-white/80 hover:text-white">Nossa visão</a></li>
//               <li><a href="#" className="text-white/80 hover:text-white">Nosso time</a></li>
//             </ul>
//           </div>

//           {/* Coluna 3: Endereço */}
//           <div className="flex flex-col gap-4">
//             <h3 className="text-lg font-bold text-white mb-2">Endereço</h3>
//             <div className="w-full h-40 bg-gray-300 rounded-lg overflow-hidden">
//               <img 
//                 // 🚨 ATENÇÃO: Substitua este placeholder pela URL da sua imagem de mapa
//                 src="https://api.builder.io/api/v1/image/assets/TEMP/f201944a-4e4f-4a0b-9c7f-7a4b8b6a4a4a"
//                 alt="Mapa da localização"
//                 className="w-full h-full object-cover"
//               />
//             </div>
//           </div>
//         </div>

//         {/* 3. Barra de Copyright */}
//         <div className="border-t border-white/20 mt-16 pt-8 text-center text-white/60 text-sm">
//           © 2023 Todos os direitos reservados
//         </div>
//       </div>
//     </footer>
//   );
// };

// export default Footer;

import React from 'react';

export const Footer: React.FC = () => {
  return (
    <footer className="w-[1440px] h-[471px] relative left-0 bg-[#0061FE] max-md:w-full">
      {/* Logo */}
      <div className="w-[120px] h-9 absolute left-40 top-[97px] max-sm:left-[5%]">
        <div className="text-white text-2xl font-normal absolute w-[81px] h-9 left-[39px] top-0">
          AVICia
        </div>
        <div className="w-[30px] h-[30px] absolute left-0 top-[3px]">
          <div className="w-[30px] h-[30px] absolute bg-white rounded-md left-0 top-0" />
          <div
            dangerouslySetInnerHTML={{
              __html:
                "<svg width=\"21\" height=\"19\" viewBox=\"0 0 21 19\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"width: 20px; height: 19px; position: absolute; left: 5px; top: 5px\"> <path d=\"M16.7568 12.6L14.5781 13.968L15.7942 16.26H4.35725L10.0739 5.47604L11.29 7.77204L13.4686 6.40004L10.0739 0L-2.67029e-05 19H20.1515L16.7568 12.6Z\" fill=\"#0061FE\"></path> </svg>",
            }}
          />
        </div>
      </div>

      {/* Description */}
      <div className="w-[366px] text-white text-base font-normal absolute h-[126px] left-40 top-[161px] max-sm:w-[90%] max-sm:text-sm max-sm:left-[5%]">
        Somos uma plataforma de saúde inteligente que integra dados, otimiza atendimentos e facilita decisões precisas. Com inteligência artificial, melhora a eficiência de clínicas, equipes e a experiência de pacientes
      </div>

      {/* Social Media */}
      <div
        dangerouslySetInnerHTML={{
          __html:
            "<svg width=\"72\" height=\"17\" viewBox=\"0 0 72 17\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"width: 72px; height: 16px; position: absolute; left: 160px; top: 305px\"> <path d=\"M16.002 8.05C16.002 3.604 12.42 0 8.002 0C3.582 0.001 0 3.604 0 8.051C0 12.068 2.926 15.398 6.75 16.002V10.377H4.72V8.051H6.752V6.276C6.752 4.259 7.947 3.145 9.774 3.145C10.65 3.145 11.565 3.302 11.565 3.302V5.282H10.556C9.563 5.282 9.253 5.903 9.253 6.54V8.05H11.471L11.117 10.376H9.252V16.001C13.076 15.397 16.002 12.067 16.002 8.05Z\" fill=\"white\"></path> <path d=\"M43.9485 4.73034C43.9395 4.05742 43.8135 3.39118 43.5762 2.76144C43.3704 2.23026 43.056 1.74784 42.6532 1.34503C42.2504 0.942215 41.768 0.627855 41.2368 0.422035C40.6151 0.188678 39.9584 0.0624978 39.2945 0.0488672C38.4398 0.010662 38.1688 0 35.9991 0C33.8294 0 33.5513 5.95782e-08 32.7028 0.0488672C32.0392 0.062598 31.3828 0.188777 30.7614 0.422035C30.2302 0.627712 29.7477 0.942021 29.3449 1.34485C28.942 1.74769 28.6277 2.23017 28.422 2.76144C28.1882 3.38261 28.0623 4.03919 28.0498 4.7028C28.0116 5.55842 28 5.82941 28 7.99911C28 10.1688 28 10.446 28.0498 11.2954C28.0631 11.96 28.1884 12.6157 28.422 13.2386C28.6281 13.7697 28.9426 14.252 29.3456 14.6546C29.7485 15.0573 30.2311 15.3715 30.7623 15.5771C31.382 15.8198 32.0385 15.955 32.7037 15.9769C33.5593 16.0151 33.8303 16.0267 36 16.0267C38.1697 16.0267 38.4478 16.0267 39.2963 15.9769C39.9602 15.9638 40.6169 15.8379 41.2386 15.6046C41.7696 15.3986 42.2519 15.0841 42.6547 14.6813C43.0575 14.2786 43.3719 13.7963 43.578 13.2652C43.8116 12.6433 43.9369 11.9876 43.9502 11.3221C43.9884 10.4673 44 10.1964 44 8.02577C43.9982 5.85606 43.9982 5.58063 43.9485 4.73034ZM35.9938 12.1022C33.7246 12.1022 31.8863 10.2639 31.8863 7.99467C31.8863 5.72546 33.7246 3.88716 35.9938 3.88716C37.0832 3.88716 38.1279 4.31992 38.8982 5.09022C39.6685 5.86053 40.1013 6.90529 40.1013 7.99467C40.1013 9.08405 39.6685 10.1288 38.8982 10.8991C38.1279 11.6694 37.0832 12.1022 35.9938 12.1022ZM40.2648 4.69303C40.139 4.69314 40.0144 4.66845 39.8981 4.62036C39.7818 4.57226 39.6762 4.50172 39.5872 4.41275C39.4983 4.32379 39.4277 4.21816 39.3796 4.1019C39.3316 3.98564 39.3069 3.86104 39.307 3.73523C39.307 3.60951 39.3317 3.48502 39.3798 3.36887C39.428 3.25271 39.4985 3.14718 39.5874 3.05828C39.6763 2.96938 39.7818 2.89886 39.898 2.85075C40.0141 2.80264 40.1386 2.77788 40.2643 2.77788C40.39 2.77788 40.5145 2.80264 40.6307 2.85075C40.7468 2.89886 40.8524 2.96938 40.9413 3.05828C41.0302 3.14718 41.1007 3.25271 41.1488 3.36887C41.1969 3.48502 41.2217 3.60951 41.2217 3.73523C41.2217 4.26477 40.7934 4.69303 40.2648 4.69303Z\" fill=\"white\"></path> <path d=\"M35.9938 10.663C37.4674 10.663 38.662 9.46839 38.662 7.99481C38.662 6.52123 37.4674 5.32666 35.9938 5.32666C34.5203 5.32666 33.3257 6.52123 33.3257 7.99481C33.3257 9.46839 34.5203 10.663 35.9938 10.663Z\" fill=\"white\"></path> <path d=\"M61.0327 15.0013C62.2612 15.0091 63.479 14.7728 64.6154 14.3062C65.7519 13.8397 66.7844 13.1521 67.653 12.2835C68.5217 11.4148 69.2093 10.3823 69.6758 9.24584C70.1424 8.10939 70.3786 6.89162 70.3709 5.66315V5.23406C71.0077 4.76751 71.5589 4.19419 72 3.53952C71.4004 3.80177 70.7659 3.9757 70.1164 4.05588C70.805 3.64554 71.3217 2.99964 71.5709 2.2377C70.9291 2.62274 70.2253 2.89343 69.4909 3.0377C68.9963 2.51069 68.3418 2.16137 67.6287 2.0438C66.9156 1.92622 66.1836 2.04693 65.546 2.38726C64.9084 2.72759 64.4007 3.26857 64.1015 3.92648C63.8023 4.58439 63.7283 5.32257 63.8909 6.02679C62.5862 5.96275 61.3096 5.62462 60.1443 5.03443C58.9789 4.44424 57.9509 3.61521 57.1273 2.60133C56.7119 3.32219 56.5861 4.17393 56.7751 4.98411C56.9641 5.79429 57.454 6.50237 58.1455 6.96497C57.6354 6.9453 57.1371 6.80577 56.6909 6.5577V6.59406C56.6864 7.34739 56.9402 8.07953 57.41 8.66844C57.8798 9.25735 58.5372 9.66746 59.2727 9.83042C58.7983 9.95803 58.3013 9.97791 57.8182 9.88861C58.0311 10.5311 58.4376 11.0919 58.9819 11.4943C59.5261 11.8966 60.1815 12.1207 60.8582 12.1359C59.7012 13.0672 58.2633 13.5798 56.7782 13.5904C56.5178 13.5829 56.258 13.561 56 13.525C57.5032 14.4827 59.2503 14.9877 61.0327 14.9795\" fill=\"white\"></path> </svg>",
        }}
      />

      {/* Links */}
      <div className="w-[167px] h-[166px] absolute left-[652px] top-[97px] max-sm:left-[5%] max-sm:top-[200px]">
        <h3 className="text-white text-2xl font-bold absolute w-[120px] h-[31px] left-0 top-0">
          Links úteis
        </h3>
        <nav className="flex flex-col gap-4 mt-12">
          <a href="#about" className="text-white text-base font-normal">
            Sobre nós
          </a>
          <a href="#privacy" className="text-white text-base font-normal">
            Politica de privacidade
          </a>
          <a href="#vision" className="text-white text-base font-normal">
            Nossa visão
          </a>
          <a href="#team" className="text-white text-base font-normal">
            Nosso time
          </a>
        </nav>
      </div>

      {/* Address */}
      <div className="text-white text-2xl font-bold absolute w-[105px] h-[31px] left-[914px] top-[97px] max-sm:left-[5%] max-sm:top-[300px]">
        Endereço
      </div>

      {/* Map */}
      <img
        src="https://api.builder.io/api/v1/image/assets/TEMP/a9cd933799590ebd52b576cd9e72801ae1e05d77?width=732"
        alt="Location map"
        className="w-[366px] h-40 absolute rounded-[10px] left-[914px] top-[161px] max-sm:w-[90%] max-sm:left-[5%] max-sm:top-[330px]"
      />

      {/* Divider */}
      <div
        dangerouslySetInnerHTML={{
          __html:
            "<svg width=\"1121\" height=\"1\" viewBox=\"0 0 1121 1\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"width: 1120px; height: 0px; position: absolute; left: 160px; top: 401px\"> <path d=\"M0.5 0.5L1120.5 0.499902\" stroke=\"white\" stroke-linecap=\"round\"></path> </svg>",
        }}
      />

      {/* Copyright */}
      <div className="w-[272px] h-[21px] absolute left-[621px] top-[425px] max-sm:w-[90%] max-sm:left-[5%] max-sm:top-[520px]">
        <div className="w-[262px] text-white text-center text-base font-normal absolute h-[21px] left-2.5 top-0">
          2025 Todos os direitos reservados
        </div>
        <div
          dangerouslySetInnerHTML={{
            __html:
              "<svg width=\"14\" height=\"14\" viewBox=\"0 0 14 14\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" style=\"width: 14px; height: 14px; position: absolute; left: 0px; top: 4px\"> <path d=\"M7 0C3.14031 0 0 3.14031 0 7C0 10.8597 3.14031 14 7 14C10.8597 14 14 10.8597 14 7C14 3.14031 10.8597 0 7 0ZM7 1.07692C10.2771 1.07692 12.9231 3.72292 12.9231 7C12.9231 10.2771 10.2771 12.9231 7 12.9231C3.72292 12.9231 1.07692 10.2771 1.07692 7C1.07692 3.72292 3.72292 1.07692 7 1.07692ZM6.94939 3.76923C5.15954 3.76923 3.71862 5.21015 3.71862 7C3.71862 8.78985 5.15954 10.2308 6.94939 10.2308C8.24115 10.2308 9.34554 9.45646 9.86031 8.36285L8.88462 7.90892C8.53731 8.64662 7.812 9.15385 6.94939 9.15385C5.72331 9.15385 4.79554 8.22608 4.79554 7C4.79554 5.77392 5.72331 4.84615 6.94939 4.84615C7.812 4.84615 8.53785 5.35285 8.88462 6.09162L9.86085 5.63715C9.345 4.54354 8.24115 3.76923 6.94885 3.76923H6.94939Z\" fill=\"white\"></path> </svg>",
          }}
        />
      </div>
    </footer>
  );
};
