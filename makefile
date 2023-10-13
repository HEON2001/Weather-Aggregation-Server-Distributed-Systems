
# Compile java files, start registry then run server
compile:
	javac -d . -classpath jackson-databind-2.15.2.jar:jackson-core-2.15.2.jar:jackson-annotations-2.15.2.jar: ContentServer.java AggregationServer.java Packet.java parser.java GETClient.java
# Run AggregationServer
server:
	java -cp jackson-core-2.15.2.jar:jackson-databind-2.15.2.jar:jackson-annotations-2.15.2.jar: aggserver.AggregationServer
# Run ContentServer
content:
	java -cp jackson-core-2.15.2.jar:jackson-databind-2.15.2.jar:jackson-annotations-2.15.2.jar: aggserver.ContentServer 4567 test_file01.txt
# Run GETClient
client:
	java -cp jackson-core-2.15.2.jar:jackson-databind-2.15.2.jar:jackson-annotations-2.15.2.jar: aggserver.GETClient
# Clean compiled files
clean:
	rm -rf aggserver/*.class
	rmdir aggserver