package client;

import client.threads.ThReaderUDP;
import client.threads.ThSendHeartBeat;
import client.threads.*;
import common.registry.ClientRegistry;
import dirserver.RemoteServices;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Samuel on 30/10/2016.
 */
public class Client {

    //Client
    private ClientController myClientController = null;
    private ThTextUI threadUI;
    private ThReaderUDP threadUDPReader = null;
    private ThSendHeartBeat threadHeartBeat;

    //RMIasd
    RemoteServices remoteInterface = null;

    //Common
    private ClientRegistry myUserID = null;
    private Socket socketTCP = null;
    private InetAddress serverAddr = null;
    private DatagramSocket socketDirServer = null, socketRemServer = null;
    private int serverPortHB = -1 ,serverPortCommand = -1;
    private boolean registedFlag = false;
    private boolean loginFlag = false;

    Client(InetAddress serverAddress, Integer serverPort, Integer serverPortCommand){

        myUserID=new ClientRegistry();
        this.serverAddr=serverAddress;
        this.serverPortHB=serverPort;
        this.serverPortCommand = serverPortCommand;
        setUpRMIService();
        myClientController =new ClientController(this);

        try {
            socketDirServer = new DatagramSocket();
            socketRemServer = new DatagramSocket();
            socketTCP=new Socket();

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void setUpRMIService(){
        String registry = "localhost";
        String registrartion = "rmi://"+registry+"/RemoteServices";

        Remote remoteService = null;
        try {
            remoteService = Naming.lookup(registrartion);
            remoteInterface = (RemoteServices) remoteService;
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Client Threads Create

    public void createThreads(){
        threadUI = new ThTextUI(this);
        threadUDPReader=new ThReaderUDP();//Thread that will be reading all the received data from DirServer
    }

    // Client Threads Start

    public void startThreads(){
        threadUI.start();
        threadUDPReader.start();
    }

    public void startThreadHB(){
        threadHeartBeat=new ThSendHeartBeat(serverAddr,serverPortHB,this);
        threadHeartBeat.start();
    }

    // Client Setters

    public void setRegistedFlagTrue(){
        this.registedFlag=true;
        return;
    }

    public void setloginFlagTrue(){
        this.loginFlag=true;
        return;
    }

    public void setloginFlagFalse(){
        this.loginFlag=false;
        return;
    }

    // Client Getters

    public RemoteServices getRemoteInterface() {
        return remoteInterface;
    }

    public ClientController getController() {
        return myClientController;
    }

    public ClientRegistry getMyUserID() {
        return myUserID;
    }

    public DatagramSocket getSocketDirServer() {
        return socketDirServer;
    }

    public DatagramSocket getSocketRemServer(){
        return socketRemServer;
    }

    public Socket getSocketTCP() {
        return socketTCP;
    }

    public InetAddress getServerAddr() {
        return serverAddr;
    }

    public int getServerDirCommandPort() {
        return serverPortHB;
    }

    public int getServerPortCommand(){
        return serverPortCommand;
    }

    public int getServerPortHB() {
        return serverPortHB;
    }

    public boolean getRegistedFlag(){
        return this.registedFlag;
    }

    public boolean getLoginFlag(){
        return this.loginFlag;
    }

    public static void main(String args[]){

        Client thisClient;

        InetAddress serverAddr=null;
        int serverPortHB = -1 , serverPortCommand=-1;

        try {

            if(args.length!=3){
                System.out.println("Sintax Error [DIRIP][UDP_PORT_FOR HB][UDP_PORT_FOR_COMMAND]");
                System.exit(0);
            }

            serverAddr = InetAddress.getByName(args[0]);    //Get the IP Server
            serverPortHB = Integer.parseInt(args[1]);       //Get the Directory Server Port for HB
            serverPortCommand = Integer.parseInt(args[2]);  //Get the Directory Server Port for Commands

            thisClient = new Client(serverAddr, serverPortHB,serverPortCommand);
            thisClient.createThreads();
            thisClient.startThreads();

        }catch (IOException e){
            System.out.println("Erro na recepção de datagrama");
        }
    }
}
