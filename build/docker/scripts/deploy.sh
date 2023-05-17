cd ../../..

# Build all the modules
mvn clean package

# Enter docker-compose folder
# shellcheck disable=SC2164
cd build/docker/

# Deploy
docker-compose up --build -d