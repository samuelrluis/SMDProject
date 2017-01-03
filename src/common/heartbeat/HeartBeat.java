package common.heartbeat;
import java.io.Serializable;
/**
 * Created by Samuel on 15/11/2016.
 */
public class HeartBeat implements Serializable{
    protected String nameAndPass;
    protected int udpPort;
    protected int tcpPort;
    protected String type;

    public HeartBeat(String name, String type, int uPort, int tPort) {
        this.nameAndPass = name;
        this.udpPort = uPort;
        this.tcpPort = tPort;
        this.type=type;
    }

    public String getName(){
        return nameAndPass;
    }

    public void setName(String name){this.nameAndPass=name;
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
