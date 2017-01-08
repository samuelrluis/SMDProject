package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */

import common.registry.ClientRegistry;
import common.registry.ServerRegistry;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteRef;
import java.util.ArrayList;


 public class DirectoryServer {

     //DirServer
     private DirectoryServerController Scontroller = null;
     private DirectoryServerRMI RemoteServicesInstance = null;

     //Common
     private ArrayList<ClientRegistry> cliRegistries = null;
     private ArrayList<ServerRegistry> serverRegistries = null;

     public static final int MAX_SIZE = 1024;
     private DatagramSocket socketUDP=null;
     private DatagramPacket packet = null;

     private ThManageRegs manageRegsThread = null; //Thread that will manage the server registries

     public DirectoryServer(){
         createSocket();
         createPacket();
         Scontroller=new DirectoryServerController(this);
         cliRegistries=new ArrayList<>();
         serverRegistries=new ArrayList<>();

         manageRegsThread = new ThManageRegs(serverRegistries,Scontroller);
         manageRegsThread.start();
     }

     private void setRMIService(String serverAddr){
         String registry = null;

         try {
             LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
             RemoteServicesInstance = new DirectoryServerRMI(Scontroller);
         } catch (RemoteException e) {
             e.printStackTrace();
         }

         RemoteRef location = RemoteServicesInstance.getRef();
         System.out.println(location.remoteToString());

         if(serverAddr!=null)
             registry = serverAddr;
         else
             registry = "localhost";

         String registration = "rmi://" + registry + "/RemoteServices";

         try {
             Naming.rebind(registration,RemoteServicesInstance);
         } catch (RemoteException e) {
             e.printStackTrace();
         } catch (MalformedURLException e) {
             System.out.println("No service Found");
         }
     }

     public void createSocket(){
         //Creating SocketUDP
         try {
             socketUDP = new DatagramSocket(6001);
         } catch (SocketException e) {
             System.out.println("Error Creating Sockets");
         }
     }

     private void createPacket(){
         packet = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
     }

     public DatagramSocket getSocketUDP() {
         return socketUDP;
     }

     public DatagramPacket getPacket() {
         return packet;
     }

     private void runDirServer(){
         Scontroller.setRMIService(RemoteServicesInstance);
         Scontroller.answeringDatagram();
     }

     public static void main(String args[]) {
         DirectoryServer myServer=null;
         myServer=new DirectoryServer();
         myServer.setRMIService(args[0]);
         myServer.runDirServer();
     }

     public ArrayList<ServerRegistry> getServerRegistries() {
         return serverRegistries;
     }

     public ArrayList<ClientRegistry> getCliRegistries() {
         return cliRegistries;
     }

     public String getListServ(){
         return Scontroller.getListServ();
     }

     public String getListClient(){
         return Scontroller.getListClients();
     }
 }







