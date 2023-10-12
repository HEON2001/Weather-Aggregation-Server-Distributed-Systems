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

    /* 
        private static ObjectMapper getDefaultObjectMapper() {
            ObjectMapper defaultObjectMapper = new ObjectMapper();
            return defaultObjectMapper;
        }

        private static ObjectMapper objectMapper = getDefaultObjectMapper();
    */ 
    
    public ContentServer() throws Exception
    {
        parser p = new parser();
        String nodeString = p.parse("test_file01.txt");
        String message = "PUT /weather.json HTTP/1.1\n" +
                         "User-Agent: ATOMClient/1/0\n" +
                         "Content-Type: Weather-Data\n" +
                         "Content-Length: " + nodeString.length() + "\n" + nodeString;

        Socket socket = new Socket("localhost", 4567);
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
        new ContentServer();

        try{
            //String nodeString = p.parse("test_file01.txt");
            //node = objectMapper.readTree(nodeString);
            //System.out.println(node.get("cloud").asText());
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
