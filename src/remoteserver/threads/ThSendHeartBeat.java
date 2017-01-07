package remoteserver.threads;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import common.heartbeat.HeartBeat;
import common.heartbeat.ServerHeartBeat;

/**
 * Created by Samuel on 02/11/2016.
 */
public class ThSendHeartBeat extends Thread {

    //Common
    HeartBeat heartBeat;

    DatagramPacket packetHeartBeat=null;
    DatagramSocket socketHeartBeatUDP = null;
    ByteArrayOutputStream b0ut;
    ObjectOutputStream out;

    public ThSendHeartBeat(InetAddress serverAddr, int serverPortToDirectory, ServerHeartBeat myHeatBeat){
        try{
            socketHeartBeatUDP = new DatagramSocket();          //Create Socket to send the HeartBeat
            heartBeat = myHeatBeat;    //Create the HeartBeat Serializable Object
            b0ut = new ByteArrayOutputStream();                 //Create an array of byte in OutputStream
            out = new ObjectOutputStream(b0ut);                 //Place the ArrayOutputStream in the OBjectOutputSream
            out.writeObject(heartBeat);                         //Write the Heartbeat on the object
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
                //TODO implementar/modificar um mecanismo de enviar arraylist de clientes logados activos nos ulimos Xsegundos(Acho que sao 30 tbm)
                //TODO, tem de ser adicionado ao packetHeartBeat o arraylist
                socketHeartBeatUDP.send(packetHeartBeat);    //Send the Packet
                System.out.print("... ");
                Thread.sleep(10000);

                //TODO falta acabar de implementar isto
                //TODO quando o servidor é desligado manda mensagem ao HB para desaparecer da lista de servidores
                Runtime.getRuntime().addShutdownHook(new ShutdownHook(packetHeartBeat, socketHeartBeatUDP));

                //TODO isto é para alterar para 30secs
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
