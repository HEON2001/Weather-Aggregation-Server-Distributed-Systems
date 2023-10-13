package aggserver;

import java.util.StringTokenizer;
import javax.swing.text.html.HTMLEditorKit.Parser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;


public class GETClient {
    
    public GETClient(String req, int port) throws Exception
    {
        int p = port;
        String r = req;
        //Assembling GET message
        String message = "GET /weather.json HTTP/1.1\n" +
                         "User-Agent: ATOMClient/1/0\n" + r;

        //Establish connection
        Socket socket = new Socket("localhost", p);

        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

        //Sending GET message to AggregationServer
        Packet packet = new Packet(message);
        outStream.writeObject(packet);

        //Receiving response from AggregationServer
        Packet rPacket = (Packet)inStream.readObject();
        System.out.println(rPacket.message);

        //Closing socket and streams
        socket.close();
        outStream.close();
        inStream.close();

    }

    public static void main(String[] args)throws Exception
    {   
        //Default port
        int port = 4567; 
        //Checking for port argument
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }

        //Keep scanning for GET requests
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("\nEnter request: ");
            String req = sc.nextLine();
            new GETClient(req, port);
        }

    }
}
