/usr/local/tomcat/bin/catalina.sh stop 
rm -rf /Users/gianlucamajor/git/caravela/caravela-web/target/caravela*
rm -rf /usr/local/tomcat/webapps/caravela*
mvn package 
mv /Users/gianlucamajor/git/caravela/caravela-web/target/caravela.war /usr/local/tomcat/webapps/
/usr/local/tomcat/bin/catalina.sh run

