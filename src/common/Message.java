package common;

import java.io.Serializable;

/**
 * Created by Samuel on 15/12/2016.
 */
public class Message implements Serializable {
    String command;

    public Message(String command) {
        this.command=command;

    }

    public String getCommand() {
        return command;
    }
}
