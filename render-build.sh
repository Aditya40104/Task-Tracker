#!/usr/bin/env bash
# exit on error
set -o errexit

./mvnw clean install -DskipTests
