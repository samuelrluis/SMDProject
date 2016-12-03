package remoteserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Samuel on 26/11/2016.
 */
public class ThAnswerDirServer extends Thread {
    public static final int MAX_SIZE = 256;
    DatagramSocket socketReaderUdp=null;
    DatagramPacket packetRead=null;

    ThAnswerDirServer(DatagramSocket s){
        socketReaderUdp=s;
    }

    @Override
    public void run() {
        while(true) {
            try {
                packetRead = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE); //Creating the packet that will be received from DirServerv
                socketReaderUdp.receive(packetRead);             //Update content in Packet with the received data
                String msgRecebida = new String(packetRead.getData(), 0, packetRead.getLength());
                System.out.println(msgRecebida);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
