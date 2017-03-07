#!/bin/sh
CARAVELA_HOME=/home/gianluca/Downloads/caravela
CARAVELA_DEV=${PWD}

echo $CARAVELA_HOME;
echo $CARAVELA_DEV;
 
$CARAVELA_HOME/tomcat/bin/shutdown.sh
 
rm -rf $CARAVELA_DEV/target/caravela*
rm -f $CARAVELA_HOME/lib/caravela.war
mvn package 
mv $CARAVELA_DEV/target/caravela.war $CARAVELA_HOME/lib/
$CARAVELA_HOME/tomcat/bin/catalina.sh run

