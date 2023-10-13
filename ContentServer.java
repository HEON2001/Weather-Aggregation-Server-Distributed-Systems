package aggserver;

import java.util.StringTokenizer;
import javax.swing.text.html.HTMLEditorKit.Parser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;


public class ContentServer {
    
    public ContentServer(String file, int port) throws Exception
    {
        int p = port;
        parser parse = new parser();

        //Parsing the file into JSON format using implemented parser
        String nodeString = parse.parse(file);
        //Assembling PUT message
        String message = "PUT /weather.json HTTP/1.1\n" +
                         "User-Agent: ATOMClient/1/0\n" +
                         "Content-Type: Weather-Data\n" +
                         "Content-Length: " + nodeString.length() + "\n" + nodeString;

        //Creating socket and streams
        Socket socket = new Socket("localhost", p);

        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

        //Sending PUT message to AggregationServer
        Packet packet = new Packet(message);
        outStream.writeObject(packet);

        //Receiving response from AggregationServer =
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
        if(args.length>1){
            port = Integer.parseInt(args[0]);
        }else if(args.length==0){
            System.out.println("Error: Invalid number of arguments");
            System.exit(500);
        }

        //Creating and running ContentServer object
        new ContentServer(args[1], port);
    }
}
