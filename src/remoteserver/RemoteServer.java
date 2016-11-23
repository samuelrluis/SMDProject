/**
 * Created by Samuel on 29/10/2016.
 */
package remoteserver;
import common.HeartBeat;

import java.net.*;
import java.io.*;

public class RemoteServer {
    private String name;
    private static int id=0000;
    ServerSocket serverSocket = null; //TCP
    InetAddress myAddress=null;
    Socket socketToClient = null;
    int myUdpPort,myTcpPort;
    ThreadHeartBeat threadHeartbeat;
    ThreadAnswerClient threadAnswerClient = null;

    RemoteServer(InetAddress address,int udp,int tcp){
        name="RemoteServer " + id;
        id++;
        myAddress=address;
        myUdpPort=udp;
        myTcpPort=tcp;

        //TCP-Client
        try{
            serverSocket = new ServerSocket(myTcpPort);
            createThreadUdp();
            awaitsForNewClient();
            createThreadTcp();

        }catch(IOException e){
            System.out.println("Error creating the New Socket");
        }
    }

    private void awaitsForNewClient(){
        try{
            socketToClient=serverSocket.accept();
        }catch(IOException e){
            System.out.println("Error creating the New Socket");
        }
    }

    private void createThreadUdp(){
        //UDP-Socket to directory Servers
        threadHeartbeat=new ThreadHeartBeat(myAddress,myUdpPort,myTcpPort,this.getName());
        threadHeartbeat.start();
    }

    private void createThreadTcp(){
        //TCP-Socket to the Client
        threadAnswerClient=new ThreadAnswerClient(socketToClient);
        threadAnswerClient.start();
    }

    public String getName(){
        return name;
    }

    public static void main(String[] args) {
        RemoteServer remServer;
        InetAddress serverAddr = null;
        int serverPortToDirectory = -1,serverPortTCP=-1;

        try{
            if(args.length!=3)
                System.out.println("Sintax Error [SERVICEIP][UDP_SERVICEPORT][TCP_SERVICEPORT]");

            serverAddr = InetAddress.getByName(args[0]);
            serverPortToDirectory = Integer.parseInt(args[1]);
            serverPortTCP = Integer.parseInt(args[2]);

            if(serverPortToDirectory<0 || serverPortTCP <0)
                System.out.println("The values of the port in the arguments are wrong");

            remServer=new RemoteServer(serverAddr,serverPortToDirectory,serverPortTCP);

        }catch(UnknownHostException e){
            System.out.println("Não foi encontrada a Máquina");
        }
    }
}
