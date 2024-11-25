# Utiliza uma imagem base do OpenJDK 17 (Java 17)
FROM openjdk:17-jdk-slim

# Define o argumento JAR_FILE com valor padrão de ./target/*.jar
ARG JAR_FILE=./target/*.jar

# Copia o arquivo JAR para o container
COPY ${JAR_FILE} app.jar

# Garante que o arquivo app.jar esteja presente
RUN bash -c 'touch /app.jar'

# Exponha a porta padrão da aplicação Spring Boot
EXPOSE 8080

# Comando para executar o JAR
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

