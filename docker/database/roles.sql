REVOKE CONNECT, TEMPORARY ON DATABASE teste_db FROM PUBLIC;

CREATE ROLE backend_permission_teste_db;

GRANT CONNECT ON DATABASE teste_db TO backend_permission_teste_db;
GRANT USAGE ON SCHEMA clinico, administrativo, cadastro, seguranca TO backend_permission_teste_db;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA clinico, administrativo, cadastro, seguranca TO backend_permission_teste_db;

CREATE USER backend_user WITH PASSWORD 'backend12345';
GRANT backend_permission_teste_db TO backend_user;