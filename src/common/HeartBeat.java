package common;
import java.io.Serializable;
/**
 * Created by Samuel on 15/11/2016.
 */
public class HeartBeat implements Serializable{
    private String name;
    private int tcpPort;


    public HeartBeat(){
        name="";
        tcpPort=0;
    }

    public HeartBeat(String name, int tcpPort) {
        this.name = name;
        this.tcpPort = tcpPort;
    }

    public String getName(){
        return name;
    }

    public int getTcpPort(){
        return tcpPort;
    }
}
