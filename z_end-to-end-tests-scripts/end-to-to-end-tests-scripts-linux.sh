#!/bin/bash
cd scripts
echo "Starting the back-end server." 
./run_Backend_server-Linux.sh
sleep 5

echo ""
echo "Starting the front-end server." 
./run_Angular_server-Linux.sh
sleep 5

echo ""
echo "Starting the test suites" 
echo "Waiting 50s for the servers to be done starting, before starting the end-to-end tests. "
echo "The time value can be changed in the end-to-to-end-tests-scripts-linux.sh file located in the z_end-to-end-tests-scripts folder."
sleep 50

cd ../..
mvn test 

z_end-to-end-tests-scripts/scripts/stop_potentially_existing_Angular_Spring_server_processes-linux.sh 
