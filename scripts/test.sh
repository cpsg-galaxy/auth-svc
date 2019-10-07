#!/bin/bash

docker run --link="mongo-test" --rm -v "$PWD":/home/gradle/project -w /home/gradle/project gradle gradle clean jacocoTestReport check || true