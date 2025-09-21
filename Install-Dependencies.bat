@echo off 
echo Instalando dependencias do projeto e iniciando o container Docker...

start cmd /k "cd avicia && npm install" 

start cmd /k "cd docker && docker-compose up -d"
