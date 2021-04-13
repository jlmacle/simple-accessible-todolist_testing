taskkill -T /F /FI "WINDOWTITLE eq spring_server_parent*"
echo "Waiting for a potential SpringBoot server process to be suppressed."
timeout /T 5 
cd src/test/java/jl/project/pa11ytest/scripts
start "spring_server_parent" run_Backend_server-windows_forBackendServerStartup.bat