package common;

import common.heartbeat.ClientHeartBeat;
import common.heartbeat.ServerHeartBeat;

import java.io.Serializable;

/**
 * Created by Samuel on 15/12/2016.
 */
public class Msg implements Serializable {
    String command;
    ClientHeartBeat hBeat;
    ServerHeartBeat hBeatSer;

    public Msg(String command, ClientHeartBeat hBeat) {
        this.command = command;
        this.hBeat = hBeat;
    }
    public Msg(String command,ClientHeartBeat hBeat, ServerHeartBeat hBeatSer) {
        this.command = command;
        this.hBeat = hBeat;
        this.hBeatSer=hBeatSer;

    }


    public ClientHeartBeat gethBeat() {
        return hBeat;
    }

    public ServerHeartBeat gethBeatSer() {
        return hBeatSer;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String cmd) {this.command = cmd;}

    
}
