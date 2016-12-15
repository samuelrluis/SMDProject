package common;

import java.awt.*;

/**
 * Created by Samuel on 15/12/2016.
 */
public class ClientHeartBeat extends HeartBeat {
    private String password;


    public ClientHeartBeat() {

    }

    public ClientHeartBeat(String name, String password, int uPort, int tPort) {
        super(name,"client",uPort,tPort);
        this.password=password;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
