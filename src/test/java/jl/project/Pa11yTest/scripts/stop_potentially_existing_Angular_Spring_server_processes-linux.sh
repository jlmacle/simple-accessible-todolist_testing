#!/bin/bash
IsAngularRunning=$(lsof -nP -iTCP -sTCP:LISTEN | grep 4200 | sed 's/node//' | sed 's/jean-louis.*//')
isSpringBootRunning=$(lsof -nP -iTCP -sTCP:LISTEN | grep 8080 | sed 's/java//' | sed 's/jean-louis.*//')

if [ -z $IsAngularRunning ] 
	then 
		echo "No Angular server process currently running on port 4200." 
	else
		kill $IsAngularRunning && echo "The Angular server process that was running on port 4200 has been suppressed." 
fi

if [ -z $isSpringBootRunning ]
	then
		echo "No SpringBoot server process currently running on port 8080."
	else
		kill $isSpringBootRunning && echo "The SpringBoot server process that was running on port 8080  has been suppressed." 
fi


# lsof
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

