# Etapa 1: Compilación (Build)
# Usamos una imagen de Maven con Java 17 para compilar el código
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos el pom.xml y descargamos las dependencias (optimiza la caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos el proyecto
COPY src ./src
# Usamos el perfil 'prod' que configuramos en tu pom.xml y saltamos los tests para acelerar el deploy
RUN mvn clean package -Pprod -DskipTests

# Etapa 2: Ejecución (Runtime)
# Usamos una imagen ligera de Java 17 para correr la app
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos solo el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Configuración de entorno para producción
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]