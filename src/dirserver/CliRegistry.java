package dirserver;

import java.io.*;

/**
 * Created by MarceloCortesao on 05/12/16.
 */
public class CliRegistry extends Registries implements Serializable  {


    CliRegistry(String LogAndPass,int udp,int tcp,long entry){

        name = LogAndPass;
        udpPort=udp;
        tcpPort=tcp;
        entryTime=entry;

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





