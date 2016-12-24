package common.heartbeat;

import common.heartbeat.HeartBeat;

import java.io.Serializable;

/**
 * Created by Samuel on 15/12/2016.
 */
public class ClientHeartBeat extends HeartBeat implements Serializable {
    private String nameAndPassword;

    public ClientHeartBeat(String nameAndPassword,int hBport) {
        //TODO falta ir buscar o porto TCP
        super(nameAndPassword,"client",hBport,0);
    }

    public void setNameAndPassword(String password) {
        this.nameAndPassword = password;
    }

    public String getNameAndPassword(){
        return nameAndPassword;
    }
}
