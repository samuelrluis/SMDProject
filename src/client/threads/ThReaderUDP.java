package client.threads;

import client.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Samuel on 02/11/2016.
 */
public class ThReaderUDP extends Thread{

    public static final int MAX_SIZE = 1024;
    Client myClient = null;
    DatagramSocket socket=null;
    DatagramPacket packetRead=null;
    boolean portAvailable;

    public ThReaderUDP(Client myclient){
        try {
            this.myClient = myclient;
            socket=new DatagramSocket();
            myClient.setReaderPort(socket.getLocalPort());

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public boolean isPortAvailable(){
        return portAvailable;
    }

    @Override
    public void run(){
        while(true){
            try {
                //TODO Destinada a receber o porto TCP do REMOTE SERVER
                System.out.println("waiting.....");
                packetRead = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE); //Creating the packet that will be received from DirServerv
                socket.receive(packetRead);             //Update content in Packet with the received data
                portAvailable=true;
                String msgRecebida = new String(packetRead.getData(), 0, packetRead.getLength());
                System.out.println(msgRecebida);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
