#!/bin/bash
export GD_ENVIRONMENT=production
export NEO4J_PATH=./database

java -server -XX:+UseCompressedOops -Xmx768M -jar ./jetty-runner.jar ./gamediscovery-site-1.0-SNAPSHOT.war &>/dev/null