@echo off 
echo Instalando dependencias do projeto... 

start cmd /k "cd avicia && npm install" 

start cmd /k "cd docker && docker-compose up -d"

