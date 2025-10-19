const BASE_URL = "http://localhost:9081/api";

// --- Interfaces de Retorno ---
interface LoginResponse {
  accessToken: string;
  expiresIn: string;
}

interface Usuario {
  idUsuario: number;
  nome: string;
}

interface Paciente {
  idPaciente: string;
}

interface Role {
  idRole: string;
}

interface Role {
  idRole: string;
  nome: string;
  idTipoRole: number;
  descricao: string;
  permissoes: Record<string, string>;
}

// Interface para o input de criação (alinhada com o form)
interface CreateRoleInput {
  nome: string;
  idTipoRole: number;
  descricao: string;
  permissoes: Record<string, string>;
}

interface Funcionario {
  idFuncionario: string;
}

interface Profissional_saude {
  idFuncionario: number;
  idProfissionalSaude: number;
}

interface UsuariosResponse {
  idUsuario: number;
  nome: string;
  cpf: string;
  email: string;
  telefone: string;
  ativo: boolean;
  mfaHabilitado: boolean;
  dataCriacao: string;
  idRole: number;
}

// --- Interfaces de Entrada ---
interface CreateUsuarioInput {
  nome: string;
  cpf: string;
  dataNascimento?: string;
  sexo?: string;
  estadoCivil?: string;
  email: string;
  senha: string;
  telefone?: string;
  endereco?: string;
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

interface LoginInput {
  cpf?: string;
  nome?: string;
  senha: string;
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
  getRoleByName: async (roleName: string): Promise<Role> => {
    console.log("🔍 Buscando Role:", roleName);
    const role = await apiFetch<Role>(
      `/roles/${roleName}`,
      { method: "GET" },
      false,
      `Role "${roleName}" não encontrada`
    );
    console.log("✅🔍 Role encontrada:", role);
    return role;
  },

  getAllUsuarios: async (): Promise<UsuariosResponse[]> => {
    console.log("👥 Buscando todos os usuários...");
    const usuarios = await apiFetch<UsuariosResponse[]>(
      "/usuarios",
      { method: "GET" },
      true,
      "Erro ao buscar lista de usuários"
    );
    console.log("✅👥 Usuários carregados:", usuarios);
    return usuarios;
  },

  criarUsuario: async (dados: CreateUsuarioInput): Promise<Usuario> => {
    console.log("👤 Criando usuário:", dados);
    const usuarioCriado = await apiFetch<Usuario>(
      "/usuarios/cadastro",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Erro ao criar usuário"
    );
    console.log("✅👤 Usuário criado:", usuarioCriado);
    return usuarioCriado;
  },

  criarPaciente: async (dados: CreatePacienteInput): Promise<Paciente> => {
    console.log("🏥 Criando paciente:", dados);
    const pacienteCriado = await apiFetch<Paciente>(
      "/pacientes/cadastro",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Erro ao criar paciente"
    );
    console.log("✅🏥 Paciente criado:", pacienteCriado);
    return pacienteCriado;
  },

  criarRole: async (dados: CreateRoleInput): Promise<Role> => {
    console.log("🛡️ Criando role:", dados);
    const roleCriada = await apiFetch<Role>(
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

  getAllRoles: async (): Promise<Role[]> => {
    console.log("🔍 Buscando todas as roles...");
    const roles = await apiFetch<Role[]>(
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
  ): Promise<Funcionario> => {
    console.log("👔 Criando funcionário:", dados);
    const funcionarioCriado = await apiFetch<Funcionario>(
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
  ): Promise<Profissional_saude> => {
    console.log("🩺 Criando profissional de saúde:", dados);
    const profissionalCriado = await apiFetch<Profissional_saude>(
      "/profissionais-saude",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar profissional de saúde"
    );
    console.log("✅🩺 Profissional de saúde criado:", profissionalCriado);
    return profissionalCriado;
  },

  loginPaciente: async (dados: LoginInput): Promise<LoginResponse> => {
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

  loginAdmin: async (dados: LoginInput): Promise<LoginResponse> => {
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
};
