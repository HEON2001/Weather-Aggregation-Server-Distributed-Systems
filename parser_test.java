package aggserver;

import java.util.StringTokenizer;
import javax.swing.text.html.HTMLEditorKit.Parser;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;


public class parser_test {
    public static void main(String[] args)throws IOException
    {   
        parser p = new parser();
        String json = p.parse("test_file01.txt");
        
        System.out.println(json);
    }
}
