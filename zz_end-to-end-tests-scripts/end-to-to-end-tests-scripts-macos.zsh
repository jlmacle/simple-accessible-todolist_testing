# ConfigurationUtility class to run before starting this script
# Script asking if this was done
cd $ATL_E2E_DIR
cd z__utility_scripts/_configuration_utility
./run_configuration_utility.zsh

# Backend and frontend to start
cd $ATL_E2E_DIR
cd z__utility_scripts/_start_backend_and_frontend
./_start_backend_and_frontend.zsh


echo ""
echo "*********************************************************"
echo "Starting the test suites"
echo "*********************************************************"
cd $ATL_E2E_DIR
mvn test
