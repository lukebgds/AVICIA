import {
  Search,
  Calendar,
  Clock,
  Users,
  ExternalLink,
  PlusCircle,
  FileText,
  Settings,
  ArrowLeft,
  Mail,
  Phone,
  X,
  ChevronLeft, 
  ChevronRight,
  Pin,
} from "lucide-react";
import React, { useState, useEffect, useRef, useMemo, memo } from "react";
import { api, ConsultaResponse, PacienteResponse } from "@/services/api";
import { format, parseISO, addMinutes } from "date-fns";
import { ptBR } from "date-fns/locale";
import { DashboardHeader } from "@/components/auth/dashboard/DashboardHeader";
import { DayPicker } from "react-day-picker";
import { useNavigate, useLocation } from "react-router-dom";

interface PacienteMap {
  [idPaciente: number]: { nome: string };
}

interface AgendamentoHoje {
  idAgenda: number;
  nomePaciente: string;
  horario: string;
  status: string;
  horarioInicio: Date;
  horarioFim: Date;
  tipoConsulta: string;
  localConsulta: string;
  idConsulta?: number | null;
}

interface ConsultaRecente {
  [idPaciente: number]: string;
}

type Tab = "dashboard" | "agenda" | "relatorios" | "pacientes";

/* ******************************************************************
 *                         Componentes Internos
 * ******************************************************************/

