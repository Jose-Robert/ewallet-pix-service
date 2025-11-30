#!/bin/bash

echo "🚀 Criando rede Docker..."
docker network create ewallet-net 2>/dev/null || echo "Rede já existe."

echo "🐘 Subindo PostgreSQL..."
docker rm -f postgres-ewallet 2>/dev/null
docker run -d \
  --name postgres-ewallet \
  --network ewallet-net \
  -p 5432:5432 \
  -e POSTGRES_DB=ewallet_pix_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=admin \
  postgres:16

echo "⏳ Aguardando PostgreSQL iniciar..."
sleep 5

echo "📦 Subindo API eWallet Pix..."
docker rm -f ewallet-pix-service 2>/dev/null
docker run -d \
  --name ewallet-pix-service \
  --network ewallet-net \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://postgres-ewallet:5432/ewallet_pix_db" \
  -e SPRING_DATASOURCE_USERNAME="postgres" \
  -e SPRING_DATASOURCE_PASSWORD="admin" \
  jsrobert10/ewallet-pix-service:1.0.0

echo "✔ Tudo pronto!"
echo "📌 API:      http://localhost:8080"
echo "📌 Postgres: localhost:5432"
echo "📌 Banco:    ewallet_pix_db"
