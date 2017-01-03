package common.heartbeat;

import common.heartbeat.HeartBeat;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Samuel on 15/12/2016.
 */
public class ClientHeartBeat extends HeartBeat implements Serializable {
    private String nameAndPassword;
    private int readerPort;


    public ClientHeartBeat(String nameAndPassword, int hBport, int readerPort) {
        //TODO falta ir buscar o porto TCP
        super(nameAndPassword,"client",hBport,0);
        this.readerPort = readerPort;
    }

    public void setNameAndPassword(String password) {
        this.nameAndPassword = password;
    }

    public String getNameAndPassword(){
        return nameAndPassword;
    }


    public int getReaderPort() {
        return readerPort;
    }

}
