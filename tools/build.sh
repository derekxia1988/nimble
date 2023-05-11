#!/bin/bash

mvn clean
mvn package -Dmaven.test.skip
mvn dependency:copy-dependencies