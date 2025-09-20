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
)