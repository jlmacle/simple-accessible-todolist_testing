taskkill -T /F /FI "WINDOWTITLE eq angular_server_parent" 
taskkill -T /F /FI "WINDOWTITLE eq spring_server_parent"

:: another potential option that could be explored.
:: wmic process where "commandline like '%chromedriver.exe%--port=8000%'" delete

