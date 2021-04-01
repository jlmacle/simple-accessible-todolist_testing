rem cmd.exe /c "taskkill /FI "WINDOWTITLE eq angular*" && cd ../../../../../../../../AccessibleTodoList_FrontEnd && title "angular_server" && ng serve -o  & "
rem taskkill /FI "WINDOWTITLE eq angular*" && start "angular_server" ng serve -o &> tempLog.txt & title "angular_server"

rem I had an issue while trying to rename the window where the angular server starts.
rem Idea of work around: to name a parent window, to start the angular server from that window, 
rem and to suppress parent window with the option removing potential children processes as well. 

cmd.exe /c  "taskkill -T /F /FI "WINDOWTITLE eq angular_server_parent" && start "angular_server_parent" start run_Angular_server-Windows_forAngularServerStartup.bat &"

rem run_Angular_server-Windows_forAngularServerStartup.bat

