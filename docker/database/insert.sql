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
        "usuario": "CRUD",
        "paciente": "CRUD",
        "profissionalSaude": "N",
        "funcionario": "N",
        "convenio": "R",
        "alergia": "R",
        "medicamento": "R",
        "vacina": "R",
        "antecedente": "R",
        "anexo": "R",
        "associacao": "R",
        "log": "R",
        "consulta": "R",
        "diagnostico": "R",
        "prescriacao": "R",
        "exame": "R",
        "exameSolicitar": "CRD",
        "exameResultado": "R",
        "internacao": "R",
        "relatorio": "R"
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
        "convenio": "N",
        "alergia": "N",
        "medicamento": "N",
        "vacina": "N",
        "antecedente": "N",
        "anexo": "N",
        "associacao": "CRUD",
        "log": "CRUD",
        "consulta": "N",
        "diagnostico": "N",
        "prescriacao": "N",
        "exame": "N",
        "exameSolicitar": "N",
        "exameResultado": "N",
        "internacao": "N",
        "relatorio": "CRUD"
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
        "sistema": "N",
        "role": "R",
        "usuario": "CRUD",
        "paciente": "CRUD",
        "profissionalSaude": "R",
        "funcionario": "N",
        "convenio": "N",
        "alergia": "N",
        "medicamento": "N",
        "vacina": "N",
        "antecedente": "R",
        "anexo": "R",
        "associacao": "R",
        "log": "R",
        "consulta": "R",
        "diagnostico": "R",
        "prescriacao": "R",
        "exame": "R",
        "exameSolicitar": "CRUD",
        "exameResultado": "CRUD",
        "internacao": "CRUD",
        "relatorio": "CRUD"
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
        "usuario": "CRUD",
        "paciente": "CRUD",
        "profissionalSaude": "N",
        "funcionario": "N",
        "convenio": "CRUD",
        "alergia": "CRUD",
        "medicamento": "CRUD",
        "vacina": "CRUD",
        "antecedente": "CRUD",
        "anexo": "CRUD",
        "associacao": "CRUD",
        "log": "R",
        "consulta": "R",
        "diagnostico": "CRUD",
        "prescriacao": "CRUD",
        "exame": "CRUD",
        "exameSolicitar": "CRUD",
        "exameResultado": "CRUD",
        "internacao": "R",
        "relatorio": "R"
    }'::jsonb,
    'Profissional de saúde (médico/enfermeiro)'
);

INSERT INTO seguranca.usuario (
    id_usuario,
    id_role,
    nome,
    cpf,
    data_nascimento,
    sexo,
    estado_civil,
    email,
    telefone,
    endereco,
    senha_hash,
    data_criacao,
    mfa_habilitado,
    ativo
) VALUES (
    100,
    101,
    'SISTEMA',
    'N/A',
    '2025-01-01',
    'N/A',
    'N/A',
    'N/A',
    'N/A',
    'N/A',
    'N/A',
    '2025-01-01',
    false,
    false
);

