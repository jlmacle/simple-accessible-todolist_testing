#!/bin/bash
pwd
echo "Starting the back-end server." && cd ../../AccessibleTodoList_Backend && mvn spring-boot:run &> ../AccessibleTodoList_End2endTests/z_end-to-end-tests-scripts/logs/log_SpringBoot-script.txt & #Re-directing both error and standard output to the file. 
echo "Starting the front-end server." && cd ../../AccessibleTodoList_FrontEnd && ng serve -o &> ../AccessibleTodoList_End2endTests/z_end-to-end-tests-scripts/logs/log_Angular-end-to-end-test-script.txt  & 
echo "Starting the Test Suites" && cd .. && sleep 120 && mvn test && ./src/test/java/jl/project/Pa11yTest/scripts/stop_potentially_existing_Angular_Spring_server_processes-linux.sh 

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



