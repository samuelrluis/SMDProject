package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */

import common.CliRegistry;
import common.ServerRegistry;

import java.net.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

 public class DirectoryServer {

    private DatagramSocket socketServers = null,socketClientsHB = null,socketClientsCommand = null;
    private ThAnswerHeartBeat threadHbServer = null, threadHbClients = null;
    private ThAnswerCommand threadCommand = null;
    private ThManageServerRegs threadManageRegs = null;
    private ArrayList<CliRegistry> cliRegistries = null;
    private ArrayList<ServerRegistry> serverRegistries = null;
    private ServerController Scontroller = null;

    DirectoryServer(){
        Scontroller=new ServerController(this);
        cliRegistries=new ArrayList<>();
        serverRegistries=new ArrayList<>();
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
        threadHbServer = new ThAnswerHeartBeat(socketServers,cliRegistries,serverRegistries);  //Create Thread To receive HB from Servers
        threadHbClients = new ThAnswerHeartBeat(socketClientsHB,cliRegistries,serverRegistries); //Create Thread To receive HB from Clients
        threadCommand = new ThAnswerCommand(socketClientsCommand, this); // Create Thread to Receive and Answer Commands from Clients
        threadManageRegs = new ThManageServerRegs(serverRegistries);

        //Start Threads
        threadHbServer.start();
        threadHbClients.start();
        threadCommand.start();
        threadManageRegs.start();
    }

/*     //TODO passar este metodo para a class DirServer
     public String getListServ(){
         int x=0;
         StringBuilder List = new StringBuilder();
         for(int i = 0;i<registries.size();i++) {
             List.append(i+":"+registries.get(i).getName()+"\n");
             }
         if (List==null)
             return "No Server's Connected";
         return List.toString();
     }*/

    public static void main(String[] args) {
        DirectoryServer myServer=null;
        myServer=new DirectoryServer();
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





