package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */
import common.HeartBeat;

import java.net.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class directoryServer {

    DatagramSocket socketServers = null;
    DatagramSocket socketClients = null;
    ThreadAnswerHeartBeat threadHbServer = null;
    ThreadAnswerHeartBeat threadHbClients = null;

    directoryServer(){
        createSockets();
        createThreads();
    }

    public void createSockets(){
        //Creating Sockets
        try {
            socketServers = new DatagramSocket(6001);
            socketClients = new DatagramSocket(6002);
        } catch (SocketException e) {
            System.out.println("Error Creating Sockets");
        }
    }

    public void createThreads(){
        //Threads Heartbeat
        threadHbServer = new ThreadAnswerHeartBeat(socketServers);  //Create Thread To receive HB from Servers
        threadHbClients = new ThreadAnswerHeartBeat(socketClients); //Create Thread To receive HB from Clients

        //Start Threads
        threadHbServer.start();
        threadHbClients.start();
    }

    public static void main(String[] args) {
        directoryServer myServer=null;
        myServer=new directoryServer();
    }
}



