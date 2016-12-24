package dirserver; /**
 * Created by Samuel on 29/10/2016.
 */

import common.registry.CliRegistry;
import common.registry.ServerRegistry;
import java.net.*;
import java.util.ArrayList;


 public class DirectoryServer {

     //DirServer
     private ServerController Scontroller = null;

     //Common
     private ArrayList<CliRegistry> cliRegistries = null;
     private ArrayList<ServerRegistry> serverRegistries = null;

     public static final int MAX_SIZE = 1024;
     private DatagramSocket socketUDP=null;
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







