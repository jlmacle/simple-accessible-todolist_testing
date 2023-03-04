$ATL_BACKEND_DIRecho ""
echo "*********************************************************"
echo "Starting the back-end server"
echo "*********************************************************"
cd $ATL_BACKEND_DIR
cd /scripts
./run_Spring.sh
read -p "Press the [Enter] key to continue..."

echo ""
echo "*********************************************************"
echo "Starting the front-end server"
echo "*********************************************************"
cd $ATL_FRONTEND_DIR
xdg-open  "npx ng serve -o"
read -p "Press the [Enter] key to continue..."

echo ""
echo "*********************************************************"
echo "Starting the test suites"
echo "*********************************************************"
cd $ATL_E2E_DIR
mvn test

# Credits :
# https://stackoverflow.com/questions/43025289/windows-bash-equivalent-of-start-in-bash#:~:text=xdg%2Dopen%20opens%20a%20file,ftp%2C%20http%20and%20https%20URLs.
