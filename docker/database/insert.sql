INSERT INTO seguranca.roles (
    id_role,
    nome,
    permissoes,
    descricao
)
VALUES (
    701,
    'PACIENTE',
    '{
        "sistema": "N",
        "usuarios": "N",
        "pacientes": "N",
        "consultas": "RW",
        "exames": "R",
        "financeiro": "R",
        "relatorios": "R"
    }'::jsonb,
    'Paciente comum'
);

INSERT INTO seguranca.roles (
    id_role,
    nome,
    permissoes,
    descricao
)
VALUES (
    101,
    'SYSTEM.ADMIN',
    '{
        "sistema": "RW",
        "roles": "RW",
        "usuario": "RW",
        "funcionario": "RW",
        "paciente": "RW",
        "profissional_saude": "RW"
    }'::jsonb,
    'Admin total sistema'
);

INSERT INTO seguranca.usuario (
    id_usuario,
    id_role,
    nome,
    sobrenome,
    cpf,
    email,
    telefone,
    senha_hash,
    data_criacao,
    mfa_habilitado,
    ativo
) VALUES (
    1,
    101,
    'system.admin',
    'admin',
    '000.000.000-00',
    'admin@admin.com',
    '00000000000',
    'Avicia@2025',
    '2025-09-27',
    false,
    true
);

