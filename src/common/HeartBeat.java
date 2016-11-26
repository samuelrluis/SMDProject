package common;
import java.io.Serializable;
/**
 * Created by Samuel on 15/11/2016.
 */
public class HeartBeat implements Serializable{
    private String name;
    private int udpPort;
    private int tcpPort;

    public HeartBeat(){
        name="";
        udpPort=0;
        tcpPort=0;
    }

    public HeartBeat(String name, int uPort,int tPort) {
        this.name = name;
        this.udpPort = uPort;
        this.tcpPort = tPort;
    }

    public String getName(){
        return name;
    }

    public int getUdpPort(){
        return udpPort;
    }

    public int getTcpPort(){
        return tcpPort;
    }
}
