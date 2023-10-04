package aggserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.StringTokenizer;
import javax.swing.text.html.HTMLEditorKit.Parser;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;


public class ContentServer {
    public static void main(String[] args)throws IOException
    {   
        parser p = new parser();

        try{
            JsonNode node = p.parse("test_file01.txt");
            System.out.println(node.get("id").asText());
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}
