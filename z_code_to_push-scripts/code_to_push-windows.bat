#Running ConfigurationUtility to have localhost by default
java -cp C:/_Synchronized_Code/ATL/AccessibleTodoList_End2endTests/target/test-classes;C:/Users/jeanl/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.30/jcl-over-slf4j-1.7.30.jar;C:/Users/jeanl/.m2/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar;C:/Users/jeanl/.m2/repository/org/slf4j/slf4j-api/1.7.13/slf4j-api-1.7.13.jar;C:/_Synchronized_Code/ATL/AccessibleTodoList_End2endTests/tmp/StringExternalization.java.txt  jl.project.ConfigurationUtility "../tmp"

# To add: code quality
# At the time of writing, issue with running Docker Desktop on Windows 
# Issue alredy reported


echo "** mvn clean **"
echo "The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub."
echo "The dump file was in the surefire-reports folder."
echo "mvn clean suppresses some data, including the surefire-reports."
cd ..
mvn clean

#To add: "** git add, commit and push **"

