FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve dependency:resolve-plugins
COPY src ./src
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 9081
CMD ["mvn", "spring-boot:run"]
