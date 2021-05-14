taskkill -T /F /FI "WINDOWTITLE eq spring_server_parent*"
echo "Waiting for a potential SpringBoot server process to be suppressed."
timeout /T 5 
start "spring_server_parent" run_Backend_server-windows_forBackendServerStartup.bat