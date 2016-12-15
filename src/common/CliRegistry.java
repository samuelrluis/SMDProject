package common;

import common.Registries;

import java.io.*;

/**
 * Created by MarceloCortesao on 05/12/16.
 */
public class CliRegistry extends Registries implements Serializable  {

    private String name,password;
    private int udpHB,udpReader,tcp;
    private Boolean login;

    public CliRegistry(){
        name="User";
        udpHB=0;
        udpReader=0;
        tcp=0;
        login=false;
    }

    public CliRegistry(String name,String password,int udpHB,int tcp,long entry){
        this.name = name;

        udpHB = udpHB;
        udpReader = udpReader;
        tcp = tcp;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getLogin() {
        return login;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(Boolean login) {
        this.login = login;
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
        return "usernameAndPass"+ name + " " + udpHB + " " + udpReader + " " + tcp;
    }

    public void writeObjectToFile() {
        try {
            OutputStream outputStream = new
                    BufferedOutputStream(new FileOutputStream("../SMDProject/src/dirserver/saveCliRegistry.txt"));
            ObjectOutput objectOutput = new
                    ObjectOutputStream(outputStream);
            objectOutput.writeObject(this);
            objectOutput.close();
        } catch (FileNotFoundException e1) {
            System.out.println("File not found");
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}





