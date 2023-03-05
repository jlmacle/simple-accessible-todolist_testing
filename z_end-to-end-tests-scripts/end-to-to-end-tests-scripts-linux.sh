# In case of need
#  sudo apt install gnome-terminal


echo ""
echo "*********************************************************"
echo "Starting the back-end server"
echo "*********************************************************"
cd $ATL_E2E_DIR/z_end-to-end-tests-scripts/scripts
# sleep 600 useful for debugging
gnome-terminal -- sh -c './run_Spring.sh;sleep 600'
read -p "Press the [Enter] key to continue..."

echo ""
echo "*********************************************************"
echo "Starting the front-end server"
echo "*********************************************************"
cd $ATL_E2E_DIR/z_end-to-end-tests-scripts/scripts
gnome-terminal -- sh -c './run_Angular.sh;sleep 600'
read -p "Press the [Enter] key to continue..."

echo ""
echo "*********************************************************"
echo "Starting the test suites"
echo "*********************************************************"
cd $ATL_E2E_DIR
mvn test

# Credits :
# https://stackoverflow.com/questions/43025289/windows-bash-equivalent-of-start-in-bash#:~:text=xdg%2Dopen%20opens%20a%20file,ftp%2C%20http%20and%20https%20URLs.
