package aggserver;

import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class parser 
{
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    public static JsonNode parse( String arg ) throws IOException
    {
        Scanner scanner = new Scanner(new File(arg));
        String[] lines = new String[17];
        int i = 0;
        String jsonSource = "";
        String jsonParsed = "";

        while (scanner.hasNextLine()) {
            lines[i] = scanner.nextLine();
            //System.out.println(lines[i]);
            //System.out.println(scanner.nextLine());
            StringTokenizer st = new StringTokenizer(lines[i],":");
            jsonSource += " \"" + st.nextToken()+ "\": " + " \"" + st.nextToken() + "\",";
            i++;
        }

        jsonSource = jsonSource.substring(0, jsonSource.length() - 1);
        jsonParsed = "{" + jsonSource + "}";
        
        return objectMapper.readTree(jsonParsed);
    }

}
