FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

RUN apt-get update && \
    apt-get install -y --no-install-recommends maven && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jdk AS runtime
WORKDIR /usr/local/tomcat

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl && \
    rm -rf /var/lib/apt/lists/*

ENV TOMCAT_VERSION=10.1.48
RUN curl -sSL -O https://dlcdn.apache.org/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    tar -xzf apache-tomcat-${TOMCAT_VERSION}.tar.gz --strip-components=1 && \
    rm apache-tomcat-${TOMCAT_VERSION}.tar.gz

RUN rm -rf webapps/*
COPY --from=build /app/target/*.war webapps/ROOT.war

EXPOSE 8080
CMD ["bin/catalina.sh", "run"]
