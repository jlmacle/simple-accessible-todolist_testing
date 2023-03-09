:: Backend to start
cd %ATL_E2E_DIR%
call z__utility_scripts/backend/run_backend.bat
timeout /T 60

:: Frontend to start
cd %ATL_E2E_DIR%
call z__utility_scripts/frontend/run_frontend.bat
timeout /T 120