package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */

import common.Registries;

import java.net.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

 public class DirectoryServer {

    DatagramSocket socketServers = null,socketClientsHB = null,socketClientsCommand = null;
    ThAnswerHeartBeat threadHbServer = null, threadHbClients = null;
    ThAnswerCommand threadCommand = null;
    ThManageRegs threadManageRegs = null;
    ArrayList<Registries> registries = null;
    ServerController SController=null;



    DirectoryServer(){
        SController = new ServerController(this);
        registries=new ArrayList<>();
        createSockets();
        createThreads();
    }

    public void createSockets(){
        //Creating Sockets
        try {
            //TODO PASSAR ESTES PORTOS COMO ARGUMENTO
            socketServers = new DatagramSocket(6001);
            socketClientsHB = new DatagramSocket(6002);
            socketClientsCommand = new DatagramSocket(6003);

        } catch (SocketException e) {
            System.out.println("Error Creating Sockets");
        }
    }

    public void createThreads(){
        //Threads Heartbeat
        threadHbServer = new ThAnswerHeartBeat(socketServers,registries);  //Create Thread To receive HB from Servers
        threadHbClients = new ThAnswerHeartBeat(socketClientsHB,registries); //Create Thread To receive HB from Clients
        threadCommand = new ThAnswerCommand(socketClientsCommand, this); // Create Thread to Receive and Answer Commands from Clients
        threadManageRegs = new ThManageRegs(registries);

        //Start Threads
        threadHbServer.start();
        threadHbClients.start();
        threadCommand.start();
        threadManageRegs.start();
    }
    public String getListServ(){return SController.getListServ();}
    public static void main(String[] args) {
        DirectoryServer myServer=null;
        myServer=new DirectoryServer();
    }
}





