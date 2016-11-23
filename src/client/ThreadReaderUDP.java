package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Samuel on 02/11/2016.
 */
public class ThreadReaderUDP extends Thread{
    DatagramPacket packet=null;
    DatagramSocket socket=null;
    boolean portAvailable;
    String TCPport;


    ThreadReaderUDP(DatagramPacket s, DatagramSocket socket){
        this.packet=s;
        this.socket=socket;
        portAvailable=false;
        TCPport=null;
    }

    public boolean isPortAvailable(){
        return portAvailable;
    }

    public int getPort(){
        return Integer.parseInt(TCPport);
    }

    @Override
    public void run(){
        while(true){
            try {
                //TODO Destinada a receber o porto TCP do REMOTE SERVER
                socket.receive(packet);             //Update content in Packet with the received data
                portAvailable=true;
                String msgRecebida = new String(packet.getData(), 0, packet.getLength());
                TCPport=msgRecebida;
                System.out.println(msgRecebida);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
