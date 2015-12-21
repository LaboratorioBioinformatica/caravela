/opt/tomcat/bin/shutdown.sh 
rm -rf /home/gianluca/git/caravela/caravela-web/target/caravela*
rm -rf /opt/tomcat/webapps/caravela*
mvn package 
mv /home/gianluca/git/caravela/caravela-web/target/caravela.war /opt/tomcat/webapps/
/opt/tomcat/bin/startup.sh

