echo ""
echo "*********************************************************"
echo "Starting the back-end server"
echo "*********************************************************"
cd $ATL_BACKEND_DIR
osascript -e 'tell application "Terminal" to do script "mvn spring-boot:run"'
