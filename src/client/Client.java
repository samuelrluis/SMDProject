package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.*;

/**
 * Created by Samuel on 30/10/2016.
 */
public class Client {
    public static final int MAX_SIZE = 256;
    public static void main(String args[]){
        int createdTCP;
        System.out.println("Ola mundo3");
        Socket socketToServer = null;
        PrintWriter pout;
        InputStream in;

        ReaderUDP thread=null;
        DatagramSocket socket=null;
        DatagramPacket packetWrite=null;
        DatagramPacket packetRead=null;
        String msg;
        int serverPort = -1;
        InetAddress serverAddr = null;

    try{
        serverAddr = InetAddress.getByName(args[0]);
        serverPort = Integer.parseInt(args[1]);
        socket = new DatagramSocket(6003);

        packetRead= new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
        packetWrite = new DatagramPacket("MensagemCliente".getBytes(), "MensagemCliente".length(), serverAddr, serverPort);
        socket.send(packetWrite);

        thread=new ReaderUDP(packetRead,socket);
        thread.start();

        System.out.println("main");

        while(true){
            System.out.print("");
            if(thread.isPortAvailable()==true)
                break;
        }

        System.out.println("sai do if");
        socketToServer=new Socket("127.0.0.7",thread.getPort());
        in = socketToServer.getInputStream();
        pout = new PrintWriter(socketToServer.getOutputStream(), true);
        pout.println("primeira ligacao TCP");
        pout.flush();

        System.out.println("mandei msg");


/*        socket.receive(packetRead);
        String msgRecebida = new String(packetRead.getData(), 0, packetRead.getLength());
        System.out.println(msgRecebida);*/

    }catch (SocketException e){
        System.out.println("Erro na criação do socket ");
    }catch (IOException e){
        System.out.println("Erro na recepção de datagrama");
    }


    }
}
