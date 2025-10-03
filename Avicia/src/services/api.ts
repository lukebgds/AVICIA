const BASE_URL = "http://localhost:9081/api";

interface LoginResponse {
  accessToken: string;
  expiresIn: string;
}

interface Usuario {
  idUsuario: string;
  nome: string;
}

interface Paciente {
  idPaciente: string;
}

interface Role {
  idRole: string;
}

interface Funcionario {
  idFuncionario: string;
}

// Nova interface para o response de getAllUsuarios (array de usuários)
interface UsuariosResponse {
  idUsuario: number;
  nome: string;
  sobrenome: string;
  cpf: string;
  email: string;
  telefone: string;
  ativo: boolean;
  mfaHabilitado: boolean;
  dataCriacao: string;
  idRole: number;
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

    if (!response.ok) {
      const errorData = await response
        .json()
        .catch(() => ({ message: "Erro desconhecido" }));
      throw new Error(errorMessage || errorData.message);
    }

    const data = await response.json();
    console.log(`✅🚀 Resposta recebida: ${endpoint}`, data);
    return data as T;
  } catch (error) {
    console.error(`❌🚀 Erro na requisição: ${endpoint}`, error);
    throw error;
  }
};

export const api = {
  getRoleByName: async (dados: any) => {
    console.log("🔍 Buscando Role:", dados);
    const role = await apiFetch<Role>(
      `/roles/${dados}`,
      undefined,
      false,
      `Role "${dados}" não encontrada`
    );
    console.log("✅🔍 Role encontrada:", role);
    return role;
  },

  getAllUsuarios: async (): Promise<UsuariosResponse[]> => {
    console.log("👥 Buscando todos os usuários...");
    const usuarios = await apiFetch<UsuariosResponse[]>(
      "/api/usuarios",
      { method: "GET" },
      true,
      "Erro ao buscar usuários"
    );
    console.log("✅👥 Usuários carregados:", usuarios);
    return usuarios;
  },

  criarUsuario: async (dados: any) => {
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

  criarPaciente: async (dados: any) => {
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

  criarFuncionario: async (dados: any) => {
    console.log("👔 Criando funcionário:", dados);
    const funcionarioCriado = await apiFetch<Funcionario>(
      "/funcionarios/cadastro",
      { method: "POST", body: JSON.stringify(dados) },
      true,
      "Erro ao criar funcionário"
    );
    console.log("✅👔  Funcionário criado:", funcionarioCriado);
    return funcionarioCriado;
  },

  loginPaciente: async (dados: any) => {
    console.log("🔐 Login paciente:", dados);
    const resultado = await apiFetch<LoginResponse>(
      "/login",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "CPF ou senha incorretos"
    );
    localStorage.setItem("token", resultado.accessToken);
    localStorage.setItem("expiresIn", resultado.expiresIn.toString());
    console.log("✅🔐 Login paciente bem-sucedido:", resultado);
    return resultado;
  },

  loginAdmin: async (dados: any) => {
    console.log("🔐 Login admin:", dados);
    const resultado = await apiFetch<LoginResponse>(
      "/admin/login",
      { method: "POST", body: JSON.stringify(dados) },
      false,
      "Nome ou senha incorretos"
    );
    localStorage.setItem("token", resultado.accessToken);
    localStorage.setItem("expiresIn", resultado.expiresIn.toString());
    console.log("✅🔐 Login admin bem-sucedido:", resultado);
    return resultado;
  },
};
