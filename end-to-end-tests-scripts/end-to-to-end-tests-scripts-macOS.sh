#!/bin/zsh
echo "Starting the backend server" && pwd &&\
cd ../../AccessibleTodoList_Backend && mvn spring-boot:run &> ../AccessibleTodoList_End2endTests/end-to-end-tests-scripts/log_SpringBoot-script.txt & #Re-directing both error and standard output to the file. 

echo "Starting the front-end server" && pwd &&\
cd ../AccessibleTodoList_FrontEnd && ng serve -o &> ../AccessibleTodoList_End2endTests/end-to-end-tests-scripts/log_Angular-end-to-end-test-script.txt  & 

echo "Starting the test suites" && pwd &&\
cd ../AccessibleTodoList_End2endTests && sleep 120 && mvn test && kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//p' | sed 's/jean-louis.*//p') && kill$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//p' | sed 's/jean-louis.*//p')

#lsof
# -n inhibits the conversion of network numbers to host names for network files.  
#  Inhibiting conversion may make lsof run faster.   
#  It  is  also  useful when host name lookup is not working properly.

# -P inhibits the conversion of port numbers to port names for network files.  
#  Inhibiting the conversion may  make  lsof  run  a little faster.  
#  It is also useful when port name lookup is not working properly.

# -i selects the listing of files any of whose Internet address
#  matches the address specified in i.

# -s When  the optional form is available, the s may be followed by a protocol name (p), either TCP or UDP, 
#  a colon  (`:')  and  a comma-separated  protocol  state  name list, 
#  the option causes open TCP and UDP files 
#  to be excluded if their  state  name(s) are  in  the  list (s) preceded by a `^'; 
#  or included if their name(s) are not preceded by a `^'.

# https://maven.apache.org/install.html

