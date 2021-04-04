#!/bin/zsh
echo "Starting the back-end server" &&\
cd ../../AccessibleTodoList_Backend && mvn spring-boot:run &> ../AccessibleTodoList_End2endTests/z_end-to-end-tests-scripts/logs/log_SpringBoot-script.txt & #Re-directing both error and standard output to the file. 

echo "Starting the front-end server"  &&\
cd ../AccessibleTodoList_FrontEnd && ng serve &> ../AccessibleTodoList_End2endTests/z_end-to-end-tests-scripts/logs/log_Angular-end-to-end-test-script.txt  & 

echo "Starting the test suites"  &&\
cd ../AccessibleTodoList_End2endTests && sleep 120 && mvn test &&  ./src/test/java/jl/project/Pa11yTest/scripts/stop_potentially_existing_Angular_Spring_server_processes-macOS.zsh
