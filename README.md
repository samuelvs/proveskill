# ProveSkill
Projeto para a disciplina de PWEBII do Instituto Federal de Alagoas que visa a criação de um sistema web para avaliações.

## SonarQube

* Execute `docker-compose up -d mysql-service sonarqube`, then verify localhost:9000.
First login default is `user:admin`, `password:admin`. You'll need change password.

* Create a TOKEN in `User -> My Account -> Security -> Generate Tokens`. Copy the token and insert in pom.xml `SONAR-USER-TOKER`.

* Run `mvn clean install` to generate sources and run tests.
* Run `mvn clean verify sonar:sonar` to send data to SonarQube.

You can see de reports in localhost:9000.

## Configuration
Just `docker-compose up -d`.

* [Prove Skill](http://proveskill.com)


## Configuration
You should have Docker compose, Maven and JDK 17 installed on your machine.

* At first, you should create a docker network. Run `docker network create proveskillnetwork` on your CMD.
* On root folder, run `docker build -t mysql-proveskill -f ./docker-config/db/Dockerfile .` to build the database image.
* In sequel, on root folder, run `docker build -t springboot-proveskill -f ./docker-config/app/Dockerfile .` to build the application image.
* After all, run `docker run -p 3306:3306 --network proveskillnetwork --name mysql mysql-proveskill` and than `docker run -p 8080:8080 --network proveskillnetwork --name springboot --env-file .env springboot-proveskill`
* You can make login as ADMIN using the follow credentials: email: admin, password: admin

## API
* [Application url](http://localhost:2364/api/v1)
* [Mysql](http://localhost:3306)
