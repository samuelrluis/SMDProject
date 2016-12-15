package dirserver;


import common.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Created by Samuel on 22/11/2016.
 */
public class ThAnswerHeartBeat extends Thread{
    public static final int MAX_SIZE = 256;
    private DatagramSocket socket=null;
    private DatagramPacket packet;
    private ArrayList<CliRegistry> cliRegistries=null;
    private ArrayList<ServerRegistry> serverRegistries=null;

    ThAnswerHeartBeat(DatagramSocket s, ArrayList<CliRegistry> regCli,ArrayList<ServerRegistry> regServ){
        this.socket=s;
        packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
        cliRegistries=regCli;
        serverRegistries=regServ;
    }

    @Override
    public void run() {
        HeartBeat hBeat=null;
        try {
            while(true){
                socket.receive(packet);
                //Codigo para TCP
                ByteArrayInputStream Bin = new ByteArrayInputStream(packet.getData());
                ObjectInputStream in = new ObjectInputStream(Bin);
                hBeat=(HeartBeat) in.readObject();

                if(hBeat.getType().equalsIgnoreCase("server")){
                    ServerHeartBeat sHBeat=(ServerHeartBeat)hBeat;
                    serverRegistries.add(new ServerRegistry(sHBeat.getName(),sHBeat.getUdpPort(),sHBeat.getTcpPort(),System.nanoTime()));
                }

                else if(hBeat.getType().equalsIgnoreCase("client")){
                    ClientHeartBeat cHBeat=(ClientHeartBeat) hBeat;
                    cliRegistries.add(new CliRegistry(cHBeat.getName(),cHBeat.getPassword(),cHBeat.getUdpPort(),cHBeat.getTcpPort(),System.nanoTime()));


                }

            }
        } catch (IOException e) {
            System.out.println("Error Receiving Datagram Packet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

