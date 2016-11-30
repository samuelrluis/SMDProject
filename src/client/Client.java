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
    Socket socket=null;
    InetAddress serverAddr=null;
    int serverPort=0;
    UserID myUserID=null;

    Client(InetAddress serverAddress, Integer serverPort){
        myUserID=new UserID();

        socket=new Socket();
        this.serverAddr=serverAddress;
        this.serverPort=serverPort;
    }

    public void createThreads(){
        threadUI = new ThTextUI(this);
        threadUDPReader=new ThReaderUDP();        //Thread that will be reading all the received data from DirServer
        threadHeartBeat=new ThSendHeartBeat(serverAddr,serverPort,socket.getPort(),"xpto");
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

    public static void main(String args[]){
        Client thisClient;
        InetAddress serverAddr=null;
        int serverPort = -1;

        try {
            serverAddr = InetAddress.getByName(args[0]);    //Get the IP Server
            serverPort = Integer.parseInt(args[1]);         //Get the Directory Server Port

            thisClient = new Client(serverAddr, serverPort);

            thisClient.createThreads();
            thisClient.startThreads();

        }catch (IOException e){
            System.out.println("Erro na recepção de datagrama");
        }
    }
}
