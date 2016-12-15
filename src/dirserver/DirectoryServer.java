package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */

import common.CliRegistry;
import common.ServerRegistry;
import java.net.*;
import java.util.ArrayList;


 public class DirectoryServer {
    public static final int MAX_SIZE = 256;
    private DatagramSocket socketUDP=null;
    private ArrayList<CliRegistry> cliRegistries = null;
    private ArrayList<ServerRegistry> serverRegistries = null;
    private ServerController Scontroller = null;
    private DatagramPacket packet = null;

     public DirectoryServer(){
        createSocket();
        createPacket();
        Scontroller=new ServerController(this);
        cliRegistries=new ArrayList<>();
        serverRegistries=new ArrayList<>();

    }

    public void createSocket(){
        //Creating SocketUDP
        try {
            socketUDP = new DatagramSocket(6001);
        } catch (SocketException e) {
            System.out.println("Error Creating Sockets");
        }
    }

    public void createPacket(){
        packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
    }

     public DatagramSocket getSocketUDP() {
         return socketUDP;
     }

     public DatagramPacket getPacket() {
         return packet;
     }


// public void createThreads(){
        //Threads Heartbeat

        /*threadHbServer = new ThAnswerHeartBeat(socketServers,cliRegistries,serverRegistries);  //Create Thread To receive HB from Servers
        threadHbClients = new ThAnswerHeartBeat(socketClientsHB,cliRegistries,serverRegistries); //Create Thread To receive HB from Clients
        threadCommand = new ThAnswerCommand(socketClientsCommand, this); // Create Thread to Receive and Answer Commands from Clients
        threadManageServRegs = new ThManageServerRegs(serverRegistries); // Create Thread that will Manage the Server Registry
        threadManageCliRegs = new ThManageClientRegs(cliRegistries); //Create Thread that will Manage the Client Registry*/

/*        //Start Threads
        threadHbServer.start();
        threadHbClients.start();
        threadCommand.start();
        threadManageServRegs.start();
        threadManageCliRegs.start();
    }
*/;

    public void runDirServeR(){
        Scontroller.answeringDatagram();
    }

    public static void main(String args[]) {
        DirectoryServer myServer=null;
        myServer=new DirectoryServer();

        myServer.runDirServeR();
    }

     public ArrayList<ServerRegistry> getServerRegistries() {
         return serverRegistries;
     }

     public ArrayList<CliRegistry> getCliRegistries() {
         return cliRegistries;
     }

     public String getListServ(){
        return Scontroller.getListServ();
    }

     public String getListClient(){
         return Scontroller.getListClients();
     }
}





