#!/bin/bash

docker build --build-arg JAR_FILE=build/libs/auth-svc.jar -t $DOCKER_REGISTRY/auth-svc:$BUILD_ID .
cat $GOOGLE_KEYFILE | docker login -u _json_key --password-stdin https://$DOCKER_REGISTRY
docker push $DOCKER_REGISTRY/auth-svc:$BUILD_ID