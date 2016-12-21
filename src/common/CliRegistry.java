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

        int i=0;
        ArrayList<CliRegistry> recordList = new ArrayList<>();
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream("../SMDProject/src/dirserver/saveCliRegistry.obj");
            objectinputstream = new ObjectInputStream(streamIn);
            CliRegistry readCase = null;
            do {
                try {
                    readCase = (CliRegistry) objectinputstream.readObject();
                }catch (IOException e){
                    System.out.println("File empty");
                }
                if(readCase != null){
                    recordList.add(readCase);
                }
            } while (readCase != null);
            System.out.println(recordList.get(i));
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectinputstream != null) {
                try {
                    objectinputstream.close();
                }catch (IOException e){}
                return false;
            }
        }
        for(i=0;i<recordList.size();i++){
            if(recordList.get(i).getNameAndPassword().compareTo(nameAndPass)==0){
                return true;
            }
        }
        return false;


    }

    public void sethBeat(ClientHeartBeat hBeat) {
        this.hBeat = hBeat;
    }
}





