package remoteserver.threads;


import common.Msg;
import remoteserver.RemoteServer;
import remoteserver.RemoteServerController;
import sun.plugin2.message.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;

/**
 * Created by Samuel on 03/11/2016.
 */
public class ThAnswerClient extends Thread {

    public static final int TIMEOUT = 5;
    public static final int MAX_SIZE = 4000;

    //Common
    private RemoteServer myServer = null;
    private Msg msg;
    private ServerSocket socketTCP;
    private Socket socketToClient;
    private RemoteServerController myController = null;

    public ThAnswerClient(RemoteServer server){
        this.myServer = server;
        this.socketTCP = myServer.getServerSocketTcp();
        this.socketToClient = myServer.getSocketToClient();
        this.myController = myServer.getRemoteServerController();
    }

    @Override
    public void run() {
        RemoteServerController controller;
        ObjectInputStream in;
        ObjectOutputStream out;
        controller = new RemoteServerController();
        Msg msgAnswer;
        try{
            System.out.println("My port is: " + socketTCP.getLocalPort());
            while(true){
                //Read Message from CLient
                //System.out.println("Ja respondi e ja estou a espera");
                in = new ObjectInputStream(socketToClient.getInputStream());
                msg = (Msg) in.readObject();
                System.out.println("recebi mensagem de " + msg.gethBeat().getName());
                //msgAnswer = new Msg()
                out = new ObjectOutputStream(socketToClient.getOutputStream());
                System.out.println(msg.getCommand());
                out.writeObject(msg);
                out.flush();
                //test
                //controller.receivedCommand(msg);
            }
        } catch(IOException e){
            System.out.println("Ocorreu a excepcao de E/S: \n\t" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            System.out.printf("fechei o socket");

            socketToClient.close();
        } catch (IOException e) {}
    }
}