const TagStatus = memo(({ text }: { text: string }) => {
  const cores: Record<string, string> = {
    AGENDADO: "bg-blue-100 text-blue-800",
    CONFIRMADO: "bg-teal-100 text-teal-800",
    PRESENTE: "bg-green-100 text-green-800",
    CANCELADO: "bg-red-100 text-red-800",
    AUSENTE: "bg-gray-100 text-gray-800",
    CONCLUIDO: "bg-purple-100 text-purple-800",
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

const StatCard = memo(
  ({
    title,
    value,
    delta,
    icon,
    isLongText = false,
  }: {
    title: string;
    value: string;
    delta?: React.ReactNode;
    icon: React.ReactNode;
    isLongText?: boolean;
  }) => (
    <div className="flex flex-col justify-between bg-[#0061FE] text-white px-5 py-5 rounded-[28px] min-h-[140px] w-full shadow-lg shadow-blue-200/50">
      <div className="flex items-start justify-between">
        <span className="text-base font-bold tracking-wide">{title}</span>
        <div className="opacity-90 scale-105">{icon}</div>
      </div>
      <div className="mt-1 flex flex-col justify-center min-h-[70px]">
        <div
          className={`${
            isLongText ? "text-xl" : "text-3xl"
          } font-bold leading-tight break-words`}
        >
          {value}
        </div>
        {delta && (
          <div className="text-xs font-light opacity-80 mt-1">{delta}</div>
        )}
      </div>
    </div>
  )
);

const QuickActionButton = memo(
  ({ icon: Icon, title, subtitle, onClick }: any) => (
    <button
      onClick={onClick}
      className="group flex items-center justify-start px-5 h-[65px] w-full bg-[#0061FE] text-white rounded-[38px] hover:bg-blue-700 transition-all shadow-md hover:shadow-lg"
    >
      <Icon className="w-6 h-6 mr-3 opacity-90 group-hover:scale-110 transition-transform" />
      <div className="flex flex-col items-start">
        <span className="text-sm font-bold leading-tight">{title}</span>
        <span className="text-[11px] font-light text-blue-100 opacity-80 leading-tight">
          {subtitle}
        </span>
      </div>
    </button>
  )
);

const MedicoDashboard = () => {
  /* ******************************************************************
   *                       Estados Principais
   * ******************************************************************/

  const [userInfo, setUserInfo] = useState<{
    idProfissional: number;
    idUsuario: number;
    nome: string;
    cargo: string;
  } | null>(null);

  const navigate = useNavigate();
  const [pacientes, setPacientes] = useState<PacienteResponse[]>([]);
  const [nomeMedico, setNomeMedico] = useState<string>("Carregando...");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [agendamentosHoje, setAgendamentosHoje] = useState<AgendamentoHoje[]>(
    []
  );

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const tabFromUrl = queryParams.get("tab") as Tab | null;

  const initialTab: Tab =
    tabFromUrl &&
    ["dashboard", "agenda", "relatorios", "pacientes"].includes(tabFromUrl)
      ? tabFromUrl
      : "dashboard";

  const [activeTab, setActiveTab] = useState<Tab>(initialTab);

  useEffect(() => {
    if (tabFromUrl) {
      navigate("/dashboard-medico", { replace: true });
    }
  }, [tabFromUrl, navigate]);

  const [searchTerm, setSearchTerm] = useState<string>("");
  const [consultaAtual, setConsultaAtual] = useState<
    (AgendamentoHoje & { emAndamento: boolean }) | null
  >(null);
  const [proximaConsultaFutura, setProximaConsultaFutura] =
    useState<AgendamentoHoje | null>(null);
  const [consultasRestantes, setConsultasRestantes] = useState<number>(0);
  const [consultasRealizadas, setConsultasRealizadas] = useState<number>(0);
  const [ultimaConsultaMap, setUltimaConsultaMap] = useState<ConsultaRecente>(
    {}
  );
  const [consultas, setConsultas] = useState<ConsultaResponse[]>([]);
  const [selectedDateCalendar, setSelectedDateCalendar] = useState<
    Date | undefined
  >(undefined);
  const [pacientesMap, setPacientesMap] = useState<PacienteMap>({});
  const [page, setPage] = useState(1);

  const inputBuscaRef = useRef<HTMLInputElement>(null);
  const ITEMS_PER_PAGE = 7;

  const consultasFiltradas = useMemo(() => {
    if (!selectedDateCalendar) return consultas;
    const dataStr = format(selectedDateCalendar, "yyyy-MM-dd");
    return consultas.filter(
      (c) => format(parseISO(c.dataConsulta), "yyyy-MM-dd") === dataStr
    );
  }, [consultas, selectedDateCalendar]);

  const diasComConsultaPassada = consultas
    .filter((c) => parseISO(c.dataConsulta) < new Date())
    .map((c) => parseISO(c.dataConsulta));

  const diasComConsultaFutura = consultas
    .filter((c) => parseISO(c.dataConsulta) >= new Date())
    .map((c) => parseISO(c.dataConsulta));

  const filteredPacientes = useMemo(() => {
    if (!searchTerm.trim()) return pacientes;

    const termoOriginal = searchTerm.toLowerCase().trim();
    const termoNumerico = termoOriginal.replace(/\D/g, "");

    return pacientes.filter((p) => {
      const nome = (p.usuario?.nome || "").toLowerCase();
      const cpfOriginal = p.usuario?.cpf || "";
      const cpfLimpo = cpfOriginal.replace(/\D/g, "");

      return (
        nome.includes(termoOriginal) ||
        cpfOriginal.toLowerCase().includes(termoOriginal) ||
        (termoNumerico && cpfLimpo.includes(termoNumerico))
      );
    });
  }, [pacientes, searchTerm]);

  /* ******************************************************************
   *             Focus na Busca ao Mudar de Aba
   * ******************************************************************/

  useEffect(() => {
    if (activeTab === "pacientes" && inputBuscaRef.current) {
      requestAnimationFrame(() => {
        inputBuscaRef.current?.focus();

        // Rola suavemente até o input e centraliza na tela
        inputBuscaRef.current?.scrollIntoView({
          behavior: "smooth",
          block: "center",
        });
      });
    }
  }, [activeTab]);

  /* ******************************************************************
   *                Carregamento do Profissional
   * ******************************************************************/

  useEffect(() => {
    const carregarProfissional = async () => {
      const response = await api.getProfissionalSaudeLogado();
      const nomeFormatado = response.usuario.nome
        .trim()
        .toLowerCase()
        .split(" ")
        .map(
          (palavra: string) =>
            palavra.charAt(0).toUpperCase() + palavra.slice(1)
        )
        .join(" ");

      setUserInfo({
        idProfissional: response.idProfissional,
        idUsuario: response.usuario.idUsuario,
        nome: nomeFormatado,
        cargo: response.cargo || "Médico",
      });
      setNomeMedico(`Dr. ${nomeFormatado}`);
    };

    carregarProfissional();
  }, []);

  /* ******************************************************************
   *           Carregamento e Atualização dos Dados
   * ******************************************************************/

  useEffect(() => {
    if (!userInfo?.idProfissional) return;

    let isMounted = true;
    let intervaloId: NodeJS.Timeout;

    const carregarDados = async (silencioso = false) => {
      if (!isMounted) return;
      if (!silencioso) setLoading(true);

      try {
        const [agendas, consultasRes, pacientesRes] = await Promise.all([
          api.getAgendasByProfissional(userInfo.idProfissional),
          api.getConsultasByProfissional(userInfo.idProfissional),
          api.getAllPacientes?.() || Promise.resolve([]),
        ]);

        setConsultas(consultasRes);

        if (!isMounted) return;

        const mapaConsultas = new Map<string, ConsultaResponse>();
        consultasRes.forEach((c: ConsultaResponse) => {
          const chave = c.dataConsulta
            .replace(/\+\d{2}:\d{2}$/, "")
            .slice(0, 19);
          mapaConsultas.set(chave, c);
        });

        const pacientesMap: PacienteMap = {};
        pacientesRes.forEach((p: any) => {
          pacientesMap[p.idPaciente] = {
            nome: p.usuario?.nome || "Paciente desconhecido!",
          };
        });
        setPacientes(pacientesRes);
        setPacientesMap(pacientesMap);

        const hojeStr = format(new Date(), "yyyy-MM-dd");
        const agora = new Date();

        const agendamentosDoDia = agendas
          .filter((a: any) => a.dataHorario.startsWith(hojeStr))
          .map((agenda: any) => {
            const inicio = parseISO(agenda.dataHorario);
            const fim = addMinutes(inicio, 40);
            const chave = agenda.dataHorario.slice(0, 19);
            const consulta = mapaConsultas.get(chave);

            return {
              idAgenda: agenda.idAgenda,
              idConsulta: consulta?.idConsulta || null,
              nomePaciente:
                pacientesMap[agenda.idPaciente]?.nome ||
                "Paciente desconhecido!",
              horario: `${format(inicio, "HH:mm")} - ${format(fim, "HH:mm")}`,
              horarioInicio: inicio,
              horarioFim: fim,
              status: agenda.status || "Status da agenda não informado!",
              tipoConsulta:
                consulta?.tipoConsulta || "Tipo da consulta não informado!",
              localConsulta:
                consulta?.localConsulta || "Local da consulta não informado!",
            };
          })
          .sort(
            (a: AgendamentoHoje, b: AgendamentoHoje) =>
              a.horarioInicio.getTime() - b.horarioInicio.getTime()
          );

        setAgendamentosHoje(agendamentosDoDia);

        const restantes = agendamentosDoDia.filter(
          (a) => agora < a.horarioFim
        ).length;
        const realizadas = agendamentosDoDia.filter(
          (a) => agora >= a.horarioFim
        ).length;
        setConsultasRestantes(restantes);
        setConsultasRealizadas(realizadas);

        const emAndamento = agendamentosDoDia.find(
          (a) => agora >= a.horarioInicio && agora < a.horarioFim
        );
        setConsultaAtual(
          emAndamento ? { ...emAndamento, emAndamento: true } : null
        );

        const proxima =
          agendamentosDoDia
            .filter((a) => a.horarioInicio > agora)
            .sort(
              (a, b) => a.horarioInicio.getTime() - b.horarioInicio.getTime()
            )[0] || null;
        setProximaConsultaFutura(proxima);

        const ultimaTemp: ConsultaRecente = {};
        agendas.forEach((a: any) => {
          if (parseISO(a.dataHorario) >= agora) return;
          const id = a.idPaciente;
          if (!ultimaTemp[id] || a.dataHorario > ultimaTemp[id]) {
            ultimaTemp[id] = a.dataHorario;
          }
        });
        setUltimaConsultaMap(ultimaTemp);
      } catch (err) {
        if (!silencioso) setError("Erro ao atualizar dados");
        console.error("Erro no auto-refresh:", err);
      } finally {
        if (!silencioso) setLoading(false);
      }
    };

    carregarDados();
    intervaloId = setInterval(() => carregarDados(true), 1000);

    return () => {
      isMounted = false;
      clearInterval(intervaloId);
    };
  }, [userInfo?.idProfissional]);

  useEffect(() => {
    setPage(1);
  }, [selectedDateCalendar, consultas.length]);

  /* ******************************************************************
   *                          Renderização
   * ******************************************************************/

  return (
    <div className="min-h-screen bg-gray-50">
      <DashboardHeader activeTab={activeTab} onTabChange={setActiveTab} />

      {/* ******************************************************************
       *                       Conteúdo Principal
       * ******************************************************************/}

      <main className="p-8">
        {/* ******************************************************************
         *                         Aba Dashboard
         * ******************************************************************/}
        {activeTab === "dashboard" && (
          <div className="space-y-8">
            <div>
              <h1 className="text-3xl font-bold text-[#1E255E]">
                Bem vindo, {nomeMedico}!
              </h1>
              <p className="text-gray-600 mt-2 text-lg">
                Resumo dos agendamentos de hoje (
                {format(new Date(), "EEEE, d 'de' MMMM 'de' yyyy", {
                  locale: ptBR,
                })}
                )
              </p>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
              <div className="lg:col-span-2 space-y-8">
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5 max-w-7xl mx-auto">
                  <StatCard
                    title="Consultas Hoje"
                    value={
                      loading
                        ? "—"
                        : consultasRestantes === 0 &&
                          agendamentosHoje.length > 0
                        ? `Todas as ${agendamentosHoje.length} consultas do dia concluídas`
                        : consultasRestantes.toString()
                    }
                    delta={
                      loading ? (
                        "Carregando..."
                      ) : consultasRestantes === 0 &&
                        agendamentosHoje.length >
                          0 ? null : consultasRealizadas === 0 ? (
                        <span className="text-lime-400 text-base font-medium">
                          Nenhuma realizada ainda
                        </span>
                      ) : (
                        <span className="text-lime-400 text-base font-medium">
                          Consultas concluídas: {consultasRealizadas}
                        </span>
                      )
                    }
                    icon={<Calendar className="w-6 h-6" />}
                    isLongText={
                      consultasRestantes === 0 && agendamentosHoje.length > 0
                    }
                  />

                  <StatCard
                    title="Consulta Atual"
                    value={
                      loading
                        ? "—"
                        : consultaAtual
                        ? consultaAtual.horario
                        : "Nenhuma no momento"
                    }
                    delta={
                      consultaAtual ? (
                        <span className="text-lime-400 text-base font-medium">
                          {consultaAtual.nomePaciente}
                        </span>
                      ) : null
                    }
                    icon={<Clock className="w-6 h-6" />}
                    isLongText={!consultaAtual}
                  />

                  <StatCard
                    title="Próxima Consulta"
                    value={
                      loading
                        ? "—"
                        : proximaConsultaFutura
                        ? format(proximaConsultaFutura.horarioInicio, "HH:mm")
                        : "Nenhuma agendada"
                    }
                    delta={
                      proximaConsultaFutura ? (
                        <span className="text-lime-400 text-base font-medium">
                          {proximaConsultaFutura.nomePaciente}
                        </span>
                      ) : null
                    }
                    icon={<Clock className="w-6 h-6" />}
                    isLongText={!proximaConsultaFutura}
                  />

                  <StatCard
                    title="Pacientes Ativos"
                    value={pacientes.length.toString()}
                    delta={
                      <span className="text-lime-400 text-base font-medium">
                        +0 este mês
                      </span>
                    }
                    icon={<Users className="w-6 h-6" />}
                  />
                </div>

                <div className="bg-white rounded-xl shadow-sm p-6">
                  <h2 className="text-xl font-semibold text-[#1E255E]">
                    Agendamentos do Dia
                  </h2>
                  <p className="text-sm text-gray-500 mb-4">
                    Lista de consultas programadas para hoje
                  </p>

                  {loading ? (
                    <div className="text-center py-12 text-gray-500">
                      Carregando agendamentos...
                    </div>
                  ) : error ? (
                    <div className="text-center py-12 text-red-600">
                      {error}
                    </div>
                  ) : agendamentosHoje.length === 0 ? (
                    <div className="text-center py-12 text-gray-500">
                      Nenhuma consulta agendada para hoje.
                    </div>
                  ) : (
                    <div className="space-y-4">
                      {agendamentosHoje.map((a) => (
                        <div
                          key={a.idAgenda}
                          className="flex items-center p-4 rounded-lg border border-gray-200 hover:border-[#0061FE] transition-colors cursor-pointer"
                        >
                          <img
                            src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
                              a.nomePaciente
                            )}&background=random`}
                            alt={a.nomePaciente}
                            className="w-12 h-12 rounded-full border-4 border-gray-100 shadow flex-shrink-0"
                          />
                          <div className="ml-4 flex-1">
                            <div className="text-[18px] font-semibold text-[#1E255E]">
                              {a.nomePaciente}
                            </div>
                            <div className="text-[14px] font-semibold text-gray-600 flex items-center gap-1">
                              {a.horario} •
                              <span className="px-1 py-0.4 rounded text-xs text-[13px] bg-indigo-100 text-indigo-800">
                                {a.tipoConsulta.charAt(0).toUpperCase() +
                                  a.tipoConsulta.slice(1).toLowerCase()}
                              </span>
                            </div>
                            <div className="text-[14px] font-semibold text-gray-600 flex items-center gap-1">
                              Local:
                              <span className="px-1 py-0.4 rounded text-xs text-[13px] bg-amber-100 text-amber-800">
                                {a.localConsulta.charAt(0).toUpperCase() +
                                  a.localConsulta.slice(1).toLowerCase()}
                              </span>
                            </div>
                          </div>
                          <TagStatus text={a.status} />
                          <a
                            href="#"
                            className="ml-6 flex items-center gap-1 text-sm text-[#0061FE] font-medium hover:underline whitespace-nowrap"
                          >
                            Ver Detalhes <ExternalLink className="w-4 h-4" />
                          </a>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>

              <div className="space-y-6">
                <div className="bg-white rounded-[30px] shadow-sm p-6">
                  <h2 className="text-xl font-semibold text-[#1E255E]">
                    Ações Rápidas
                  </h2>
                  <p className="text-sm text-gray-500 mb-6">
                    Acesso rápido às funcionalidades principais
                  </p>
                  <div className="grid grid-cols-1 xl:grid-cols-2 gap-4 justify-items-center">
                    <QuickActionButton
                      icon={PlusCircle}
                      title={<span className="text-[16px]">Nova Consulta</span>}
                      subtitle={
                        <span className="text-[12px]">Agendar Consulta</span>
                      }
                      onClick={() => setActiveTab("agenda")}
                    />
                    <QuickActionButton
                      icon={Search}
                      title={
                        <span className="text-[16px]">Buscar Paciente</span>
                      }
                      subtitle={
                        <span className="text-[12px]">
                          Encontrar Prontuário
                        </span>
                      }
                      onClick={() => setActiveTab("pacientes")}
                    />
                    <QuickActionButton
                      icon={FileText}
                      title={<span className="text-[16px]">Relatórios</span>}
                      subtitle={
                        <span className="text-[12px]">
                          Ver relatórios médicos
                        </span>
                      }
                      onClick={() => setActiveTab("relatorios")}
                    />
                    <QuickActionButton
                      icon={Settings}
                      title={<span className="text-[16px]">Configurações</span>}
                      subtitle={
                        <span className="text-[12px]">
                          Ajustar preferências
                        </span>
                      }
                      onClick={() => setActiveTab("dashboard")}
                    />
                  </div>
                </div>

                <div className="bg-white rounded-[30px] shadow-sm p-6">
                  <h2 className="text-xl font-semibold text-[#1E255E]">
                    Atividades Recentes
                  </h2>
                  <p className="text-sm text-gray-500 mb-4">
                    Últimas movimentações do sistema
                  </p>
                  <div className="text-center py-20 text-gray-500">
                    <p className="text-lg">
                      Funcionalidade em desenvolvimento...
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ******************************************************************
         *                            Aba Agenda
         * ******************************************************************/}
        {activeTab === "agenda" && (
          <div className="grid grid-cols-1 xl:grid-cols-3 gap-8">
            <div className="xl:col-span-2 space-y-8">
              <div className="mb-8">
                <h1 className="text-3xl font-bold text-[#1E255E]">
                  Agenda de Consultas
                </h1>
                <div className="mt-3 space-y-3">
                  {consultas.length > 0 ? (
                    <>
                      {(() => {
                        const todasDatas = consultas
                          .map((c) => parseISO(c.dataConsulta))
                          .sort((a, b) => a.getTime() - b.getTime());
                        const futuras = todasDatas.filter(
                          (d) => d >= new Date()
                        );
                        const passadas = todasDatas.filter(
                          (d) => d < new Date()
                        );
                        const proximaData =
                          futuras.length > 0
                            ? futuras[0]
                            : passadas[passadas.length - 1];
                        if (!proximaData) return null;

                        const dataStr = format(proximaData, "yyyy-MM-dd");
                        const consultasNaData = consultas.filter(
                          (c) =>
                            format(parseISO(c.dataConsulta), "yyyy-MM-dd") ===
                            dataStr
                        );

                        return (
                          <p className="text-lg text-gray-600">
                            Próxima consulta em{" "}
                            <span className="font-semibold text-[#0061FE]">
                              {format(proximaData, "EEEE, d 'de' MMMM", {
                                locale: ptBR,
                              })}
                            </span>{" "}
                            • {consultasNaData.length} consulta
                            {consultasNaData.length !== 1 ? "s" : ""} marcada
                            {consultasNaData.length !== 1 ? "s" : ""}
                          </p>
                        );
                      })()}
                    </>
                  ) : (
                    <p className="text-lg text-gray-600">
                      Nenhuma consulta agendada no momento.
                    </p>
                  )}
                </div>
              </div>

              <div className="bg-white rounded-2xl shadow-sm p-8">
                <div className="mb-8">
                  <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
                    <div>
                      <h2 className="text-2xl font-bold text-[#1E255E]">
                        Histórico de Consultas
                      </h2>
                      <p className="text-sm text-gray-500">
                        {selectedDateCalendar
                          ? `Consultas em ${format(
                              selectedDateCalendar,
                              "dd 'de' MMMM 'de' yyyy",
                              { locale: ptBR }
                            )}`
                          : "Todas as consultas registradas"}
                      </p>
                    </div>

                    {selectedDateCalendar && (
                      <div className="flex items-center gap-2 px-4 py-2 rounded-full text-sm font-medium border border-[#0061FE]/30 bg-[#EEF4FF]/50">
                        <Pin className="w-4 h-4 text-[#0061FE]" />
                        Filtrado:{" "}
                        {format(selectedDateCalendar, "dd/MM/yyyy", {
                          locale: ptBR,
                        })}
                        <button
                          onClick={() => setSelectedDateCalendar(undefined)}
                          className="ml-2 hover:bg-white/50 rounded-full p-0.5 transition-colors"
                          aria-label="Remover filtro"
                        >
                          <X className="w-4 h-4 text-[#0061FE]" />
                        </button>
                      </div>
                    )}
                  </div>
                </div>

                {loading ? (
                  <div className="text-center py-12 text-gray-500">
                    Carregando consultas...
                  </div>
                ) : consultasFiltradas.length === 0 ? (
                  <div className="text-center py-12 text-gray-500">
                    Nenhuma consulta encontrada.
                  </div>
                ) : (
                  <>
                    <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-8">
                      <span className="text-base text-gray-600 font-medium">
                        Exibindo {(page - 1) * ITEMS_PER_PAGE + 1}–
                        {Math.min(
                          page * ITEMS_PER_PAGE,
                          consultasFiltradas.length
                        )}{" "}
                        de {consultasFiltradas.length} consultas
                      </span>
                      <div className="flex items-center gap-3 bg-gray-50 px-4 py-2.5 rounded-xl border border-gray-200 mt-4 sm:mt-0">
                        <button
                          onClick={() =>
                            setPage((prev) => Math.max(prev - 1, 1))
                          }
                          disabled={page === 1}
                          className="p-2 rounded-lg hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed transition-colors"
                        >
                          <ChevronLeft className="w-5 h-5 text-gray-700" />
                        </button>
                        <span className="font-semibold text-[#1E255E] min-w-[40px] text-center">
                          {page}
                        </span>
                        <button
                          onClick={() => setPage((prev) => prev + 1)}
                          disabled={
                            page * ITEMS_PER_PAGE >= consultasFiltradas.length
                          }
                          className="p-2 rounded-lg hover:bg-gray-200 disabled:opacity-40 disabled:cursor-not-allowed transition-colors"
                        >
                          <ChevronRight className="w-5 h-5 text-gray-700" />
                        </button>
                      </div>
                    </div>

                    <div className="space-y-8">
                      {Object.entries(
                        consultasFiltradas
                          .sort((a, b) =>
                            b.dataConsulta.localeCompare(a.dataConsulta)
                          )
                          .slice(
                            (page - 1) * ITEMS_PER_PAGE,
                            page * ITEMS_PER_PAGE
                          )
                          .reduce((groups, consulta) => {
                            const dateKey = format(
                              parseISO(consulta.dataConsulta),
                              "yyyy-MM-dd"
                            );
                            if (!groups[dateKey]) groups[dateKey] = [];
                            groups[dateKey].push(consulta);
                            return groups;
                          }, {} as Record<string, ConsultaResponse[]>)
                      ).map(([dateKey, consultasDoDia]) => {
                        const data = parseISO(dateKey + "T00:00:00");
                        const isHoje =
                          format(data, "yyyy-MM-dd") ===
                          format(new Date(), "yyyy-MM-dd");

                        return (
                          <div key={dateKey}>
                            <h3
                              className={`text-lg font-bold mb-4 ${
                                isHoje
                                  ? "text-green-600"
                                  : data < new Date()
                                  ? "text-red-600"
                                  : "text-[#0061FE]"
                              }`}
                            >
                              {isHoje
                                ? "Hoje"
                                : format(data, "EEEE, d 'de' MMMM 'de' yyyy", {
                                    locale: ptBR,
                                  })}
                              <span className="text-[15px] font-normal ml-1.5 text-lime-700">
                                {" • "}
                                {consultasDoDia.length} consulta
                                {consultasDoDia.length !== 1 ? "s" : ""}{" "}
                                {isHoje
                                  ? "agendada" +
                                    (consultasDoDia.length !== 1 ? "s" : "") +
                                    " para hoje"
                                  : data < new Date()
                                  ? "realizada" +
                                    (consultasDoDia.length !== 1 ? "s" : "")
                                  : "agendada" +
                                    (consultasDoDia.length !== 1 ? "s" : "")}
                                {isHoje ? "" : " nesse dia"}
                              </span>
                            </h3>

                            <div className="space-y-4">
                              {consultasDoDia
                                .sort((a, b) =>
                                  b.dataConsulta.localeCompare(a.dataConsulta)
                                )
                                .map((consulta) => {
                                  const dataConsulta = parseISO(
                                    consulta.dataConsulta
                                  );
                                  const horarioInicio = dataConsulta;
                                  const horarioFim = addMinutes(
                                    dataConsulta,
                                    40
                                  );
                                  const nomePaciente =
                                    pacientesMap[consulta.idPaciente]?.nome ||
                                    "Paciente desconhecido";
                                  const isPassada = dataConsulta < new Date();

                                  return (
                                    <div
                                      key={consulta.idConsulta}
                                      className="flex items-center p-4 rounded-lg border border-gray-200 hover:border-[#0061FE] transition-colors cursor-pointer"
                                    >
                                      <img
                                        src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
                                          nomePaciente
                                        )}&background=random`}
                                        alt={nomePaciente}
                                        className="w-12 h-12 rounded-full border-4 border-gray-100 shadow flex-shrink-0"
                                      />
                                      <div className="ml-4 flex-1">
                                        <div className="text-[18px] font-semibold text-[#1E255E]">
                                          {nomePaciente}
                                        </div>
                                        <div className="text-[14px] font-semibold text-gray-600 flex items-center gap-1">
                                          {format(horarioInicio, "HH:mm")} -{" "}
                                          {format(horarioFim, "HH:mm")} •
                                          <span className="px-1 py-0.4 rounded text-xs bg-indigo-100 text-indigo-800">
                                            {consulta.tipoConsulta
                                              .charAt(0)
                                              .toUpperCase() +
                                              consulta.tipoConsulta
                                                .slice(1)
                                                .toLowerCase()}
                                          </span>
                                        </div>
                                        <div className="text-[14px] font-semibold text-gray-600 flex items-center gap-1">
                                          Local:
                                          <span className="px-1 py-0.4 rounded text-xs bg-amber-100 text-amber-800">
                                            {consulta.localConsulta
                                              .charAt(0)
                                              .toUpperCase() +
                                              consulta.localConsulta
                                                .slice(1)
                                                .toLowerCase()}
                                          </span>
                                        </div>
                                      </div>
                                      <TagStatus
                                        text={
                                          isPassada ? "CONCLUIDO" : "AGENDADO"
                                        }
                                      />
                                      <a
                                        href="#"
                                        className="ml-6 flex items-center gap-1 text-sm text-[#0061FE] font-medium hover:underline whitespace-nowrap"
                                      >
                                        Ver Detalhes{" "}
                                        <ExternalLink className="w-4 h-4" />
                                      </a>
                                    </div>
                                  );
                                })}
                            </div>
                          </div>
                        );
                      })}
                    </div>
                  </>
                )}
              </div>
            </div>

            <div className="space-y-6">
              <div className="bg-white rounded-[30px] shadow-sm p-6">
                <h2 className="text-xl font-semibold text-[#1E255E]">
                  Ações Rápidas
                </h2>
                <p className="text-sm text-gray-500 mb-6">
                  Acesso rápido às funcionalidades
                </p>
                <div className="grid grid-cols-1 xl:grid-cols-2 gap-4 justify-items-center">
                  <QuickActionButton
                    icon={PlusCircle}
                    title={<span className="text-[16px]">Nova Consulta</span>}
                    subtitle={
                      <span className="text-[12px]">Agendar Consulta</span>
                    }
                    onClick={() => setActiveTab("agenda")}
                  />
                  <QuickActionButton
                    icon={FileText}
                    title={<span className="text-[16px]">Relatórios</span>}
                    subtitle={
                      <span className="text-[12px]">
                        Ver relatórios médicos
                      </span>
                    }
                    onClick={() => setActiveTab("relatorios")}
                  />
                </div>
              </div>

              <div
                className="bg-white rounded-[30px] shadow-sm p-8 flex flex-col items-center"
                style={{ width: "501px", height: "645px" }}
              >
                <DayPicker
                  mode="single"
                  selected={selectedDateCalendar}
                  onSelect={(date) =>
                    setSelectedDateCalendar(date || undefined)
                  }
                  locale={ptBR}
                  showOutsideDays
                  fixedWeeks
                  modifiers={{
                    hasConsulta: [
                      ...diasComConsultaPassada,
                      ...diasComConsultaFutura,
                    ],
                    hasPassada: diasComConsultaPassada,
                    hasFutura: diasComConsultaFutura,
                  }}
                  classNames={{
                    root: "w-full h-full",
                    month: "w-full h-full flex flex-col justify-between",
                    caption:
                      "relative flex items-center justify-center pb-6 border-b border-gray-100 mb-4",
                    caption_label: "text-lg font-bold text-[#1E255E]",
                    nav: "absolute w-full flex justify-between items-center px-2",
                    nav_button:
                      "p-1 text-gray-400 hover:text-[#0061FE] transition-colors",
                    table: "w-full h-full border-collapse",
                    head_row: "flex justify-between mb-4",
                    head_cell:
                      "w-14 text-center text-sm font-normal text-gray-400 uppercase tracking-wide",
                    row: "flex justify-between mt-2",
                    cell: "p-0",
                    day: `w-14 h-14 flex items-center justify-center text-xl text-[#1E255E] font-medium rounded-[14px] transition-all duration-200 hover:bg-gray-50 relative`,
                    day_today: "font-bold text-green-600",
                    day_selected: `bg-[#EEF4FF] border-[1.5px] border-[#0061FE] text-[#0061FE] font-bold hover:bg-[#EEF4FF] hover:text-[#0061FE]`,
                    day_outside: "text-gray-300 opacity-50",
                  }}
                  components={{
                    IconLeft: () => <ChevronLeft className="w-6 h-6" />,
                    IconRight: () => <ChevronRight className="w-6 h-6" />,
                    DayContent: ({ date, activeModifiers }) => {
                      const dayStr = format(date, "yyyy-MM-dd");
                      const hasPassada = diasComConsultaPassada.some(
                        (d) => format(d, "yyyy-MM-dd") === dayStr
                      );
                      const hasFutura = diasComConsultaFutura.some(
                        (d) => format(d, "yyyy-MM-dd") === dayStr
                      );
                      const isSelected =
                        selectedDateCalendar &&
                        format(selectedDateCalendar, "yyyy-MM-dd") === dayStr;
                      const isOutside = activeModifiers.outside;

                      return (
                        <div
                          className={`relative w-full h-full flex items-center justify-center ${
                            isOutside ? "opacity-100 text-gray-300" : ""
                          }`}
                        >
                          <span className="relative z-10 text-xl">
                            {date.getDate()}
                          </span>
                          {(hasPassada || hasFutura) && (
                            <div
                              className={`absolute inset-2 rounded-full border-4 ${
                                hasPassada
                                  ? "border-red-500"
                                  : "border-lime-600"
                              } pointer-events-none`}
                            />
                          )}
                          {isSelected && (
                            <Pin className="absolute w-7 h-7 text-[#0061FE] -top-3 -right-3 rotate-12 z-20" />
                          )}
                        </div>
                      );
                    },
                  }}
                />

                <div className="mt-9 flex justify-center gap-5 text-[15px]">
                  <div className="flex items-center gap-2">
                    <div className="w-5 h-5 rounded-full border-4 border-red-500" />
                    <span className="text-gray-600">Consulta realizada</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <div className="w-5 h-5 rounded-full border-4 border-lime-600" />
                    <span className="text-gray-600">Consulta agendada</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Pin className="w-5 h-5 text-[#0061FE]" />
                    <span className="text-gray-600">Dia selecionado</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* ******************************************************************
         *                          Aba Relatórios
         * ******************************************************************/}
        {activeTab === "relatorios" && (
          <div className="text-center py-20">
            <h2 className="text-2xl font-bold text-[#1E255E]">Relatórios</h2>
            <p className="text-gray-500 mt-4">
              Página de relatórios em desenvolvimento...
            </p>
          </div>
        )}

        {/* ******************************************************************
         *                           Aba Pacientes
         * ******************************************************************/}
        {activeTab === "pacientes" && (
          <div className="space-y-8">
            <div className="mb-8">
              <h1 className="text-3xl font-bold text-[#1E255E]">
                Meus Pacientes
              </h1>
              <p className="text-gray-600 mt-2">
                Gerencie e visualize os prontuários dos seus pacientes
              </p>
            </div>

            <div className="mb-8">
              <button
                onClick={() => setActiveTab("dashboard")}
                className="flex items-center gap-2.5 text-gray-600 hover:text-[#0061FE] transition-all font-medium text-base group"
              >
                <ArrowLeft
                  size={22}
                  className="group-hover:-translate-x-1 transition-transform"
                />
                Voltar ao Dashboard
              </button>
            </div>

            <div className="mb-10 relative max-w-2xl">
              <input
                ref={inputBuscaRef}
                type="text"
                placeholder="Busque o paciente pelo nome ou CPF"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full pl-12 pr-12 py-4 rounded-xl border border-gray-300 focus:border-[#0061FE] focus:outline-none transition-all text-base placeholder-gray-400 shadow-sm"
              />
              <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-6 h-6 text-gray-400" />
              {searchTerm && (
                <button
                  onClick={() => setSearchTerm("")}
                  className="absolute right-4 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-800 transition-colors"
                  aria-label="Limpar busca"
                >
                  <X className="w-5 h-5" />
                </button>
              )}
            </div>

            {loading && pacientes.length === 0 ? (
              <div className="text-center py-20">
                <div className="inline-block animate-spin rounded-full h-12 w-12 border-4 border-[#0061FE] border-t-transparent"></div>
                <p className="mt-4 text-gray-500">Carregando pacientes...</p>
              </div>
            ) : error ? (
              <div className="text-center py-20 text-red-600">{error}</div>
            ) : filteredPacientes.length === 0 ? (
              <div className="text-center py-20 bg-white rounded-2xl shadow-sm">
                <Users className="w-20 h-20 mx-auto text-gray-300 mb-4" />
                <p className="text-xl text-gray-600">
                  {searchTerm
                    ? "Nenhum paciente encontrado para esta busca."
                    : "Você ainda não possui pacientes cadastrados."}
                </p>
              </div>
            ) : (
              <div className="space-y-6">
                {filteredPacientes.map((p) => {
                  const nomeCompleto =
                    p.usuario?.nome?.trim() || "Nome não informado";
                  const cpfFormatado = (p.usuario?.cpf || "").replace(
                    /(\d{3})(\d{3})(\d{3})(\d{2})/,
                    "$1.$2.$3-$4"
                  );
                  const telefoneFormatado = p.usuario?.telefone
                    ? p.usuario.telefone.replace(
                        /(\d{2})(\d{5})(\d{4})/,
                        "($1) $2-$3"
                      )
                    : "Não informado";

                  return (
                    <div
                      key={p.idPaciente}
                      className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden hover:shadow-lg hover:-translate-y-1 transition-all duration-300"
                    >
                      <div className="p-8">
                        <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-6">
                          <div className="flex items-center gap-5">
                            <img
                              src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
                                nomeCompleto
                              )}&background=random`}
                              alt={nomeCompleto}
                              className="w-16 h-16 rounded-full border-4 border-gray-100 shadow"
                            />
                            <div>
                              <h3 className="text-2xl font-bold text-[#1E255E]">
                                {nomeCompleto}
                              </h3>
                              <p className="text-sm mt-1 font-medium">
                                {p.usuario?.ativo === true && (
                                  <span className="text-green-600">
                                    Paciente ativo
                                  </span>
                                )}
                                {p.usuario?.ativo === false && (
                                  <span className="text-red-600">
                                    Paciente inativo
                                  </span>
                                )}
                                {p.usuario?.ativo === undefined && (
                                  <span className="text-gray-500">
                                    Não informado
                                  </span>
                                )}
                              </p>
                            </div>
                          </div>
                          <div className="flex gap-3">
                            <button className="px-6 py-3 bg-gray-100 text-gray-700 font-medium rounded-xl hover:bg-gray-200 transition-colors flex items-center gap-2">
                              <Mail size={18} />
                              Enviar mensagem
                            </button>

                            <button
                              onClick={() => {
                                const cpfLimpo =
                                  p.usuario?.cpf?.replace(/\D/g, "") || "";
                                navigate(
                                  `/dashboard-medico/prontuario?pacienteId=${p.idPaciente}&cpf=${cpfLimpo}`
                                );
                              }}
                              className="px-8 py-3 bg-[#0061FE] text-white font-medium rounded-xl hover:bg-blue-700 transition-colors shadow-md hover:shadow-lg flex items-center gap-2"
                            >
                              <ExternalLink size={18} />
                              Acessar Prontuário
                            </button>
                          </div>
                        </div>

                        <div className="mt-8 pt-8 border-t border-gray-200 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 text-sm">
                          <div>
                            <span className="flex items-center gap-2 font-semibold text-[#1E255E]">
                              <Calendar size={16} />
                              Última consulta
                            </span>
                            <p className="font-semibold text-gray-600 mt-1">
                              {ultimaConsultaMap[p.idPaciente] ? (
                                format(
                                  parseISO(ultimaConsultaMap[p.idPaciente]),
                                  "dd/MM/yyyy 'às' HH:mm"
                                )
                              ) : (
                                <span className="italic">
                                  Nenhuma consulta realizada
                                </span>
                              )}
                            </p>
                          </div>
                          <div>
                            <span className="flex items-center gap-2 font-semibold text-[#1E255E]">
                              <FileText size={16} />
                              CPF
                            </span>
                            <p className="font-semibold text-gray-600 mt-1">
                              {cpfFormatado || "Não informado"}
                            </p>
                          </div>
                          <div>
                            <span className="flex items-center gap-2 font-semibold text-[#1E255E]">
                              <Phone size={16} />
                              Telefone
                            </span>
                            <p className="font-semibold text-gray-600 mt-1">
                              {telefoneFormatado}
                            </p>
                          </div>
                          <div>
                            <span className="flex items-center gap-2 font-semibold mt-[4px] text-[#1E255E]">
                              <Mail size={16} />
                              E-mail
                            </span>
                            <p className="font-semibold text-gray-600 mt-1">
                              {p.usuario?.email || "Não informado"}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
            )}
          </div>
        )}
      </main>
    </div>
  );
};

export default MedicoDashboard;
