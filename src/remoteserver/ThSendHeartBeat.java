package remoteserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import common.HeartBeat;
import common.ServerHeartBeat;

/**
 * Created by Samuel on 02/11/2016.
 */
public class ThSendHeartBeat extends Thread {
    DatagramPacket packetHeartBeat=null;
    DatagramSocket socketHeartBeatUDP = null;
    HeartBeat heartBeat;
    ByteArrayOutputStream b0ut;
    ObjectOutputStream out;

    public ThSendHeartBeat(InetAddress serverAddr, int serverPortToDirectory, int uPort , int tPort, String name){
        try{
            socketHeartBeatUDP = new DatagramSocket();  //Create Socket to send the HeartBeat
            heartBeat=new ServerHeartBeat(name,uPort,tPort); //Create the HeartBeat Serializable Object
            b0ut = new ByteArrayOutputStream();         //Create an array of byte in OutputStream
            out = new ObjectOutputStream(b0ut);         //Place the ArrayOutputStream in the OBjectOutputSream
            out.writeObject(heartBeat);                 //Write the Heartbeat on the object

            packetHeartBeat=new DatagramPacket(b0ut.toByteArray(),b0ut.size(),serverAddr,serverPortToDirectory); //Create a Packet

        }catch(SocketException e){
            System.out.println("Error Creating the UDP Socket");
        }catch (IOException e){
            System.out.println("Error Creating the Output Streams");
        }
    }

    public void run(){
        while(true) {
            try {
                socketHeartBeatUDP.send(packetHeartBeat);    //Send the Packet
                System.out.println("Enviei");
                Thread.sleep(10000);
                //TODO isto é para alterar para 30secs
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
