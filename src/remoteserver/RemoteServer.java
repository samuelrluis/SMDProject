/**
 * Created by Samuel on 29/10/2016.
 */
package remoteserver;

import java.net.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoteServer {
    private String name;
    ServerSocket serverSocketTcp = null; //TCP
    DatagramSocket serverSocketUdp = null; //UDP
    InetAddress myAddress=null;
    Socket socketToClient = null;
    int myTcpPort,serverDirPort,myUdpPort;
    ThSendHeartBeat threadHeartbeat;
    ThAnswerClient threadAnswerClient = null;

    RemoteServer(String name,InetAddress address,int udp){
        this.name=name;
        myAddress=address;
        myUdpPort=0;
        myTcpPort=0;
        serverDirPort=udp;
    }
//TODO e preciso arrumar estas funçoes no controlador
    private void awaitsForNewClient(){
            while (true) {
                try {
                    socketToClient = serverSocketTcp.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ThAnswerClient thread = new ThAnswerClient(serverSocketTcp, socketToClient);
                thread.start();
                System.out.println("after thread");
            }
    }

    private void createThreadUdp(){
        //UDP-Socket to directory Servers
        System.out.println("dirport: "+serverDirPort);
        System.out.println("udpPort: "+myUdpPort);
        System.out.println("tcpPort: "+myTcpPort);
        threadHeartbeat=new ThSendHeartBeat(myAddress,serverDirPort,myUdpPort,myTcpPort,this.getName());
        threadHeartbeat.start();
    }

    private void createThreadTcp(Socket socketToClient){
        //TCP-Socket to the Client
        threadAnswerClient=new ThAnswerClient(serverSocketTcp,socketToClient);
        threadAnswerClient.start();
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
    }

    public String getName(){
        return name;
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

            remServer=new RemoteServer(name,serverAddr,serverPortToDirectory);
            remServer.runServer();

        }catch(UnknownHostException e){
            System.out.println("Não foi encontrada a Máquina");
        }
    }
}
