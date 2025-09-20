@echo off
echo Iniciando Aplicação...

start cmd /k "cd api && mvn spring-boot:run"

start cmd /k "cd avicia && npm run dev"