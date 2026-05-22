@echo off
REM Script untuk menjalankan Algorithm Challenge Quiz di Windows
title Algorithm Challenge Quiz Runner

REM Berpindah ke direktori tempat batch file ini berada
cd /d %~dp0

echo Memulai aplikasi Algorithm Challenge Quiz...
call mvn javafx:run

if %ERRORLEVEL% neq 0 (
    echo Terjadi kesalahan saat menjalankan aplikasi. Pastikan Java dan Maven sudah terpasang.
    pause
)
