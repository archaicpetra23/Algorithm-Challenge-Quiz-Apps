#!/bin/bash
# Script untuk menjalankan Algorithm Challenge Quiz di Linux
# Mendapatkan direktori tempat script ini berada
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR"

# Menjalankan aplikasi menggunakan Maven
echo "Memulai aplikasi Algorithm Challenge Quiz..."
mvn javafx:run
