package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */
import common.HeartBeat;

import java.net.*;
import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

public class directoryServer {
    public static final int MAX_SIZE = 256;
    public static void main(String[] args) {

        DatagramSocket socketServers=null;
        DatagramSocket socketClients=null;
        DatagramPacket packetRead=null;
        DatagramPacket packetWrite=null;

        try{
            socketServers = new DatagramSocket(6001);
            socketClients = new DatagramSocket(6002);
            packetRead = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);

           //Função que guarde o IP do cliente;

            while(true){
                socketServers.receive(packetRead);
                System.out.println("recebi");

                                HeartBeat beat=new HeartBeat();

                ByteArrayInputStream Bin = new ByteArrayInputStream(packetRead.getData());
                ObjectInputStream in = new ObjectInputStream(Bin);
                try{
                    beat=(HeartBeat) in.readObject();

                }catch (ClassNotFoundException e){
                    System.out.println("Error reading the datagram received");
                }

                System.out.println(beat.getName()+beat.getTcpPort());

                //TODO falta implementar envio para cliente

        ;
              //  packetWrite = new DatagramPacket(msgRecebida.getBytes(),msgRecebida.length(),InetAddress.getLocalHost(),6003);
//                socketClients.send(packetWrite);
            }

        }catch (SocketException e){
            System.out.println("Erro na criação do socket ");
        }catch (IOException e){
            System.out.println("Erro na recepção de datagrama");
        }
    }
}



