package common.registry;

import common.heartbeat.ServerHeartBeat;

import java.net.InetAddress;

/**
 * Created by Samuel on 25/11/2016.
 */
public class ServerRegistry extends Registry {
    private ServerHeartBeat hBeat;
    private InetAddress myAddress;

    public ServerRegistry(ServerHeartBeat hb,long entry){
        this.hBeat = hb;
        this.entryTime = entry;
        this.name=hb.getName();
        this.tcpPort=hb.getTcpPort();

    }

    public ServerHeartBeat gethBeat() {
        return hBeat;
    }

    public InetAddress getMyAddress() {
        return myAddress;
    }

    public void setAddress(InetAddress address) {
        this.myAddress = address;
    }
}


