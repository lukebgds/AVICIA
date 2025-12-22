import Hero from "@/components/Hero";
import { ResourceCard } from "@/components/ResourceCard";
import { ChecklistItem } from "@/components/ChecklistItem";
import { Footer } from "@/components/Footer";
import { BackgroundElements } from "@/components/BackgroundElements";

// Imports das imagens
import ilustracao2 from "../assets/ILUSTRAÇÂO_2.svg";
import ilustracao3 from "../assets/ILUSTRAÇÂO_3.svg";
import foto1 from "../assets/foto1.png";
import foto2 from "../assets/foto2.png";
import foto3 from "../assets/foto3.png";
import foto4 from "../assets/foto4.png";

const Index = () => {
  return (
    <main className="min-h-screen relative overflow-hidden">
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&family=Montserrat:wght@400;500&family=IBM+Plex+Sans:wght@400;600;700&display=swap"
      />

      <div className="w-[1440px] h-[4160px] relative mx-auto my-0 max-md:w-full max-md:max-w-screen-lg max-md:h-auto max-sm:w-full max-sm:max-w-[600px]">
        <BackgroundElements />
        <Hero />

        {/* Resources Section */}
        <section className="flex w-[1140px] justify-center items-start absolute h-[759px] px-0 py-20 left-[150px] top-[694px] max-md:w-[90%] max-md:flex-col max-md:h-auto max-md:left-[5%]">
          <div className="flex flex-col items-start gap-5 flex-[1_0_0] relative p-2.5">
            <div
              dangerouslySetInnerHTML={{
                __html:
                  '<svg width="57" height="6" viewBox="0 0 57 6" fill="none" xmlns="http://www.w3.org/2000/svg" style="width: 57px; height: 6px; position: relative"> <path d="M0 0H57V6H0V0Z" fill="#0061FE"></path> </svg>',
              }}
            />
            <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] relative max-sm:text-xl">
              Gestão Inteligente de negócios para você
            </h2>
            <p className="w-[475px] text-[#44494F] text-base font-normal leading-[20.8px] relative max-sm:text-sm max-sm:w-full">
              Tenha todas as ferramentas que sua clínica precisa em um único
              sistema. Organize processos de forma estratégica, gerencie
              diferentes perfis de colaboradores como médicos, secretários e
              administradores, e acompanhe análises detalhadas e estatísticas
              inteligentes. Transforme dados em decisões estratégicas, aumente a
              eficiência da equipe e ofereça um atendimento de qualidade
              superior aos pacientes.
            </p>
          </div>

          <div className="flex flex-col items-start gap-10 flex-[1_0_0] self-stretch relative p-2.5 max-md:w-full">
            <div className="flex items-start gap-5 self-stretch relative max-md:flex-col">
              <ResourceCard
                icon={
                  <div
                    dangerouslySetInnerHTML={{
                      __html:
                        '<svg width="78" height="78" viewBox="0 0 78 78" fill="none" xmlns="http://www.w3.org/2000/svg" style="width: 78px; height: 78px; position: relative"> <rect width="78" height="78" rx="10" fill="#0061FE"></rect> <path d="M-nan -nanL39 51.1875C42.9599 51.1875 46.9688 49.8995 46.9688 47.4375V43.3277C46.9688 42.5867 47.4051 41.9153 48.0822 41.6143C49.3221 41.0632 50.7188 41.9708 50.7188 43.3277V50.25C50.7188 50.7677 51.1386 51.1875 51.6562 51.1875C52.1739 51.1875 52.5938 50.7677 52.5938 50.25V39C52.5938 38.5843 52.334 38.2676 52.0197 38.1356L39.3807 32.5183C39.1383 32.4105 38.8616 32.4105 38.6192 32.5183L25.9629 38.1433C25.6245 38.2938 25.4062 38.6295 25.4062 39C25.4062 39.3704 25.6244 39.7062 25.9629 39.8567L26.1622 39.9452C29.1231 41.2611 31.0312 44.1974 31.0312 47.4375C31.0312 49.8995 35.04 51.1875 39 51.1875L-nan -nanZM45.0938 47.4375C45.0938 47.9982 43.0205 49.3125 39 49.3125C34.9795 49.3125 32.9062 47.9982 32.9062 47.4375C32.9062 45.3236 35.082 43.9097 37.0137 44.7682L38.6192 45.4818C38.7404 45.5356 38.8702 45.5625 39 45.5625C39.1298 45.5625 39.2596 45.5356 39.3808 45.4818L40.9863 44.7682C42.9179 43.9096 45.0938 45.3236 45.0938 47.4375ZM37.2932 42.8405C33.9654 41.3615 33.9653 36.6386 37.2931 35.1595C38.3798 34.6765 39.6202 34.6765 40.7069 35.1595C44.0347 36.6386 44.0346 41.3615 40.7068 42.8405C39.6202 43.3234 38.3798 43.3234 37.2932 42.8405Z" fill="white"></path> <path d="M62.0625 18.375H15.9375C15.4197 18.375 15 18.7947 15 19.3125V58.6875C15 59.2052 15.4197 59.625 15.9375 59.625H34.7812C35.299 59.625 35.7188 59.2052 35.7188 58.6875C35.7188 58.1698 35.299 57.75 34.7812 57.75H26.875C21.3522 57.75 16.875 53.2728 16.875 47.75V38.6875C16.875 33.1647 21.3522 28.6875 26.875 28.6875H51.125C56.6478 28.6875 61.125 33.1647 61.125 38.6875V47.75C61.125 53.2728 56.6478 57.75 51.125 57.75H43.2188C42.7011 57.75 42.2812 58.1698 42.2812 58.6875C42.2812 59.2052 42.7011 59.625 43.2188 59.625H62.0625C62.5802 59.625 63 59.2052 63 58.6875V19.3125C63 18.7947 62.5802 18.375 62.0625 18.375ZM61.125 23.5312C61.125 25.3434 59.6559 26.8125 57.8438 26.8125H20.1562C18.3441 26.8125 16.875 25.3434 16.875 23.5312C16.875 21.7191 18.3441 20.25 20.1562 20.25H41.8125C42.848 20.25 43.6875 21.0895 43.6875 22.125C43.6875 22.6427 44.1073 23.0625 44.625 23.0625C45.1427 23.0625 45.5625 22.6427 45.5625 22.125C45.5625 21.0895 46.402 20.25 47.4375 20.25C48.473 20.25 49.3125 21.0895 49.3125 22.125C49.3125 22.6427 49.7323 23.0625 50.25 23.0625C50.7677 23.0625 51.1875 22.6427 51.1875 22.125C51.1875 21.0895 52.027 20.25 53.0625 20.25C54.098 20.25 54.9375 21.0895 54.9375 22.125C54.9375 22.6427 55.3573 23.0625 55.875 23.0625C56.3927 23.0625 56.8125 22.6427 56.8125 22.125C56.8125 21.0895 57.652 20.25 58.6875 20.25H58.9688C60.1596 20.25 61.125 21.2154 61.125 22.4062V23.5312Z" fill="white"></path> <path d="M39 59.625C39.5178 59.625 39.9375 59.2053 39.9375 58.6875C39.9375 58.1697 39.5178 57.75 39 57.75C38.4822 57.75 38.0625 58.1697 38.0625 58.6875C38.0625 59.2053 38.4822 59.625 39 59.625Z" fill="white"></path> </svg>',
                    }}
                  />
                }
                title="Feito estrategicamente"
                description="Plataforma desenvolvida para organizar processos, otimizar fluxos e facilitar a gestão de clínicas e consultórios."
              />

              <ResourceCard
                icon={
                  <div className="w-[78px] h-[78px] relative">
                    <div className="w-[78px] h-[78px] absolute bg-[#0061FE] rounded-[10px] left-0 top-0" />
                    <div
                      dangerouslySetInnerHTML={{
                        __html:
                          '<svg width="48" height="36" viewBox="0 0 48 36" fill="none" xmlns="http://www.w3.org/2000/svg" style="width: 48px; height: 35px; position: absolute; left: 15px; top: 21px"> <path d="M45.8542 25.8727L42.9503 25.043C42.825 25.0072 42.7374 24.8911 42.7374 24.7607V24.6618C42.7374 24.1167 43.0185 23.6198 43.4097 23.2401C44.4307 22.2493 44.9929 20.9204 44.9929 19.498V18.7883C44.9929 18.3754 45.089 17.9683 45.2737 17.599C45.5818 16.9826 45.7447 16.2925 45.7447 15.6035V11.98C45.7447 11.5917 45.4298 11.2769 45.0415 11.2769H38.2753C35.8149 11.2769 33.8132 13.2787 33.8132 15.739V15.7809C33.8132 16.3539 33.9487 16.9278 34.2049 17.4402C34.4417 17.9138 34.565 18.4361 34.565 18.9656V19.3088C34.565 20.762 35.1367 22.0959 36.0586 23.0748C36.4961 23.5393 36.8205 24.1224 36.8206 24.7605C36.8206 24.9172 36.8206 24.982 36.24 25.148L36.0787 25.1941C35.2625 25.4272 34.3935 25.3966 33.5958 25.1065L31.2772 24.2634C30.9503 24.1445 30.7725 23.776 30.5326 23.524L29.9534 22.9155C29.4844 22.4228 29.2228 21.7686 29.2228 21.0883V20.3785C29.2228 20.0362 29.3803 19.7167 29.6301 19.4827C31.2824 17.934 32.23 15.7474 32.23 13.4836C32.23 12.2681 32.5594 11.0808 32.8092 9.89128C32.924 9.34444 32.9818 8.78661 32.9818 8.22094V0.703125C32.9818 0.314812 32.667 0 32.2787 0H21.7535C18.0494 0 15.036 3.0135 15.036 6.71747V8.22112C15.036 8.87367 15.1129 9.51578 15.2656 10.1426C15.5131 11.1587 15.7878 12.1804 15.7878 13.2262C15.7878 15.3946 16.5832 17.3857 17.8938 18.8927C18.4219 19.4999 18.795 20.2475 18.795 21.0521C18.795 21.7557 18.5245 22.4323 18.0394 22.9419L17.4852 23.5241C17.2453 23.7761 17.0675 24.1446 16.7406 24.2635L12.9675 25.6355C12.7075 25.73 12.4627 25.8524 12.2359 25.9981C12.1499 26.0533 12.0412 26.0624 11.9497 26.0166C11.7392 25.9114 11.7458 25.6044 11.9539 25.4945C13.5563 24.6484 14.0846 23.6273 14.1125 23.5716C14.2115 23.3737 14.2115 23.1406 14.1125 22.9427C13.6056 21.9289 13.5442 20.0707 13.4948 18.5776C13.4784 18.0805 13.4629 17.6109 13.4332 17.1928C13.1934 13.8202 10.6296 11.2769 7.46925 11.2769C4.30894 11.2769 1.74497 13.8202 1.50534 17.1928C1.47562 17.611 1.46006 18.0806 1.44366 18.5778C1.39434 20.0707 1.33284 21.9288 0.826031 22.9426C0.727031 23.1405 0.727031 23.3736 0.826031 23.5715C0.84475 23.6089 1.08897 24.0814 1.7343 24.6441C2.36464 25.1938 2.38345 26.319 1.63538 26.6931C0.626625 27.1978 0 28.2117 0 29.3394V34.534C0 34.9223 0.314812 35.2372 0.703125 35.2372C1.09144 35.2372 1.40625 34.9223 1.40625 34.534V29.3394C1.40625 28.7477 1.73503 28.2157 2.26425 27.951L3.60542 27.2804C4.21069 26.9778 4.94069 27.0868 5.43122 27.5529C6.00272 28.096 6.73584 28.3674 7.46925 28.3674C8.20247 28.3674 8.93597 28.0958 9.50728 27.5529L10.0827 27.0061C10.2278 26.8682 10.4438 26.836 10.6229 26.9255C10.8737 27.0509 10.9593 27.3645 10.8453 27.6207C10.6385 28.0849 10.5251 28.5948 10.5251 29.1225V34.5342C10.5251 34.9225 10.8399 35.2373 11.2282 35.2373C11.6166 35.2373 11.9314 34.9225 11.9314 34.5342V29.1225C11.9314 28.1572 12.5408 27.2871 13.4481 26.9572L14.7922 26.4684C16.6805 25.7818 18.7934 26.475 19.9079 28.1468C20.1506 28.511 20.5396 28.7445 20.9751 28.7876C21.0236 28.7924 21.0719 28.7947 21.1201 28.7947C21.5043 28.7947 21.8723 28.6435 22.1473 28.3686C22.5748 27.9411 23.3057 28.2438 23.3057 28.8484V34.5343C23.3057 34.9226 23.6205 35.2374 24.0088 35.2374C24.3971 35.2374 24.7119 34.9226 24.7119 34.5343V28.8484C24.7119 28.2438 25.4428 27.9411 25.8703 28.3686C26.1453 28.6436 26.5133 28.7948 26.8975 28.7948C26.9456 28.7948 26.994 28.7925 27.0425 28.7877C27.478 28.7446 27.867 28.5111 28.1097 28.1469C29.2243 26.4752 31.3372 25.782 33.2255 26.4686L34.5696 26.9573C35.4767 27.2872 36.0863 28.1574 36.0863 29.1227V34.5344C36.0863 34.9227 36.4011 35.2375 36.7894 35.2375C37.1777 35.2375 37.4925 34.9227 37.4925 34.5344V29.1227C37.4925 28.2077 37.1523 27.3459 36.5745 26.6833C36.5207 26.6216 36.5475 26.523 36.6263 26.5005C37.0269 26.386 37.4785 26.4622 37.7731 26.7568L38.4171 27.4008C38.8388 27.8226 39.0758 28.3945 39.0758 28.9909V34.5344C39.0758 34.9227 39.3906 35.2375 39.7789 35.2375C40.1672 35.2375 40.482 34.9227 40.482 34.5344V28.9755C40.482 28.389 40.715 27.8265 41.1297 27.4118L42.0368 26.5047C42.1727 26.3687 42.379 26.3427 42.5639 26.3955L45.4679 27.2252C46.1307 27.4146 46.5938 28.0283 46.5938 28.7178V34.5344C46.5938 34.9227 46.9086 35.2375 47.2969 35.2375C47.6852 35.2375 48 34.9227 48 34.5344V28.7178C48 27.4036 47.1176 26.2337 45.8542 25.8727Z" fill="white"></path> </svg>',
                      }}
                    />
                  </div>
                }
                title="Para todo o time"
                description="Oferece análises, relatórios e estatísticas inteligentes que ajudam na tomada de decisão e na melhora do atendimento."
              />
            </div>

            <div className="flex items-start gap-5 self-stretch relative max-md:flex-col">
              <div
                dangerouslySetInnerHTML={{
                  __html:
                    '<svg width="265" height="279" viewBox="0 0 265 279" fill="none" xmlns="http://www.w3.org/2000/svg" style="display: flex; flex-direction: column; align-items: flex-start; gap: 20px; flex: 1 0 0; align-self: stretch; position: relative"> <rect width="78" height="78" rx="10" fill="#00BBD4"></rect> <path d="M54.6065 33.3321C54.2403 32.9661 53.6466 32.9661 53.2806 33.3321C52.9145 33.6983 52.9145 34.2919 53.2806 34.658C53.295 34.6724 53.31 34.6871 53.3245 34.7006C53.5055 34.87 53.7356 34.9539 53.9653 34.9539C54.2154 34.9539 54.4652 34.8542 54.6497 34.6572C55.0037 34.2795 54.9843 33.6861 54.6065 33.3321Z" fill="white"></path> <path d="M52.7018 31.7924C52.1652 31.428 51.6142 31.1029 51.0643 30.8261C47.9531 29.2606 44.6562 28.9463 41.5298 29.9172C41.0354 30.0709 40.7589 30.5961 40.9126 31.0906C41.0661 31.5851 41.5912 31.8613 42.086 31.7078C44.7394 30.8837 47.5526 31.158 50.2213 32.5008C50.692 32.7379 51.1856 33.0295 51.6487 33.3437C51.81 33.4532 51.9931 33.5058 52.1743 33.5058C52.4745 33.5058 52.7696 33.3618 52.9509 33.0947C53.2417 32.6663 53.1301 32.0832 52.7018 31.7924Z" fill="white"></path> <path d="M61.2309 43.9245C61.1406 43.7351 61.0364 43.5166 60.9297 43.2873C60.3491 42.04 60.5518 40.6946 60.7334 39.3308C61.0168 37.2027 60.4518 34.985 59.0689 32.9135C56.2608 28.7067 50.5055 25.6533 45.3843 25.6533C41.3924 25.6533 36.4968 23.0678 32.8053 21.5487C31.8665 21.1624 30.8388 20.9492 29.7621 20.9492C25.3366 20.9492 21.7362 24.5495 21.7362 28.975C21.7362 31.7906 23.1711 34.3506 25.5743 35.8229C26.2671 36.2474 26.4239 36.6144 26.4796 37.2156V40.4165V42.7014C26.4796 43.7483 27.331 44.5999 28.3775 44.5999C28.5596 44.5999 28.7129 44.7356 28.7389 44.9158C29.2261 48.2857 30.7096 51.3877 33.0183 53.8813C33.8429 54.7719 34.4296 55.8275 34.0941 56.9939C33.8039 58.0031 33.3586 59.124 32.9659 60.1131C32.703 60.7749 32.476 61.3465 32.3387 61.7786C32.2483 62.0636 32.2991 62.3746 32.4756 62.616C32.6522 62.8573 32.9333 63 33.2323 63H46.6145C47.0542 63 47.6582 62.8031 48.0383 61.8652C48.2038 61.457 48.3118 60.9659 48.4263 60.4459C48.5317 59.9661 48.7716 58.8764 49.0173 58.6406C49.042 58.6169 49.0767 58.6072 49.1105 58.6124C49.1586 58.6201 49.2073 58.6238 49.256 58.6238C50.6575 58.6238 51.8027 58.6873 52.813 58.7433C55.7763 58.9078 57.5651 59.007 59.1342 56.7469C59.9322 55.5974 59.8417 53.9208 59.746 52.1459C59.7169 51.6067 59.6873 51.0573 59.6864 50.5363C59.6852 49.8657 60.0134 49.2626 60.5663 48.8831C61.4501 48.2761 62.2847 47.703 62.2847 46.8316C62.285 46.1692 61.8844 45.2954 61.2309 43.9245ZM26.554 34.2239C24.7114 33.0952 23.6113 31.133 23.6113 28.9749C23.6113 25.5832 26.3706 22.8241 29.7622 22.8241C33.1539 22.8241 35.9131 25.5833 35.9131 28.9749C35.9131 31.133 34.8132 33.0953 32.9704 34.2241C32.6314 34.4319 32.3456 34.6531 32.1083 34.8952C31.4591 35.5576 30.6895 36.2324 29.7619 36.2321C28.8348 36.2324 28.0655 35.5581 27.4167 34.8958C27.1793 34.6534 26.8933 34.432 26.554 34.2239ZM-nan -nanL31.1699 38.1078V38.7934C31.1699 39.172 30.8629 39.4789 30.4843 39.4789H29.0403C28.6617 39.4789 28.3547 39.172 28.3547 38.7934C28.3547 38.4147 28.6617 38.1078 29.0403 38.1078H31.1699L-nan -nanZM28.3777 42.7248C28.3673 42.7248 28.3547 42.7119 28.3547 42.7014C28.3547 41.9572 28.958 41.3539 29.7022 41.3539H29.8224C30.5666 41.3539 31.1699 41.9572 31.1699 42.7014C31.1699 42.7119 31.1572 42.7248 31.1466 42.7248H28.3777ZM59.5051 47.3373C58.6982 47.8915 58.0013 48.37 57.8985 49.0941C57.7573 50.0875 57.8166 51.1852 57.8738 52.2467C57.9448 53.5636 58.0253 55.0562 57.594 55.6775C56.6612 57.0213 55.8544 57.0344 52.917 56.8711C51.9478 56.8173 50.7452 56.7507 49.3193 56.7486C48.7717 56.6841 47.8452 56.7869 47.2269 57.9888C46.9129 58.5991 46.7441 59.3661 46.5952 60.0427C46.4508 60.6992 45.871 61.1248 45.1988 61.1248H34.926C34.7609 61.1248 34.6479 60.9584 34.7088 60.805C36.0594 57.4044 36.9147 54.9103 35.6127 53.7847C32.996 51.5215 31.2782 48.4984 30.677 45.1509C30.625 44.8608 30.852 44.5998 31.1467 44.5998C32.1934 44.5998 33.0449 43.7482 33.0449 42.7014V40.4164V37.2174C33.1005 36.615 33.2568 36.2478 33.9502 35.8228C34.6407 35.3998 35.2512 34.8869 35.7706 34.304C37.933 31.8772 39.3922 28.3972 42.5856 27.7909C43.5024 27.6168 44.439 27.5282 45.3844 27.5282C49.8513 27.5282 55.0641 30.2909 57.5096 33.9544C58.5577 35.5245 59.5701 37.9672 58.4406 40.6869C58.0727 41.5725 58.6182 42.8008 59.5384 44.7313C59.6714 45.0103 59.831 45.345 59.9771 45.6677C60.2552 46.2822 60.0612 46.9555 59.5051 47.3373Z" fill="white"></path> <path d="M34.3608 27.6503C34.0839 26.6242 33.3957 25.8024 32.3704 25.2734C31.9103 25.0363 31.3449 25.2167 31.1074 25.677C30.8701 26.1372 31.0507 26.7026 31.5109 26.9399C32.0707 27.2286 32.4108 27.6209 32.5505 28.1386C32.7716 28.9584 32.4371 29.8454 32.2903 30.0757C32.012 30.5124 32.1403 31.0919 32.5769 31.3703C32.7331 31.4699 32.9076 31.5173 33.08 31.5173C33.3896 31.5173 33.6926 31.3642 33.8713 31.0837C34.2713 30.4566 34.7316 29.0245 34.3608 27.6503Z" fill="white"></path> <path d="M29.7622 15C29.2444 15 28.8247 15.4199 28.8247 15.9375V18.6101C28.8247 19.1277 29.2444 19.5476 29.7622 19.5476C30.2799 19.5476 30.6997 19.1277 30.6997 18.6101V15.9375C30.6997 15.4199 30.2799 15 29.7622 15Z" fill="white"></path> <path d="M19.3601 28.0748H16.6875C16.1697 28.0748 15.75 28.4947 15.75 29.0123C15.75 29.5301 16.1697 29.9498 16.6875 29.9498H19.3602C19.8779 29.9498 20.2976 29.5301 20.2976 29.0123C20.2976 28.4947 19.8778 28.0748 19.3601 28.0748Z" fill="white"></path> <path d="M25.3727 19.534L24.0368 17.2204C23.7778 16.772 23.2044 16.6186 22.7561 16.8773C22.3077 17.1363 22.1541 17.7095 22.413 18.1579L23.749 20.4715C23.9227 20.7723 24.2377 20.9404 24.5617 20.9404C24.7208 20.9404 24.882 20.8999 25.0296 20.8146C25.478 20.5557 25.6316 19.9824 25.3727 19.534Z" fill="white"></path> <path d="M21.2216 22.9992L18.9078 21.6633C18.4594 21.4045 17.886 21.5582 17.6271 22.0064C17.3682 22.4548 17.5219 23.0281 17.9703 23.287L20.2841 24.6229C20.4317 24.7083 20.5929 24.7488 20.7519 24.7488C21.0759 24.7488 21.391 24.5807 21.5647 24.2798C21.8236 23.8316 21.67 23.2581 21.2216 22.9992Z" fill="white"></path> <path d="M41.8977 22.0063C41.6387 21.5579 41.0654 21.4042 40.6171 21.6631L38.3033 22.9991C37.8549 23.258 37.7012 23.8315 37.9601 24.2797C38.1338 24.5805 38.4489 24.7486 38.7729 24.7486C38.9319 24.7486 39.0931 24.7081 39.2408 24.6228L41.5546 23.2869C42.003 23.0279 42.1567 22.4547 41.8977 22.0063Z" fill="white"></path> <path d="M21.5652 33.745C21.3064 33.2966 20.7332 33.1431 20.2848 33.4017L17.9704 34.7376C17.5219 34.9964 17.3682 35.5698 17.627 36.0182C17.8007 36.3191 18.1159 36.4872 18.4399 36.4872C18.5989 36.4872 18.76 36.4467 18.9077 36.3615L21.222 35.0256C21.6704 34.7669 21.8241 34.1934 21.5652 33.745Z" fill="white"></path> <path d="M24.8368 37.5437C24.3885 37.2849 23.8151 37.4386 23.5561 37.8868L22.413 39.8671C22.1541 40.3155 22.3078 40.8889 22.7562 41.1477C22.9038 41.233 23.065 41.2735 23.224 41.2735C23.548 41.2735 23.8632 41.1054 24.0368 40.8046L25.18 38.8243C25.4389 38.3759 25.2853 37.8026 24.8368 37.5437Z" fill="white"></path> <path d="M36.7686 16.8773C36.3201 16.6184 35.7469 16.772 35.488 17.2205L34.1521 19.534C33.8931 19.9824 34.0467 20.5558 34.4951 20.8146C34.6427 20.8999 34.804 20.9404 34.963 20.9404C35.287 20.9404 35.6021 20.7723 35.7758 20.4715L37.1117 18.158C37.3707 17.7095 37.217 17.1363 36.7686 16.8773Z" fill="white"></path> <text fill="#0B1F3B" xml:space="preserve" style="white-space: pre" font-family="Poppins" font-size="16" font-weight="600" letter-spacing="0em"><tspan x="0" y="114.1">Soluções criativas</tspan></text> <rect y="127" width="78" height="6" fill="#00BBD4"></rect> <text fill="#44494F" xml:space="preserve" style="white-space: pre" font-family="Montserrat" font-size="16" letter-spacing="0em"><tspan x="0" y="169.236">Descubra insights valiosos </tspan><tspan x="0" y="190.236">por meio de análises </tspan><tspan x="0" y="211.236">avançadas e estatísticas que </tspan><tspan x="0" y="232.236">impulsionam decisões </tspan><tspan x="0" y="253.236">estratégicas.</tspan></text> </svg>',
                }}
              />

              <ResourceCard
                icon={
                  <div
                    dangerouslySetInnerHTML={{
                      __html:
                        '<svg width="78" height="78" viewBox="0 0 78 78" fill="none" xmlns="http://www.w3.org/2000/svg" style="width: 78px; height: 78px; position: relative"> <rect width="78" height="78" rx="10" fill="#00BBD4"></rect> <path d="M57.9034 27.7501C55.0931 27.7501 52.8069 30.0364 52.8069 32.8466C52.8069 33.8716 53.112 34.826 53.6348 35.626L48.1286 41.1322C47.3285 40.6095 46.374 40.3043 45.3491 40.3043C44.287 40.3043 43.2999 40.6313 42.4826 41.1894L36.9196 35.6256C37.4423 34.8257 37.7473 33.8713 37.7473 32.8465C37.7473 30.0363 35.461 27.75 32.6507 27.75C29.8405 27.75 27.5542 30.0363 27.5542 32.8465C27.5542 33.8713 27.8593 34.8254 28.3818 35.6254L22.8754 41.1319C22.0755 40.6093 21.1212 40.3043 20.0965 40.3043C17.2863 40.3043 15 42.5907 15 45.4009C15 48.2112 17.2863 50.4975 20.0965 50.4975C22.9068 50.4975 25.193 48.2112 25.193 45.4009C25.193 44.376 24.8879 43.4215 24.3651 42.6214L29.8713 37.1152C30.6714 37.638 31.6259 37.9431 32.6508 37.9431C33.6757 37.9431 34.6302 37.638 35.4301 37.1152L41.0238 42.7097C40.5358 43.4913 40.2526 44.4135 40.2526 45.4008C40.2526 48.2111 42.5389 50.4974 45.3492 50.4974C48.1594 50.4974 50.4457 48.2111 50.4457 45.4008C50.4457 44.3761 50.1406 43.4219 49.6182 42.622L55.1246 37.1155C55.9246 37.6381 56.8789 37.943 57.9035 37.943C60.7137 37.943 63 35.6568 63 32.8465C63 30.0362 60.7136 27.7501 57.9034 27.7501ZM20.0965 48.3908C18.4478 48.3908 17.1066 47.0495 17.1066 45.4009C17.1066 43.7523 18.4478 42.411 20.0965 42.411C21.7451 42.411 23.0865 43.7523 23.0865 45.4009C23.0865 47.0495 21.7452 48.3908 20.0965 48.3908ZM32.6508 35.8365C31.0023 35.8365 29.6609 34.4952 29.6609 32.8465C29.6609 31.1979 31.0022 29.8566 32.6508 29.8566C34.2995 29.8566 35.6408 31.1978 35.6408 32.8465C35.6408 34.4952 34.2995 35.8365 32.6508 35.8365ZM45.3491 48.3908C43.7005 48.3908 42.3591 47.0495 42.3591 45.4008C42.3591 43.7522 43.7004 42.4109 45.3491 42.4109C46.9977 42.4109 48.339 43.7522 48.339 45.4008C48.3389 47.0495 46.9977 48.3908 45.3491 48.3908ZM57.9034 35.8365C56.2548 35.8365 54.9134 34.4952 54.9134 32.8465C54.9134 31.1979 56.2547 29.8566 57.9034 29.8566C59.552 29.8566 60.8933 31.1978 60.8933 32.8465C60.8933 34.4952 59.552 35.8365 57.9034 35.8365Z" fill="white"></path> <path d="M31.5724 40.6909C31.1611 40.2797 30.4942 40.2797 30.0828 40.6909L27.9403 42.8335C27.529 43.2448 27.529 43.9117 27.9404 44.3231C28.1461 44.5288 28.4156 44.6316 28.6851 44.6316C28.9547 44.6316 29.2243 44.5287 29.4299 44.3231L31.5724 42.1805C31.9839 41.7692 31.9839 41.1022 31.5724 40.6909Z" fill="white"></path> <path d="M50.0589 33.9618C49.6475 33.5505 48.9807 33.5505 48.5692 33.9619L46.4266 36.1044C46.0153 36.5157 46.0153 37.1826 46.4266 37.594C46.6324 37.7996 46.9019 37.9024 47.1715 37.9024C47.4411 37.9024 47.7105 37.7995 47.9162 37.5939L50.0588 35.4514C50.4701 35.04 50.4701 34.373 50.0589 33.9618Z" fill="white"></path> </svg>',
                    }}
                  />
                }
                title="Análises e estatísticas"
                description="Oferece análises, relatórios e estatísticas inteligentes que ajudam na tomada de decisão e no aprimoramento do atendimento."
                accentColor="#00BBD4"
              />
            </div>
          </div>
        </section>

        {/* Technology Section */}
        <section className="flex w-[1140px] justify-center items-start absolute h-96 px-0 py-20 left-[150px] top-[1432px] max-md:w-[90%] max-md:flex-col max-md:h-auto max-md:left-[5%]">
          <div className="flex flex-col items-start gap-2.5 flex-[1_0_0] self-stretch relative p-2.5" />
          <div className="flex w-[475px] flex-col items-start gap-5 shrink-0 relative p-2.5">
            <div className="w-[57px] h-1.5 relative bg-[#0061FE]" />
            <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] relative max-sm:text-xl">
              Tecnologia que Transforma
            </h2>
            <p className="self-stretch text-[#44494F] text-base font-normal leading-[20.8px] relative max-sm:text-sm max-sm:w-full">
              Leve sua clínica para o próximo nível com recursos avançados de
              inteligência artificial, automação de tarefas e integração
              completa de dados. Simplifique processos, reduza erros e
              potencialize a experiência de colaboradores e pacientes com
              soluções digitais inteligentes e inovadoras.
            </p>
          </div>
        </section>

        {/* Business Section */}
        <section className="flex w-[1140px] justify-center items-start absolute h-[547px] px-0 py-20 left-[150px] top-[1806px] max-md:w-[90%] max-md:flex-col max-md:h-auto max-md:left-[5%]">
          <div className="flex w-[475px] flex-col justify-center items-start gap-5 shrink-0 self-stretch relative p-2.5">
            <div className="w-[57px] h-1.5 relative bg-[#0061FE]" />
            <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] relative max-sm:text-xl">
              Melhores oportunidades de negócios para sua empresa
            </h2>
            <p className="self-stretch text-[#44494F] text-base font-normal leading-[20.8px] relative max-sm:text-sm max-sm:w-full">
              Com o sistema, empresas que administram clínicas podem centralizar
              prontuários, agendamentos, exames e faturamento em uma única
              plataforma. A inteligência artificial ajuda a otimizar processos,
              reduzir erros, aumentar a eficiência operacional e garantir a
              segurança e a privacidade dos dados de pacientes e colaboradores.
            </p>
          </div>
          <img
            src={ilustracao3}
            alt="Business solutions illustration"
            className="flex flex-col items-end gap-2.5 flex-[1_0_0] self-stretch relative"
          />
        </section>

        {/* Doctors Section */}
        <div className="text-white text-base font-bold leading-[20.8px] absolute w-[221px] h-[21px] left-40 top-[2466px]">
          Melhorando Atendimentos
        </div>

        <div className="flex w-[475px] h-[161px] justify-center items-center shrink-0 absolute left-[815px] top-[2496px] max-md:w-[90%] max-md:left-[5%] max-md:top-[2200px]">
          <div className="w-[475px] h-[161px] shrink-0 absolute left-0 top-0">
            <div className="w-[57px] h-1.5 shrink-0 absolute bg-[#0061FE] left-2.5 top-2.5" />
            <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] absolute w-44 h-8 left-2.5 top-9 max-sm:text-xl">
              Para médicos
            </h2>
            <p className="w-[455px] text-[#44494F] text-base font-normal leading-[20.8px] absolute h-[63px] left-2.5 top-[84px] max-sm:text-sm max-sm:w-full">
              AVICIA é uma plataforma de saúde com inteligência artificial que
              ajuda profissionais a otimizar atendimentos e tomar decisões mais
              precisas.
            </p>
          </div>
        </div>

        <img
          src={foto1}
          alt="Medical professionals using platform"
          className="w-[319px] h-[220px] absolute rounded-[9.58px] left-[167px] top-[2496px]"
        />

        <img
          src={foto2}
          alt="Healthcare dashboard interface"
          className="w-[510px] h-[291px] absolute rounded-[19.019px] left-[200px] top-[2614px]"
        />

        {/* Doctor Benefits */}
        <div className="absolute left-[825px] top-[2657px]">
          <ChecklistItem text="AVICIA otimiza o tempo médico com automação inteligente, facilitando decisões clínicas com mais segurança." />
        </div>

        <div className="absolute left-[825px] top-[2727px]">
          <ChecklistItem
            text="Com inteligência artificial integrada, AVICIA reduz tarefas administrativas e aumenta o foco no cuidado ao paciente."
            iconColor="#0360D9"
          />
        </div>

        <div className="absolute left-[825px] top-[2797px]">
          <ChecklistItem
            text="Com Avicia, pacientes têm acesso seguro ao histórico médico, resultados de exames e informações de saúde de forma prática."
            iconColor="#0360D9"
          />
        </div>

        <div className="absolute left-[825px] top-[2867px]">
          <ChecklistItem
            text="Avicia conecta pacientes à sua saúde digital, permitindo organizar exames, consultas e registros de forma rápida e segura."
            iconColor="#0360D9"
          />
        </div>

        {/* Patients Section */}
        <div className="text-white text-base font-bold leading-[20.8px] absolute w-[205px] h-[21px] left-[1077px] top-[3170px]">
          Melhorando seu controle
        </div>

        <div className="flex w-[475px] h-[161px] justify-center items-center shrink-0 absolute left-[151px] top-[3150px] max-md:w-[90%] max-md:left-[5%] max-md:top-[2800px]">
          <div className="w-[475px] h-[161px] shrink-0 absolute left-0 top-0">
            <div className="w-[57px] h-1.5 shrink-0 absolute bg-[#0061FE] left-2.5 top-2.5" />
            <h2 className="text-[#0B1F3B] text-[25px] font-bold leading-[32.5px] absolute w-48 h-8 left-2.5 top-9 max-sm:text-xl">
              Para pacientes
            </h2>
            <p className="w-[455px] text-[#44494F] text-base font-normal leading-[20.8px] absolute h-[63px] left-2.5 top-[84px] max-sm:text-sm max-sm:w-full">
              Avicia é uma plataforma de saúde com inteligência artificial que
              ajuda pacientes a acompanhar exames, consultas e histórico de
              forma simples e segura.
            </p>
          </div>
        </div>

        <div className="w-[636px] h-[420px] absolute left-[747px] top-[3170px]">
          <img
            src={foto3}
            alt="Patient mobile app interface"
            className="w-[347px] h-[336px] shadow-[0_0_48px_0_rgba(0,0,0,0.16)] absolute rounded-[36px] left-[289px] top-[37px]"
          />
          <img
            src={foto4}
            alt="Patient dashboard interface"
            className="w-[323px] h-[420px] shadow-[0_0_44.665px_0_rgba(0,0,0,0.16)] absolute rounded-[33.498px] left-0 top-0"
          />
        </div>

        {/* Patient Benefits */}
        <div className="absolute left-40 top-[3320px]">
          <ChecklistItem
            text="Avicia é uma plataforma de saúde com inteligência artificial que facilita o acompanhamento de exames e consultas em um só lugar."
            iconColor="#0360D9"
          />
        </div>

        <div className="absolute left-40 top-[3390px]">
          <ChecklistItem
            text="Avicia é uma plataforma de saúde com inteligência artificial que facilita o acompanhamento de exames e consultas em um só lugar."
            iconColor="#0360D9"
          />
        </div>

        <div className="absolute left-40 top-[3460px]">
          <ChecklistItem
            text="AVICIA centraliza exames, histórico e prescrições em um único ambiente, proporcionando atendimento mais preciso."
            iconColor="#0360D9"
          />
        </div>

        <div className="absolute left-40 top-[3530px]">
          <ChecklistItem
            text="A inteligência da AVICIA transforma informações médicas em insights clínicos que elevam a qualidade do diagnóstico."
            iconColor="#0360D9"
          />
        </div>

        {/* Illustration */}
        <img
          src={ilustracao2}
          alt="Healthcare technology illustration"
          className="w-[674px] h-[673px] absolute left-9 top-[1084px]"
        />

        {/* Footer */}
        <div className="absolute top-[3689px]">
          <Footer />
        </div>
      </div>
    </main>
  );
};

export default Index;
