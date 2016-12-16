package remoteserver;

import common.Msg;
import sun.plugin2.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Samuel on 03/11/2016.
 */
public class ThAnswerClient extends Thread {
    public static final int TIMEOUT = 5;
    public static final int MAX_SIZE = 4000;
    ServerSocket socketTCP;
    Socket socketToClient;
    String msgClient;
    private Msg msg;

    ThAnswerClient(ServerSocket socket){
        socketTCP=socket;
    }

    @Override
    public void run() {

        ObjectInputStream in;
        OutputStream out;
        byte []fileChunck = new byte[MAX_SIZE];

        try{
            socketToClient = socketTCP.accept();
            socketToClient.setSoTimeout(1000*TIMEOUT);

            in = new ObjectInputStream(socketToClient.getInputStream());
           // in = new BufferedReader(new InputStreamReader(socketToClient.getInputStream()));
            //out = socketToClient.getOutputStream();

            msg = (Msg) in.readObject();

            System.out.println("recebi mensagem de" + msg.gethBeat().getName());

            System.out.println("Recebido: " + msgClient);
            System.out.println("thread concluida");

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