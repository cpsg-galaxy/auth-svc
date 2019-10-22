#!/bin/bash

rm -rf "$PWD":/home/gradle/project/build
docker run --rm -v "$PWD":/home/gradle/project -w /home/gradle/project gradle gradle clean bootJar -x test -x jacocoTestCoverageVerification