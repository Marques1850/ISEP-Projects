#!/usr/bin/env bash
ECHO OFF
ECHO make sure JAVA_HOME is set to JDK folder
ECHO make sure maven is on the system PATH
../apache-maven-3.9.2/bin/mvn $1 dependency:copy-dependencies package -DskipTests=true -D maven.javadoc.skip=true
