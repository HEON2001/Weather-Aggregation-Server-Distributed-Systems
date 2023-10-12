package aggserver;

import java.util.StringTokenizer;
import javax.swing.text.html.HTMLEditorKit.Parser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;


public class ContentServer {
    
    public ContentServer(String file, int port) throws Exception
    {
        int p = port;
        parser parse = new parser();
        String nodeString = parse.parse(file);
        String message = "PUT /weather.json HTTP/1.1\n" +
                         "User-Agent: ATOMClient/1/0\n" +
                         "Content-Type: Weather-Data\n" +
                         "Content-Length: " + nodeString.length() + "\n" + nodeString;

        Socket socket = new Socket("localhost", p);

        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

        Packet packet = new Packet(message);
        outStream.writeObject(packet);

        Packet rPacket = (Packet)inStream.readObject();
        System.out.println(rPacket.message);

        socket.close();
        outStream.close();
        inStream.close();

    }

    public static void main(String[] args)throws Exception
    {   
        if(args.length==0){
            System.out.println("Error: No file provided");
            System.exit(0);
        }else if(args.length==1){
            new ContentServer(args[0], 4567);
        }else if(args.length>1){
            new ContentServer(args[0], Integer.parseInt(args[1]));
        }
    }
}
