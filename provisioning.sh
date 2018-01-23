#!/usr/bin/env bash

echo ""
echo "========================================================================="
echo "Configuring apt-get additional repositories"
echo "========================================================================="
# java
sudo add-apt-repository ppa:webupd8team/java

# neo4j
wget -O - https://debian.neo4j.org/neotechnology.gpg.key | sudo apt-key add -
echo 'deb http://debian.neo4j.org/repo stable/' | sudo tee -a /etc/apt/sources.list.d/neo4j.list

# postgresql
wget -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -

echo ""
echo "========================================================================="
echo "Updating apt-get"
echo "========================================================================="
sudo apt-get update

echo ""
echo "========================================================================="
echo "Installing tools"
echo "========================================================================="
sudo apt-get --assume-yes install mcedit
sudo apt-get --assume-yes install htop
sudo apt-get --assume-yes install tree

echo ""
echo "========================================================================="
echo "Installing Java"
echo "========================================================================="
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get --assume-yes install oracle-java8-installer

echo ""
echo "========================================================================="
echo "Installing Maven"
echo "========================================================================="
sudo apt-get --assume-yes install maven
mkdir ~/.m2

echo ""
echo "========================================================================="
echo "Installing Neo4J"
echo "========================================================================="
sudo apt-get --assume-yes install neo4j

echo ""
echo "========================================================================="
echo "Installing PostgreSQL"
echo "========================================================================="
sudo apt-get --assume-yes install postgresql-9.6

echo ""
echo "========================================================================="
echo "Installing JRuby"
echo "========================================================================="
sudo apt-get --assume-yes install jruby

echo ""
echo "========================================================================="
echo "Installing Gems and JARs"
echo "========================================================================="
sudo jgem install bundler

cd workspace
bundle install
jbundle install
