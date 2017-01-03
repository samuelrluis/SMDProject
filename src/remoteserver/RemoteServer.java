/**
 * Created by Samuel on 29/10/2016.
 */
package remoteserver;

import common.registry.ClientRegistry;
import common.registry.ServerRegistry;
import remoteserver.threads.ThAnswerClient;
import remoteserver.threads.ThSendHeartBeat;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class RemoteServer {

    //RemoteServer
    RemoteServerController remoteServerController;
    ThSendHeartBeat threadHeartbeat;
    ThAnswerClient threadAnswerClient = null;

    //Common
    private ArrayList<ClientRegistry> cliRegistries = null;
    private ArrayList<ServerRegistry> serverRegistries = null;

    private String name;
    private ServerSocket serverSocketTcp = null; //TCP
    private DatagramPacket serverPacket = null;
    private RemoteServerController myController= null;
    DatagramSocket serverSocketUdp = null; //UDP
    InetAddress myAddress=null;
    private Socket socketToClient = null;
    int myTcpPort,serverDirPort,myUdpPort;


    RemoteServer(String name, InetAddress address, int udp){

        this.name=name;
        myAddress=address;
        myUdpPort=0;
        myTcpPort=0;
        serverDirPort=udp;
        myController = new RemoteServerController();

    }



//TODO e preciso arrumar estas funçoes no controlador
    private void awaitsForNewClient(){

                try {
                    socketToClient = serverSocketTcp.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ThAnswerClient thread = new ThAnswerClient(this);
                thread.start();

    }

    private void createThreadUdp(){

        //UDP-Socket to directory Servers
        System.out.println("dirport: "+serverDirPort);
        System.out.println("udpPort: "+myUdpPort);
        System.out.println("tcpPort: "+myTcpPort);
        threadHeartbeat=new ThSendHeartBeat(myAddress,serverDirPort,myUdpPort,myTcpPort,this.getName());
        threadHeartbeat.start();

    }

    public void runServer(){
        //TCP-Client
        try{
            serverSocketTcp = new ServerSocket(0);
            serverSocketUdp = new DatagramSocket();
            myTcpPort=serverSocketTcp.getLocalPort();
            myUdpPort=serverSocketUdp.getLocalPort();

            createThreadUdp();
            awaitsForNewClient();

        }catch(IOException e){
            System.out.println("Error creating the New Socket X");
        }
        //remoteServerController.answeringDatagram();
    }

    public String getName(){
        return this.name;
    }


    public RemoteServerController getRemoteServerController() {
        return remoteServerController;
    }



    public DatagramPacket getPacket() {
        return serverPacket;
    }

    public ArrayList<ServerRegistry> getServerRegistries() {
        return serverRegistries;
    }

    public ArrayList<ClientRegistry> getCliRegistries() {
        return cliRegistries;
    }

    public ServerSocket getServerSocketTcp() {return this.serverSocketTcp;}
    public Socket getSocketToClient() {return this.socketToClient;}

    public static void main(String[] args) {
        RemoteServer remServer;
        InetAddress serverAddr = null;
        int serverPortToDirectory = -1;

        try{
            if(args.length!=3) {
                System.out.println("Sintax Error [SERVICEIP][UDP_SERVICEPORT_TODIRSERVER]");
                System.exit(0);
            }

            String name = args[0];
            serverAddr = InetAddress.getByName(args[1]);
            serverPortToDirectory = Integer.parseInt(args[2]);

            if(serverPortToDirectory<0 )
                System.out.println("The values of the port in the arguments are wrong");

            remServer=new RemoteServer(name,serverAddr,serverPortToDirectory);
            remServer.runServer();

        }catch(UnknownHostException e){
            System.out.println("Não foi encontrada a Máquina");
        }
    }
}
