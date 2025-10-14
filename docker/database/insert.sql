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
        "role": "N",
        "usuario": "N",
        "paciente": "N",
        "profissionalSaude": "N",
        "funcionario": "N",
        "exames": "R",
        "financeiro": "R",
        "relatorio": "R",
        "consulta": "CRD",
        "medicamento": "R"
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
        "sistema": "CRUD",
        "role": "CRUD",
        "usuario": "CRUD",
        "paciente": "CRUD",
        "profissionalSaude": "CRUD",
        "funcionario": "CRUD",
        "exames": "N",
        "financeiro": "N",
        "relatorio": "CRUD",
        "consulta": "N",
        "medicamento": "N"
    }'::jsonb,
    'Admin total sistema'
);

INSERT INTO seguranca.roles (
    id_role,
    nome,
    permissoes,
    descricao
)
VALUES (
    501,
    'FUNCIONARIO',
    '{
        "sistema": "R",
        "role": "R",
        "usuario": "R",
        "paciente": "R",
        "profissionalSaude": "R",
        "funcionario": "R",
        "exames": "N",
        "financeiro": "R",
        "relatorio": "R",
        "consulta": "N",
        "medicamento": "N"
    }'::jsonb,
    'Funcionário operacional'
);

INSERT INTO seguranca.roles (
    id_role,
    nome,
    permissoes,
    descricao
)
VALUES (
    601,
    'PROFISSIONAL.SAUDE',
    '{
        "sistema": "N",
        "role": "N",
        "usuario": "N",
        "paciente": "N",
        "profissionalSaude": "N",
        "funcionario": "N",
        "exames": "CRUD",
        "financeiro": "R",
        "relatorio": "R",
        "consulta": "CRUD",
        "medicamento": "CRUD"
    }'::jsonb,
    'Profissional de saúde (médico/enfermeiro)'
);

