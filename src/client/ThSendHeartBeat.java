package client;

import common.HeartBeat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Created by Samuel on 22/11/2016.
 */
public class ThSendHeartBeat extends Thread {
    HeartBeat myHeartBeat;
    DatagramPacket packetHeartBeat=null;
    DatagramSocket socketHeartBeat = null;
    ByteArrayOutputStream b0ut;
    ObjectOutputStream out;

    ThSendHeartBeat(InetAddress serverAddr, int serverPortToDirectory, int tcpPort, String name){
        try{
            socketHeartBeat = new DatagramSocket();                        //Create Socket to send the HeartBeat
            myHeartBeat=new HeartBeat(name,serverPortToDirectory,tcpPort); //Create the HeartBeat Serializable Object
            b0ut = new ByteArrayOutputStream();                            //Create an array of byte in OutputStream
            out = new ObjectOutputStream(b0ut);                            //Place the ArrayOutputStream in the OBjectOutputSream
            out.writeObject(myHeartBeat);                                  //Write the Heartbeat on the object

            packetHeartBeat=new DatagramPacket(b0ut.toByteArray(),b0ut.size(),serverAddr,serverPortToDirectory); //Create a Packet

        } catch (SocketException e) {
            System.out.println("Error Creating the Socket to HeartBeat!");
        } catch (IOException e) {
            System.out.println("Error Creating data to send in the HeartBeat");
        }
    }

    @Override
    public void run() {
        try {
            socketHeartBeat.send(packetHeartBeat);    //Send the Packet
            //System.out.println("HeartBeat");
            Thread.sleep(10000);
        } catch (IOException e) {
            System.out.println("Error Sending the Packet with The HeartBeat");
        } catch (InterruptedException e) {
            System.out.println("Error With the sleeping Thread");
        }
    }
}
