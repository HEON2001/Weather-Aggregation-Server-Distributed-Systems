package aggserver;

import java.io.Serializable;

//Packet class for sending messages between client and server
public class Packet implements Serializable{
    
    String message;

    public Packet(String message){
        this.message = message;
    }
}