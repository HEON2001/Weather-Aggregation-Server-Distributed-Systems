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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;


public class AggregationServer {

    private ServerSocket serverSocket;

    public AggregationServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() throws Exception{
        try{
            while(!serverSocket.isClosed()){


                Socket socket = serverSocket.accept();
                System.out.println("Server is up on port: " + serverSocket.getLocalPort() + "\n");

                ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

                Packet rPacket = (Packet) inStream.readObject();
                System.out.println(rPacket.message);
                String[] Message = rPacket.message.split("\n");

                if(Message[0].equals("PUT /weather.json HTTP/1.1") && Message[1].equals("User-Agent: ATOMClient/1/0")){
                    BufferedWriter writer = new BufferedWriter(new FileWriter("weather.txt"));
                    writer.write(Message[4]);
                    writer.close();
                    Packet sPacket = new Packet("201 - HTTP_CREATED");
                    outStream.writeObject(sPacket);
                }

                if(Message[0].equals("GET /weather.json HTTP/1.1") && Message[1].equals("User-Agent: ATOMClient/1/0")){
                    BufferedReader reader = new BufferedReader(new FileReader("weather.txt"));
                    String line = reader.readLine();
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode node = objectMapper.readTree(line);
                    Packet sPacket = new Packet(node.get(Message[2]).asText());
                    outStream.writeObject(sPacket);
                }
            }
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 4567; 
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }
        ServerSocket serverSocket = new ServerSocket(port);
        AggregationServer server = new AggregationServer(serverSocket);
        server.startServer();
    }

}

