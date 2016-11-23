package common;
import java.io.Serializable;
/**
 * Created by Samuel on 15/11/2016.
 */
public class HeartBeat implements Serializable{
    private String name;
    private int port; //No server TCP // No Client UDP

    public HeartBeat(){
        name="";
        port=0;
    }

    public HeartBeat(String name, int port) {
        this.name = name;
        this.port = port;
    }

    public String getName(){
        return name;
    }

    public int getPort(){
        return port;
    }
}
