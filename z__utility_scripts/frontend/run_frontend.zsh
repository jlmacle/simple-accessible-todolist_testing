echo ""
echo "*********************************************************"
echo "Starting the front-end server"
echo "*********************************************************"
cd $ATL_FRONTEND_DIR
osascript -e 'tell application "Terminal" to do script "npx ng serve -o"'