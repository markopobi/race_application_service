.PHONY: build
build:
	mvn clean install

.PHONY: docker-build
docker-build:
	docker build -t race_application_query_service .

.PHONY: run
run:
	java -jar target/race_application_query_service-0.0.1-SNAPSHOT.jar

.PHONY: docker-run
docker-run:
	docker run -p 8081:8081 race_application_query_servicee

.PHONY: test
test:
	mvn test

.PHONY: docker-compose-up-query-service
docker-compose-up-query-service:
	docker-compose -f ../../build/docker/docker-compose.yml up race_application_query_service


.PHONY: docker-compose-down-query-service
docker-compose-down-query-service:
	docker-compose -f ../../build/docker/docker-compose.yml down race_application_query_service
