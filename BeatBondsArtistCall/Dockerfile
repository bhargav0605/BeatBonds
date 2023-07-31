FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

ENV DB_DRIVER_CLASS="com.mysql.cj.jdbc.Driver" \
    DB_FORMAT_SQL=false \
    DB_PASSWORD="blaster@25" \
    DB_SHOW_SQL=false \
    DB_URL=jdbc:"mysql://localhost:3306/identity_reconciliation" \
    DB_USERNAME="blaster" \
    LOG_LEVEL="info"
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]