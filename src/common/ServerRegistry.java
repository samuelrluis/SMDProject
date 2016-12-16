package common;

/**
 * Created by Samuel on 25/11/2016.
 */
public class ServerRegistry extends Registry {
    private ServerHeartBeat hBeat;


    public ServerRegistry(ServerHeartBeat hb,long entry){
        this.hBeat = hb;
        this.entryTime = entry;
        this.name=hb.getName();
        this.tcpPort=hb.getTcpPort();
    }

    public ServerHeartBeat gethBeat() {
        return hBeat;
    }
}


