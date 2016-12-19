package common;

import java.io.*;

/**
 * Created by MarceloCortesao on 05/12/16.
 */
public class CliRegistry extends Registry implements Serializable  {
    private Boolean login;
    private ClientHeartBeat hBeat;

    public CliRegistry(){
        hBeat = null;
        login = false;
    }

    public CliRegistry(ClientHeartBeat hBeat,long entry){
        this.hBeat = hBeat;
        this.entryTime = entry;
    }

    public String getName() {
        return this.hBeat.getName();
    }

    public String getPassword() {
        return this.hBeat.getPassword();
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.hBeat.setPassword(password);
    }

    public ClientHeartBeat gethBeat() {
        return hBeat;
    }

    @Override
    public String toString() {
        return "usernameAndPass"+ name + " " + this.hBeat.getUdpPort() + " " + this.hBeat.getTcpPort();
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

    public void sethBeat(ClientHeartBeat hBeat) {
        this.hBeat = hBeat;
    }
}





