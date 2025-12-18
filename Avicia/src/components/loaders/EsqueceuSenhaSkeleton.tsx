export function EsqueceuSenhaSkeleton() {
  return (
    <main className="flex min-h-screen">
      {/* ========================= COLUNA ESQUERDA (ILUSTRAÇÃO) ========================= */}
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
        {/* Logo Mobile */}
        <div className="hidden max-md:block mb-8">
          <div className="h-10 w-32 bg-gray-200 rounded animate-pulse mx-auto" />
        </div>

        <div className="max-w-[480px] w-full px-4 md:px-0 space-y-8">
          {/* Botão Voltar */}
          <div className="h-5 w-32 bg-gray-200 rounded animate-pulse" />

          {/* Cabeçalho */}
          <div className="space-y-3 mt-4">
            <div className="h-8 w-64 bg-gray-200 rounded animate-pulse" />
            <div className="h-5 w-80 bg-gray-200 rounded animate-pulse" />
          </div>

          {/* Formulário */}
          <div className="space-y-6">
            {/* Input Email */}
            <div className="space-y-2">
              <div className="h-4 w-16 bg-gray-300 rounded animate-pulse" />
              <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse" />
            </div>

            {/* Botão */}
            <div className="h-11 w-full bg-blue-300 rounded-full animate-pulse" />
          </div>

          {/* Link - Já lembra senha */}
          <div className="h-4 w-48 bg-gray-200 rounded mx-auto animate-pulse" />
        </div>
      </div>
    </main>
  );
}
