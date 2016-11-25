package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */

import java.net.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class DirectoryServer {

    DatagramSocket socketServers = null;
    DatagramSocket socketClients = null;
    ThreadAnswerHeartBeat threadHbServer = null;
    ThreadAnswerHeartBeat threadHbClients = null;
    ArrayList<Registries> registries = null;

    DirectoryServer(){
        registries=new ArrayList<>();
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
        threadHbServer = new ThreadAnswerHeartBeat(socketServers,registries);  //Create Thread To receive HB from Servers
        threadHbClients = new ThreadAnswerHeartBeat(socketClients,registries); //Create Thread To receive HB from Clients

        //Start Threads
        threadHbServer.start();
        threadHbClients.start();
    }

    public static void main(String[] args) {
        DirectoryServer myServer=null;
        myServer=new DirectoryServer();
    }
}



