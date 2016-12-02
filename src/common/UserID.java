package common;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by diogomiguel on 25/11/16.
 */
public class UserID {
    private int udpHB,udpReader,tcp;
    private String username, password;
    private Boolean login;

    public UserID(){
        username="x";
        password="xx";
        udpHB=0;
        udpReader=0;
        tcp=0;
        login=false;
    }

    //Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUdpHB(int udpHB) {
        this.udpHB = udpHB;
    }

    public void setUdpReader(int udpReader) {
        this.udpReader = udpReader;
    }

    public void setTcp(int tcp) {
        this.tcp = tcp;
    }

    @Override
    public String toString() {
        return "username:" + username + " password:" + password + " " + udpHB + " " + udpReader + " " + tcp;
    }

    public boolean verifyLogin(String username, String password){

        if(username.toUpperCase().equals(this.username.toUpperCase())==true && password.equals(this.password.toUpperCase())==true)
            return true;
        return false;

    }
}
