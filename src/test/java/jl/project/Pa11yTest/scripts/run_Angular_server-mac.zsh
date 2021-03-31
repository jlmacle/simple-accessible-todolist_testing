#!/bin/zsh
kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//' | sed 's/jean-louis.*//')
cd ../AccessibleTodoList_FrontEnd && ng serve -o 