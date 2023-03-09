echo ""
echo "*********************************************************"
echo "Starting the front-end server"
echo "*********************************************************"
cd $ATL_FRONTEND_DIR
x-terminal-emulator -e /bin/bash -c 'npx ng serve -o;sleep 600'