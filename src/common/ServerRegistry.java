package common;

/**
 * Created by Samuel on 25/11/2016.
 */
public class ServerRegistry extends Registry {
    ServerHeartBeat hBeat;
    private int udpPort,tcpPort;

    public ServerRegistry(ServerHeartBeat hb,long entry){
        this.hBeat = hb;
        this.entryTime = entry;
    }

    public ServerHeartBeat gethBeat() {
        return hBeat;
    }
}


