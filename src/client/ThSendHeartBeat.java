package client;

import common.ClientHeartBeat;
import common.HeartBeat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Created by Samuel on 22/11/2016.
 */
public class ThSendHeartBeat extends Thread {
    private HeartBeat myHeartBeat;
    private DatagramPacket packetHeartBeat=null;
    private DatagramSocket socketHeartBeat = null;
    private ByteArrayOutputStream b0ut;
    private ObjectOutputStream out;

    ThSendHeartBeat(InetAddress serverAddr, int serverPortToDirectory, int tcpPort, String name,String password){
        try{
            socketHeartBeat = new DatagramSocket();                        //Create Socket to send the HeartBeat
            myHeartBeat=new ClientHeartBeat(name,password,serverPortToDirectory,tcpPort); //Create the HeartBeat Serializable Object
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
            socketHeartBeat.send(packetHeartBeat);//Send the Packet
            //System.out.println("HeartBeat");
            Thread.sleep(30000);
        } catch (IOException e) {
            System.out.println("Error Sending the Packet with The HeartBeat");
        } catch (InterruptedException e) {
            System.out.println("Error With the sleeping Thread");
        }
    }
}
