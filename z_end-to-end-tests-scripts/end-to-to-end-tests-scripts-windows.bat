:: To start from command console
:: Numerous windows :)

echo "Starting the back-end server" 
cd scripts 
start run_Backend_server-windows.bat 

echo "Starting the front-end server"  
start run_Angular_server-windows.bat 
echo "Starting the test suites"  
cd ../../
timeout /T 120 
mvn test 
stop_potentially_existing_Angular_Spring_server_processes-windows.bat


