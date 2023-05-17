.PHONY: build
build:
	mvn clean install

.PHONY: docker-build
docker-build:
	docker build -t race_application_command_service .

.PHONY: run
run:
	java -jar target/race_application_command_service-0.0.1-SNAPSHOT.jar

.PHONY: docker-run
docker-run:
	docker run -p 8080:8080 race_application_command_service

.PHONY: test
test:
	mvn test

.PHONY: docker-compose-up-command-service
docker-compose-up-command-service:
	docker-compose -f ../../build/docker/docker-compose.yml up race_application_command_service


.PHONY: docker-compose-down-command-service
docker-compose-down-command-service:
	docker-compose -f ../../build/docker/docker-compose.yml down race_application_command_service