@echo off
:: ** Running ConfigurationUtility to have localhost configuration by default ** 
echo ** Running ConfigurationUtility **
java -cp ../target/test-classes;../../../../Users/jeanl/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.30/jcl-over-slf4j-1.7.30.jar;../../../../Users/jeanl/.m2/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar;../../../../Users/jeanl/.m2/repository/org/slf4j/slf4j-api/1.7.13/slf4j-api-1.7.13.jar;../../../../_Synchronized_Code/ATL/AccessibleTodoList_End2endTests/tmp/StringExternalization.java.txt jl.project.ConfigurationUtility "../tmp" "\..\src\test\java\jl\project\" 

:: ** Running the code quality **
echo ------------------------------------------   
echo ** Starting SonarQube and running the code quality analysis **
:: Starting the SonarQube server
start "SonarQube" StartSonar.bat &

:: Running the analysis
timeout /T 60
cd ..
start mvn sonar:sonar -Dsonar.projectKey=End-to-endTesting:jl.project -Dsonar.host.url=http://localhost:9000 -Dsonar.login=%SONARQUBE_E2E% 
:: Starting a browser to check the result of the analysis
pause 40 "Waiting for the analysis to be done."
echo ------------------------------------------  
echo Starting a browser to check the result of the analysis.
start msedge http://localhost:9000
	
echo ------------------------------------------  
echo ** Running mvn clean ** 
echo The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub.
echo The dump file was in the surefire-reports folder. 
echo mvn clean suppresses some data, including the surefire-reports. 

start mvn clean
pause 30 "Waiting for the mvn clean to be done."

echo -------------------------------------------   
echo ** git ** 
echo git add .
git add .
echo git commit: enter a commit message
set /p commit=
git commit -m "%commit%"
echo You entered %commit%
pause 40 "Waiting before pushing the code."
echo git push
git push




