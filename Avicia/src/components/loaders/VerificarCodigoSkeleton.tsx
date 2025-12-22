export function VerificarCodigoSkeleton() {
  return (
    <main className="flex min-h-screen">
      {/* ================================ COLUNA DA ESQUERDA (IMAGEM) ================================ */}
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
          <div className="h-[600px] w-[500px] bg-white/20 rounded-2xl animate-pulse" />
        </div>
      </div>

      {/* ================================ COLUNA DA DIREITA (FORMULÁRIO) ================================ */}
      <div className="flex flex-col items-center justify-center w-[40%] p-10 bg-white max-md:w-full max-md:p-5 min-h-screen">
        {/* Logo mobile */}
        <div className="hidden max-md:block mb-8">
          <div className="h-10 w-32 bg-gray-200 rounded animate-pulse mx-auto" />
        </div>

        <div className="max-w-[480px] w-full px-4 md:px-0 space-y-8">
          {/* Botão "Voltar ao login" */}
          <div className="flex items-center gap-2">
            <div className="h-5 w-5 bg-gray-300 rounded animate-pulse" />
            <div className="h-4 w-32 bg-gray-300 rounded animate-pulse" />
          </div>

          {/* Títulos */}
          <div className="space-y-3">
            <div className="h-8 w-64 bg-gray-200 rounded animate-pulse" />
            <div className="h-5 w-96 bg-gray-200 rounded animate-pulse" />
          </div>

          {/* Input do código */}
          <div className="space-y-6">
            <div className="space-y-2">
              <div className="h-4 w-32 bg-gray-300 rounded animate-pulse" />
              <div className="relative">
                <div className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 bg-gray-300 rounded animate-pulse" />
                <div className="h-11 w-full bg-gray-200 rounded-3xl animate-pulse pl-10" />
              </div>
            </div>
          </div>

          {/* Botão principal */}
          <div className="h-11 w-full bg-blue-300 rounded-full animate-pulse" />

          {/* Link "Reenviar código" */}
          <div className="text-center space-y-2">
            <div className="h-4 w-64 bg-gray-200 rounded animate-pulse mx-auto" />
          </div>
        </div>
      </div>
    </main>
  );
}
