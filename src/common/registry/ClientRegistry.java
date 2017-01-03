package common.registry;

import common.heartbeat.ClientHeartBeat;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by MarceloCortesao on 05/12/16.
 */
public class ClientRegistry extends Registry implements Serializable  {

    private Boolean login;
    private ClientHeartBeat hBeat;
    private InetAddress myAddress;

    public ClientRegistry(){
        hBeat = null;
        login = false;
    }

    public ClientRegistry(ClientHeartBeat hBeat, long entry ){
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

    public void writeObjectToFile(String path) {
        ArrayList<ClientRegistry> allObjs = null;
        try {
            allObjs= new ArrayList<>(readObjsFile(path));
            allObjs.add(this);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(allObjs);
            objectOutput.close();
            System.out.println("Gravou sucessso");
        } catch (FileNotFoundException e1) {
            System.out.println("File not found");
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public ArrayList<ClientRegistry> readObjsFile (String path){
        ArrayList<ClientRegistry> ListObj = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ListObj= (ArrayList<ClientRegistry>) ois.readObject();
        }catch (Exception e){}
        return ListObj;
    }

    public boolean checkCliOnFile (String nameAndPass, String path){
        ArrayList<ClientRegistry> recordList = new ArrayList<>();
        try {
            try {
                FileInputStream fis = new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);
                     recordList = (ArrayList<ClientRegistry>) ois.readObject();

            } catch (FileNotFoundException e) {System.out.println("File not found");return false;}
        }catch (Exception e){}


        for (int i=0; i<recordList.size();i++){
            if(recordList.get(i).getName().toString().compareTo(nameAndPass)==0){
                return true;
            }
        }
        return false;
    }

    public InetAddress getMyAddress() {
        return myAddress;
    }

    public void setAddress(InetAddress address) {
        this.myAddress = address;
    }

    public void sethBeat(ClientHeartBeat hBeat) {
        this.hBeat = hBeat;
    }


}





