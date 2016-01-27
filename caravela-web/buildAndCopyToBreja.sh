rm -rf /Users/gianlucamajor/git/caravela/caravela-web/target/caravela*
mvn package
rsync -avz --progress target/caravela.war gianluca@10.9.2.100:/tmp/
ssh gianluca@10.9.2.100 /home/gianluca/git/caravela/caravela-web/runAppFromTMP.sh
