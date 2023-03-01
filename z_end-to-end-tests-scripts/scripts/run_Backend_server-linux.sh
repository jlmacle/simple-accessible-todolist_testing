#!/bin/bash
isSpringBootRunning=$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//' | sed 's/jl.*//' | sed 's/ *//' | sed 's/ //')

if [ -z $isSpringBootRunning]
	then
		echo "No SpringBoot server process currently running on port 8080."
	else
		kill $isSpringBootRunning && echo "The SpringBoot server process that was running on port 8080  has been suppressed." 
fi

echo "Starting a fresh instance of the server."
cd ../../../simple-accessible-todolist_backend 
mvn spring-boot:run &> ../AccessibleTodoList_End2endTests/z_end-to-end-tests-scripts/logs/log_SpringBoot-end-to-end-test-script.txt &

