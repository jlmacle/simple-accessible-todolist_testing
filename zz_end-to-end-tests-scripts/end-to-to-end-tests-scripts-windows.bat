@echo off

:: ConfigurationUtility class to run before starting this script
:: Script asking if this was done
cd %ATL_E2E_DIR%
call z__utility_scripts/_configuration_utility/run_configuration_utility.bat

:: Backend and frontend to start
cd %ATL_E2E_DIR%
call z__utility_scripts/_start_backend_and_frontend/_start_backend_and_frontend.bat

:: Test suites to start
echo.
echo *********************************************************
echo Starting the test suites  
echo *********************************************************
cd %ATL_E2E_DIR%
mvn test 
