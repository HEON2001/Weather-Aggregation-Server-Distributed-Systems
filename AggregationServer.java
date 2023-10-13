package aggserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Timer;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;


public class AggregationServer {

    private ServerSocket serverSocket;

    //timer variables
    static long startTime = System.currentTimeMillis();
    static long elapsedTime = 0L;
    static File f = new File("weather.txt");

    public AggregationServer(ServerSocket serverSocket) {
        //Pass in the server socket
        this.serverSocket = serverSocket;
    }

    public void startServer() throws Exception{

        try{
            //While the server socket is open, accept connections
            while(!serverSocket.isClosed()){

                Socket socket = serverSocket.accept();

                ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

                //Read packet
                Packet rPacket = (Packet) inStream.readObject();
                System.out.println(rPacket.message);
                String[] Message = rPacket.message.split("\n");

                //PUT Request
                if(Message[0].equals("PUT /weather.json HTTP/1.1") && Message[1].equals("User-Agent: ATOMClient/1/0")){
                    
                    elapsedTime = 0L; //reset timer
                    
                    if(!f.exists()){
                        Packet sPacket = new Packet("201 - HTTP_CREATED");
                        outStream.writeObject(sPacket);
                    }else{
                        Packet sPacket = new Packet("200 - HTTP_OK");
                        outStream.writeObject(sPacket);
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter("weather.txt"));
                    writer.write(Message[4]);
                    writer.close();

                }
                //GET Request
                else if(Message[0].equals("GET /weather.json HTTP/1.1") && Message[1].equals("User-Agent: ATOMClient/1/0")){
                    
                    //if file is older than 30 seconds, delete it
                    elapsedTime = (new Date()).getTime() - startTime;
                    if(elapsedTime >= 30000){
                        elapsedTime = 0L; //reset timer
                        System.out.println("File is older than 30 seconds, deleting...");
                        f.delete();
                    }
                    try{
                    BufferedReader reader = new BufferedReader(new FileReader("weather.txt"));
                    String line = reader.readLine();
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode node = objectMapper.readTree(line);
                    Packet sPacket = new Packet(node.get(Message[2]).asText());
                    outStream.writeObject(sPacket);} catch(Exception e){
                        Packet sPacket = new Packet("204 - HTTP_NO_CONTENT");
                        outStream.writeObject(sPacket);
                    }
                }else if(Message[0].equals("Error")){
                    Packet sPacket = new Packet("500 - INTERNAL_SERVER_ERROR");
                    outStream.writeObject(sPacket);
                }
                //Bad Request
                else{
                    Packet sPacket = new Packet("400 - HTTP_BAD_REQUEST");
                    outStream.writeObject(sPacket);
                }
            }
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) throws Exception{
        //Default port
        int port = 4567; 
        //Checking for port argument
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }

        System.out.println("Server is up on port: " + port + "\n");

        //Creating and running AggregationServer object
        ServerSocket serverSocket = new ServerSocket(port);
        AggregationServer server = new AggregationServer(serverSocket);

        f.delete(); //delete leftover file if exists

        server.startServer();
    }

}

