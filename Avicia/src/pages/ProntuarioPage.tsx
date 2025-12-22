import { useState, useEffect } from "react";
import { useSearchParams, useNavigate, Link } from "react-router-dom";
import { DashboardHeader } from "@/components/auth/dashboard/DashboardHeader";
import {
  ArrowLeft,
  Search,
  User,
  Phone,
  Mail,
  MapPin,
  FileText,
  ExternalLink,
  Calendar as CalendarIcon,
  FlaskConical,
  Info,
} from "lucide-react";
import {
  api,
  ConsultaResponse,
  PacienteResponse,
  ExameSolicitadoResponse,
  ConsultaPrescricaoResponse,
} from "@/services/api";
import { format, parseISO } from "date-fns";
import { memo } from "react";

type Tab = "dashboard" | "agenda" | "relatorios" | "pacientes";
type LocalTab = "Históricos" | "Exames" | "Prescrições";

const ProntuarioPage = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const cpfPaciente = searchParams.get("cpf") || "";
  const pacienteIdParam = searchParams.get("pacienteId");
  const idPaciente = pacienteIdParam ? Number(pacienteIdParam) : null;

  const [activeTab, setActiveTab] = useState<Tab>("pacientes");

  // Estado local das abas do prontuário
  const [localTab, setLocalTab] = useState<LocalTab>("Históricos");

  const TagStatus = memo(({ text }: { text: string }) => {
    const cores: Record<string, string> = {
      AGENDADO: "bg-blue-100 text-blue-800",
      CONFIRMADO: "bg-teal-100 text-teal-800",
      PRESENTE: "bg-green-100 text-green-800",
      CANCELADO: "bg-red-100 text-red-800",
      AUSENTE: "bg-gray-100 text-gray-800",
      CONCLUIDO: "bg-purple-100 text-purple-800",
      REALIZADO: "bg-purple-100 text-purple-800",
    };

    const classe = cores[text.toUpperCase()] || "bg-gray-100 text-gray-800";
    const textoFormatado =
      text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();

    return (
      <span className={`px-2 py-0.5 rounded text-[14px] font-medium ${classe}`}>
        {textoFormatado}
      </span>
    );
  });

  const [paciente, setPaciente] = useState<PacienteResponse | null>(null);
  const [consultas, setConsultas] = useState<ConsultaResponse[]>([]);
  const [exames, setExames] = useState<ExameSolicitadoResponse[]>([]);
  const [prescricoesGrupos, setPrescricoesGrupos] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [loadingPrescricao, setLoadingPrescricao] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searchExame, setSearchExame] = useState("");

  const handleHeaderTabChange = (tab: Tab) => {
    setActiveTab(tab);
    navigate(`/dashboard-medico?tab=${tab}`);
  };

  if (!idPaciente || !cpfPaciente) {
    return (
      <div className="min-h-screen bg-[#F5F5F5] flex items-center justify-center">
        <div className="text-center max-w-md">
          <p className="text-xl font-medium text-red-600 mb-4">
            Acesso inválido ao prontuário
          </p>
          <p className="text-gray-600 mb-8">
            Por favor, acesse o prontuário a partir da lista de pacientes.
          </p>
          <button
            onClick={() => navigate("/dashboard-medico")}
            className="px-8 py-3 bg-[#0061FE] text-white font-medium rounded-xl hover:bg-blue-700 transition-colors shadow-md"
          >
            Voltar ao Dashboard
          </button>
        </div>
      </div>
    );
  }

  useEffect(() => {
    const carregarDadosIniciais = async () => {
      if (!cpfPaciente || !idPaciente) return;

      setLoading(true);
      setError(null);

      try {
        const [pacienteRes, consultasRes, examesRes] = await Promise.all([
          api.getPacienteByCpf(cpfPaciente),
          api.getConsultasByPaciente(idPaciente),
          api.getExamesSolicitadosByPaciente(idPaciente),
        ]);

        setPaciente(pacienteRes);
        setConsultas(consultasRes);
        setExames(examesRes);
      } catch (err) {
        console.error("Erro ao carregar dados do prontuário:", err);
        setError("Erro ao carregar os dados do paciente. Tente novamente.");
      } finally {
        setLoading(false);
      }
    };

    carregarDadosIniciais();
  }, [cpfPaciente, idPaciente]);

  // Carrega prescrições apenas na aba Prescrições
  useEffect(() => {
    if (localTab !== "Prescrições" || consultas.length === 0) return;

    const carregarPrescricoes = async () => {
      setLoadingPrescricao(true);
      const grupos: any[] = [];

      for (const consulta of consultas) {
        if (!consulta.idConsulta) continue;

        try {
          const prescricoes = await api.getConsultaPrescricoesByConsulta(
            consulta.idConsulta
          );
          if (prescricoes.length > 0) {
            grupos.push({ consulta, prescricoes });
          }
        } catch (err) {
          console.warn(
            "Prescrição não encontrada para consulta:",
            consulta.idConsulta
          );
        }
      }

      grupos.sort((a, b) =>
        b.consulta.dataConsulta.localeCompare(a.consulta.dataConsulta)
      );
      setPrescricoesGrupos(grupos);
      setLoadingPrescricao(false);
    };

    if (prescricoesGrupos.length === 0) {
      carregarPrescricoes();
    } else {
      setLoadingPrescricao(false);
    }
  }, [localTab, consultas]);

  const consultasOrdenadas = [...consultas].sort((a, b) =>
    b.dataConsulta.localeCompare(a.dataConsulta)
  );

  const examesFiltrados = exames.filter(
    (ex) =>
      ex.nomeExame.toLowerCase().includes(searchExame.toLowerCase()) ||
      format(parseISO(ex.dataSolicitacao), "dd/MM/yyyy").includes(searchExame)
  );

  if (loading) {
    return (
      <div className="min-h-screen bg-[#F5F5F5] flex items-center justify-center">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-4 border-[#0061FE] border-t-transparent mb-4"></div>
          <p className="text-gray-600">Carregando prontuário...</p>
        </div>
      </div>
    );
  }

  if (error || !paciente) {
    return (
      <div className="min-h-screen bg-[#F5F5F5] flex items-center justify-center">
        <div className="text-center text-red-600 max-w-md">
          <p className="text-lg font-medium">
            {error || "Paciente não encontrado"}
          </p>
          <button
            onClick={() => navigate(-1)}
            className="inline-flex items-center gap-2 text-gray-500 hover:text-[#0061FE] mb-4 transition-colors font-medium text-sm"
          >
            <ArrowLeft size={18} /> Voltar para Meus Pacientes
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#F5F5F5]">
      <DashboardHeader
        activeTab={activeTab}
        onTabChange={handleHeaderTabChange}
      />

      <main className="p-8 max-w-[1400px] mx-auto">
        <Link
          to="/dashboard-medico?tab=pacientes"
          className="inline-flex items-center gap-2 text-gray-500 hover:text-[#0061FE] mb-4 transition-colors font-medium text-sm"
        >
          <ArrowLeft size={18} /> Voltar para Meus Pacientes
        </Link>

        <div className="flex items-center justify-between mb-6">
          <div>
            <h1 className="text-3xl font-bold text-[#1E255E]">
              Prontuário do Paciente
            </h1>
            <p className="text-gray-500">
              Informações completas de {paciente.usuario.nome}
            </p>
          </div>
        </div>

        {/* Abas locais do prontuário */}
        <div className="flex gap-2 mb-6 border-b border-gray-200 pb-1">
          {(["Históricos", "Exames", "Prescrições"] as LocalTab[]).map(
            (tab) => (
              <button
                key={tab}
                onClick={() => setLocalTab(tab)}
                className={`px-6 py-2 font-medium text-sm rounded-full transition-all ${
                  localTab === tab
                    ? "bg-[#0061FE] text-white shadow-sm"
                    : "text-gray-500 hover:text-[#0061FE] hover:bg-white"
                }`}
              >
                {tab}
              </button>
            )
          )}
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2 space-y-8">
            {/* Histórico de Consultas */}
            {localTab === "Históricos" && (
              <div>
                <h2 className="text-[#1E255E] font-bold mb-4 text-xl">
                  Histórico de Consultas
                </h2>
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <p className="text-sm text-gray-500 mb-4">
                    Consultas realizadas com este paciente
                  </p>

                  {consultasOrdenadas.length === 0 ? (
                    <div className="text-center py-12 text-gray-500">
                      Nenhuma consulta registrada
                    </div>
                  ) : (
                    <div className="space-y-4">
                      {consultasOrdenadas.map((c) => (
                        <div
                          key={c.idConsulta}
                          className="flex items-center justify-between p-5 rounded-xl border border-gray-200 hover:border-[#0061FE] hover:shadow-md transition-all duration-200 bg-white"
                        >
                          <div className="flex items-start gap-4 flex-1">
                            <div className="flex-1">
                              <div className="flex items-center gap-3">
                                <CalendarIcon
                                  size={20}
                                  className="text-[#0061FE]"
                                />
                                <div className="text-lg font-semibold text-[#1E255E]">
                                  Consulta -{" "}
                                  {format(
                                    parseISO(c.dataConsulta),
                                    "dd/MM/yyyy 'às' HH:mm"
                                  )}
                                </div>
                              </div>

                              <div className="text-sm text-gray-600 mt-1">
                                {c.tipoConsulta.charAt(0).toUpperCase() +
                                  c.tipoConsulta.slice(1).toLowerCase()}{" "}
                                •{" "}
                                <span className="inline-block px-2 py-0.5 rounded text-xs bg-amber-100 text-amber-800 font-medium">
                                  {c.localConsulta.charAt(0).toUpperCase() +
                                    c.localConsulta.slice(1).toLowerCase()}
                                </span>
                              </div>

                              <div className="mt-3">
                                <p className="text-sm font-medium text-gray-700">
                                  Anamnese:
                                </p>
                                <p className="text-sm text-gray-600 leading-relaxed">
                                  {c.anamnese || "Nenhuma anamnese registrada"}
                                </p>
                              </div>

                              <div className="mt-3">
                                <p className="text-sm font-medium text-gray-700">
                                  Observações:
                                </p>
                                <p className="text-sm italic text-gray-500 leading-relaxed">
                                  {c.observacoes ||
                                    "Nenhuma observação registrada"}
                                </p>
                              </div>
                            </div>
                          </div>

                          {/* Ação à direita */}
                          <a
                            href="#"
                            className="ml-6 flex items-center gap-2 text-sm font-medium text-[#0061FE] hover:underline whitespace-nowrap transition-colors"
                          >
                            Ver Detalhes <ExternalLink size={18} />
                          </a>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            )}

            {localTab === "Exames" && (
              <div>
                <h2 className="text-[#1E255E] font-bold mb-4 text-xl">
                  Exames Solicitados
                </h2>
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <p className="text-sm text-gray-500 mb-4">
                    Exames solicitados para este paciente
                  </p>

                  <div className="relative w-full max-w-md mb-6">
                    <input
                      type="text"
                      placeholder="Buscar exames por nome ou data..."
                      value={searchExame}
                      onChange={(e) => setSearchExame(e.target.value)}
                      className="w-full pl-4 pr-10 py-2.5 rounded-full border border-gray-300 text-sm focus:outline-none focus:border-[#0061FE] focus:ring-1 focus:ring-[#0061FE]"
                    />
                    <Search className="absolute right-4 top-1/2 -translate-y-1/2 text-gray-400 w-4 h-4" />
                  </div>

                  {examesFiltrados.length === 0 ? (
                    <div className="text-center py-12 text-gray-500">
                      {searchExame
                        ? "Nenhum exame encontrado"
                        : "Nenhum exame solicitado"}
                    </div>
                  ) : (
                    <div className="space-y-4">
                      {examesFiltrados.map((ex) => (
                        <div
                          key={ex.idExameSolicitado}
                          className="flex items-center justify-between p-5 rounded-xl border border-gray-200 hover:border-[#0061FE] hover:shadow-md transition-all duration-200 bg-white"
                        >
                          <div className="flex items-start gap-4 flex-1">
                            <div className="flex-1">
                              <div className="flex items-center gap-3">
                                <FlaskConical
                                  size={20}
                                  className="text-[#0061FE]"
                                />
                                <div className="text-lg font-semibold text-[#1E255E]">
                                  {ex.nomeExame}
                                </div>
                              </div>

                              <div className="text-sm text-gray-600 mt-1">
                                Solicitado em{" "}
                                {format(
                                  parseISO(ex.dataSolicitacao),
                                  "dd/MM/yyyy 'às' HH:mm"
                                )}
                              </div>
                              <div className="flex items-center gap-2 mt-2">
                                <span className="text-sm font-medium text-gray-700">
                                  Status:
                                </span>
                                <TagStatus text={ex.status} />
                              </div>
                              <div className="mt-3">
                                <p className="text-sm font-medium text-gray-700">
                                  Observações:
                                </p>
                                <p className="text-sm italic text-gray-500">
                                  {ex.observacoes ||
                                    "Nenhuma observação registrada"}
                                </p>
                              </div>
                            </div>
                          </div>

                          {/* Ações à direita */}
                          <div className="flex items-center gap-3">
                            {/* Botão Ver PDF ou Pendente */}
                            {ex.status === "CONCLUIDO" ||
                            ex.status === "REALIZADO" ? (
                              <button
                                onClick={() =>
                                  navigate(
                                    `/pacientes/${idPaciente}/exame/${ex.idExameSolicitado}/otimizar`
                                  )
                                }
                                className="bg-[#0061FE] text-white px-5 py-2.5 rounded-full text-sm font-semibold hover:bg-blue-700 transition-colors shadow-md whitespace-nowrap"
                              >
                                Ver PDF
                              </button>
                            ) : (
                              <span className="bg-[#EAB308] text-white px-5 py-2.5 rounded-full text-sm font-semibold shadow-md whitespace-nowrap">
                                Pendente
                              </span>
                            )}

                            {/* Menu de opções */}
                            <div className="relative group">
                              <button className="text-gray-500 hover:text-[#0061FE] transition-colors p-2 rounded-lg hover:bg-gray-100">
                                <svg
                                  width="24"
                                  height="24"
                                  viewBox="0 0 20 20"
                                  fill="currentColor"
                                >
                                  <circle cx="10" cy="4" r="2.5" />
                                  <circle cx="10" cy="10" r="2.5" />
                                  <circle cx="10" cy="16" r="2.5" />
                                </svg>
                              </button>
                              <div className="absolute right-0 mt-2 w-64 bg-white rounded-xl shadow-xl border border-gray-200 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 z-20">
                                <button className="flex items-center gap-3 w-full px-5 py-4 text-sm text-gray-700 hover:bg-gray-50 first:rounded-t-xl">
                                  <FileText
                                    size={20}
                                    className="text-[#0061FE]"
                                  />
                                  Anexar Resultado
                                </button>
                                <button
                                  onClick={() =>
                                    navigate(
                                      "/dashboard-medico/prontuario/exame-otimizar"
                                    )
                                  }
                                  className="flex items-center gap-3 w-full px-5 py-4 text-sm text-gray-700 hover:bg-gray-50"
                                >
                                  <svg
                                    width="20"
                                    height="20"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                  >
                                    <path d="M12 2a3 3 0 0 0-3 3v7a3 3 0 0 0 6 0V5a3 3 0 0 0-3-3Z" />
                                    <path d="M19 10v2a7 7 0 0 1-14 0v-2" />
                                    <line x1="12" y1="19" x2="12" y2="23" />
                                    <line x1="8" y1="23" x2="16" y2="23" />
                                  </svg>
                                  Otimizar exame
                                </button>
                                <button className="flex items-center gap-3 w-full px-5 py-4 text-sm text-gray-700 hover:bg-gray-50 last:rounded-b-xl">
                                  <svg
                                    width="20"
                                    height="20"
                                    viewBox="0 0 24 24"
                                    fill="none"
                                    stroke="currentColor"
                                    strokeWidth="2"
                                  >
                                    <path d="M3 6h18" />
                                    <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
                                    <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" />
                                    <line x1="10" y1="11" x2="10" y2="17" />
                                    <line x1="14" y1="11" x2="14" y2="17" />
                                  </svg>
                                  Cancelar exame
                                </button>
                              </div>
                            </div>

                            {/* Ícone de informação */}
                            <button className="text-gray-500 hover:text-[#0061FE] transition-colors p-2 rounded-lg hover:bg-gray-100">
                              <Info size={22} />
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            )}

            {/* Prescrições Emitidas */}
            {localTab === "Prescrições" && (
              <div>
                <h2 className="text-[#1E255E] font-bold mb-4 text-xl">
                  Prescrições Emitidas
                </h2>
                <div className="bg-white rounded-xl shadow-sm p-6">
                  <p className="text-sm text-gray-500 mb-4">
                    Prescrições emitidas para este paciente
                  </p>

                  {loadingPrescricao ? (
                    <div className="text-center py-12">
                      <div className="inline-block animate-spin rounded-full h-10 w-10 border-4 border-[#0061FE] border-t-transparent"></div>
                      <p className="text-gray-500 mt-4">
                        Carregando prescrições...
                      </p>
                    </div>
                  ) : prescricoesGrupos.length === 0 ? (
                    <div className="text-center py-12 text-gray-500">
                      Nenhuma prescrição emitida para este paciente
                    </div>
                  ) : (
                    <div className="space-y-4">
                      {prescricoesGrupos.map(({ consulta, prescricoes }) => (
                        <div key={consulta.idConsulta} className="space-y-4">
                          <div className="text-sm text-gray-600 font-medium mb-2">
                            Consulta em{" "}
                            {format(
                              parseISO(consulta.dataConsulta),
                              "dd/MM/yyyy 'às' HH:mm"
                            )}
                          </div>
                          {prescricoes.map((p: ConsultaPrescricaoResponse) => (
                            <div
                              key={p.idPrescricao}
                              className="flex items-center p-4 rounded-lg border border-gray-200 hover:border-[#0061FE] transition-colors cursor-pointer"
                            >
                              <div className="w-12 h-12 rounded-full bg-gradient-to-br from-green-400 to-green-600 flex items-center justify-center text-white font-bold text-xl flex-shrink-0">
                                R
                              </div>
                              <div className="ml-4 flex-1">
                                <div className="text-[18px] font-semibold text-[#1E255E]">
                                  Prescrição -{" "}
                                  {format(
                                    parseISO(p.dataEmissao),
                                    "dd/MM/yyyy"
                                  )}
                                </div>
                                <div className="text-[14px] font-semibold text-gray-600 flex items-center gap-1">
                                  Status:
                                  <TagStatus text={p.status} />
                                </div>

                                <div className="mt-2">
                                  <p className="text-sm font-medium text-gray-700">
                                    Observações:
                                  </p>
                                  <p className="text-sm italic text-gray-500">
                                    {p.observacoes ||
                                      "Nenhuma observação registrada"}
                                  </p>
                                </div>
                              </div>
                              <a
                                href="#"
                                className="ml-6 flex items-center gap-1 text-sm text-[#0061FE] font-medium hover:underline whitespace-nowrap"
                              >
                                Ver Detalhes{" "}
                                <ExternalLink className="w-4 h-4" />
                              </a>
                            </div>
                          ))}
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            )}
          </div>

          {/* Card Lateral do Paciente */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-[30px] shadow-lg p-6 border border-gray-100 sticky top-4">
              <div className="flex justify-between items-start mb-6">
                <h2 className="text-lg font-bold text-[#1E255E]">
                  Dados do Paciente
                </h2>
                <span
                  className={`px-3 py-1 rounded-full text-xs font-medium ${
                    paciente.usuario.ativo
                      ? "bg-green-100 text-green-800"
                      : "bg-red-100 text-red-800"
                  }`}
                >
                  {paciente.usuario.ativo ? "Ativo" : "Inativo"}
                </span>
              </div>

              <div className="flex justify-center mb-6">
                <img
                  src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
                    paciente.usuario.nome
                  )}&background=random&bold=true`}
                  alt={paciente.usuario.nome}
                  className="w-32 h-32 rounded-full border-4 border-gray-100 shadow-inner"
                />
              </div>

              <div className="space-y-5 text-sm border-t border-gray-100 pt-6">
                <div>
                  <div className="flex items-center gap-2 text-[#1E255E] font-semibold mb-1">
                    <User size={16} /> Nome completo
                  </div>
                  <p className="font-medium text-gray-700">
                    {paciente.usuario.nome}
                  </p>
                </div>

                <div>
                  <div className="flex items-center gap-2 text-[#1E255E] font-semibold mb-1">
                    <FileText size={16} /> CPF
                  </div>
                  <p className="font-medium text-gray-700">
                    {paciente.usuario.cpf.replace(
                      /(\d{3})(\d{3})(\d{3})(\d{2})/,
                      "$1.$2.$3-$4"
                    )}
                  </p>
                </div>

                <div>
                  <div className="flex items-center gap-2 text-[#1E255E] font-semibold mb-1">
                    <Phone size={16} /> Telefone
                  </div>
                  <p className="font-medium text-gray-700">
                    {paciente.usuario.telefone?.replace(
                      /(\d{2})(\d{5})(\d{4})/,
                      "($1) $2-$3"
                    ) || "Não informado"}
                  </p>
                </div>

                <div>
                  <div className="flex items-center gap-2 text-[#1E255E] font-semibold mb-1">
                    <Mail size={16} /> E-mail
                  </div>
                  <p className="font-medium text-gray-700">
                    {paciente.usuario.email || "Não informado"}
                  </p>
                </div>

                <div>
                  <div className="flex items-center gap-2 text-[#1E255E] font-semibold mb-1">
                    <MapPin size={16} /> Endereço
                  </div>
                  <p className="font-medium text-gray-700">
                    {paciente.usuario.endereco || "Não informado"}
                  </p>
                </div>
              </div>

              <div className="flex gap-3 mt-8">
                <button className="flex-1 py-3 bg-[#0061FE] hover:bg-blue-700 text-white font-medium rounded-xl transition-colors">
                  Nova Consulta
                </button>
                <button
                  onClick={() => navigate(-1)} // ← aqui
                  className="flex-1 bg-[#00E640] hover:bg-green-600 text-white rounded-xl py-3 font-medium transition-colors"
                >
                  Sair
                </button>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default ProntuarioPage;
