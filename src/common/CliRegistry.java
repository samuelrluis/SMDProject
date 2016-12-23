package common;

import sun.misc.JavaIOFileDescriptorAccess;

import java.io.*;
import java.util.ArrayList;

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

    public String getNameAndPassword() {
        return this.hBeat.getNameAndPassword();
    }

    //Setters

    public void setNameAndPassword(String namePassword) {
        this.hBeat.setNameAndPassword(namePassword);
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
                    BufferedOutputStream(new FileOutputStream("../SMDProject/src/dirserver/saveCliRegistry.obj"));
            ObjectOutput objectOutput = new
                    ObjectOutputStream(outputStream);
            objectOutput.writeObject(this);
            System.out.println(this.getName());
            objectOutput.close();
            System.out.println("Gravou sucessso");
        } catch (FileNotFoundException e1) {
            System.out.println("File not found");
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public boolean checkCliOnFile (String nameAndPass){


        ArrayList<CliRegistry> recordList = new ArrayList<>();
        CliRegistry object= new CliRegistry();
        try {
            try {
                FileInputStream fis = new FileInputStream("../SMDProject/src/dirserver/saveCliRegistry.obj");
                ObjectInputStream ois = new ObjectInputStream(fis);
                do {
                    object = (CliRegistry) ois.readObject();
                    if(object!=null)
                        recordList.add(object);
                }while (object!=null);
            } catch (FileNotFoundException e) {System.out.println("File not found");return false;}
        }catch (Exception e){}

        for (int i=0; i<recordList.size();i++){
            if(recordList.get(i).getName().toString().compareTo(nameAndPass)==0){
                return true;
            }
        }
        return false;
    }

    public void sethBeat(ClientHeartBeat hBeat) {
        this.hBeat = hBeat;
    }
}





