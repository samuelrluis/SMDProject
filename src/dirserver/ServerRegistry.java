package dirserver;

import dirserver.Registries;

/**
 * Created by Samuel on 25/11/2016.
 */
public class ServerRegistry extends Registries {



    ServerRegistry(String n,int udp,int tcp,int entry){
        name=n;
        udpPort=udp;
        tcpPort=tcp;
        entryTime=entry;
    }
}
