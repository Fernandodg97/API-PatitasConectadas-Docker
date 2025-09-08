#!/bin/bash

# Configuración
USUARIO="mouad"
BASEDATOS="patitasconectadas"
FECHA=$(date +"%Y%m%d_%H%M%S")
ARCHIVO="patitasconectadas_mouad_$FECHA.sql"

# Exportar la base de datos
echo "Iniciando respaldo de la base de datos..."
pg_dump -U "$USUARIO" -d "$BASEDATOS" -f "$ARCHIVO"

# Verificar si el respaldo fue exitoso
if [ $? -eq 0 ]; then
    echo "Respaldo completado exitosamente: $ARCHIVO"
else
    echo "Error al realizar el respaldo."
fi
