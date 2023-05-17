# ProveSkill
Projeto para a disciplina de PWEBII do Instituto Federal de Alagoas que visa a criação de um sistema web para avaliações.

## Configuration
Apenas execute `docker-compose up -d`.

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
