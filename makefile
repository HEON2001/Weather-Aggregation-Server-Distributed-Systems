javac -d . -classpath jackson-databind-2.15.2.jar:jackson-core-2.15.2.jar:jackson-annotations-2.15.2.jar: ContentServer.java AggregationServer.java Packet.java parser.java

java -cp jackson-core-2.15.2.jar:jackson-databind-2.15.2.jar:jackson-annotations-2.15.2.jar: aggserver.ContentServer

java -cp jackson-core-2.15.2.jar:jackson-databind-2.15.2.jar:jackson-annotations-2.15.2.jar: aggserver.AggregationServer