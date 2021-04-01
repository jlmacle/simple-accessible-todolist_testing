#!/bin/bash

kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//' | sed 's/jl.*//')
cd ../AccessibleTodoList_FrontEnd && ng serve  