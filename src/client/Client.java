package client;

import client.threads.ThReaderUDP;
import client.threads.ThSendHeartBeat;
import client.threads.*;
import common.registry.ClientRegistry;

import java.io.*;
import java.net.*;


/**
 * Created by Samuel on 30/10/2016.
 */
public class Client {

    //Client
    private ClientController myClientController = null;
    private ThTextUI threadUI;
    private ThReaderUDP threadUDPReader = null;
    private ThSendHeartBeat threadHeartBeat;

    //Common
    private ClientRegistry myUserID = null;

    private DatagramSocket socketDirServer = null;
    private DatagramSocket socketRemServer = null;
    private Socket socketTCP = null;
    private InetAddress serverAddr = null;
    private int serverPortHB = -1 ,serverPortCommand = -1;
    private boolean registedFlag = false;
    private boolean loginFlag = false;

    Client(InetAddress serverAddress, Integer serverPort, Integer serverPortCommand){

        myUserID=new ClientRegistry();
        this.serverAddr=serverAddress;
        this.serverPortHB=serverPort;
        this.serverPortCommand = serverPortCommand;
        myClientController =new ClientController(this);

        try {
            socketDirServer = new DatagramSocket();
            socketRemServer = new DatagramSocket();
            socketTCP=new Socket();

        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public int getServerPortHB() {
        return serverPortHB;
    }

    public void createThreads(){
        threadUI = new ThTextUI(this);
        threadUDPReader=new ThReaderUDP();//Thread that will be reading all the received data from DirServer
    }

    public void startThreads(){
        threadUI.start();
        threadUDPReader.start();
    }

    public void startThreadHB(){
        threadHeartBeat=new ThSendHeartBeat(serverAddr,serverPortHB,this);
        threadHeartBeat.start();
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
