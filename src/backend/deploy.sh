#!/bin/bash

mvn package
gcloud app deploy target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar -q