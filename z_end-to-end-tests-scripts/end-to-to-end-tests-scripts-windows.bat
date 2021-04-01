rem To start from command console
rem Numerous windows :)
cmd.exe /c "echo "Starting the front-end server"  && cd ../src/test/java/jl/project/Pa11yTest/scripts && start run_Angular_server-windows.bat  && echo "Starting the front-end server"  && start run_Backend_server-windows.bat && echo "Starting the test suites"  && cd && cd ../../../../../../../ && cd && timeout /T 120 && mvn test &&  ./src/test/java/jl/project/Pa11yTest/scripts/stop_potentially_existing_Angular_Spring_server_processes-windows.bat"


