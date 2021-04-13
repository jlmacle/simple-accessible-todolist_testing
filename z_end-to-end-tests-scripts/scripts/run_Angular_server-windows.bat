:: I had an issue while trying to rename the window where the angular server starts.
:: Workaround: to name a parent window, to start the angular server from that window, 
:: and to suppress the parent window with the option removing potential children processes as well. 

taskkill -T /F /FI "WINDOWTITLE eq angular_server_parent" 
start "angular_server_parent" run_Angular_server-windows_forAngularServerStartup.bat 


