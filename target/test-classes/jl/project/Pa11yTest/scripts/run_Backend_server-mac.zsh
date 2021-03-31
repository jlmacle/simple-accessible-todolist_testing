#!/bin/zsh
kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//' | sed 's/jl.*//')
cd ../AccessibleTodoList_Backend && mvn spring-boot:run 
