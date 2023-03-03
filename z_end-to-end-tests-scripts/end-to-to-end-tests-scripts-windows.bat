@echo off

echo.
echo *********************************************************
echo Starting the back-end server
echo *********************************************************
cd %ATL_BACKEND_DIR%
start mvn spring-boot:run 
timeout /T 60

echo.
echo *********************************************************
echo Starting the front-end server  
echo *********************************************************
cd %ATL_FRONTEND_DIR%
start npx ng serve -o 
timeout /T 120

echo.
echo *********************************************************
echo Starting the test suites  
echo *********************************************************
cd %ATL_E2E_DIR%
mvn test 
