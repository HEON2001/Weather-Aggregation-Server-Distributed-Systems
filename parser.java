package aggserver;

import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class parser 
{

    public static String parse( String arg ) throws IOException
    {   
        //Scanning file into string array
        Scanner scanner = new Scanner(new File(arg));
        String[] lines = new String[17];
        int i = 0;
        String jsonSource = "";
        String jsonParsed = "";

        //Tokenise each line and assemble into JSON format
        while (scanner.hasNextLine()) {
            lines[i] = scanner.nextLine();
            String[] str = lines[i].split(":", 2);
            //StringTokenizer st = new StringTokenizer(lines[i],":");
            jsonSource += " \"" + str[0]+ "\":"+ " \"" + str[1] + "\",";
            i++;
        }

        jsonSource = jsonSource.substring(0, jsonSource.length() - 1);
        jsonParsed = "{" + jsonSource + "}";
        
        //Return JSON formatted string
        return jsonParsed;
    }

}
