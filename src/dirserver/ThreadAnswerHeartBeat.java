package dirserver;

import common.HeartBeat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Samuel on 22/11/2016.
 */
public class ThreadAnswerHeartBeat extends Thread{
    public static final int MAX_SIZE = 256;
    DatagramSocket socket=null;

    DatagramPacket packet;
    ThreadAnswerHeartBeat(DatagramSocket s){
        this.socket=s;
        packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
    }

    @Override
    public void run() {
        HeartBeat hBeat=null;
        try {
            while(true){
                socket.receive(packet);
                System.out.println("recebi HB");

                //Codigo para TCP
            ByteArrayInputStream Bin = new ByteArrayInputStream(packet.getData());
            ObjectInputStream in = new ObjectInputStream(Bin);
            hBeat=(HeartBeat) in.readObject();
            System.out.println(hBeat.getName()+hBeat.getPort());
            }
        } catch (IOException e) {
            System.out.println("Error Receiving Datagram Packet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
