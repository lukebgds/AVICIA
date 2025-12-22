import React, { useState } from 'react';

const Footer = () => {
  const [email, setEmail] = useState('');

  const handleNewsletterSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Newsletter signup:', email);
    setEmail('');
  };

  return (
    <footer className="w-[1440px] h-[471px] absolute left-0 top-[3689px] max-md:w-full">
      <div className="w-[1440px] h-[471px] absolute bg-[#0061FE] left-0 top-0" />
      
      <div className="w-[366px] text-white text-base font-normal absolute h-[126px] left-40 top-[161px] max-sm:w-[90%] max-sm:text-sm max-sm:left-[5%]">
        Somos uma plataforma de saúde inteligente que integra dados, otimiza atendimentos e facilita decisões precisas. Com inteligência artificial, melhora a eficiência de clínicas, equipes e a experiência de pacientes
      </div>
      
      <div className="w-[120px] h-9 absolute left-40 top-[97px] max-sm:left-[5%]">
        <div className="text-white text-2xl font-normal absolute w-[81px] h-9 left-[39px] top-0">
          AVICia
        </div>
        <div className="w-[30px] h-[30px] absolute left-0 top-[3px]">
          <div className="w-[30px] h-[30px] absolute bg-white rounded-md left-0 top-0" />
          <div>
            <svg width="20" height="19" viewBox="0 0 21 19" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-5 h-[19px] fill-[#0061FE] absolute left-[5px] top-[5px]">
              <path d="M16.7568 12.6L14.5781 13.968L15.7942 16.26H4.35725L10.0739 5.47604L11.29 7.77204L13.4686 6.40004L10.0739 0L-2.67029e-05 19H20.1515L16.7568 12.6Z" fill="#0061FE"/>
            </svg>
          </div>
        </div>
      </div>
      
      <div className="text-white text-2xl font-bold absolute w-[105px] h-[31px] left-[914px] top-[97px] max-md:left-[5%] max-md:top-[350px]">
        Endereço
      </div>
      
      <div className="w-[167px] h-[166px] absolute left-[652px] top-[97px] max-md:left-[5%] max-sm:left-[5%] max-sm:top-[250px]">
        <h3 className="text-white text-2xl font-bold absolute w-[120px] h-[31px] left-0 top-0">
          Links úteis
        </h3>
        <nav className="flex flex-col gap-4 mt-12">
          <a href="#sobre" className="text-white text-base font-normal hover:text-blue-200 transition-colors">
            Sobre nós
          </a>
          <a href="#privacidade" className="text-white text-base font-normal hover:text-blue-200 transition-colors">
            Política de privacidade
          </a>
          <a href="#visao" className="text-white text-base font-normal hover:text-blue-200 transition-colors">
            Nossa visão
          </a>
          <a href="#time" className="text-white text-base font-normal hover:text-blue-200 transition-colors">
            Nosso time
          </a>
        </nav>
      </div>
      
      <div className="absolute left-40 top-[320px] max-sm:left-[5%]">
        <form onSubmit={handleNewsletterSubmit} className="flex gap-2">
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Seu email"
            className="px-4 py-2 rounded-lg text-black"
            required
          />
          <button
            type="submit"
            className="px-4 py-2 bg-white text-[#0061FE] rounded-lg font-bold hover:bg-gray-100 transition-colors"
          >
            Inscrever
          </button>
        </form>
      </div>
      
      <div>
        <svg width="72" height="16" viewBox="0 0 72 17" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-[72px] h-4 absolute left-40 top-[305px]">
          <path d="M16.002 8.05C16.002 3.604 12.42 0 8.002 0C3.582 0.001 0 3.604 0 8.051C0 12.068 2.926 15.398 6.75 16.002V10.377H4.72V8.051H6.752V6.276C6.752 4.259 7.947 3.145 9.774 3.145C10.65 3.145 11.565 3.302 11.565 3.302V5.282H10.556C9.563 5.282 9.253 5.903 9.253 6.54V8.05H11.471L11.117 10.376H9.252V16.001C13.076 15.397 16.002 12.067 16.002 8.05Z" fill="white"/>
          <path d="M43.9485 4.73034C43.9395 4.05742 43.8135 3.39118 43.5762 2.76144C43.3704 2.23026 43.056 1.74784 42.6532 1.34503C42.2504 0.942215 41.768 0.627855 41.2368 0.422035C40.6151 0.188678 39.9584 0.0624978 39.2945 0.0488672C38.4398 0.010662 38.1688 0 35.9991 0C33.8294 0 33.5513 5.95782e-08 32.7028 0.0488672C32.0392 0.062598 31.3828 0.188777 30.7614 0.422035C30.2302 0.627712 29.7477 0.942021 29.3449 1.34485C28.942 1.74769 28.6277 2.23017 28.422 2.76144C28.1882 3.38261 28.0623 4.03919 28.0498 4.7028C28.0116 5.55842 28 5.82941 28 7.99911C28 10.1688 28 10.446 28.0498 11.2954C28.0631 11.96 28.1884 12.6157 28.422 13.2386C28.6281 13.7697 28.9426 14.252 29.3456 14.6546C29.7485 15.0573 30.2311 15.3715 30.7623 15.5771C31.382 15.8198 32.0385 15.955 32.7037 15.9769C33.5593 16.0151 33.8303 16.0267 36 16.0267C38.1697 16.0267 38.4478 16.0267 39.2963 15.9769C39.9602 15.9638 40.6169 15.8379 41.2386 15.6046C41.7696 15.3986 42.2519 15.0841 42.6547 14.6813C43.0575 14.2786 43.3719 13.7963 43.578 13.2652C43.8116 12.6433 43.9369 11.9876 43.9502 11.3221C43.9884 10.4673 44 10.1964 44 8.02577C43.9982 5.85606 43.9982 5.58063 43.9485 4.73034ZM35.9938 12.1022C33.7246 12.1022 31.8863 10.2639 31.8863 7.99467C31.8863 5.72546 33.7246 3.88716 35.9938 3.88716C37.0832 3.88716 38.1279 4.31992 38.8982 5.09022C39.6685 5.86053 40.1013 6.90529 40.1013 7.99467C40.1013 9.08405 39.6685 10.1288 38.8982 10.8991C38.1279 11.6694 37.0832 12.1022 35.9938 12.1022ZM40.2648 4.69303C40.139 4.69314 40.0144 4.66845 39.8981 4.62036C39.7818 4.57226 39.6762 4.50172 39.5872 4.41275C39.4983 4.32379 39.4277 4.21816 39.3796 4.1019C39.3316 3.98564 39.3069 3.86104 39.307 3.73523C39.307 3.60951 39.3317 3.48502 39.3798 3.36887C39.428 3.25271 39.4985 3.14718 39.5874 3.05828C39.6763 2.96938 39.7818 2.89886 39.898 2.85075C40.0141 2.80264 40.1386 2.77788 40.2643 2.77788C40.39 2.77788 40.5145 2.80264 40.6307 2.85075C40.7468 2.89886 40.8524 2.96938 40.9413 3.05828C41.0302 3.14718 41.1007 3.25271 41.1488 3.36887C41.1969 3.48502 41.2217 3.60951 41.2217 3.73523C41.2217 4.26477 40.7934 4.69303 40.2648 4.69303Z" fill="white"/>
          <path d="M35.9938 10.663C37.4674 10.663 38.662 9.46839 38.662 7.99481C38.662 6.52123 37.4674 5.32666 35.9938 5.32666C34.5203 5.32666 33.3257 6.52123 33.3257 7.99481C33.3257 9.46839 34.5203 10.663 35.9938 10.663Z" fill="white"/>
          <path d="M61.0327 15.0013C62.2612 15.0091 63.479 14.7728 64.6154 14.3062C65.7519 13.8397 66.7844 13.1521 67.653 12.2835C68.5217 11.4148 69.2093 10.3823 69.6758 9.24584C70.1424 8.10939 70.3786 6.89162 70.3709 5.66315V5.23406C71.0077 4.76751 71.5589 4.19419 72 3.53952C71.4004 3.80177 70.7659 3.9757 70.1164 4.05588C70.805 3.64554 71.3217 2.99964 71.5709 2.2377C70.9291 2.62274 70.2253 2.89343 69.4909 3.0377C68.9963 2.51069 68.3418 2.16137 67.6287 2.0438C66.9156 1.92622 66.1836 2.04693 65.546 2.38726C64.9084 2.72759 64.4007 3.26857 64.1015 3.92648C63.8023 4.58439 63.7283 5.32257 63.8909 6.02679C62.5862 5.96275 61.3096 5.62462 60.1443 5.03443C58.9789 4.44424 57.9509 3.61521 57.1273 2.60133C56.7119 3.32219 56.5861 4.17393 56.7751 4.98411C56.9641 5.79429 57.454 6.50237 58.1455 6.96497C57.6354 6.9453 57.1371 6.80577 56.6909 6.5577V6.59406C56.6864 7.34739 56.9402 8.07953 57.41 8.66844C57.8798 9.25735 58.5372 9.66746 59.2727 9.83042C58.7983 9.95803 58.3013 9.97791 57.8182 9.88861C58.0311 10.5311 58.4376 11.0919 58.9819 11.4943C59.5261 11.8966 60.1815 12.1207 60.8582 12.1359C59.7012 13.0672 58.2633 13.5798 56.7782 13.5904C56.5178 13.5829 56.258 13.561 56 13.525C57.5032 14.4827 59.2503 14.9877 61.0327 14.9795" fill="white"/>
        </svg>
      </div>
      
      <img
        src="https://api.builder.io/api/v1/image/assets/TEMP/a9cd933799590ebd52b576cd9e72801ae1e05d77?width=732"
        alt="Location map"
        className="w-[366px] h-40 absolute rounded-[10px] left-[914px] top-[161px] max-md:w-[90%] max-md:left-[5%] max-md:top-[380px]"
      />
      
      <div>
        <svg width="1120" height="1" viewBox="0 0 1121 1" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-[1120px] h-0 stroke-white absolute left-40 top-[401px]">
          <path d="M0.5 0.5L1120.5 0.499902" stroke="white" strokeLinecap="round"/>
        </svg>
      </div>
      
      <div className="w-[272px] h-[21px] absolute left-[621px] top-[425px] max-sm:w-[90%] max-sm:left-[5%] max-sm:top-[500px]">
        <div className="w-[262px] text-white text-center text-base font-normal absolute h-[21px] left-2.5 top-0 max-sm:w-full max-sm:left-0">
          2025 Todos os direitos reservados
        </div>
        <div>
          <svg width="14" height="14" viewBox="0 0 14 14" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-[14px] h-[14px] fill-white absolute left-0 top-1">
            <path d="M7 0C3.14031 0 0 3.14031 0 7C0 10.8597 3.14031 14 7 14C10.8597 14 14 10.8597 14 7C14 3.14031 10.8597 0 7 0ZM7 1.07692C10.2771 1.07692 12.9231 3.72292 12.9231 7C12.9231 10.2771 10.2771 12.9231 7 12.9231C3.72292 12.9231 1.07692 10.2771 1.07692 7C1.07692 3.72292 3.72292 1.07692 7 1.07692ZM6.94939 3.76923C5.15954 3.76923 3.71862 5.21015 3.71862 7C3.71862 8.78985 5.15954 10.2308 6.94939 10.2308C8.24115 10.2308 9.34554 9.45646 9.86031 8.36285L8.88462 7.90892C8.53731 8.64662 7.812 9.15385 6.94939 9.15385C5.72331 9.15385 4.79554 8.22608 4.79554 7C4.79554 5.77392 5.72331 4.84615 6.94939 4.84615C7.812 4.84615 8.53785 5.35285 8.88462 6.09162L9.86085 5.63715C9.345 4.54354 8.24115 3.76923 6.94885 3.76923H6.94939Z" fill="white"/>
          </svg>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
