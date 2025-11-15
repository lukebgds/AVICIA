const BASE_URL = "http://localhost:9081/api";

// --- Interfaces de Retorno ---
interface LoginResponse {
  accessToken: string;
  expiresIn: string;
}

interface RoleResponse {
  idRole: string;
  nome: string;
  idTipoRole: number;
  descricao: string;
  permissoes: Record<string, string>;
}

interface TwoFactorResponse {
  message: string;
}

interface SystemLogResponse {
  idLog: number;
  idUsuario: number;
  tipoLog: string;
  acao: string;
  dataHora: string;
  entidadeAfetada: string;
  detalhes: string;
}

interface EstatisticasLogsResponse {
  totalLogs: number;
  totalCriacoes: number;
  totalModificacoes: number;
  totalExclusoes: number;
  totalErros: number;
  totalAvisos: number;
  usuarioMaisAtivo: string;
  idUsuarioMaisAtivo: number;
  acoesUsuarioMaisAtivo: number;
  entidadeMaisAfetada: string;
  operacoesEntidadeMaisAfetada: number;
}

interface UsuarioResponse {
  idUsuario: number;
  nome: string;
  cpf: string;
  dataNascimento: string;
  sexo: string;
  estadoCivil: string;
  email: string;
  telefone: string;
  endereco: string;
  ativo: boolean;
  mfaHabilitado: boolean;
  dataCriacao: string;
  idRole: number;
}

interface PacienteResponse {
  idPaciente: number;
  usuario: {
    idUsuario: number;
    nome: string;
    cpf: string;
    email: string;
  };
  profissao: string;
  preferenciaContato: string;
}

interface PacienteAlergiaResponse {
  idAlergia: number;
  idPaciente: number;
  gravidade: string;
  descricao: string;
}

interface PacienteAnexoResponse {
  idAnexo: number;
  idPaciente: number;
  tipo: string;
  descricao: string;
  urlArquivo: string;
  dataUpload: string;
}

interface PacienteAntecedenteResponse {
  idAntecedente: number;
  idPaciente: number;
  tipoDoenca: string;
  parentesco: string;
}

interface PacienteConvenioResponse {
  idConvenio: number;
  idPaciente: number;
  nomeConvenio: string;
  numeroCarteirinha: string;
  validade: string;
}

interface PacienteDiagnosticoResponse {
  idDiagnostico: number;
  idPaciente: number;
  codigoCidDez: string;
  descricao: string;
  dataDiagnostico: string;
  tipo: string;
  status: string;
}

interface PacienteMedicamentoResponse {
  idMedicamento: number;
  idPaciente: number;
  medicamento: string;
  dosagem: string;
  frequencia: string;
}

interface PacienteVacinaResponse {
  idVacina: number;
  idPaciente: number;
  vacina: string;
  dataAplicacao: string;
}

interface ProfissionalSaudeResponse {
  idProfissional: number;
  usuario: {
    idUsuario: number;
    nome: string;
    cpf: string;
    email: string;
  };
  matricula: string;
  conselho: string;
  registroConselho: string;
  especialidade: string;
  cargo: string;
  unidade: string;
}

interface FuncionarioResponse {
  idFuncionario: number;
  usuario: {
    idUsuario: number;
    nome: string;
    cpf: string;
    email: string;
  };
  cargo: string;
  setor: string;
  matricula: string;
  observacoes: string;
}

interface ConsultaResponse {
  idConsulta: number;
  idPaciente: number;
  idProfissionalSaude: number;
  dataConsulta: string;
  tipoConsulta: string;
  localConsulta: string;
  anamnese: string;
  observacoes: string;
}

interface ConsultaDiagnosticoResponse {
  idDiagnostico: number;
  idConsulta: number;
  codigoCidDez: string;
  descricao: string;
}

interface ConsultaPrescricaoResponse {
  idPrescricao: number;
  idConsulta: number;
  dataEmissao: string;
  observacoes: string;
  status: string;
}

interface PrescricaoItemResponse {
  idItem: number;
  idPrescricao: number;
  medicamento: string;
  dosagem: string;
  frequencia: string;
  duracao: string;
}

interface ExameResponse {
  idExame: number;
  nome: string;
  descricao: string;
  tipo: string;
  ativo: boolean;
}

interface ExameSolicitadoResponse {
  idExameSolicitado: number;
  idConsulta: number;
  idPaciente: number;
  idExame: number;
  nomeExame: string;
  idProfissionalSaude: number;
  dataSolicitacao: string;
  observacoes: string;
  status: string;
}

interface ExameResultadoResponse {
  idResultado: number;
  idExameSolicitado: number;
  nomeExame: string;
  dataResultado: string;
  laudo: string;
  arquivoResultado: string;
  observacoes: string;
  idAssinadoPor: number;
  status: string;
}

interface InternacaoResponse {
  idInternacao: number;
  idPaciente: number;
  idProfissionalSaude: number;
  dataAdmissao: string;
  dataAlta: string;
  leito: string;
  observacoes: string;
}

interface AgendaResponse {
  idAgenda: number;
  idProfissionalSaude: number;
  idPaciente: number;
  dataHorario: string;
  status: string;
}

interface CreateRoleInput {
  nome: string;
  idTipoRole: number;
  descricao: string;
  permissoes: Record<string, string>;
}

// --- Interfaces de Entrada --- (Mantidas inalteradas, pois o foco é em Retorno)
interface CreateUsuarioInput {
  nome: string;
  cpf: string;
  dataNascimento?: string;
  sexo: string;
  estadoCivil: string;
  email: string;
  senha: string;
  telefone: string;
  endereco: string;
  ativo: boolean;
  mfaHabilitado: boolean;
  dataCriacao: string;
  idRole: string;
}

interface CreatePacienteInput {
  idUsuario: number;
  profissao: string;
  preferenciaContato: string;
}

interface CreateFuncionarioInput {
  idUsuario: number;
  cargo: string;
  setor: string;
  matricula: string;
  observacoes?: string;
}

interface CreateProfissionalSaudeInput {
  idUsuario: number;
  matricula: string;
  conselho: string;
  registroConselho: string;
  especialidade: string;
  cargo: string;
  unidade: string;
}

interface LoginAdmin {
  nome: string;
  senha: string;
}

interface LoginPaciente {
  cpf: string;
  senha: string;
}

interface TwoFactorRequest {
  email: string;
}

interface TwoFactorValidationRequest {
  email: string;
  codigo: string;
}

interface RoleRequest {
  nome: string;
  idTipoRole: number;
  descricao: string;
  permissoes: Record<string, boolean>;
}

interface AlterarSenhaRequest {
  senhaAtual: string;
  novaSenha: string;
}

export interface RecuperarSenhaRequest {
  cpf: string;
  senhaNova: string;
}

interface PacienteRequest {
  idUsuario: number;
  profissao: string;
  preferenciaContato: string;
}

interface PacienteAlergiaRequest {
  idPaciente: number;
  gravidade: string;
  descricao: string;
}

interface PacienteAnexoRequest {
  idPaciente: number;
  tipo: string;
  descricao: string;
  urlArquivo: string;
}

interface PacienteAntecedenteRequest {
  idPaciente: number;
  tipoDoenca: string;
  parentesco: string;
}

interface PacienteConvenioRequest {
  idPaciente: number;
  nomeConvenio: string;
  numeroCarteirinha: string;
  validade: string;
}

interface PacienteDiagnosticoRequest {
  idPaciente: number;
  codigoCidDez: string;
  descricao: string;
  dataDiagnostico: string;
  tipo: string;
  status: string;
}

interface PacienteMedicamentoRequest {
  idPaciente: number;
  medicamento: string;
  dosagem: string;
  frequencia: string;
}

interface PacienteVacinaRequest {
  idPaciente: number;
  vacina: string;
  dataAplicacao: string;
}

interface FuncionarioRequest {
  idUsuario: number;
  cargo: string;
  setor: string;
  matricula: string;
  observacoes: string;
}

interface ProfissionalSaudeRequest {
  idUsuario: number;
  matricula: string;
  conselho: string;
  registroConselho: string;
  especialidade: string;
  cargo: string;
  unidade: string;
}

interface ConsultaRequest {
  idPaciente: number;
  idProfissionalSaude: number;
  dataConsulta: string;
  tipoConsulta: string;
  localConsulta: string;
  anamnese: string;
  observacoes: string;
}

interface ConsultaDiagnosticoRequest {
  idConsulta: number;
  codigoCidDez: string;
  descricao: string;
}

interface ConsultaPrescricaoRequest {
  idConsulta: number;
  dataEmissao: string;
  observacoes: string;
  status: string;
}

interface PrescricaoItemRequest {
  idPrescricao: number;
  medicamento: string;
  dosagem: string;
  frequencia: string;
  duracao: string;
}

interface ExameRequest {
  nome: string;
  descricao: string;
  tipo: string;
  ativo: boolean;
}

interface ExameSolicitadoRequest {
  idConsulta: number;
  idPaciente: number;
  idExame: number;
  idProfissionalSaude: number;
  dataSolicitacao: string;
  observacoes: string;
  status: string;
}

interface ExameResultadoRequest {
  idExameSolicitado: number;
  dataResultado: string;
  laudo: string;
  arquivoResultado: string;
  observacoes: string;
  idAssinadoPor: number;
  status: string;
}

interface InternacaoRequest {
  idPaciente: number;
  idProfissionalSaude: number;
  dataAdmissao: string;
  dataAlta: string;
  leito: string;
  observacoes: string;
}

interface AgendaRequest {
  idProfissionalSaude: number;
  idPaciente: number;
  dataHorario: string;
  status: string;
}
const apiFetch = async <T>(
  endpoint: string,
  config: RequestInit = {},
  requireAuth: boolean,
  errorMessage?: string
): Promise<T> => {
  const token = localStorage.getItem("token");

  const baseHeaders: HeadersInit = {
    "Content-Type": "application/json",
  };

  if (requireAuth && !token) {
    throw new Error("Autenticação necessária. Faça login.");
  }

  if (requireAuth && token) {
    baseHeaders.Authorization = `Bearer ${token}`;
  }

  try {
    console.log(`🚀 Requisição: ${endpoint}`, config);

    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: baseHeaders,
      ...config,
    });

    console.log(
      `Status da resposta: ${response.status} ${response.statusText}`
    );

    if (!response.ok) {
      const errorData = await response
        .json()
        .catch(() => ({ message: "Erro desconhecido no servidor" }));
      throw new Error(errorMessage || errorData.message);
    }

    if (response.status === 204) {
      return undefined as T;
    }

    const contentType = response.headers.get("Content-Type");
    if (contentType && contentType.includes("application/json")) {
      const data = await response.json();
      console.log(`✅🚀 Resposta recebida: ${endpoint}`, data);
      return data as T;
    }

    return undefined as T;
  } catch (error) {
    console.error(`❌🚀 Erro na requisição: ${endpoint}`, error);
    throw error;
  }
};

