#!/bin/zsh
kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//' | sed 's/jean-louis.*//')
kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//' | sed 's/jean-louis.*//')