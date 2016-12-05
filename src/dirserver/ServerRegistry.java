package dirserver;

import dirserver.Registries;

import java.io.*;

/**
 * Created by Samuel on 25/11/2016.
 */
public class ServerRegistry extends Registries implements Serializable {

    ServerRegistry(String n,int udp,int tcp,long entry){
        name=n;
        udpPort=udp;
        tcpPort=tcp;
        entryTime=entry;
    }

    public void writeObjectToFile() {
        try {
            OutputStream outputStream = new
                    BufferedOutputStream(new FileOutputStream("../SMDProject/src/dirserver/saveServerRegistry.txt"));
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
