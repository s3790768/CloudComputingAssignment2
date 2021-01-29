#!/bin/bash

gcloud config set project cloudcomputinga2
gcloud app deploy target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar -q