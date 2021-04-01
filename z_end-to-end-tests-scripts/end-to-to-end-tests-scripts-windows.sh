cmd.exe /c^
echo "Starting the backend server" &&^
cd ../../AccessibleTodoList_Backend && start "spring_server" mvn spring-boot:run &> ../AccessibleTodoList_End2endTests/z_end-to-end-tests-scripts/logs/log_SpringBoot-script.txt & #Re-directing both error and standard output to the file. 

#echo "Starting the front-end server"  &&\
#cd ../AccessibleTodoList_FrontEnd && ng serve -o &> ../AccessibleTodoList_End2endTests/z_end-to-end-tests-scripts/logs/log_Angular-end-to-end-test-script.txt  & 

#echo "Starting the test suites"  &&\
#cd ../AccessibleTodoList_End2endTests && sleep 120 && mvn test &&  ./z_end-to-end-tests-scripts/stop_existing_Angular_Spring_server_processes-macOS.zsh

./z_end-to-end-tests-scripts/stop_existing_Angular_Spring_server_processes-windows.bat
