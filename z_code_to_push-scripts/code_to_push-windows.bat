@echo off
::-----------------------------------------------------------------------------------------------------------------------------
::  Making sure to have the default network configuration
::-----------------------------------------------------------------------------------------------------------------------------
:: ** Running ConfigurationUtility to have localhost configuration by default ** 
echo ** Running ConfigurationUtility **
java -cp ../target/test-classes;../../../../Users/jeanl/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.30/jcl-over-slf4j-1.7.30.jar;../../../../Users/jeanl/.m2/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar;../../../../Users/jeanl/.m2/repository/org/slf4j/slf4j-api/1.7.13/slf4j-api-1.7.13.jar;../../../../_Synchronized_Code/ATL/AccessibleTodoList_End2endTests/tmp/StringExternalization.java.txt jl.project.ConfigurationUtility "../tmp" "\..\src\test\java\jl\project\" 

::-----------------------------------------------------------------------------------------------------------------------------
::  Testing the code quality
::-----------------------------------------------------------------------------------------------------------------------------

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

::-----------------------------------------------------------------------------------------------------------------------------
::  Reducing the risk of credential leak.
::-----------------------------------------------------------------------------------------------------------------------------
	
echo ------------------------------------------  
echo ** Running mvn clean ** 
echo The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub.
echo The dump file was in the surefire-reports folder. 
echo mvn clean suppresses some data, including the surefire-reports. 

start mvn clean
pause 10 "Waiting for the mvn clean to be done."

::-----------------------------------------------------------------------------------------------------------------------------
::#  Pushing the code to Git
::-----------------------------------------------------------------------------------------------------------------------------
set commit_prefix=Android tests: worked on UserRequirement
set quotation_mark="
echo git add .
git add .
echo git commit_end: enter the message to append to: %quotation_mark% %commit_prefix% %quotation_mark% 
set /p commit_end=
git commit -m %quotation_mark% %commit_prefix% %commit_end% %quotation_mark%
echo "git push"
git push
echo Commited and pushed  %quotation_mark%  %commit_prefix% %commit_end%  %quotation_mark%




