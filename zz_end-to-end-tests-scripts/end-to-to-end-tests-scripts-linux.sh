# ConfigurationUtility class to run before starting this script
# Script asking if this was done
cd $ATL_E2E_DIR
cd z__utility_scripts/_configuration_utility
./run_configuration_utility.sh

# Backend and frontend to start
cd $ATL_E2E_DIR
cd z__utility_scripts/_start_backend_and_frontend
./_start_backend_and_frontend.sh


echo ""
echo "*********************************************************"
echo "Starting the test suites"
echo "*********************************************************"
cd $ATL_E2E_DIR
mvn test

# Credits :
# https://stackoverflow.com/questions/43025289/windows-bash-equivalent-of-start-in-bash#:~:text=xdg%2Dopen%20opens%20a%20file,ftp%2C%20http%20and%20https%20URLs.
# https://unix.stackexchange.com/questions/373377/start-xterm-with-different-shell-and-execute-commands
