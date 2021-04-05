#!/bin/zsh
cd scripts
echo "** Starting the back-end server **"
./run_Backend_server-macOS.zsh &
sleep 5
echo "\n** Starting the front-end server **"
./run_Angular_server-macOS.zsh  &
sleep 5
echo "\n** Starting the test suites **"  
echo "Waiting 30s for the servers to be done starting, before starting the end-to-end tests. "
echo "The time value can be changed in the end-to-to-end-tests-scripts-macOS.zsh file located in the z_end-to-end-tests-scripts folder."
cd ../../../AccessibleTodoList_End2endTests

sleep 30 
mvn test
scripts/stop_potentially_existing_Angular_Spring_server_processes-macOS.zsh
