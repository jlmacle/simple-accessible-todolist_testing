#!/bin/zsh
isSpringBootRunning=$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//' | sed 's/jean-louis.*//' | sed 's/ *//' | sed 's/ //')

if [ -z $isSpringBootRunning]
	then
		echo "No SpringBoot server process currently running on port 8080."
	else
		kill $isSpringBootRunning && echo "The SpringBoot server process that was running on port 8080  has been suppressed." 
fi

cd ../AccessibleTodoList_Backend && mvn spring-boot:run 
