package common.heartbeat;

import common.heartbeat.HeartBeat;

/**
 * Created by Samuel on 15/12/2016.
 */
public class ServerHeartBeat extends HeartBeat {
    public ServerHeartBeat(String name, int uPort, int tPort){
        super(name,"server",uPort,tPort);

    }
}
