/**
 * Created by Samuel on 29/10/2016.
 */
package remoteserver;

import common.heartbeat.HeartBeat;
import common.heartbeat.ServerHeartBeat;
import common.registry.ClientRegistry;
import common.registry.ServerRegistry;
import remoteserver.threads.ThAnswerClient;
import remoteserver.threads.ThSendHeartBeat;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class RemoteServer {

    public static final  String SERVICE_NAME = "DownloadFile";

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
    private ServerHeartBeat myHeartServer;
    DatagramSocket serverSocketUdp = null; //UDP
    InetAddress myAddress=null;
    private Socket socketToClient = null;
    int myTcpPort,serverDirPort,myUdpPort;


    RemoteServer(String name, InetAddress address, int udp){
        cliRegistries = new ArrayList<ClientRegistry>();
        this.name=name;
        myAddress=address;
        myUdpPort=0;
        myTcpPort=0;
        serverDirPort=udp;
        myController = new RemoteServerController();
        myHeartServer = null;

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
        threadHeartbeat=new ThSendHeartBeat(myAddress,serverDirPort,myHeartServer);
        threadHeartbeat.start();

    }

    public void runServer(){
        //TCP-Client
        try{
            serverSocketTcp = new ServerSocket(0);
            serverSocketUdp = new DatagramSocket();
            myTcpPort=serverSocketTcp.getLocalPort();
            myUdpPort=serverSocketUdp.getLocalPort();
            myHeartServer = new ServerHeartBeat(this.name,this.myUdpPort,this.myTcpPort);
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

    public ServerHeartBeat getMyHeartServer() {return myHeartServer;}

    public boolean addClientToArray (ClientRegistry cli) {
        return this.cliRegistries.add(cli);
    }


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
            //cria o .obj
            remServer=new RemoteServer(name,serverAddr,serverPortToDirectory);
            File file = new File("../SMDProject/servFolder/"+ remServer.getName() +".obj");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                }catch (IOException e){
                    System.out.println("Nao Foi Possivel criar ficheiro");
                }
            }
            //cria a diretoria para as diretorias dos clientes
            File dir = new File("../SMDProject/cliFolders/"+ remServer.getName());
            if (!dir.exists()) {
                try {
                    dir.mkdir();
                }catch (Exception e){
                    System.out.println("Nao Foi Possivel criar a pasta");
                }
            }


            remServer.runServer();

        }catch(UnknownHostException e){
            System.out.println("Não foi encontrada a Máquina");
        }
    }
}
