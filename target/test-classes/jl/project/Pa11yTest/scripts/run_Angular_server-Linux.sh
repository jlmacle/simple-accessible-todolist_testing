#!/bin/bash
kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//p' | sed 's/jl.*//p')
cd ../AccessibleTodoList_FrontEnd && ng serve -o 