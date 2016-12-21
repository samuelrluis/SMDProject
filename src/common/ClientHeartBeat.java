package common;

import java.awt.*;
import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Samuel on 15/12/2016.
 */
public class ClientHeartBeat extends HeartBeat implements Serializable {
    private String nameAndPassword;

    public ClientHeartBeat(String name,String nameAndPassword,int hBport) {
        //TODO falta ir buscar o porto TCP
        super(name,"client",hBport,0);
        this.nameAndPassword=nameAndPassword;
    }

    public void setNameAndPassword(String password) {
        this.nameAndPassword = password;
    }

    public String getNameAndPassword(){
        return nameAndPassword;
    }
}
