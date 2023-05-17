.PHONY: build race_application_command_service race_application_query_service start stop test

.PHONY: build project
build:
	mvn clean install -f race_application_service/pom.xml

.PHONY: docker-build
race_application_command_service:
	docker build -t race_application_command_service:latest -f race_application_service/race_application_command_service/src/main/docker/Dockerfile .

.PHONY: docker-build
race_application_query_service:
	docker build -t race_application_query_service:latest -f race_application_service/race_application_query_service/src/main/docker/Dockerfile .

.PHONY: docker-compose-up
start:
	docker-compose -f race_application_service/build/docker/docker-compose.yml up -d

.PHONY: docker-compose-down
stop:
	docker-compose -f race_application_service/build/docker/docker-compose.yml down

.PHONY: run tests
test:
	mvn test -f race_application_service/race_application_command_service/pom.xml
	mvn test -f race_application_service/race_application_query_service/pom.xml