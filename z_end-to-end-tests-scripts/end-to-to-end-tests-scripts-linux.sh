echo ""
echo "*********************************************************"
echo "Starting the back-end server"
echo "*********************************************************"
cd $ATL_BACKEND_DIR
# sleep 600 useful for debugging
x-terminal-emulator -e /bin/bash -c 'mvn spring-boot:run;sleep 600'
read -p "Press the [Enter] key to continue..."

echo ""
echo "*********************************************************"
echo "Starting the front-end server"
echo "*********************************************************"
cd $ATL_FRONTEND_DIR
x-terminal-emulator -e /bin/bash -c 'npx ng serve -o;sleep 600'
read -p "Press the [Enter] key to continue..."

echo ""
echo "*********************************************************"
echo "Starting the test suites"
echo "*********************************************************"
cd $ATL_E2E_DIR
mvn test

# Credits :
# https://stackoverflow.com/questions/43025289/windows-bash-equivalent-of-start-in-bash#:~:text=xdg%2Dopen%20opens%20a%20file,ftp%2C%20http%20and%20https%20URLs.
# https://unix.stackexchange.com/questions/373377/start-xterm-with-different-shell-and-execute-commands
