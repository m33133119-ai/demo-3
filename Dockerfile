# 第一階段：使用 Maven 建置專案
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 第二階段：使用輕量的 Java 環境執行程式
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY external-images ./external-images
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]