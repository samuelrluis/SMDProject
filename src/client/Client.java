package client;

import common.UserID;

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
    ThSendHeartBeat threadHeartBeat;
    ThTextUI threadUI;
    ThReaderUDP threadUDPReader=null;
    DatagramSocket socketToDir=null;
    Socket socketTCP=null;
    InetAddress serverAddr=null;
    int serverPortHB=-1,serverPortCommand=-1;
    UserID myUserID=null;


    Client(InetAddress serverAddress, Integer serverPort,Integer serverPortCommand){
        myUserID=new UserID();
        this.serverAddr=serverAddress;
        this.serverPortHB=serverPort;
        this.serverPortCommand = serverPortCommand;

        try {
            socketToDir=new DatagramSocket();
            socketTCP=new Socket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void createThreads(){
        threadUI = new ThTextUI(this);
        threadUDPReader=new ThReaderUDP();        //Thread that will be reading all the received data from DirServer
        threadHeartBeat=new ThSendHeartBeat(serverAddr,serverPortHB,socketTCP.getPort(),"xpto");
        //TODO THREAD HEARTBEAT SO E CRIADA E LANÇADA QUANDO O UTILIZADOR SE AUTENTICA
    }

    public void startThreads(){
        threadUI.start();
        threadUDPReader.start();
        threadHeartBeat.start();
    }

    public UserID getMyUserID() {
        return myUserID;
    }

    public DatagramSocket getSocketToDir() {
        return socketToDir;
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
