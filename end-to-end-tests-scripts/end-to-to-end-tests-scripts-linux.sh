#!/bin/bash
pwd
cd ../../AccessibleTodoList_Backend && mvn spring-boot:run &> ../AccessibleTodoList_End2endTests/end-to-end-tests-scripts/log_SpringBoot-script.txt & #Re-directing both error and standard output to the file. 
cd ../../AccessibleTodoList_FrontEnd && ng serve -o &> ../AccessibleTodoList_End2endTests/end-to-end-tests-scripts/log_Angular-end-to-end-test-script.txt  & 
cd .. && sleep 120 && mvn test && kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//p' | sed 's/jl.*//p') && kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//p' | sed 's/jl.*//p')

#lsof
# -n inhibits the conversion of network numbers to host names for network files.  Inhibiting conversion may make lsof run faster.   It  is  also  useful
#  when host name lookup is not working properly.

# 