// --- API Services ---
export const api = {
  getRoleByName: async (roleName: string): Promise<RoleResponse> => {
    console.log("🔍 Buscando Role:", roleName);
    const role = await apiFetch<RoleResponse>(
      `/roles/${roleName}`,
      { method: "GET" },
      false,
      `Role "${roleName}" não encontrada`
    );
    console.log("✅🔍 Role encontrada:", role);
    return role;
  },

  getAllUsuarios: async (): Promise<UsuarioResponse[]> => {
    console.log("👥 Buscando todos os usuários...");
    const usuarios = await apiFetch<UsuarioResponse[]>(
      "/usuarios",
      { method: "GET" },
      true,
      "Erro ao buscar lista de usuários"
    );
    console.log("✅👥 Usuários carregados:", usuarios);
    return usuarios;
  },

  criarUsuario: async (dados: CreateUsuarioInput): Promise<UsuarioResponse> => {
    console.log("👤 Criando usuário:", dados);
    const usuarioCriado = await apiFetch<UsuarioResponse>(
      "/usuarios/cadastro",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Erro ao criar usuário"
    );
    console.log("✅👤 Usuário criado:", usuarioCriado);
    return usuarioCriado;
  },

  criarPaciente: async (
    dados: CreatePacienteInput
  ): Promise<PacienteResponse> => {
    console.log("🏥 Criando paciente:", dados);
    const pacienteCriado = await apiFetch<PacienteResponse>(
      "/pacientes/cadastro",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Erro ao criar paciente"
    );
    console.log("✅🏥 Paciente criado:", pacienteCriado);
    return pacienteCriado;
  },

  criarRole: async (dados: CreateRoleInput): Promise<RoleResponse> => {
    console.log("🛡️ Criando role:", dados);
    const roleCriada = await apiFetch<RoleResponse>(
      "/roles",
      {
        method: "POST",
        body: JSON.stringify(dados),
      },
      true,
      "Erro ao criar role"
    );
    console.log("✅🛡️ Role criada:", roleCriada);
    return roleCriada;
  },

  getAllRoles: async (): Promise<RoleResponse[]> => {
    console.log("🔍 Buscando todas as roles...");
    const roles = await apiFetch<RoleResponse[]>(
      "/roles",
      { method: "GET" },
      true,
      "Erro ao buscar lista de roles"
    );
    console.log("✅🔍 Roles carregadas:", roles);
    return roles;
  },

  deleteUsuario: async (idUsuario: number): Promise<void> => {
    console.log("🗑️ Deletando usuário com ID:", idUsuario);
    await apiFetch<void>(
      `/usuarios/${idUsuario}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar usuário"
    );
    console.log("✅🗑️ Usuário deletado com sucesso");
  },

  criarFuncionario: async (
    dados: CreateFuncionarioInput
  ): Promise<FuncionarioResponse> => {
    console.log("👔 Criando funcionário:", dados);
    const funcionarioCriado = await apiFetch<FuncionarioResponse>(
      "/funcionarios",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar funcionário"
    );
    console.log("✅👔 Funcionário criado:", funcionarioCriado);
    return funcionarioCriado;
  },

  criarProfissional_saude: async (
    dados: CreateProfissionalSaudeInput
  ): Promise<ProfissionalSaudeResponse> => {
    console.log("🩺 Criando profissional de saúde:", dados);
    const profissionalCriado = await apiFetch<ProfissionalSaudeResponse>(
      "/profissionais-saude",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar profissional de saúde"
    );
    console.log("✅🩺 Profissional de saúde criado:", profissionalCriado);
    return profissionalCriado;
  },

  loginPaciente: async (dados: LoginPaciente): Promise<LoginResponse> => {
    console.log("🔐 Login paciente:", dados);
    const resultado = await apiFetch<LoginResponse>(
      "/login",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "CPF ou senha incorretos"
    );
    console.log("✅🔐 Login paciente bem-sucedido:", resultado);
    return resultado;
  },

  loginAdmin: async (dados: LoginAdmin): Promise<LoginResponse> => {
    console.log("🔐 Login admin:", dados);
    const resultado = await apiFetch<LoginResponse>(
      "/admin/login",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Nome ou senha incorretos"
    );
    console.log("✅🔐 Login admin bem-sucedido:", resultado);
    return resultado;
  },

  enviarCodigo2FA: async (
    dados: TwoFactorRequest
  ): Promise<TwoFactorResponse> => {
    console.log("📧 Enviando código 2FA:", dados);
    const resultado = await apiFetch<TwoFactorResponse>(
      "/sistema/auth/2fa/enviar",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Erro ao enviar código 2FA"
    );
    console.log("✅📧 Código 2FA enviado:", resultado);
    return resultado;
  },

  validarCodigo2FA: async (
    dados: TwoFactorValidationRequest
  ): Promise<TwoFactorResponse> => {
    console.log("🔑 Validando código 2FA:", dados);
    const resultado = await apiFetch<TwoFactorResponse>(
      "/sistema/auth/2fa/validar",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Erro ao validar código 2FA"
    );
    console.log("✅🔑 Código 2FA validado:", resultado);
    return resultado;
  },

  atualizarRole: async (
    nome: string,
    dados: RoleRequest
  ): Promise<RoleResponse> => {
    console.log("🛡️ Atualizando role:", nome, dados);
    const roleAtualizada = await apiFetch<RoleResponse>(
      `/roles/${nome}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar role"
    );
    console.log("✅🛡️ Role atualizada:", roleAtualizada);
    return roleAtualizada;
  },

  deleteRole: async (nome: string): Promise<void> => {
    console.log("🗑️ Deletando role:", nome);
    await apiFetch<void>(
      `/roles/${nome}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar role"
    );
    console.log("✅🗑️ Role deletada com sucesso");
  },

  getAllLogs: async (): Promise<SystemLogResponse[]> => {
    console.log("📋 Buscando todos os logs...");
    const logs = await apiFetch<SystemLogResponse[]>(
      "/logs",
      { method: "GET" },
      true,
      "Erro ao buscar logs"
    );
    console.log("✅📋 Logs carregados:", logs);
    return logs;
  },

  getLogById: async (id: number): Promise<SystemLogResponse> => {
    console.log("📋 Buscando log por ID:", id);
    const log = await apiFetch<SystemLogResponse>(
      `/logs/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar log"
    );
    console.log("✅📋 Log encontrado:", log);
    return log;
  },

  getLogsByTipo: async (tipo: string): Promise<SystemLogResponse[]> => {
    console.log("📋 Buscando logs por tipo:", tipo);
    const logs = await apiFetch<SystemLogResponse[]>(
      `/logs/tipo/${tipo}`,
      { method: "GET" },
      true,
      "Erro ao buscar logs por tipo"
    );
    console.log("✅📋 Logs por tipo carregados:", logs);
    return logs;
  },

  getLogsByUsuario: async (idUsuario: number): Promise<SystemLogResponse[]> => {
    console.log("📋 Buscando logs por usuário:", idUsuario);
    const logs = await apiFetch<SystemLogResponse[]>(
      `/logs/usuario/${idUsuario}`,
      { method: "GET" },
      true,
      "Erro ao buscar logs por usuário"
    );
    console.log("✅📋 Logs por usuário carregados:", logs);
    return logs;
  },

  getLogsByEntidade: async (entidade: string): Promise<SystemLogResponse[]> => {
    console.log("📋 Buscando logs por entidade:", entidade);
    const logs = await apiFetch<SystemLogResponse[]>(
      `/logs/entidade/${entidade}`,
      { method: "GET" },
      true,
      "Erro ao buscar logs por entidade"
    );
    console.log("✅📋 Logs por entidade carregados:", logs);
    return logs;
  },

  getLogsByPeriodo: async (
    dataInicio: string,
    dataFim: string
  ): Promise<SystemLogResponse[]> => {
    console.log("📋 Buscando logs por período:", dataInicio, dataFim);
    const logs = await apiFetch<SystemLogResponse[]>(
      `/logs/periodo?dataInicio=${dataInicio}&dataFim=${dataFim}`,
      { method: "GET" },
      true,
      "Erro ao buscar logs por período"
    );
    console.log("✅📋 Logs por período carregados:", logs);
    return logs;
  },

  deleteLogsAntigos: async (dataLimite: string): Promise<string> => {
    console.log("🗑️ Deletando logs antigos até:", dataLimite);
    const mensagem = await apiFetch<string>(
      `/logs/antigos?dataLimite=${dataLimite}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar logs antigos"
    );
    console.log("✅🗑️ Logs antigos deletados:", mensagem);
    return mensagem;
  },

  getEstatisticasLogs: async (): Promise<EstatisticasLogsResponse> => {
    console.log("📊 Buscando estatísticas de logs...");
    const estatisticas = await apiFetch<EstatisticasLogsResponse>(
      "/logs/estatisticas",
      { method: "GET" },
      true,
      "Erro ao buscar estatísticas de logs"
    );
    console.log("✅📊 Estatísticas carregadas:", estatisticas);
    return estatisticas;
  },

  getUsuarioByCpf: async (cpf: string): Promise<UsuarioResponse> => {
    console.log("👤 Buscando usuário por CPF:", cpf);
    const usuario = await apiFetch<UsuarioResponse>(
      `/usuarios/${cpf}`,
      { method: "GET" },
      true,
      "Erro ao buscar usuário"
    );
    console.log("✅👤 Usuário encontrado:", usuario);
    return usuario;
  },

  atualizarUsuario: async (
    cpf: string,
    dados: CreateUsuarioInput
  ): Promise<UsuarioResponse> => {
    console.log("👤 Atualizando usuário:", cpf, dados);
    const usuarioAtualizado = await apiFetch<UsuarioResponse>(
      `/usuarios/${cpf}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar usuário"
    );
    console.log("✅👤 Usuário atualizado:", usuarioAtualizado);
    return usuarioAtualizado;
  },

  alterarSenhaUsuario: async (
    dados: AlterarSenhaRequest
  ): Promise<UsuarioResponse> => {
    console.log("🔑 Alterando senha do usuário");
    const usuario = await apiFetch<UsuarioResponse>(
      `/usuarios/alterar-senha`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao alterar senha"
    );
    console.log("✅🔑 Senha alterada:", usuario);
    return usuario;
  },

  recuperarSenha: async (
    dados: RecuperarSenhaRequest
  ): Promise<UsuarioResponse> => {
    console.log("🔐 Recuperando senha:", dados.cpf);
    const usuario = await apiFetch<UsuarioResponse>(
      "/usuarios/recuperar-senha",
      { method: "PUT", body: JSON.stringify(dados) },
      false,
      "Erro ao recuperar senha"
    );
    console.log("✅🔐 Senha recuperada com sucesso:", usuario);
    return usuario;
  },

  getAllPacientes: async (): Promise<PacienteResponse[]> => {
    console.log("🏥 Buscando todos os pacientes...");
    const pacientes = await apiFetch<PacienteResponse[]>(
      "/pacientes",
      { method: "GET" },
      true,
      "Erro ao buscar pacientes"
    );
    console.log("✅🏥 Pacientes carregados:", pacientes);
    return pacientes;
  },

  getPacienteByCpf: async (cpf: string): Promise<PacienteResponse> => {
    console.log("🏥 Buscando paciente por CPF:", cpf);
    const paciente = await apiFetch<PacienteResponse>(
      `/pacientes/${cpf}`,
      { method: "GET" },
      true,
      "Erro ao buscar paciente"
    );
    console.log("✅🏥 Paciente encontrado:", paciente);
    return paciente;
  },

  atualizarPaciente: async (
    cpf: string,
    dados: PacienteRequest
  ): Promise<PacienteResponse> => {
    console.log("🏥 Atualizando paciente:", cpf, dados);
    const pacienteAtualizado = await apiFetch<PacienteResponse>(
      `/pacientes/${cpf}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar paciente"
    );
    console.log("✅🏥 Paciente atualizado:", pacienteAtualizado);
    return pacienteAtualizado;
  },

  deletePaciente: async (cpf: string): Promise<void> => {
    console.log("🗑️ Deletando paciente:", cpf);
    await apiFetch<void>(
      `/pacientes/${cpf}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar paciente"
    );
    console.log("✅🗑️ Paciente deletado com sucesso");
  },

  criarVinculoUsuarioPaciente: async (
    idUsuario: number,
    idPaciente: number
  ): Promise<void> => {
    console.log("🔗 Criando vínculo usuário-paciente:", idUsuario, idPaciente);
    await apiFetch<void>(
      `/usuarios-pacientes/${idUsuario}/${idPaciente}`,
      { method: "POST" },
      false,
      "Erro ao criar vínculo"
    );
    console.log("✅🔗 Vínculo criado com sucesso");
  },

  deleteVinculoUsuarioPaciente: async (
    idUsuario: number,
    idPaciente: number
  ): Promise<void> => {
    console.log(
      "🗑️ Deletando vínculo usuário-paciente:",
      idUsuario,
      idPaciente
    );
    await apiFetch<void>(
      `/usuarios-pacientes/${idUsuario}/${idPaciente}`,
      { method: "DELETE" },
      false,
      "Erro ao deletar vínculo"
    );
    console.log("✅🗑️ Vínculo deletado com sucesso");
  },

  getAllAlergias: async (): Promise<PacienteAlergiaResponse[]> => {
    console.log("🤧 Buscando todas as alergias...");
    const alergias = await apiFetch<PacienteAlergiaResponse[]>(
      "/pacientes/alergias",
      { method: "GET" },
      true,
      "Erro ao buscar alergias"
    );
    console.log("✅🤧 Alergias carregadas:", alergias);
    return alergias;
  },

  getAlergiasByPaciente: async (
    idPaciente: number
  ): Promise<PacienteAlergiaResponse[]> => {
    console.log("🤧 Buscando alergias por paciente:", idPaciente);
    const alergias = await apiFetch<PacienteAlergiaResponse[]>(
      `/pacientes/alergias/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar alergias por paciente"
    );
    console.log("✅🤧 Alergias por paciente carregadas:", alergias);
    return alergias;
  },

  getAlergiaById: async (id: number): Promise<PacienteAlergiaResponse> => {
    console.log("🤧 Buscando alergia por ID:", id);
    const alergia = await apiFetch<PacienteAlergiaResponse>(
      `/pacientes/alergias/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar alergia"
    );
    console.log("✅🤧 Alergia encontrada:", alergia);
    return alergia;
  },

  criarAlergia: async (
    dados: PacienteAlergiaRequest
  ): Promise<PacienteAlergiaResponse> => {
    console.log("🤧 Criando alergia:", dados);
    const alergiaCriada = await apiFetch<PacienteAlergiaResponse>(
      "/pacientes/alergias",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar alergia"
    );
    console.log("✅🤧 Alergia criada:", alergiaCriada);
    return alergiaCriada;
  },

  atualizarAlergia: async (
    id: number,
    dados: PacienteAlergiaRequest
  ): Promise<PacienteAlergiaResponse> => {
    console.log("🤧 Atualizando alergia:", id, dados);
    const alergiaAtualizada = await apiFetch<PacienteAlergiaResponse>(
      `/pacientes/alergias/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar alergia"
    );
    console.log("✅🤧 Alergia atualizada:", alergiaAtualizada);
    return alergiaAtualizada;
  },

  deleteAlergia: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando alergia:", id);
    await apiFetch<void>(
      `/pacientes/alergias/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar alergia"
    );
    console.log("✅🗑️ Alergia deletada com sucesso");
  },

  getAllAnexos: async (): Promise<PacienteAnexoResponse[]> => {
    console.log("📎 Buscando todos os anexos...");
    const anexos = await apiFetch<PacienteAnexoResponse[]>(
      "/pacientes/anexos",
      { method: "GET" },
      true,
      "Erro ao buscar anexos"
    );
    console.log("✅📎 Anexos carregados:", anexos);
    return anexos;
  },

  getAnexosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteAnexoResponse[]> => {
    console.log("📎 Buscando anexos por paciente:", idPaciente);
    const anexos = await apiFetch<PacienteAnexoResponse[]>(
      `/pacientes/anexos/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar anexos por paciente"
    );
    console.log("✅📎 Anexos por paciente carregados:", anexos);
    return anexos;
  },

  getAnexosByPacienteAndTipo: async (
    idPaciente: number,
    tipo: string
  ): Promise<PacienteAnexoResponse[]> => {
    console.log("📎 Buscando anexos por paciente e tipo:", idPaciente, tipo);
    const anexos = await apiFetch<PacienteAnexoResponse[]>(
      `/pacientes/anexos/paciente/${idPaciente}/tipo?tipo=${tipo}`,
      { method: "GET" },
      true,
      "Erro ao buscar anexos por paciente e tipo"
    );
    console.log("✅📎 Anexos por paciente e tipo carregados:", anexos);
    return anexos;
  },

  getAnexoById: async (id: number): Promise<PacienteAnexoResponse> => {
    console.log("📎 Buscando anexo por ID:", id);
    const anexo = await apiFetch<PacienteAnexoResponse>(
      `/pacientes/anexos/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar anexo"
    );
    console.log("✅📎 Anexo encontrado:", anexo);
    return anexo;
  },

  criarAnexo: async (
    dados: PacienteAnexoRequest
  ): Promise<PacienteAnexoResponse> => {
    console.log("📎 Criando anexo:", dados);
    const anexoCriado = await apiFetch<PacienteAnexoResponse>(
      "/pacientes/anexos",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar anexo"
    );
    console.log("✅📎 Anexo criado:", anexoCriado);
    return anexoCriado;
  },

  atualizarAnexo: async (
    id: number,
    dados: PacienteAnexoRequest
  ): Promise<PacienteAnexoResponse> => {
    console.log("📎 Atualizando anexo:", id, dados);
    const anexoAtualizado = await apiFetch<PacienteAnexoResponse>(
      `/pacientes/anexos/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar anexo"
    );
    console.log("✅📎 Anexo atualizado:", anexoAtualizado);
    return anexoAtualizado;
  },

  deleteAnexo: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando anexo:", id);
    await apiFetch<void>(
      `/pacientes/anexos/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar anexo"
    );
    console.log("✅🗑️ Anexo deletado com sucesso");
  },

  getAllAntecedentes: async (): Promise<PacienteAntecedenteResponse[]> => {
    console.log("🧬 Buscando todos os antecedentes...");
    const antecedentes = await apiFetch<PacienteAntecedenteResponse[]>(
      "/pacientes/antecedentes",
      { method: "GET" },
      true,
      "Erro ao buscar antecedentes"
    );
    console.log("✅🧬 Antecedentes carregados:", antecedentes);
    return antecedentes;
  },

  getAntecedentesByPaciente: async (
    idPaciente: number
  ): Promise<PacienteAntecedenteResponse[]> => {
    console.log("🧬 Buscando antecedentes por paciente:", idPaciente);
    const antecedentes = await apiFetch<PacienteAntecedenteResponse[]>(
      `/pacientes/antecedentes/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar antecedentes por paciente"
    );
    console.log("✅🧬 Antecedentes por paciente carregados:", antecedentes);
    return antecedentes;
  },

  getAntecedentesByPacienteAndTipoDoenca: async (
    idPaciente: number,
    tipoDoenca: string
  ): Promise<PacienteAntecedenteResponse[]> => {
    console.log(
      "🧬 Buscando antecedentes por paciente e tipo de doença:",
      idPaciente,
      tipoDoenca
    );
    const antecedentes = await apiFetch<PacienteAntecedenteResponse[]>(
      `/pacientes/antecedentes/paciente/${idPaciente}/tipo-doenca?tipoDoenca=${tipoDoenca}`,
      { method: "GET" },
      true,
      "Erro ao buscar antecedentes por tipo de doença"
    );
    console.log(
      "✅🧬 Antecedentes por tipo de doença carregados:",
      antecedentes
    );
    return antecedentes;
  },

  getAntecedentesByPacienteAndParentesco: async (
    idPaciente: number,
    parentesco: string
  ): Promise<PacienteAntecedenteResponse[]> => {
    console.log(
      "🧬 Buscando antecedentes por paciente e parentesco:",
      idPaciente,
      parentesco
    );
    const antecedentes = await apiFetch<PacienteAntecedenteResponse[]>(
      `/pacientes/antecedentes/paciente/${idPaciente}/parentesco?parentesco=${parentesco}`,
      { method: "GET" },
      true,
      "Erro ao buscar antecedentes por parentesco"
    );
    console.log("✅🧬 Antecedentes por parentesco carregados:", antecedentes);
    return antecedentes;
  },

  getAntecedenteById: async (
    id: number
  ): Promise<PacienteAntecedenteResponse> => {
    console.log("🧬 Buscando antecedente por ID:", id);
    const antecedente = await apiFetch<PacienteAntecedenteResponse>(
      `/pacientes/antecedentes/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar antecedente"
    );
    console.log("✅🧬 Antecedente encontrado:", antecedente);
    return antecedente;
  },

  criarAntecedente: async (
    dados: PacienteAntecedenteRequest
  ): Promise<PacienteAntecedenteResponse> => {
    console.log("🧬 Criando antecedente:", dados);
    const antecedenteCriado = await apiFetch<PacienteAntecedenteResponse>(
      "/pacientes/antecedentes",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar antecedente"
    );
    console.log("✅🧬 Antecedente criado:", antecedenteCriado);
    return antecedenteCriado;
  },

  atualizarAntecedente: async (
    id: number,
    dados: PacienteAntecedenteRequest
  ): Promise<PacienteAntecedenteResponse> => {
    console.log("🧬 Atualizando antecedente:", id, dados);
    const antecedenteAtualizado = await apiFetch<PacienteAntecedenteResponse>(
      `/pacientes/antecedentes/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar antecedente"
    );
    console.log("✅🧬 Antecedente atualizado:", antecedenteAtualizado);
    return antecedenteAtualizado;
  },

  deleteAntecedente: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando antecedente:", id);
    await apiFetch<void>(
      `/pacientes/antecedentes/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar antecedente"
    );
    console.log("✅🗑️ Antecedente deletado com sucesso");
  },

  getAllConvenios: async (): Promise<PacienteConvenioResponse[]> => {
    console.log("🛡️ Buscando todos os convênios...");
    const convenios = await apiFetch<PacienteConvenioResponse[]>(
      "/pacientes/convenios",
      { method: "GET" },
      true,
      "Erro ao buscar convênios"
    );
    console.log("✅🛡️ Convênios carregados:", convenios);
    return convenios;
  },

  getConveniosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteConvenioResponse[]> => {
    console.log("🛡️ Buscando convênios por paciente:", idPaciente);
    const convenios = await apiFetch<PacienteConvenioResponse[]>(
      `/pacientes/convenios/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar convênios por paciente"
    );
    console.log("✅🛡️ Convênios por paciente carregados:", convenios);
    return convenios;
  },

  getConveniosByPacienteAndNomeConvenio: async (
    idPaciente: number,
    nomeConvenio: string
  ): Promise<PacienteConvenioResponse[]> => {
    console.log(
      "🛡️ Buscando convênios por paciente e nome:",
      idPaciente,
      nomeConvenio
    );
    const convenios = await apiFetch<PacienteConvenioResponse[]>(
      `/pacientes/convenios/paciente/${idPaciente}/nome-convenio?nomeConvenio=${nomeConvenio}`,
      { method: "GET" },
      true,
      "Erro ao buscar convênios por nome"
    );
    console.log("✅🛡️ Convênios por nome carregados:", convenios);
    return convenios;
  },

  getConveniosValidosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteConvenioResponse[]> => {
    console.log("🛡️ Buscando convênios válidos por paciente:", idPaciente);
    const convenios = await apiFetch<PacienteConvenioResponse[]>(
      `/pacientes/convenios/paciente/${idPaciente}/validos`,
      { method: "GET" },
      true,
      "Erro ao buscar convênios válidos"
    );
    console.log("✅🛡️ Convênios válidos carregados:", convenios);
    return convenios;
  },

  getConveniosVencidosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteConvenioResponse[]> => {
    console.log("🛡️ Buscando convênios vencidos por paciente:", idPaciente);
    const convenios = await apiFetch<PacienteConvenioResponse[]>(
      `/pacientes/convenios/paciente/${idPaciente}/vencidos`,
      { method: "GET" },
      true,
      "Erro ao buscar convênios vencidos"
    );
    console.log("✅🛡️ Convênios vencidos carregados:", convenios);
    return convenios;
  },

  getConvenioById: async (id: number): Promise<PacienteConvenioResponse> => {
    console.log("🛡️ Buscando convênio por ID:", id);
    const convenio = await apiFetch<PacienteConvenioResponse>(
      `/pacientes/convenios/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar convênio"
    );
    console.log("✅🛡️ Convênio encontrado:", convenio);
    return convenio;
  },

  criarConvenio: async (
    dados: PacienteConvenioRequest
  ): Promise<PacienteConvenioResponse> => {
    console.log("🛡️ Criando convênio:", dados);
    const convenioCriado = await apiFetch<PacienteConvenioResponse>(
      "/pacientes/convenios",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar convênio"
    );
    console.log("✅🛡️ Convênio criado:", convenioCriado);
    return convenioCriado;
  },

  atualizarConvenio: async (
    id: number,
    dados: PacienteConvenioRequest
  ): Promise<PacienteConvenioResponse> => {
    console.log("🛡️ Atualizando convênio:", id, dados);
    const convenioAtualizado = await apiFetch<PacienteConvenioResponse>(
      `/pacientes/convenios/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar convênio"
    );
    console.log("✅🛡️ Convênio atualizado:", convenioAtualizado);
    return convenioAtualizado;
  },

  deleteConvenio: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando convênio:", id);
    await apiFetch<void>(
      `/pacientes/convenios/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar convênio"
    );
    console.log("✅🗑️ Convênio deletado com sucesso");
  },

  getAllDiagnosticos: async (): Promise<PacienteDiagnosticoResponse[]> => {
    console.log("🩺 Buscando todos os diagnósticos...");
    const diagnosticos = await apiFetch<PacienteDiagnosticoResponse[]>(
      "/pacientes/diagnosticos",
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos"
    );
    console.log("✅🩺 Diagnósticos carregados:", diagnosticos);
    return diagnosticos;
  },

  getDiagnosticosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteDiagnosticoResponse[]> => {
    console.log("🩺 Buscando diagnósticos por paciente:", idPaciente);
    const diagnosticos = await apiFetch<PacienteDiagnosticoResponse[]>(
      `/pacientes/diagnosticos/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos por paciente"
    );
    console.log("✅🩺 Diagnósticos por paciente carregados:", diagnosticos);
    return diagnosticos;
  },

  getDiagnosticosByPacienteAndCodigoCid: async (
    idPaciente: number,
    codigoCidDez: string
  ): Promise<PacienteDiagnosticoResponse[]> => {
    console.log(
      "🩺 Buscando diagnósticos por paciente e CID:",
      idPaciente,
      codigoCidDez
    );
    const diagnosticos = await apiFetch<PacienteDiagnosticoResponse[]>(
      `/pacientes/diagnosticos/paciente/${idPaciente}/codigo-cid?codigoCidDez=${codigoCidDez}`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos por CID"
    );
    console.log("✅🩺 Diagnósticos por CID carregados:", diagnosticos);
    return diagnosticos;
  },

  getDiagnosticosByPacienteAndTipo: async (
    idPaciente: number,
    tipo: string
  ): Promise<PacienteDiagnosticoResponse[]> => {
    console.log(
      "🩺 Buscando diagnósticos por paciente e tipo:",
      idPaciente,
      tipo
    );
    const diagnosticos = await apiFetch<PacienteDiagnosticoResponse[]>(
      `/pacientes/diagnosticos/paciente/${idPaciente}/tipo?tipo=${tipo}`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos por tipo"
    );
    console.log("✅🩺 Diagnósticos por tipo carregados:", diagnosticos);
    return diagnosticos;
  },

  getDiagnosticosAtivosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteDiagnosticoResponse[]> => {
    console.log("🩺 Buscando diagnósticos ativos por paciente:", idPaciente);
    const diagnosticos = await apiFetch<PacienteDiagnosticoResponse[]>(
      `/pacientes/diagnosticos/paciente/${idPaciente}/ativos`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos ativos"
    );
    console.log("✅🩺 Diagnósticos ativos carregados:", diagnosticos);
    return diagnosticos;
  },

  getDiagnosticosInativosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteDiagnosticoResponse[]> => {
    console.log("🩺 Buscando diagnósticos inativos por paciente:", idPaciente);
    const diagnosticos = await apiFetch<PacienteDiagnosticoResponse[]>(
      `/pacientes/diagnosticos/paciente/${idPaciente}/inativos`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos inativos"
    );
    console.log("✅🩺 Diagnósticos inativos carregados:", diagnosticos);
    return diagnosticos;
  },

  getDiagnosticoById: async (
    id: number
  ): Promise<PacienteDiagnosticoResponse> => {
    console.log("🩺 Buscando diagnóstico por ID:", id);
    const diagnostico = await apiFetch<PacienteDiagnosticoResponse>(
      `/pacientes/diagnosticos/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnóstico"
    );
    console.log("✅🩺 Diagnóstico encontrado:", diagnostico);
    return diagnostico;
  },

  criarDiagnostico: async (
    dados: PacienteDiagnosticoRequest
  ): Promise<PacienteDiagnosticoResponse> => {
    console.log("🩺 Criando diagnóstico:", dados);
    const diagnosticoCriado = await apiFetch<PacienteDiagnosticoResponse>(
      "/pacientes/diagnosticos",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar diagnóstico"
    );
    console.log("✅🩺 Diagnóstico criado:", diagnosticoCriado);
    return diagnosticoCriado;
  },

  atualizarDiagnostico: async (
    id: number,
    dados: PacienteDiagnosticoRequest
  ): Promise<PacienteDiagnosticoResponse> => {
    console.log("🩺 Atualizando diagnóstico:", id, dados);
    const diagnosticoAtualizado = await apiFetch<PacienteDiagnosticoResponse>(
      `/pacientes/diagnosticos/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar diagnóstico"
    );
    console.log("✅🩺 Diagnóstico atualizado:", diagnosticoAtualizado);
    return diagnosticoAtualizado;
  },

  deleteDiagnostico: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando diagnóstico:", id);
    await apiFetch<void>(
      `/pacientes/diagnosticos/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar diagnóstico"
    );
    console.log("✅🗑️ Diagnóstico deletado com sucesso");
  },

  getAllMedicamentos: async (): Promise<PacienteMedicamentoResponse[]> => {
    console.log("💊 Buscando todos os medicamentos...");
    const medicamentos = await apiFetch<PacienteMedicamentoResponse[]>(
      "/pacientes/medicamentos",
      { method: "GET" },
      true,
      "Erro ao buscar medicamentos"
    );
    console.log("✅💊 Medicamentos carregados:", medicamentos);
    return medicamentos;
  },

  getMedicamentosByPaciente: async (
    idPaciente: number
  ): Promise<PacienteMedicamentoResponse[]> => {
    console.log("💊 Buscando medicamentos por paciente:", idPaciente);
    const medicamentos = await apiFetch<PacienteMedicamentoResponse[]>(
      `/pacientes/medicamentos/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar medicamentos por paciente"
    );
    console.log("✅💊 Medicamentos por paciente carregados:", medicamentos);
    return medicamentos;
  },

  getMedicamentosByPacienteAndMedicamento: async (
    idPaciente: number,
    medicamento: string
  ): Promise<PacienteMedicamentoResponse[]> => {
    console.log(
      "💊 Buscando medicamentos por paciente e nome:",
      idPaciente,
      medicamento
    );
    const medicamentos = await apiFetch<PacienteMedicamentoResponse[]>(
      `/pacientes/medicamentos/paciente/${idPaciente}/medicamento?medicamento=${medicamento}`,
      { method: "GET" },
      true,
      "Erro ao buscar medicamentos por nome"
    );
    console.log("✅💊 Medicamentos por nome carregados:", medicamentos);
    return medicamentos;
  },

  getMedicamentoById: async (
    id: number
  ): Promise<PacienteMedicamentoResponse> => {
    console.log("💊 Buscando medicamento por ID:", id);
    const medicamento = await apiFetch<PacienteMedicamentoResponse>(
      `/pacientes/medicamentos/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar medicamento"
    );
    console.log("✅💊 Medicamento encontrado:", medicamento);
    return medicamento;
  },

  criarMedicamento: async (
    dados: PacienteMedicamentoRequest
  ): Promise<PacienteMedicamentoResponse> => {
    console.log("💊 Criando medicamento:", dados);
    const medicamentoCriado = await apiFetch<PacienteMedicamentoResponse>(
      "/pacientes/medicamentos",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar medicamento"
    );
    console.log("✅💊 Medicamento criado:", medicamentoCriado);
    return medicamentoCriado;
  },

  atualizarMedicamento: async (
    id: number,
    dados: PacienteMedicamentoRequest
  ): Promise<PacienteMedicamentoResponse> => {
    console.log("💊 Atualizando medicamento:", id, dados);
    const medicamentoAtualizado = await apiFetch<PacienteMedicamentoResponse>(
      `/pacientes/medicamentos/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar medicamento"
    );
    console.log("✅💊 Medicamento atualizado:", medicamentoAtualizado);
    return medicamentoAtualizado;
  },

  deleteMedicamento: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando medicamento:", id);
    await apiFetch<void>(
      `/pacientes/medicamentos/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar medicamento"
    );
    console.log("✅🗑️ Medicamento deletado com sucesso");
  },

  getAllVacinas: async (): Promise<PacienteVacinaResponse[]> => {
    console.log("💉 Buscando todas as vacinas...");
    const vacinas = await apiFetch<PacienteVacinaResponse[]>(
      "/pacientes/vacinas",
      { method: "GET" },
      true,
      "Erro ao buscar vacinas"
    );
    console.log("✅💉 Vacinas carregadas:", vacinas);
    return vacinas;
  },

  getVacinasByPaciente: async (
    idPaciente: number
  ): Promise<PacienteVacinaResponse[]> => {
    console.log("💉 Buscando vacinas por paciente:", idPaciente);
    const vacinas = await apiFetch<PacienteVacinaResponse[]>(
      `/pacientes/vacinas/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar vacinas por paciente"
    );
    console.log("✅💉 Vacinas por paciente carregadas:", vacinas);
    return vacinas;
  },

  getVacinasByPacienteAndVacina: async (
    idPaciente: number,
    vacina: string
  ): Promise<PacienteVacinaResponse[]> => {
    console.log("💉 Buscando vacinas por paciente e nome:", idPaciente, vacina);
    const vacinas = await apiFetch<PacienteVacinaResponse[]>(
      `/pacientes/vacinas/paciente/${idPaciente}/vacina?vacina=${vacina}`,
      { method: "GET" },
      true,
      "Erro ao buscar vacinas por nome"
    );
    console.log("✅💉 Vacinas por nome carregadas:", vacinas);
    return vacinas;
  },

  getVacinaById: async (id: number): Promise<PacienteVacinaResponse> => {
    console.log("💉 Buscando vacina por ID:", id);
    const vacina = await apiFetch<PacienteVacinaResponse>(
      `/pacientes/vacinas/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar vacina"
    );
    console.log("✅💉 Vacina encontrada:", vacina);
    return vacina;
  },

  criarVacina: async (
    dados: PacienteVacinaRequest
  ): Promise<PacienteVacinaResponse> => {
    console.log("💉 Criando vacina:", dados);
    const vacinaCriada = await apiFetch<PacienteVacinaResponse>(
      "/pacientes/vacinas",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar vacina"
    );
    console.log("✅💉 Vacina criada:", vacinaCriada);
    return vacinaCriada;
  },

  atualizarVacina: async (
    id: number,
    dados: PacienteVacinaRequest
  ): Promise<PacienteVacinaResponse> => {
    console.log("💉 Atualizando vacina:", id, dados);
    const vacinaAtualizada = await apiFetch<PacienteVacinaResponse>(
      `/pacientes/vacinas/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar vacina"
    );
    console.log("✅💉 Vacina atualizada:", vacinaAtualizada);
    return vacinaAtualizada;
  },

  deleteVacina: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando vacina:", id);
    await apiFetch<void>(
      `/pacientes/vacinas/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar vacina"
    );
    console.log("✅🗑️ Vacina deletada com sucesso");
  },

  getAllProfissionaisSaude: async (): Promise<ProfissionalSaudeResponse[]> => {
    console.log("🩺 Buscando todos os profissionais de saúde...");
    const profissionais = await apiFetch<ProfissionalSaudeResponse[]>(
      "/profissionais-saude",
      { method: "GET" },
      true,
      "Erro ao buscar profissionais de saúde"
    );
    console.log("✅🩺 Profissionais de saúde carregados:", profissionais);
    return profissionais;
  },

  getProfissionalSaudeById: async (
    id: number
  ): Promise<ProfissionalSaudeResponse> => {
    console.log("🩺 Buscando profissional de saúde por ID:", id);
    const profissional = await apiFetch<ProfissionalSaudeResponse>(
      `/profissionais-saude/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar profissional de saúde"
    );
    console.log("✅🩺 Profissional de saúde encontrado:", profissional);
    return profissional;
  },

  atualizarProfissionalSaude: async (
    id: number,
    dados: ProfissionalSaudeRequest
  ): Promise<ProfissionalSaudeResponse> => {
    console.log("🩺 Atualizando profissional de saúde:", id, dados);
    const profissionalAtualizado = await apiFetch<ProfissionalSaudeResponse>(
      `/profissionais-saude/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar profissional de saúde"
    );
    console.log(
      "✅🩺 Profissional de saúde atualizado:",
      profissionalAtualizado
    );
    return profissionalAtualizado;
  },

  deleteProfissionalSaude: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando profissional de saúde:", id);
    await apiFetch<void>(
      `/profissionais-saude/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar profissional de saúde"
    );
    console.log("✅🗑️ Profissional de saúde deletado com sucesso");
  },

  getAllFuncionarios: async (): Promise<FuncionarioResponse[]> => {
    console.log("👔 Buscando todos os funcionários...");
    const funcionarios = await apiFetch<FuncionarioResponse[]>(
      "/funcionarios",
      { method: "GET" },
      true,
      "Erro ao buscar funcionários"
    );
    console.log("✅👔 Funcionários carregados:", funcionarios);
    return funcionarios;
  },

  getFuncionarioById: async (id: number): Promise<FuncionarioResponse> => {
    console.log("👔 Buscando funcionário por ID:", id);
    const funcionario = await apiFetch<FuncionarioResponse>(
      `/funcionarios/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar funcionário"
    );
    console.log("✅👔 Funcionário encontrado:", funcionario);
    return funcionario;
  },

  atualizarFuncionario: async (
    id: number,
    dados: FuncionarioRequest
  ): Promise<FuncionarioResponse> => {
    console.log("👔 Atualizando funcionário:", id, dados);
    const funcionarioAtualizado = await apiFetch<FuncionarioResponse>(
      `/funcionarios/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar funcionário"
    );
    console.log("✅👔 Funcionário atualizado:", funcionarioAtualizado);
    return funcionarioAtualizado;
  },

  deleteFuncionario: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando funcionário:", id);
    await apiFetch<void>(
      `/funcionarios/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar funcionário"
    );
    console.log("✅🗑️ Funcionário deletado com sucesso");
  },

  getProfissionaisByPaciente: async (
    idPaciente: number
  ): Promise<ProfissionalSaudeResponse[]> => {
    console.log("🩺 Buscando profissionais por paciente:", idPaciente);
    const profissionais = await apiFetch<ProfissionalSaudeResponse[]>(
      `/pacientes/${idPaciente}/profissionais-saude`,
      { method: "GET" },
      true,
      "Erro ao buscar profissionais por paciente"
    );
    console.log("✅🩺 Profissionais por paciente carregados:", profissionais);
    return profissionais;
  },

  adicionarProfissionalToPaciente: async (
    idPaciente: number,
    idProfissional: number
  ): Promise<void> => {
    console.log(
      "🔗 Adicionando profissional ao paciente:",
      idPaciente,
      idProfissional
    );
    await apiFetch<void>(
      `/pacientes/${idPaciente}/profissionais-saude/${idProfissional}`,
      { method: "POST" },
      true,
      "Erro ao adicionar profissional ao paciente"
    );
    console.log("✅🔗 Profissional adicionado com sucesso");
  },

  removerProfissionalFromPaciente: async (
    idPaciente: number,
    idProfissional: number
  ): Promise<void> => {
    console.log(
      "🗑️ Removendo profissional do paciente:",
      idPaciente,
      idProfissional
    );
    await apiFetch<void>(
      `/pacientes/${idPaciente}/profissionais-saude/${idProfissional}`,
      { method: "DELETE" },
      true,
      "Erro ao remover profissional do paciente"
    );
    console.log("✅🗑️ Profissional removido com sucesso");
  },

  getFuncionariosByPaciente: async (
    idPaciente: number
  ): Promise<FuncionarioResponse[]> => {
    console.log("👔 Buscando funcionários por paciente:", idPaciente);
    const funcionarios = await apiFetch<FuncionarioResponse[]>(
      `/pacientes/${idPaciente}/funcionarios`,
      { method: "GET" },
      true,
      "Erro ao buscar funcionários por paciente"
    );
    console.log("✅👔 Funcionários por paciente carregados:", funcionarios);
    return funcionarios;
  },

  adicionarFuncionarioToPaciente: async (
    idPaciente: number,
    idFuncionario: number
  ): Promise<void> => {
    console.log(
      "🔗 Adicionando funcionário ao paciente:",
      idPaciente,
      idFuncionario
    );
    await apiFetch<void>(
      `/pacientes/${idPaciente}/funcionarios/${idFuncionario}`,
      { method: "POST" },
      true,
      "Erro ao adicionar funcionário ao paciente"
    );
    console.log("✅🔗 Funcionário adicionado com sucesso");
  },

  removerFuncionarioFromPaciente: async (
    idPaciente: number,
    idFuncionario: number
  ): Promise<void> => {
    console.log(
      "🗑️ Removendo funcionário do paciente:",
      idPaciente,
      idFuncionario
    );
    await apiFetch<void>(
      `/pacientes/${idPaciente}/funcionarios/${idFuncionario}`,
      { method: "DELETE" },
      true,
      "Erro ao remover funcionário do paciente"
    );
    console.log("✅🗑️ Funcionário removido com sucesso");
  },

  getAllConsultas: async (): Promise<ConsultaResponse[]> => {
    console.log("🩺 Buscando todas as consultas...");
    const consultas = await apiFetch<ConsultaResponse[]>(
      "/consultas",
      { method: "GET" },
      true,
      "Erro ao buscar consultas"
    );
    console.log("✅🩺 Consultas carregadas:", consultas);
    return consultas;
  },

  getConsultasByPaciente: async (
    idPaciente: number
  ): Promise<ConsultaResponse[]> => {
    console.log("🩺 Buscando consultas por paciente:", idPaciente);
    const consultas = await apiFetch<ConsultaResponse[]>(
      `/consultas/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar consultas por paciente"
    );
    console.log("✅🩺 Consultas por paciente carregadas:", consultas);
    return consultas;
  },

  getConsultasByProfissional: async (
    idProfissional: number
  ): Promise<ConsultaResponse[]> => {
    console.log("🩺 Buscando consultas por profissional:", idProfissional);
    const consultas = await apiFetch<ConsultaResponse[]>(
      `/consultas/profissional/${idProfissional}`,
      { method: "GET" },
      true,
      "Erro ao buscar consultas por profissional"
    );
    console.log("✅🩺 Consultas por profissional carregadas:", consultas);
    return consultas;
  },

  getConsultasByPeriodo: async (
    dataInicio: string,
    dataFim: string
  ): Promise<ConsultaResponse[]> => {
    console.log("🩺 Buscando consultas por período:", dataInicio, dataFim);
    const consultas = await apiFetch<ConsultaResponse[]>(
      `/consultas/periodo?dataInicio=${dataInicio}&dataFim=${dataFim}`,
      { method: "GET" },
      true,
      "Erro ao buscar consultas por período"
    );
    console.log("✅🩺 Consultas por período carregadas:", consultas);
    return consultas;
  },

  getConsultaById: async (id: number): Promise<ConsultaResponse> => {
    console.log("🩺 Buscando consulta por ID:", id);
    const consulta = await apiFetch<ConsultaResponse>(
      `/consultas/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar consulta"
    );
    console.log("✅🩺 Consulta encontrada:", consulta);
    return consulta;
  },

  criarConsulta: async (dados: ConsultaRequest): Promise<ConsultaResponse> => {
    console.log("🩺 Criando consulta:", dados);
    const consultaCriada = await apiFetch<ConsultaResponse>(
      "/consultas",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar consulta"
    );
    console.log("✅🩺 Consulta criada:", consultaCriada);
    return consultaCriada;
  },

  atualizarConsulta: async (
    id: number,
    dados: ConsultaRequest
  ): Promise<ConsultaResponse> => {
    console.log("🩺 Atualizando consulta:", id, dados);
    const consultaAtualizada = await apiFetch<ConsultaResponse>(
      `/consultas/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar consulta"
    );
    console.log("✅🩺 Consulta atualizada:", consultaAtualizada);
    return consultaAtualizada;
  },

  deleteConsulta: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando consulta:", id);
    await apiFetch<void>(
      `/consultas/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar consulta"
    );
    console.log("✅🗑️ Consulta deletada com sucesso");
  },

  getAllConsultaDiagnosticos: async (): Promise<
    ConsultaDiagnosticoResponse[]
  > => {
    console.log("🩺 Buscando todos os diagnósticos de consulta...");
    const diagnosticos = await apiFetch<ConsultaDiagnosticoResponse[]>(
      "/consultas/diagnosticos",
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos de consulta"
    );
    console.log("✅🩺 Diagnósticos de consulta carregados:", diagnosticos);
    return diagnosticos;
  },

  getConsultaDiagnosticosByConsulta: async (
    idConsulta: number
  ): Promise<ConsultaDiagnosticoResponse[]> => {
    console.log("🩺 Buscando diagnósticos por consulta:", idConsulta);
    const diagnosticos = await apiFetch<ConsultaDiagnosticoResponse[]>(
      `/consultas/diagnosticos/consulta/${idConsulta}`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnósticos por consulta"
    );
    console.log("✅🩺 Diagnósticos por consulta carregados:", diagnosticos);
    return diagnosticos;
  },

  getConsultaDiagnosticoById: async (
    id: number
  ): Promise<ConsultaDiagnosticoResponse> => {
    console.log("🩺 Buscando diagnóstico de consulta por ID:", id);
    const diagnostico = await apiFetch<ConsultaDiagnosticoResponse>(
      `/consultas/diagnosticos/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar diagnóstico de consulta"
    );
    console.log("✅🩺 Diagnóstico de consulta encontrado:", diagnostico);
    return diagnostico;
  },

  criarConsultaDiagnostico: async (
    dados: ConsultaDiagnosticoRequest
  ): Promise<ConsultaDiagnosticoResponse> => {
    console.log("🩺 Criando diagnóstico de consulta:", dados);
    const diagnosticoCriado = await apiFetch<ConsultaDiagnosticoResponse>(
      "/consultas/diagnosticos",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar diagnóstico de consulta"
    );
    console.log("✅🩺 Diagnóstico de consulta criado:", diagnosticoCriado);
    return diagnosticoCriado;
  },

  atualizarConsultaDiagnostico: async (
    id: number,
    dados: ConsultaDiagnosticoRequest
  ): Promise<ConsultaDiagnosticoResponse> => {
    console.log("🩺 Atualizando diagnóstico de consulta:", id, dados);
    const diagnosticoAtualizado = await apiFetch<ConsultaDiagnosticoResponse>(
      `/consultas/diagnosticos/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar diagnóstico de consulta"
    );
    console.log(
      "✅🩺 Diagnóstico de consulta atualizado:",
      diagnosticoAtualizado
    );
    return diagnosticoAtualizado;
  },

  deleteConsultaDiagnostico: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando diagnóstico de consulta:", id);
    await apiFetch<void>(
      `/consultas/diagnosticos/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar diagnóstico de consulta"
    );
    console.log("✅🗑️ Diagnóstico de consulta deletado com sucesso");
  },

  getAllConsultaPrescricoes: async (): Promise<
    ConsultaPrescricaoResponse[]
  > => {
    console.log("💊 Buscando todas as prescrições de consulta...");
    const prescricoes = await apiFetch<ConsultaPrescricaoResponse[]>(
      "/consultas/prescricoes",
      { method: "GET" },
      true,
      "Erro ao buscar prescrições de consulta"
    );
    console.log("✅💊 Prescrições de consulta carregadas:", prescricoes);
    return prescricoes;
  },

  getConsultaPrescricoesByConsulta: async (
    idConsulta: number
  ): Promise<ConsultaPrescricaoResponse[]> => {
    console.log("💊 Buscando prescrições por consulta:", idConsulta);
    const prescricoes = await apiFetch<ConsultaPrescricaoResponse[]>(
      `/consultas/prescricoes/consulta/${idConsulta}`,
      { method: "GET" },
      true,
      "Erro ao buscar prescrições por consulta"
    );
    console.log("✅💊 Prescrições por consulta carregadas:", prescricoes);
    return prescricoes;
  },

  getConsultaPrescricaoById: async (
    id: number
  ): Promise<ConsultaPrescricaoResponse> => {
    console.log("💊 Buscando prescrição de consulta por ID:", id);
    const prescricao = await apiFetch<ConsultaPrescricaoResponse>(
      `/consultas/prescricoes/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar prescrição de consulta"
    );
    console.log("✅💊 Prescrição de consulta encontrada:", prescricao);
    return prescricao;
  },

  criarConsultaPrescricao: async (
    dados: ConsultaPrescricaoRequest
  ): Promise<ConsultaPrescricaoResponse> => {
    console.log("💊 Criando prescrição de consulta:", dados);
    const prescricaoCriada = await apiFetch<ConsultaPrescricaoResponse>(
      "/consultas/prescricoes",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar prescrição de consulta"
    );
    console.log("✅💊 Prescrição de consulta criada:", prescricaoCriada);
    return prescricaoCriada;
  },

  atualizarConsultaPrescricao: async (
    id: number,
    dados: ConsultaPrescricaoRequest
  ): Promise<ConsultaPrescricaoResponse> => {
    console.log("💊 Atualizando prescrição de consulta:", id, dados);
    const prescricaoAtualizada = await apiFetch<ConsultaPrescricaoResponse>(
      `/consultas/prescricoes/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar prescrição de consulta"
    );
    console.log(
      "✅💊 Prescrição de consulta atualizada:",
      prescricaoAtualizada
    );
    return prescricaoAtualizada;
  },

  deleteConsultaPrescricao: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando prescrição de consulta:", id);
    await apiFetch<void>(
      `/consultas/prescricoes/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar prescrição de consulta"
    );
    console.log("✅🗑️ Prescrição de consulta deletada com sucesso");
  },

  getAllPrescricaoItens: async (): Promise<PrescricaoItemResponse[]> => {
    console.log("💊 Buscando todos os itens de prescrição...");
    const itens = await apiFetch<PrescricaoItemResponse[]>(
      "/prescricoes/itens",
      { method: "GET" },
      true,
      "Erro ao buscar itens de prescrição"
    );
    console.log("✅💊 Itens de prescrição carregados:", itens);
    return itens;
  },

  getPrescricaoItensByPrescricao: async (
    idPrescricao: number
  ): Promise<PrescricaoItemResponse[]> => {
    console.log("💊 Buscando itens por prescrição:", idPrescricao);
    const itens = await apiFetch<PrescricaoItemResponse[]>(
      `/prescricoes/itens/prescricao/${idPrescricao}`,
      { method: "GET" },
      true,
      "Erro ao buscar itens por prescrição"
    );
    console.log("✅💊 Itens por prescrição carregados:", itens);
    return itens;
  },

  getPrescricaoItemById: async (
    id: number
  ): Promise<PrescricaoItemResponse> => {
    console.log("💊 Buscando item de prescrição por ID:", id);
    const item = await apiFetch<PrescricaoItemResponse>(
      `/prescricoes/itens/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar item de prescrição"
    );
    console.log("✅💊 Item de prescrição encontrado:", item);
    return item;
  },

  criarPrescricaoItem: async (
    dados: PrescricaoItemRequest
  ): Promise<PrescricaoItemResponse> => {
    console.log("💊 Criando item de prescrição:", dados);
    const itemCriado = await apiFetch<PrescricaoItemResponse>(
      "/prescricoes/itens",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar item de prescrição"
    );
    console.log("✅💊 Item de prescrição criado:", itemCriado);
    return itemCriado;
  },

  atualizarPrescricaoItem: async (
    id: number,
    dados: PrescricaoItemRequest
  ): Promise<PrescricaoItemResponse> => {
    console.log("💊 Atualizando item de prescrição:", id, dados);
    const itemAtualizado = await apiFetch<PrescricaoItemResponse>(
      `/prescricoes/itens/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar item de prescrição"
    );
    console.log("✅💊 Item de prescrição atualizado:", itemAtualizado);
    return itemAtualizado;
  },

  deletePrescricaoItem: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando item de prescrição:", id);
    await apiFetch<void>(
      `/prescricoes/itens/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar item de prescrição"
    );
    console.log("✅🗑️ Item de prescrição deletado com sucesso");
  },

  getAllExames: async (): Promise<ExameResponse[]> => {
    console.log("🧪 Buscando todos os exames...");
    const exames = await apiFetch<ExameResponse[]>(
      "/exames",
      { method: "GET" },
      true,
      "Erro ao buscar exames"
    );
    console.log("✅🧪 Exames carregados:", exames);
    return exames;
  },

  getExamesAtivos: async (): Promise<ExameResponse[]> => {
    console.log("🧪 Buscando exames ativos...");
    const exames = await apiFetch<ExameResponse[]>(
      "/exames/ativos",
      { method: "GET" },
      true,
      "Erro ao buscar exames ativos"
    );
    console.log("✅🧪 Exames ativos carregados:", exames);
    return exames;
  },

  getExamesByTipo: async (tipo: string): Promise<ExameResponse[]> => {
    console.log("🧪 Buscando exames por tipo:", tipo);
    const exames = await apiFetch<ExameResponse[]>(
      `/exames/tipo?tipo=${tipo}`,
      { method: "GET" },
      true,
      "Erro ao buscar exames por tipo"
    );
    console.log("✅🧪 Exames por tipo carregados:", exames);
    return exames;
  },

  getExameById: async (id: number): Promise<ExameResponse> => {
    console.log("🧪 Buscando exame por ID:", id);
    const exame = await apiFetch<ExameResponse>(
      `/exames/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar exame"
    );
    console.log("✅🧪 Exame encontrado:", exame);
    return exame;
  },

  criarExame: async (dados: ExameRequest): Promise<ExameResponse> => {
    console.log("🧪 Criando exame:", dados);
    const exameCriado = await apiFetch<ExameResponse>(
      "/exames",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar exame"
    );
    console.log("✅🧪 Exame criado:", exameCriado);
    return exameCriado;
  },

  atualizarExame: async (
    id: number,
    dados: ExameRequest
  ): Promise<ExameResponse> => {
    console.log("🧪 Atualizando exame:", id, dados);
    const exameAtualizado = await apiFetch<ExameResponse>(
      `/exames/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar exame"
    );
    console.log("✅🧪 Exame atualizado:", exameAtualizado);
    return exameAtualizado;
  },

  deleteExame: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando exame:", id);
    await apiFetch<void>(
      `/exames/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar exame"
    );
    console.log("✅🗑️ Exame deletado com sucesso");
  },

  getAllExamesSolicitados: async (): Promise<ExameSolicitadoResponse[]> => {
    console.log("🧪 Buscando todos os exames solicitados...");
    const solicitados = await apiFetch<ExameSolicitadoResponse[]>(
      "/exames/solicitados",
      { method: "GET" },
      true,
      "Erro ao buscar exames solicitados"
    );
    console.log("✅🧪 Exames solicitados carregados:", solicitados);
    return solicitados;
  },

  getExamesSolicitadosByConsulta: async (
    idConsulta: number
  ): Promise<ExameSolicitadoResponse[]> => {
    console.log("🧪 Buscando exames solicitados por consulta:", idConsulta);
    const solicitados = await apiFetch<ExameSolicitadoResponse[]>(
      `/exames/solicitados/consulta/${idConsulta}`,
      { method: "GET" },
      true,
      "Erro ao buscar exames solicitados por consulta"
    );
    console.log(
      "✅🧪 Exames solicitados por consulta carregados:",
      solicitados
    );
    return solicitados;
  },

  getExamesSolicitadosByPaciente: async (
    idPaciente: number
  ): Promise<ExameSolicitadoResponse[]> => {
    console.log("🧪 Buscando exames solicitados por paciente:", idPaciente);
    const solicitados = await apiFetch<ExameSolicitadoResponse[]>(
      `/exames/solicitados/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar exames solicitados por paciente"
    );
    console.log(
      "✅🧪 Exames solicitados por paciente carregados:",
      solicitados
    );
    return solicitados;
  },

  getExamesSolicitadosByProfissional: async (
    idProfissional: number
  ): Promise<ExameSolicitadoResponse[]> => {
    console.log(
      "🧪 Buscando exames solicitados por profissional:",
      idProfissional
    );
    const solicitados = await apiFetch<ExameSolicitadoResponse[]>(
      `/exames/solicitados/profissional/${idProfissional}`,
      { method: "GET" },
      true,
      "Erro ao buscar exames solicitados por profissional"
    );
    console.log(
      "✅🧪 Exames solicitados por profissional carregados:",
      solicitados
    );
    return solicitados;
  },

  getExamesSolicitadosPendentes: async (): Promise<
    ExameSolicitadoResponse[]
  > => {
    console.log("🧪 Buscando exames solicitados pendentes...");
    const solicitados = await apiFetch<ExameSolicitadoResponse[]>(
      "/exames/solicitados/pendentes",
      { method: "GET" },
      true,
      "Erro ao buscar exames pendentes"
    );
    console.log("✅🧪 Exames pendentes carregados:", solicitados);
    return solicitados;
  },

  getExameSolicitadoById: async (
    id: number
  ): Promise<ExameSolicitadoResponse> => {
    console.log("🧪 Buscando exame solicitado por ID:", id);
    const solicitado = await apiFetch<ExameSolicitadoResponse>(
      `/exames/solicitados/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar exame solicitado"
    );
    console.log("✅🧪 Exame solicitado encontrado:", solicitado);
    return solicitado;
  },

  criarExameSolicitado: async (
    dados: ExameSolicitadoRequest
  ): Promise<ExameSolicitadoResponse> => {
    console.log("🧪 Criando exame solicitado:", dados);
    const solicitadoCriado = await apiFetch<ExameSolicitadoResponse>(
      "/exames/solicitados",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar exame solicitado"
    );
    console.log("✅🧪 Exame solicitado criado:", solicitadoCriado);
    return solicitadoCriado;
  },

  atualizarExameSolicitado: async (
    id: number,
    dados: ExameSolicitadoRequest
  ): Promise<ExameSolicitadoResponse> => {
    console.log("🧪 Atualizando exame solicitado:", id, dados);
    const solicitadoAtualizado = await apiFetch<ExameSolicitadoResponse>(
      `/exames/solicitados/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar exame solicitado"
    );
    console.log("✅🧪 Exame solicitado atualizado:", solicitadoAtualizado);
    return solicitadoAtualizado;
  },

  deleteExameSolicitado: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando exame solicitado:", id);
    await apiFetch<void>(
      `/exames/solicitados/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar exame solicitado"
    );
    console.log("✅🗑️ Exame solicitado deletado com sucesso");
  },

  getAllExameResultados: async (): Promise<ExameResultadoResponse[]> => {
    console.log("🧪 Buscando todos os resultados de exame...");
    const resultados = await apiFetch<ExameResultadoResponse[]>(
      "/exames/resultados",
      { method: "GET" },
      true,
      "Erro ao buscar resultados de exame"
    );
    console.log("✅🧪 Resultados de exame carregados:", resultados);
    return resultados;
  },

  getExameResultadosBySolicitado: async (
    idExameSolicitado: number
  ): Promise<ExameResultadoResponse[]> => {
    console.log(
      "🧪 Buscando resultados por exame solicitado:",
      idExameSolicitado
    );
    const resultados = await apiFetch<ExameResultadoResponse[]>(
      `/exames/resultados/solicitado/${idExameSolicitado}`,
      { method: "GET" },
      true,
      "Erro ao buscar resultados por solicitado"
    );
    console.log("✅🧪 Resultados por solicitado carregados:", resultados);
    return resultados;
  },

  getExameResultadosByPaciente: async (
    idPaciente: number
  ): Promise<ExameResultadoResponse[]> => {
    console.log("🧪 Buscando resultados por paciente:", idPaciente);
    const resultados = await apiFetch<ExameResultadoResponse[]>(
      `/exames/resultados/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar resultados por paciente"
    );
    console.log("✅🧪 Resultados por paciente carregados:", resultados);
    return resultados;
  },

  getExameResultadosByPeriodo: async (
    dataInicio: string,
    dataFim: string
  ): Promise<ExameResultadoResponse[]> => {
    console.log("🧪 Buscando resultados por período:", dataInicio, dataFim);
    const resultados = await apiFetch<ExameResultadoResponse[]>(
      `/exames/resultados/periodo?dataInicio=${dataInicio}&dataFim=${dataFim}`,
      { method: "GET" },
      true,
      "Erro ao buscar resultados por período"
    );
    console.log("✅🧪 Resultados por período carregados:", resultados);
    return resultados;
  },

  getExameResultadoById: async (
    id: number
  ): Promise<ExameResultadoResponse> => {
    console.log("🧪 Buscando resultado de exame por ID:", id);
    const resultado = await apiFetch<ExameResultadoResponse>(
      `/exames/resultados/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar resultado de exame"
    );
    console.log("✅🧪 Resultado de exame encontrado:", resultado);
    return resultado;
  },

  criarExameResultado: async (
    dados: ExameResultadoRequest
  ): Promise<ExameResultadoResponse> => {
    console.log("🧪 Criando resultado de exame:", dados);
    const resultadoCriado = await apiFetch<ExameResultadoResponse>(
      "/exames/resultados",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar resultado de exame"
    );
    console.log("✅🧪 Resultado de exame criado:", resultadoCriado);
    return resultadoCriado;
  },

  atualizarExameResultado: async (
    id: number,
    dados: ExameResultadoRequest
  ): Promise<ExameResultadoResponse> => {
    console.log("🧪 Atualizando resultado de exame:", id, dados);
    const resultadoAtualizado = await apiFetch<ExameResultadoResponse>(
      `/exames/resultados/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar resultado de exame"
    );
    console.log("✅🧪 Resultado de exame atualizado:", resultadoAtualizado);
    return resultadoAtualizado;
  },

  deleteExameResultado: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando resultado de exame:", id);
    await apiFetch<void>(
      `/exames/resultados/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar resultado de exame"
    );
    console.log("✅🗑️ Resultado de exame deletado com sucesso");
  },

  getAllInternacoes: async (): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando todas as internações...");
    const internacoes = await apiFetch<InternacaoResponse[]>(
      "/internacoes",
      { method: "GET" },
      true,
      "Erro ao buscar internações"
    );
    console.log("✅🏥 Internações carregadas:", internacoes);
    return internacoes;
  },

  getInternacoesByPaciente: async (
    idPaciente: number
  ): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando internações por paciente:", idPaciente);
    const internacoes = await apiFetch<InternacaoResponse[]>(
      `/internacoes/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar internações por paciente"
    );
    console.log("✅🏥 Internações por paciente carregadas:", internacoes);
    return internacoes;
  },

  getInternacoesByProfissional: async (
    idProfissional: number
  ): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando internações por profissional:", idProfissional);
    const internacoes = await apiFetch<InternacaoResponse[]>(
      `/internacoes/profissional/${idProfissional}`,
      { method: "GET" },
      true,
      "Erro ao buscar internações por profissional"
    );
    console.log("✅🏥 Internações por profissional carregadas:", internacoes);
    return internacoes;
  },

  getInternacoesAtivas: async (): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando internações ativas...");
    const internacoes = await apiFetch<InternacaoResponse[]>(
      "/internacoes/ativas",
      { method: "GET" },
      true,
      "Erro ao buscar internações ativas"
    );
    console.log("✅🏥 Internações ativas carregadas:", internacoes);
    return internacoes;
  },

  getInternacoesAtivasByPaciente: async (
    idPaciente: number
  ): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando internações ativas por paciente:", idPaciente);
    const internacoes = await apiFetch<InternacaoResponse[]>(
      `/internacoes/paciente/${idPaciente}/ativas`,
      { method: "GET" },
      true,
      "Erro ao buscar internações ativas por paciente"
    );
    console.log(
      "✅🏥 Internações ativas por paciente carregadas:",
      internacoes
    );
    return internacoes;
  },

  getInternacoesFinalizadas: async (): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando internações finalizadas...");
    const internacoes = await apiFetch<InternacaoResponse[]>(
      "/internacoes/finalizadas",
      { method: "GET" },
      true,
      "Erro ao buscar internações finalizadas"
    );
    console.log("✅🏥 Internações finalizadas carregadas:", internacoes);
    return internacoes;
  },

  getInternacoesByPeriodo: async (
    dataInicio: string,
    dataFim: string
  ): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando internações por período:", dataInicio, dataFim);
    const internacoes = await apiFetch<InternacaoResponse[]>(
      `/internacoes/periodo?dataInicio=${dataInicio}&dataFim=${dataFim}`,
      { method: "GET" },
      true,
      "Erro ao buscar internações por período"
    );
    console.log("✅🏥 Internações por período carregadas:", internacoes);
    return internacoes;
  },

  getInternacoesByLeito: async (
    leito: string
  ): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando internações por leito:", leito);
    const internacoes = await apiFetch<InternacaoResponse[]>(
      `/internacoes/leito?leito=${leito}`,
      { method: "GET" },
      true,
      "Erro ao buscar internações por leito"
    );
    console.log("✅🏥 Internações por leito carregadas:", internacoes);
    return internacoes;
  },

  getLeitoAtivo: async (leito: string): Promise<InternacaoResponse[]> => {
    console.log("🏥 Buscando leito ativo:", leito);
    const internacoes = await apiFetch<InternacaoResponse[]>(
      `/internacoes/leito/ativo?leito=${leito}`,
      { method: "GET" },
      true,
      "Erro ao buscar leito ativo"
    );
    console.log("✅🏥 Leito ativo carregado:", internacoes);
    return internacoes;
  },

  getInternacaoById: async (id: number): Promise<InternacaoResponse> => {
    console.log("🏥 Buscando internação por ID:", id);
    const internacao = await apiFetch<InternacaoResponse>(
      `/internacoes/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar internação"
    );
    console.log("✅🏥 Internação encontrada:", internacao);
    return internacao;
  },

  criarInternacao: async (
    dados: InternacaoRequest
  ): Promise<InternacaoResponse> => {
    console.log("🏥 Criando internação:", dados);
    const internacaoCriada = await apiFetch<InternacaoResponse>(
      "/internacoes",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar internação"
    );
    console.log("✅🏥 Internação criada:", internacaoCriada);
    return internacaoCriada;
  },

  atualizarInternacao: async (
    id: number,
    dados: InternacaoRequest
  ): Promise<InternacaoResponse> => {
    console.log("🏥 Atualizando internação:", id, dados);
    const internacaoAtualizada = await apiFetch<InternacaoResponse>(
      `/internacoes/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar internação"
    );
    console.log("✅🏥 Internação atualizada:", internacaoAtualizada);
    return internacaoAtualizada;
  },

  registrarAltaInternacao: async (
    id: number,
    dataAlta: string
  ): Promise<InternacaoResponse> => {
    console.log("🏥 Registrando alta na internação:", id, dataAlta);
    const internacao = await apiFetch<InternacaoResponse>(
      `/internacoes/${id}/alta?dataAlta=${dataAlta}`,
      { method: "PATCH" },
      true,
      "Erro ao registrar alta"
    );
    console.log("✅🏥 Alta registrada:", internacao);
    return internacao;
  },

  deleteInternacao: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando internação:", id);
    await apiFetch<void>(
      `/internacoes/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar internação"
    );
    console.log("✅🗑️ Internação deletada com sucesso");
  },

  getAllAgendas: async (): Promise<AgendaResponse[]> => {
    console.log("📅 Buscando todas as agendas...");
    const agendas = await apiFetch<AgendaResponse[]>(
      "/agendas",
      { method: "GET" },
      true,
      "Erro ao buscar agendas"
    );
    console.log("✅📅 Agendas carregadas:", agendas);
    return agendas;
  },

  getAgendasByProfissional: async (
    idProfissionalSaude: number
  ): Promise<AgendaResponse[]> => {
    console.log("📅 Buscando agendas por profissional:", idProfissionalSaude);
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/profissional/${idProfissionalSaude}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por profissional"
    );
    console.log("✅📅 Agendas por profissional carregadas:", agendas);
    return agendas;
  },

  getAgendasByProfissionalOrdenado: async (
    idProfissionalSaude: number
  ): Promise<AgendaResponse[]> => {
    console.log(
      "📅 Buscando agendas ordenadas por profissional:",
      idProfissionalSaude
    );
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/profissional/${idProfissionalSaude}/ordenado`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas ordenadas por profissional"
    );
    console.log("✅📅 Agendas ordenadas por profissional carregadas:", agendas);
    return agendas;
  },

  getAgendasFuturasByProfissional: async (
    idProfissionalSaude: number
  ): Promise<AgendaResponse[]> => {
    console.log(
      "📅 Buscando agendas futuras por profissional:",
      idProfissionalSaude
    );
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/profissional/${idProfissionalSaude}/futuras`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas futuras por profissional"
    );
    console.log("✅📅 Agendas futuras por profissional carregadas:", agendas);
    return agendas;
  },

  getAgendasByPaciente: async (
    idPaciente: number
  ): Promise<AgendaResponse[]> => {
    console.log("📅 Buscando agendas por paciente:", idPaciente);
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/paciente/${idPaciente}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por paciente"
    );
    console.log("✅📅 Agendas por paciente carregadas:", agendas);
    return agendas;
  },

  getAgendasByPacienteOrdenado: async (
    idPaciente: number
  ): Promise<AgendaResponse[]> => {
    console.log("📅 Buscando agendas ordenadas por paciente:", idPaciente);
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/paciente/${idPaciente}/ordenado`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas ordenadas por paciente"
    );
    console.log("✅📅 Agendas ordenadas por paciente carregadas:", agendas);
    return agendas;
  },

  getAgendasFuturasByPaciente: async (
    idPaciente: number
  ): Promise<AgendaResponse[]> => {
    console.log("📅 Buscando agendas futuras por paciente:", idPaciente);
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/paciente/${idPaciente}/futuras`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas futuras por paciente"
    );
    console.log("✅📅 Agendas futuras por paciente carregadas:", agendas);
    return agendas;
  },

  getAgendasByStatus: async (status: string): Promise<AgendaResponse[]> => {
    console.log("📅 Buscando agendas por status:", status);
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/status?status=${status}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por status"
    );
    console.log("✅📅 Agendas por status carregadas:", agendas);
    return agendas;
  },

  getAgendasByProfissionalAndStatus: async (
    idProfissionalSaude: number,
    status: string
  ): Promise<AgendaResponse[]> => {
    console.log(
      "📅 Buscando agendas por profissional e status:",
      idProfissionalSaude,
      status
    );
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/profissional/${idProfissionalSaude}/status?status=${status}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por profissional e status"
    );
    console.log("✅📅 Agendas por profissional e status carregadas:", agendas);
    return agendas;
  },

  getAgendasByPacienteAndStatus: async (
    idPaciente: number,
    status: string
  ): Promise<AgendaResponse[]> => {
    console.log(
      "📅 Buscando agendas por paciente e status:",
      idPaciente,
      status
    );
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/paciente/${idPaciente}/status?status=${status}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por paciente e status"
    );
    console.log("✅📅 Agendas por paciente e status carregadas:", agendas);
    return agendas;
  },

  getAgendasByPeriodo: async (
    dataInicio: string,
    dataFim: string
  ): Promise<AgendaResponse[]> => {
    console.log("📅 Buscando agendas por período:", dataInicio, dataFim);
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/periodo?dataInicio=${dataInicio}&dataFim=${dataFim}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por período"
    );
    console.log("✅📅 Agendas por período carregadas:", agendas);
    return agendas;
  },

  getAgendasByProfissionalAndPeriodo: async (
    idProfissionalSaude: number,
    dataInicio: string,
    dataFim: string
  ): Promise<AgendaResponse[]> => {
    console.log(
      "📅 Buscando agendas por profissional e período:",
      idProfissionalSaude,
      dataInicio,
      dataFim
    );
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/profissional/${idProfissionalSaude}/periodo?dataInicio=${dataInicio}&dataFim=${dataFim}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por profissional e período"
    );
    console.log("✅📅 Agendas por profissional e período carregadas:", agendas);
    return agendas;
  },

  getAgendasByPacienteAndPeriodo: async (
    idPaciente: number,
    dataInicio: string,
    dataFim: string
  ): Promise<AgendaResponse[]> => {
    console.log(
      "📅 Buscando agendas por paciente e período:",
      idPaciente,
      dataInicio,
      dataFim
    );
    const agendas = await apiFetch<AgendaResponse[]>(
      `/agendas/paciente/${idPaciente}/periodo?dataInicio=${dataInicio}&dataFim=${dataFim}`,
      { method: "GET" },
      true,
      "Erro ao buscar agendas por paciente e período"
    );
    console.log("✅📅 Agendas por paciente e período carregadas:", agendas);
    return agendas;
  },

  getAgendaById: async (id: number): Promise<AgendaResponse> => {
    console.log("📅 Buscando agenda por ID:", id);
    const agenda = await apiFetch<AgendaResponse>(
      `/agendas/${id}`,
      { method: "GET" },
      true,
      "Erro ao buscar agenda"
    );
    console.log("✅📅 Agenda encontrada:", agenda);
    return agenda;
  },

  criarAgenda: async (dados: AgendaRequest): Promise<AgendaResponse> => {
    console.log("📅 Criando agenda:", dados);
    const agendaCriada = await apiFetch<AgendaResponse>(
      "/agendas",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar agenda"
    );
    console.log("✅📅 Agenda criada:", agendaCriada);
    return agendaCriada;
  },

  atualizarAgenda: async (
    id: number,
    dados: AgendaRequest
  ): Promise<AgendaResponse> => {
    console.log("📅 Atualizando agenda:", id, dados);
    const agendaAtualizada = await apiFetch<AgendaResponse>(
      `/agendas/${id}`,
      { method: "PUT", body: JSON.stringify(dados) },
      true,
      "Erro ao atualizar agenda"
    );
    console.log("✅📅 Agenda atualizada:", agendaAtualizada);
    return agendaAtualizada;
  },

  deleteAgenda: async (id: number): Promise<void> => {
    console.log("🗑️ Deletando agenda:", id);
    await apiFetch<void>(
      `/agendas/${id}`,
      { method: "DELETE" },
      true,
      "Erro ao deletar agenda"
    );
    console.log("✅🗑️ Agenda deletada com sucesso");
  },
};
