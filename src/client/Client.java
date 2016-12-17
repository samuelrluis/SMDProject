package client;

import common.CliRegistry;
import common.ClientHeartBeat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Samuel on 30/10/2016.
 */
public class Client {
    //threads
    private ThSendHeartBeat threadHeartBeat;
    private ThTextUI threadUI;
    private ThReaderUDP threadUDPReader=null;
    private DatagramSocket socketToDir=null;
    private Socket socketTCP=null;
    private InetAddress serverAddr=null;
    private int serverPortHB=-1,serverPortCommand=-1;
    private CliRegistry myUserID=null;
    private Controller myController=null;
    private boolean registedFlag = false;
    private boolean loginFlag = false;

    public int getServerPortHB() {
        return serverPortHB;
    }

    Client(InetAddress serverAddress, Integer serverPort, Integer serverPortCommand){
        myUserID=new CliRegistry();
        this.serverAddr=serverAddress;
        this.serverPortHB=serverPort;
        this.serverPortCommand = serverPortCommand;
        myController=new Controller(this);

        try {
            socketToDir=new DatagramSocket();
            socketTCP=new Socket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
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

    public Controller getController() {
        return myController;
    }

    public CliRegistry getMyUserID() {
        return myUserID;
    }

    public DatagramSocket getSocketToDir() {
        return socketToDir;
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
