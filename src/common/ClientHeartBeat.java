package common;

import java.awt.*;
import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Samuel on 15/12/2016.
 */
public class ClientHeartBeat extends HeartBeat implements Serializable {
    private String password;

    public ClientHeartBeat(String name,String password,int hBport) {
        //TODO falta ir buscar o porto TCP
        super(name,"client",hBport,0);
        this.password=password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
