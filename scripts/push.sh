#!/bin/bash

docker login -u $DOCKER_USER -p $DOCKER_PSWRD $DOCKER_REGISTRY
docker build --build-arg JAR_FILE=build/libs/auth-svc.jar -t auth-svc:$BUILD_NUMBER.$BUILD_ID .
docker push $DOCKER_REGISTRY/auth-svc:$BUILD_NUMBER.$BUILD_ID