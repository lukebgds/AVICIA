export function DashboardSkeleton() {
  return (
    <div className="min-h-screen bg-gray-50">
      {/* ======================================== HEADER ======================================== */}
      <header className="flex items-center justify-between p-4 bg-white border-b border-gray-200">
        <div className="flex items-center gap-6">
          {/* Logo */}
          <div className="flex items-center gap-2">
            <div className="h-8 w-32 bg-gray-200 rounded animate-pulse" />
          </div>

          {/* Navegação */}
          <nav className="hidden md:flex gap-4">
            {[...Array(4)].map((_, i) => (
              <div
                key={i}
                className="h-10 w-28 bg-gray-200 rounded-lg animate-pulse"
              />
            ))}
          </nav>
        </div>

        <div className="flex items-center gap-4">
          {/* Notificações */}
          <div className="relative">
            <div className="h-10 w-10 bg-gray-200 rounded-full animate-pulse" />
          </div>

          {/* Perfil + Logout */}
          <div className="flex items-center gap-3 pl-2 border-l border-gray-200">
            <div className="h-10 w-10 rounded-full bg-gray-200 animate-pulse" />
            <div className="hidden md:block space-y-2">
              <div className="h-4 w-40 bg-gray-200 rounded animate-pulse" />
              <div className="h-3 w-24 bg-gray-200 rounded animate-pulse" />
            </div>
            <div className="h-10 w-24 bg-red-500 rounded-xl animate-pulse" />
          </div>
        </div>
      </header>

      {/* ======================================== MAIN ======================================== */}
      <main className="p-8">
        {/* Saudação */}
        <div className="mb-8 space-y-3">
          <div className="h-10 w-96 bg-gray-200 rounded-xl animate-pulse" />
          <div className="h-6 w-80 bg-gray-200 rounded animate-pulse" />
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* ======================================== COLUNA ESQUERDA (2/3) ======================================== */}
          <div className="lg:col-span-2 space-y-8">
            {/* Cards de estatísticas */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5">
              {[...Array(4)].map((_, i) => (
                <div
                  key={i}
                  className="flex flex-col justify-between bg-[#0061FE] text-white px-5 py-5 rounded-[28px] min-h-[140px] w-full shadow-lg"
                >
                  <div className="flex items-start justify-between">
                    <div className="h-5 w-32 bg-white/30 rounded animate-pulse" />
                    <div className="h-8 w-8 bg-white/20 rounded animate-pulse" />
                  </div>
                  <div className="mt-1">
                    <div className="h-12 w-32 bg-white/40 rounded animate-pulse" />
                    <div className="mt-2 h-4 w-40 bg-white/30 rounded animate-pulse" />
                  </div>
                </div>
              ))}
            </div>

            {/* Lista de Agendamentos do Dia */}
            <div className="bg-white rounded-xl shadow-sm p-6">
              <div className="h-8 w-64 bg-gray-200 rounded animate-pulse mb-4" />
              <div className="space-y-4">
                {[...Array(3)].map((_, i) => (
                  <div
                    key={i}
                    className="flex items-center p-4 rounded-lg border border-gray-200"
                  >
                    <div className="h-12 w-12 rounded-full bg-gray-200 animate-pulse flex-shrink-0" />
                    <div className="ml-4 flex-1 space-y-3">
                      <div className="h-6 w-64 bg-gray-200 rounded animate-pulse" />
                      <div className="h-5 w-48 bg-gray-200 rounded animate-pulse" />
                      <div className="h-5 w-36 bg-gray-200 rounded animate-pulse" />
                    </div>
                    <div className="h-7 w-20 bg-gray-200 rounded animate-pulse ml-6" />
                    <div className="ml-6 h-5 w-32 bg-gray-200 rounded animate-pulse" />
                  </div>
                ))}
              </div>
            </div>
          </div>

          {/* ======================================== COLUNA DIREITA (1/3) - AÇÕES RÁPIDAS ======================================== */}
          <div className="space-y-6">
            {/* Ações Rápidas */}
            <div className="bg-white rounded-[30px] shadow-sm p-6">
              <div className="h-8 w-48 bg-gray-200 rounded animate-pulse mb-6" />
              <div className="grid grid-cols-1 xl:grid-cols-2 gap-4">
                {[...Array(4)].map((_, i) => (
                  <div
                    key={i}
                    className="h-[65px] bg-[#0061FE] rounded-[38px] animate-pulse"
                  />
                ))}
              </div>
            </div>

            {/* Atividades Recentes */}
            <div className="bg-white rounded-[30px] shadow-sm p-6">
              <div className="h-8 w-56 bg-gray-200 rounded animate-pulse mb-4" />
              <div className="py-20 text-center">
                <div className="h-6 w-64 bg-gray-200 rounded mx-auto animate-pulse" />
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
