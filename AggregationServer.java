package aggserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;


public class AggregationServer {

    private ServerSocket serverSocket;

    public AggregationServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() throws Exception{
        try{
            while(!serverSocket.isClosed()){


                Socket socket = serverSocket.accept();
                System.out.println("Server is up on port" + serverSocket.getLocalPort());

                ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

                Packet rPacket = (Packet) inStream.readObject();
                System.out.println(rPacket.message);
                String[] PUTMessage = rPacket.message.split("\n");

                if(PUTMessage[0].equals("PUT /weather.json HTTP/1.1") && PUTMessage[1].equals("User-Agent: ATOMClient/1/0")){
                    Packet sPacket = new Packet("Hello! From Server!");
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