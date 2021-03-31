#!/bin/bash
IsAngularRunning=$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//' | sed 's/jean-louis.*//')
isSpringBootRunning=(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//' | sed 's/jean-louis.*//')

if [ -z $IsAngularRunning ] 
	then 
		echo "No Angular server process currently running on port 4200." 
	else
		kill $IsAngularRunning && echo "The Angular server process that was running on port 4200 has been suppressed." 
fi

if [ -z $isSpringBootRunning]
	then
		echo "No SpringBoot server process currently running on port 8080."
	else
		kill $isSpringBootRunning && echo "The SpringBoot server process that was running on port 8080  has been suppressed." 
fi


