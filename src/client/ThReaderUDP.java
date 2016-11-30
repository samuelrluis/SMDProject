package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Samuel on 02/11/2016.
 */
public class ThReaderUDP extends Thread{
    public static final int MAX_SIZE = 256;
    DatagramSocket socket=null;
    DatagramPacket packetRead=null;

    ThReaderUDP(DatagramSocket socket){
        this.socket=socket;
    }

    @Override
    public void run(){
        while(true){
            try {
                packetRead = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE); //Creating the packet that will be received from DirServerv
                socket.receive(packetRead);             //Update content in Packet with the received data
                String msgRecebida = new String(packetRead.getData(), 0, packetRead.getLength());
                System.out.println(msgRecebida);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
