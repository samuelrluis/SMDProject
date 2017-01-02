package common;

import common.heartbeat.ClientHeartBeat;

import java.io.Serializable;

/**
 * Created by Samuel on 15/12/2016.
 */
public class Msg implements Serializable {
    String command;
    ClientHeartBeat hBeat;

    public ClientHeartBeat gethBeat() {
        return hBeat;
    }

    public Msg(String command, ClientHeartBeat hBeat) {
        this.command = command;
        this.hBeat = hBeat;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }
}
