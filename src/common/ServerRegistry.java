package common;

import common.Registries;

/**
 * Created by Samuel on 25/11/2016.
 */
public class ServerRegistry extends Registries{
    long entryTime;
    int udpPort,tcpPort;

    public ServerRegistry(String n,int udp,int tcp,long entry){
        name=n;
        udpPort=udp;
        tcpPort=tcp;
        entryTime=entry;
    }

}


