taskkill -T /F /FI "WINDOWTITLE eq spring_server_parent"
cd src/test/java/jl/project/pa11ytest/scripts
start "spring_server_parent" run_Backend_server-windows_forBackendServerStartup.bat