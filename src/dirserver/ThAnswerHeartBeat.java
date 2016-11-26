package dirserver;

import common.HeartBeat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Created by Samuel on 22/11/2016.
 */
public class ThAnswerHeartBeat extends Thread{
    public static final int MAX_SIZE = 256;
    DatagramSocket socket=null;
    DatagramPacket packet;
    ArrayList<Registries> regList=null;


    ThAnswerHeartBeat(DatagramSocket s, ArrayList<Registries> reg){
        this.socket=s;
        packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
        regList=reg;
    }

    @Override
    public void run() {
        HeartBeat hBeat=null;
        try {
            while(true){
                socket.receive(packet);

                //Codigo para TCP
                ByteArrayInputStream Bin = new ByteArrayInputStream(packet.getData());
                ObjectInputStream in = new ObjectInputStream(Bin);
                hBeat=(HeartBeat) in.readObject();
                regList.add(new ServerRegistry(hBeat.getName(),hBeat.getUdpPort(),hBeat.getTcpPort(),0));

            }
        } catch (IOException e) {
            System.out.println("Error Receiving Datagram Packet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
