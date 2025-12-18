import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { DashboardHeader } from "@/components/auth/dashboard/DashboardHeader";
import { ArrowLeft } from "lucide-react";
import { api } from "@/services/api";

type Tab = "dashboard" | "agenda" | "relatorios" | "pacientes";

const OtimizarExamePage = () => {
  const navigate = useNavigate();

  const LOCAL_INPUT_PATH = "C:/raiox.jpeg"; 

  const PREVIEW_IMAGE_URL =
    "https://images.unsplash.com/photo-1530497610245-94d3c16cda28?q=80&w=1000&auto=format&fit=crop";

  const [brightness, setBrightness] = useState(0);
  const [contrast, setContrast] = useState(0);
  const [saturation, setSaturation] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [activeTab, setActiveTab] = useState<Tab>("pacientes");

  const handleHeaderTabChange = (tab: Tab) => {
    setActiveTab(tab);
    navigate(`/dashboard-medico?tab=${tab}`);
  };

  const handleGerarImagem = async () => {
    setLoading(true);
    setError(null);

    try {
      const resultado = await api.processarImagem({
        inputPath: LOCAL_INPUT_PATH,
        contrast,
        brightness,
        saturation,
      });

      console.log("Imagem processada:", resultado);

      alert("Nova imagem gerada com sucesso!");
      navigate(-1);
    } catch (err) {
      console.error("Erro ao processar imagem:", err);
      setError("Falha ao gerar a nova imagem. Tente novamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#F5F5F5]">
      <DashboardHeader
        activeTab={activeTab}
        onTabChange={handleHeaderTabChange}
      />

      <main className="p-8 max-w-[1400px] mx-auto">
        <Link
          to={-1 as any}
          className="inline-flex items-center gap-2 text-gray-500 hover:text-[#0061FE] mb-6 transition-colors font-medium text-sm"
        >
          <ArrowLeft size={18} /> Voltar
        </Link>

        {/* Título */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-[#1E255E]">
            Otimizando Exame
          </h1>
          <p className="text-gray-500 mt-2">
            Ajuste brilho, contraste e saturação para melhorar a visualização do
            exame.
          </p>
        </div>

        {/* Grid principal */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Coluna da Imagem */}
          <div className="lg:col-span-2">
            <div className="bg-white rounded-[30px] shadow-sm border border-gray-200 overflow-hidden">
              <img
                src={PREVIEW_IMAGE_URL}
                alt="Exame (Raio-X)"
                className="w-full h-auto object-contain"
                style={{
                  filter: `brightness(${100 + brightness}%) contrast(${
                    100 + contrast
                  }%) saturate(${100 + saturation}%)`,
                }}
              />
            </div>
          </div>

          {/* Coluna dos Controles */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-[30px] shadow-lg p-8 border border-gray-100 sticky top-4">
              <h2 className="text-xl font-bold text-[#1E255E] mb-8 text-center">
                Ajustes de Imagem
              </h2>

              {/* Sliders */}
              <div className="space-y-8">
                <div>
                  <label className="font-bold text-[#1E255E] block mb-3 text-center">
                    Brilho
                  </label>
                  <input
                    type="range"
                    min="-100"
                    max="100"
                    value={brightness}
                    onChange={(e) => setBrightness(Number(e.target.value))}
                    className="w-full h-3 bg-gray-200 rounded-lg appearance-none cursor-pointer accent-[#0061FE]"
                  />
                  <div className="text-center mt-2 text-lg font-bold text-gray-700">
                    {brightness}
                  </div>
                </div>

                <div>
                  <label className="font-bold text-[#1E255E] block mb-3 text-center">
                    Contraste
                  </label>
                  <input
                    type="range"
                    min="-100"
                    max="100"
                    value={contrast}
                    onChange={(e) => setContrast(Number(e.target.value))}
                    className="w-full h-3 bg-gray-200 rounded-lg appearance-none cursor-pointer accent-[#0061FE]"
                  />
                  <div className="text-center mt-2 text-lg font-bold text-gray-700">
                    {contrast}
                  </div>
                </div>

                <div>
                  <label className="font-bold text-[#1E255E] block mb-3 text-center">
                    Saturação
                  </label>
                  <input
                    type="range"
                    min="-100"
                    max="100"
                    value={saturation}
                    onChange={(e) => setSaturation(Number(e.target.value))}
                    className="w-full h-3 bg-gray-200 rounded-lg appearance-none cursor-pointer accent-[#0061FE]"
                  />
                  <div className="text-center mt-2 text-lg font-bold text-gray-700">
                    {saturation}
                  </div>
                </div>
              </div>

              {/* Mensagem de erro */}
              {error && (
                <div className="mt-6 p-4 bg-red-50 border border-red-200 rounded-xl text-red-700 text-sm text-center">
                  {error}
                </div>
              )}

              {/* Botão principal */}
              <button
                onClick={handleGerarImagem}
                disabled={loading}
                className="w-full mt-10 py-4 bg-[#0061FE] hover:bg-blue-700 disabled:bg-blue-300 text-white font-bold rounded-full transition-colors shadow-md text-lg"
              >
                {loading ? (
                  <span className="flex items-center justify-center gap-3">
                    <div className="inline-block animate-spin rounded-full h-5 w-5 border-3 border-white border-t-transparent"></div>
                    Processando...
                  </span>
                ) : (
                  "Gerar nova imagem"
                )}
              </button>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default OtimizarExamePage;
