#!/bin/bash

docker build --build-arg JAR_FILE=build/libs/auth-svc.jar -t $DOCKER_REGISTRY/auth-svc:$BUILD_TAG .
echo $DOCKER_PSWRD | docker login --username $DOCKER_USER --password-stdin $DOCKER_REGISTRY
docker push $DOCKER_REGISTRY/auth-svc:$BUILD_TAG