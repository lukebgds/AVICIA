@echo off
echo Instalando dependencias do projeto...

REM Vai para a pasta Avicia
cd avicia

REM Executa o npm install
npm install

REM Vai para a pasta docker
cd docker

REM Executa o docker
docker-compose up -d

pause

