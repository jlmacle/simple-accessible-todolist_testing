#!/bin/bash
kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//p' | sed 's/jl.*//p')
cd ../AccessibleTodoList_Backend && mvn spring-boot:run
