#To add: localhost/no grid used by default

#To add: code quality
sudo docker start sonarqube


echo "** mvn clean **"
echo "The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub."
echo "The dump file was in the surefire-reports folder."
echo "mvn clean suppresses some data, including the surefire-reports."
cd ..
mvn clean

#To add: "** git add, commit and push **"

