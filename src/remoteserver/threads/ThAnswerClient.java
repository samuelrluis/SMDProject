package remoteserver.threads;


import common.Msg;
import remoteserver.RemoteServerController;
import sun.plugin2.message.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Samuel on 03/11/2016.
 */
public class ThAnswerClient extends Thread {

    public static final int TIMEOUT = 5;
    public static final int MAX_SIZE = 4000;

    //Common
    private Msg msg;

    private ServerSocket socketTCP;
    private Socket socketToClient;


    public ThAnswerClient(ServerSocket socket,Socket socketToClient){
        this.socketTCP=socket;
        this.socketToClient=socketToClient;
    }

    @Override
    public void run() {
        RemoteServerController controller;
        ObjectInputStream in;
        ObjectOutputStream out;

        byte []fileChunck = new byte[MAX_SIZE];

        try{
            socketTCP.setSoTimeout(1000*TIMEOUT);
            socketToClient.setSoTimeout(1000*TIMEOUT);

            System.out.println("My port is: " + socketTCP.getLocalPort());

            //Read Message from CLient
            in = new ObjectInputStream(socketToClient.getInputStream());
            msg = (Msg) in.readObject();
            System.out.println("recebi mensagem de " + msg.gethBeat().getName());

            controller = new RemoteServerController();
            out = new ObjectOutputStream(socketToClient.getOutputStream());
            out.writeObject(msg);
            out.flush();


            //test
            System.out.println(msg.getCommand().toString());
            controller.receivedCommand(msg);
            //out.writeObject("YUIPYUI");
            //out.flush();






        } catch(IOException e){
            System.out.println("Ocorreu a excepcao de E/S: \n\t" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            socketToClient.close();
        } catch (IOException e) {}
    }
}