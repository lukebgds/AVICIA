@echo off
echo Iniciando Aplicação...

REM Abre um novo cmd, entra na pasta api e roda o Spring Boot
start cmd /k "cd api && mvn spring-boot:run"

REM Abre um novo cmd, entra na pasta Avicia e roda o React
start cmd /k "cd avicia && npm run dev"