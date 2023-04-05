FROM maven:3.9-amazoncorretto-17

ENV APP_HOME /proveskill-app
WORKDIR $APP_HOME

COPY . .
RUN mvn clean install

EXPOSE 8080

CMD ["java", "-jar", "target/pweb-project-0.0.1-SNAPSHOT.jar"]
