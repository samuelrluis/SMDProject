package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Samuel on 02/11/2016.
 */
public class ReaderUDP extends Thread{
    DatagramPacket packet=null;
    DatagramSocket socket=null;
    boolean portAvailable;
    String TCPport;


    ReaderUDP(DatagramPacket s,DatagramSocket socket){
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
                System.out.println("antes");
                socket.receive(packet);
                System.out.println("depois");
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
