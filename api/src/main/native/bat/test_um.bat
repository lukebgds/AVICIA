@echo off
setlocal enabledelayedexpansion

REM Caminho onde o .bat está
set SCRIPT_DIR=%~dp0

REM Subir um nível (pastas) para ir até /native
cd /d "%SCRIPT_DIR%.."
set WORK_DIR=%cd%

echo Script está em: %SCRIPT_DIR%
echo Pasta de trabalho: %WORK_DIR%

echo ================================
echo   Processando todas as imagens
echo ================================

REM Executável dentro de /native
set EXEC=%WORK_DIR%\image_processor.exe

if not exist "%EXEC%" (
    echo ERRO: Não encontrei o executável:
    echo %EXEC%
    pause
    exit /b 1
)

REM Loop por todas as imagens JPG dentro de /native
for %%f in (%WORK_DIR%\*.jpg) do (
    echo Processando %%~nxf ...
    "%EXEC%" "%%f" "%WORK_DIR%\output_%%~nxf" 1.5 0.1
)

echo --------------------------------
echo  PROCESSAMENTO CONCLUIDO!
echo --------------------------------
pause
