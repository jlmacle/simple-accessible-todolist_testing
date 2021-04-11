:: ** Running ConfigurationUtility to have localhost configuration by default ** 
echo "** Running ConfigurationUtility **"
:: java -cp C:/_Synchronized_Code/ATL/AccessibleTodoList_End2endTests/target/test-classes;C:/Users/jeanl/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.30/jcl-over-slf4j-1.7.30.jar;C:/Users/jeanl/.m2/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar;C:/Users/jeanl/.m2/repository/org/slf4j/slf4j-api/1.7.13/slf4j-api-1.7.13.jar;C:/_Synchronized_Code/ATL/AccessibleTodoList_End2endTests/tmp/StringExternalization.java.txt jl.project.ConfigurationUtility "../tmp" "\..\src\test\java\jl\project\" 

:: ** Running the code quality **
echo "** Starting SonarQube and running the code quality analysis **"
	:: Starting the SonarQube server
StartSonar.bat 1> sonarqube_log.txt 2>&1
	:: Running the analysis
cd ..
timeout /T 200
mvn sonar:sonar -Dsonar.projectKey=End-to-endTesting:jl.project -Dsonar.host.url=http://localhost:9000 -Dsonar.login=%SONARQUBE_E2E% 1> sonarqube_analysis_log.txt 2>&1
	:: Starting a browser to check the result of the analysis
	start msedge http://localhost:9000 
	

:: 
:: echo "** Running mvn clean **" 
:: echo "The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub. \
The dump file was in the surefire-reports folder. \
mvn clean suppresses some data, including the surefire-reports." 
:: 
::  cd ..
::  mvn clean

:: To add: "** git add, commit and push **"

