package remoteserver.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by diogomiguel on 04/01/17.
 */
public class ShutdownHook extends Thread{

    DatagramPacket packetHeartBeat = null;
    DatagramSocket socketHeartBeatUDP = null;

    ShutdownHook(DatagramPacket packetHeartBeat, DatagramSocket socketHeartBeatUDP){

        this.packetHeartBeat = packetHeartBeat;
        this.socketHeartBeatUDP = socketHeartBeatUDP;

    }

    @Override
    public void run()
    {
        System.out.println("Desliguei");
        try {
            socketHeartBeatUDP.send(packetHeartBeat);    //Send the Packet
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
