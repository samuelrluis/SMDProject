package common.registry;

import java.io.Serializable;

/**
 * Created by Samuel on 25/11/2016.
 */
public abstract class Registry implements Serializable{
    protected String name;
    protected int udpPort,tcpPort;
    protected long entryTime;

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setEntryTime() {
        this.entryTime = System.nanoTime();
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }
}
