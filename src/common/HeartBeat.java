package common;
import java.io.Serializable;
/**
 * Created by Samuel on 15/11/2016.
 */
public class HeartBeat implements Serializable{
    private String name;
    private int udpPort;
    private int tcpPort;
    private String type;
    private String password;

    public HeartBeat(){
        name="";
        udpPort=0;
        tcpPort=0;
    }

    public HeartBeat(String name, String type, int uPort, int tPort) {
        this.name = name;
        this.udpPort = uPort;
        this.tcpPort = tPort;
        this.type=type;
    }

    public String getName(){
        return name;
    }

    public String getType() {
        return type;
    }

    public int getUdpPort(){
        return udpPort;
    }

    public int getTcpPort(){
        return tcpPort;
    }
}
