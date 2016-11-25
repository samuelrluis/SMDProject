package remoteserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import common.HeartBeat;

/**
 * Created by Samuel on 02/11/2016.
 */
public class ThreadHeartBeat extends Thread {
    DatagramPacket packetHeartBeat=null;
    DatagramSocket socketUDP = null;
    HeartBeat heartBeat;
    ByteArrayOutputStream b0ut;
    ObjectOutputStream out;

    public ThreadHeartBeat(InetAddress serverAddr,int serverPortToDirectory,int uPort ,int tPort,String name){
        try{
            socketUDP = new DatagramSocket();                               //Create Socket to send the HeartBeat
            heartBeat=new HeartBeat(name,uPort,tPort);                      //Create the HeartBeat Serializable Object
            b0ut = new ByteArrayOutputStream();                             //Create an array of byte in OutputStream
            out = new ObjectOutputStream(b0ut);                             //Place the ArrayOutputStream in the OBjectOutputSream
            out.writeObject(heartBeat);                                     //Write the Heartbeat on the object

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
                socketUDP.send(packetHeartBeat);    //Send the Packet
                System.out.println("Enviei");
                Thread.sleep(10000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
