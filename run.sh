#!/bin/sh

# Exit if anything fails
set -e

echo "Packaging application... "
./mvnw package

# Unpack for for increased performance by taking advantage of docker layers
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

if ! command -v docker >/dev/null 2>&1
then
    echo "docker command not found, please install docker"
    exit
fi

echo "Building docker image... "
docker build -t dublin-analytica .

echo "Running docker image... "
docker run --rm -p 8080:8080 dublin-analytica
