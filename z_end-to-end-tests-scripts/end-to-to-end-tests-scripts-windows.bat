:: To start from command console
:: Numerous windows :)
cmd.exe /c "echo "Starting the back-end server" && cd ../src/test/java/jl/project/Pa11yTest/scripts && start run_Backend_server-windows.bat && echo "Starting the front-end server"  && start run_Angular_server-windows.bat  &&  echo "Starting the test suites"  && cd ../../../../../../../  && timeout /T 120 && mvn test && cd && cd src/test/java/jl/project/Pa11yTest/scripts/ && stop_potentially_existing_Angular_Spring_server_processes-windows.bat"


