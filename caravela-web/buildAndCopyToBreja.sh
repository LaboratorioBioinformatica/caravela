rm -rf /home/gianluca/git/caravela/caravela-web/target/caravela*
mvn package
rsync -avz --progress target/caravela.war gianluca@10.9.2.100:/home/gianluca/Downloads/caravela/lib/
ssh gianluca@10.9.2.100 /home/gianluca/Downloads/caravela/tomcat/bin/catalina.sh stop
ssh gianluca@10.9.2.100 rm -rf /home/gianluca/Downloads/caravela/tomcat/webapps/caravela
ssh gianluca@10.9.2.100 /home/gianluca/Downloads/caravela/tomcat/bin/catalina.sh run
