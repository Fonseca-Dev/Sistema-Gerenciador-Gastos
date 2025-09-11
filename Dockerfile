# Etapa 1 - Build da aplicação
FROM gradle:8.10.2-jdk21 AS build
WORKDIR /app

# Copia os arquivos do projeto (inclusive gradlew e gradle/)
COPY . .

# Dá permissão para o gradlew
RUN chmod +x gradlew

# Faz o build sem rodar os testes
RUN ./gradlew clean build -x test

# Etapa 2 - Imagem final
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o JAR gerado pelo Gradle
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
