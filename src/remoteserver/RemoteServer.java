/**
 * Created by Samuel on 29/10/2016.
 */
package remoteserver;
import common.HeartBeat;

import java.net.*;
import java.io.*;

public class RemoteServer {
    private String name;
    private static int id=0;
    ServerSocket serverSocket = null; //TCP
    InetAddress myAddress=null;
    Socket socketToClient = null;
    int myTcpPort,serverDirPort,myUdpPort;
    ThreadHeartBeat threadHeartbeat;
    ThreadAnswerClient threadAnswerClient = null;

    RemoteServer(InetAddress address,int udp){

        name="RemoteServer" + id + " ";
        id++;
        myAddress=address;
        myUdpPort=0;
        myTcpPort=0;
        serverDirPort=udp;
    }

    private void awaitsForNewClient(){
        try{
            socketToClient=serverSocket.accept();
        }catch(IOException e){
            System.out.println("Error creating the New Socket C");
        }
    }

    private void createThreadUdp(){
        //UDP-Socket to directory Servers
        threadHeartbeat=new ThreadHeartBeat(myAddress,serverDirPort,myUdpPort,myTcpPort,this.getName());
        threadHeartbeat.start();
    }

    private void createThreadTcp(){
        //TCP-Socket to the Client
        threadAnswerClient=new ThreadAnswerClient(serverSocket);
        threadAnswerClient.start();
    }

    public void runServer(){
        //TCP-Client
        try{
            serverSocket = new ServerSocket();
            myTcpPort=serverSocket.getLocalPort();
            createThreadUdp();
/*            awaitsForNewClient();
            createThreadTcp();*/

        }catch(IOException e){
            System.out.println("Error creating the New Socket X");
        }
    }

    public String getName(){
        return name;
    }

    public static void main(String[] args) {
        RemoteServer remServer;
        InetAddress serverAddr = null;
        int serverPortToDirectory = -1,serverPortTCP=-1;

        try{
            if(args.length!=2)
                System.out.println("Sintax Error [SERVICEIP][UDP_SERVICEPORT_TODIRSERVER]");

            serverAddr = InetAddress.getByName(args[0]);
            serverPortToDirectory = Integer.parseInt(args[1]);

            if(serverPortToDirectory<0 )
                System.out.println("The values of the port in the arguments are wrong");

            remServer=new RemoteServer(serverAddr,serverPortToDirectory);
            remServer.runServer();

        }catch(UnknownHostException e){
            System.out.println("Não foi encontrada a Máquina");
        }
    }
}
