# ============================
# Etapa 1: Build com Gradle
# ============================
FROM gradle:8.10.2-jdk21 AS build
WORKDIR /app

# Copia apenas os arquivos de configuração primeiro (para cache de dependências)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Faz download das dependências (usa cache do Docker nas próximas builds)
RUN ./gradlew build -x test --no-daemon || return 0

# Agora copia o restante do código
COPY . .

# Gera o JAR do Spring Boot
RUN ./gradlew bootJar --no-daemon

# ============================
# Etapa 2: Imagem final
# ============================
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copia apenas o JAR final da etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Porta exposta pelo Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
