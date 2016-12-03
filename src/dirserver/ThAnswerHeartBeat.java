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
    ArrayList<Registries> regListServer=null;

    ThAnswerHeartBeat(DatagramSocket s, ArrayList<Registries> reg){
        this.socket=s;
        packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
        regListServer=reg;
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
                regListServer.add(new ServerRegistry(hBeat.getName(),hBeat.getUdpPort(),hBeat.getTcpPort(),System.nanoTime()));

            }
        } catch (IOException e) {
            System.out.println("Error Receiving Datagram Packet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
