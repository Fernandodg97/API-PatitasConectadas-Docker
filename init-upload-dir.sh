#!/bin/bash

# Crear el directorio base de uploads
mkdir -p uploads/posts

# Establecer permisos
chmod 755 uploads
chmod 755 uploads/posts

echo "Directorio de uploads creado correctamente en: $(pwd)/uploads"
echo "Estructura:"
echo "uploads/"
echo "└── posts/" 