// Api services version final 2
const BASE_URL = 'http://localhost:9081/api';

export const api = {
  getRoleByName: async (nome: string) => {
    console.log('🔍 Buscando Role:', nome);
    const response = await fetch(`${BASE_URL}/roles/${nome}`);
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Role "${nome}" não encontrada: ${errorText}`);
    }
    const role = await response.json();
    console.log('✅ Role encontrada:', role);
    return role;
  },

  criarUsuario: async (dados: any) => {
    console.log('👤 Criando usuário:', dados);
    const response = await fetch(`${BASE_URL}/usuarios/cadastro`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(dados)
    });
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Erro ao criar usuário: ${errorText}`);
    }
    const usuarioCriado = await response.json();
    console.log('✅ Usuário criado:', usuarioCriado);
    return usuarioCriado;
  },

  criarPaciente: async (dados: any) => {
    console.log('🏥 Criando paciente:', dados);
    const response = await fetch(`${BASE_URL}/pacientes/cadastro`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(dados)
    });
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Erro ao criar paciente: ${errorText}`);
    }
    const pacienteCriado = await response.json();
    console.log('✅ Paciente criado:', pacienteCriado);
    return pacienteCriado;
  },

  loginPaciente: async (dados: any) => {
    const response = await fetch(`${BASE_URL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(dados)
    });
    if (!response.ok) throw new Error('CPF ou senha incorretos');
    return response.json();
  },

  loginAdmin: async (dados: any) => {
    const response = await fetch(`${BASE_URL}/admin/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(dados)
    });
    if (!response.ok) throw new Error('Nome ou senha incorretos');
    return response.json();
  }
};
