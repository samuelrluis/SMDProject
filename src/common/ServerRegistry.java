package common;

/**
 * Created by Samuel on 25/11/2016.
 */
public class ServerRegistry extends Registry {
    private int udpPort,tcpPort;

    public ServerRegistry(String n,int udp,int tcp,long entry){
        this.name=n;
        this.udpPort=udp;
        this.tcpPort=tcp;
        this.entryTime=entry;
    }
}


