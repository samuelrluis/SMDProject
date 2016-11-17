package remoteserver;

import java.io.*;
import java.net.Socket;

/**
 * Created by Samuel on 03/11/2016.
 */
public class ThreadAnswerClient extends Thread {
    public static final int TIMEOUT = 5;
    public static final int MAX_SIZE = 4000;
    Socket socketToClient;
    String msgClient;

    ThreadAnswerClient(Socket socketClient){
        socketToClient=socketClient;
    }

    @Override
    public void run() {

        BufferedReader in;
        OutputStream out;
        byte []fileChunck = new byte[MAX_SIZE];

        try{
            socketToClient.setSoTimeout(1000*TIMEOUT);

            in = new BufferedReader(new InputStreamReader(socketToClient.getInputStream()));
            out = socketToClient.getOutputStream();

            msgClient = in.readLine();

            System.out.println("Recebido: " + msgClient);

            System.out.println("thread concluida");

        }catch(FileNotFoundException e){   //Subclasse de IOException
            System.out.println("Ocorreu a excepcao {" + e + "} ao receber a mensagem");
        }catch(IOException e){
            System.out.println("Ocorreu a excepcao de E/S: \n\t" + e);
        }

        try{
            socketToClient.close();
        } catch (IOException e) {}
    }
}