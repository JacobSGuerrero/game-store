#!/usr/bin/env bash

set -e

# Download JDBC driver
if [ ! -e mysql-connector.jar ]; then
  wget -O mysql-connector.tar.gz http://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.38.tar.gz
  tar xvzf mysql-connector.tar.gz > /dev/null
  mv mysql-connector-java-5.1.38/mysql-connector-java-5.1.38-bin.jar ./mysql-connector.jar
fi

# Compile app
javac gameStore.java

# Run
java -cp '.:mysql-connector.jar'  gameStore