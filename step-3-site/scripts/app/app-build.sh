#!/bin/bash

if [ ! -d "source" ]; then
    echo "Cloning"
    git clone https://renatodinhani@bitbucket.org/renatodinhani/game-discovery.git source
fi

echo "Updating"
cd source
git reset --hard
git pull origin master

echo "Building"
cd core
mvn clean
mvn install -Dmaven.test.skip

echo "Copying"
cd ..
cd ..
cp -f source/core/target/gamediscovery-site-1.0-SNAPSHOT.war ./gamediscovery-site-1.0-SNAPSHOT.war