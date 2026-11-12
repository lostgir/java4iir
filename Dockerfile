# Image de build : fige le JDK et Maven pour que tout le monde compile/teste
# avec exactement les memes versions, peu importe ce qui est installe en local.
FROM maven:3.9-eclipse-temurin-25

WORKDIR /app

# Copie uniquement le pom.xml d'abord pour profiter du cache Docker sur les dependances.
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src

CMD ["mvn", "-B", "clean", "package"]
