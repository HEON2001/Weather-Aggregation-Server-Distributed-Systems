# DS-Assignment2
To first compile all the files use the makefile command: 'make compile'

Then run the server using the command: 'make server' to run the Aggregation server

Once the server is running, open another terminal instance and type in the command: 'make content' to run the content server and send the PUT request

After the weather.txt file has been created, type the command: 'make client' to run the GET client and start typing in the requests desired

To run test cases type in the command 'make test' and wait until it has finished outputting, once it is finished compare output to the ExpectedTestOutputs.txt file to make sure all outputs are as desired

Once everything has been run and you are finished with the program, use command: 'make clean' to remove all class files.