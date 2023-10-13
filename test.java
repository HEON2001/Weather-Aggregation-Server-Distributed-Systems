package aggserver;

import java.util.StringTokenizer;
import javax.swing.text.html.HTMLEditorKit.Parser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class test{
    public static void main(String args[]) throws Exception{
        System.out.println("\nCreating content:");
        new ContentServer("test_file01.txt", 4567);

        TimeUnit.SECONDS.sleep(2);
        System.out.println("\nRegular requests:");
        new GETClient("id", 4567);
        new GETClient("local_date_time", 4567);
        new GETClient("name", 4567);

        System.out.println("\nRequest not found failure:");
        new GETClient("fail", 4567);

        System.out.println("\nSleeping for 30 seconds...");
        TimeUnit.SECONDS.sleep(30);
        new GETClient("id", 4567);

        System.out.println("\nCreated and OK responses:");
        new ContentServer("test_file01.txt", 4567);
        new ContentServer("test_file01.txt", 4567);

        System.out.println("\nBad and No Content response:");
        new ContentServer("test_file02.txt", 4567);
        new GETClient("id", 4567);
    }
}