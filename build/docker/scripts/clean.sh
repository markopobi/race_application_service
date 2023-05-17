echo "Stopping all containers"
docker-compose stop

echo "Removing all containers"
# shellcheck disable=SC2046
docker rm $(docker ps -a -q)