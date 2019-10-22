#!/bin/bash

docker build --build-arg JAR_FILE=build/libs/auth-svc.jar -t auth-svc:$BUILD_NUMBER.$BUILD_ID .
echo $DOCKER_PSWRD | docker login --username $DOCKER_USER --password-stdin $DOCKER_REGISTRY
docker push $DOCKER_REGISTRY/auth-svc:$BUILD_NUMBER.$BUILD_ID