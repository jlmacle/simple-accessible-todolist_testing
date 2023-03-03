echo "Starting the back-end server" 
cd %ATL_BACKEND_DIR%
start mvn spring-boot:run 
timeout /T 60

echo "Starting the front-end server"  
cd %ATL_FRONTEND_DIR%
start npx ng serve -o 
timeout /T 120

echo "Starting the test suites"  
cd %ATL_E2E_DIR%
mvn test 
