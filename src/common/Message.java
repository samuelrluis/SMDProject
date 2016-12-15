package common;

import java.io.Serializable;

/**
 * Created by Samuel on 15/12/2016.
 */
public class Message implements Serializable {
    String command;
    ClientHeartBeat hBeat;

    public ClientHeartBeat gethBeat() {
        return hBeat;
    }

    public Message(String command, ClientHeartBeat hBeat) {
        this.command = command;
        this.hBeat = hBeat;
    }

    public String getCommand() {
        return command;
    }
}
