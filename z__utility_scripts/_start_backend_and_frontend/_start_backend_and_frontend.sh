# Backend to start
cd $ATL_E2E_DIR
# TODO: possible to run script with path?
cd z__utility_scripts/backend/
./run_backend.sh
read -p "Press the [Enter] key to continue..."

# Frontend to start
cd $ATL_E2E_DIR
# TODO: code to add
cd z__utility_scripts/frontend/
./run_frontend.sh
read -p "Press the [Enter] key to continue..."
