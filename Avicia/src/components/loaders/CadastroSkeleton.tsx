export function CadastroSkeleton() {
  return (
    <main className="flex min-h-screen">
      {/* ========================= COLUNA ESQUERDA (IMAGEM + LOGO) ========================= */}
      <div
        className="relative flex flex-col w-[60%] p-10 max-md:hidden"
        style={{
          background:
            "linear-gradient(180deg, #0575E6 0%, #02298A 84.79%, #021B79 100%)",
        }}
      >
        {/* Logo */}
        <div className="h-10 w-40 bg-white/30 rounded animate-pulse" />

        {/* Ilustração */}
        <div className="flex-grow flex items-center justify-center px-8">
          <div className="h-[550px] w-[420px] bg-white/20 rounded-2xl animate-pulse" />
        </div>
      </div>

      {/* ========================= COLUNA DIREITA (FORMULÁRIO) ========================= */}
      <div className="flex flex-col items-center justify-center w-[40%] p-10 bg-white max-md:w-full max-md:p-5 min-h-screen">
        {/* Logo mobile */}
        <div className="hidden max-md:block mb-8">
          <div className="h-10 w-32 bg-gray-200 rounded animate-pulse mx-auto" />
        </div>

        <div className="max-w-[480px] w-full px-4 md:px-0 space-y-8">
          {/* TÍTULOS */}
          <div className="space-y-3">
            <div className="h-8 w-72 bg-gray-200 rounded animate-pulse" />
            <div className="h-5 w-80 bg-gray-200 rounded animate-pulse" />
          </div>

          {/* FORM */}
          <div className="space-y-5">
            {/* NOME */}
            <div className="space-y-2">
              <div className="h-4 w-28 bg-gray-300 rounded animate-pulse" />
              <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
            </div>

            {/* CPF + TELEFONE */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {[...Array(2)].map((_, i) => (
                <div key={i} className="space-y-2">
                  <div className="h-4 w-20 bg-gray-300 rounded animate-pulse" />
                  <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
                </div>
              ))}
            </div>

            {/* EMAIL */}
            <div className="space-y-2">
              <div className="h-4 w-16 bg-gray-300 rounded animate-pulse" />
              <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
            </div>

            {/* SENHA + CONFIRMAR */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {[...Array(2)].map((_, i) => (
                <div key={i} className="space-y-2">
                  <div className="h-4 w-28 bg-gray-300 rounded animate-pulse" />
                  <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
                </div>
              ))}
            </div>

            {/* ENDEREÇO */}
            <div className="space-y-2">
              <div className="h-4 w-24 bg-gray-300 rounded animate-pulse" />
              <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
            </div>

            {/* DATA NASC + SEXO */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {[...Array(2)].map((_, i) => (
                <div key={i} className="space-y-2">
                  <div className="h-4 w-32 bg-gray-300 rounded animate-pulse" />
                  <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
                </div>
              ))}
            </div>

            {/* ESTADO CIVIL + PROFISSÃO */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {[...Array(2)].map((_, i) => (
                <div key={i} className="space-y-2">
                  <div className="h-4 w-28 bg-gray-300 rounded animate-pulse" />
                  <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
                </div>
              ))}
            </div>

            {/* CHECKBOX */}
            <div className="flex items-start gap-3 pt-2">
              <div className="h-4 w-4 bg-gray-300 rounded animate-pulse" />
              <div className="h-4 w-64 bg-gray-200 rounded animate-pulse" />
            </div>

            {/* BOTÃO */}
            <div className="h-11 w-full bg-blue-300 rounded-full animate-pulse mt-6" />

            {/* JÁ TEM CONTA */}
            <div className="h-4 w-48 bg-gray-200 rounded mx-auto animate-pulse" />
          </div>
        </div>
      </div>
    </main>
  );
}
