echo ""
echo "*********************************************************"
echo "Starting the back-end server"
echo "*********************************************************"
cd $ATL_BACKEND_DIR
# sleep 600 useful for debugging
x-terminal-emulator -e /bin/bash -c 'mvn spring-boot:run;sleep 600'
