# ProveSkill

This project is a web system for conducting school exams. It allows creating classes, adding students, creating exams, scheduling exams for classes, and consequently, students can take exams through it. The project is for the Web Programming II course at the Federal Institute of Alagoas.

[Node 14.17.0] [Npm 9.6.4]

## Configuration

* In `back` folder run `docker-compose up -d mysql-service`, then run `mvn spring-boot:run`.
* In `front` folder run `ng serve`.

* localhost:4200

* [Prove Skill](http://proveskill.com)


## SonarQube

In `back` folder:

* Execute `docker-compose up -d mysql-service sonarqube`, then verify localhost:9000.
Sonar first login default is `user:admin`, `password:admin`. You'll need to change the password.

* Create a TOKEN in `User -> My Account -> Security -> Generate Tokens`. Copy the token and insert in pom.xml `SONAR-USER-TOKER`.

* Run `mvn clean install` to generate sources and run tests.
* Run `mvn clean verify sonar:sonar` to send data to SonarQube.

You can see de reports in localhost:9000.

## Old configuration (deprecated)
You should have Docker compose, Maven and JDK 17 installed on your machine.

* At first, you should create a docker network. Run `docker network create proveskillnetwork` on your CMD.
* On root folder, run `docker build -t mysql-proveskill -f ./docker-config/db/Dockerfile .` to build the database image.
* In sequel, on root folder, run `docker build -t springboot-proveskill -f ./docker-config/app/Dockerfile .` to build the application image.
* After all, run `docker run -p 3306:3306 --network proveskillnetwork --name mysql mysql-proveskill` and than `docker run -p 8080:8080 --network proveskillnetwork --name springboot --env-file .env springboot-proveskill`
* You can make login as ADMIN using the follow credentials: email: admin, password: admin

## API
* [Application url](http://localhost:2364/api/v1)
* [Mysql](http://localhost:3306)
