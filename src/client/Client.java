package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Samuel on 30/10/2016.
 */
public class Client {

    public static void main(String args[]){
        ThSendHeartBeat threadHeartBeat;
        String name ="Samuel";
        TextUI textUI = new TextUI(args);

        //TCP
        Socket socketToServer = null;
        PrintWriter pout;
        InputStream in;
        //UDP
        ThReaderUDP thread;
        DatagramSocket socket;

        int serverPortHB = -1;
        int serverPortUDP = -1;
        InetAddress serverAddr;

    try{
        serverAddr = InetAddress.getByName(args[0]);    //Get the IP Server;
        serverPortHB = Integer.parseInt(args[1]);       //Get the Directory Server Port for HeartBeats;
        serverPortUDP = Integer.parseInt(args[2]);      //Get the Directory Server Port to receive data;
        socket = new DatagramSocket();                  //Create the Client Socket that will read data from DirServer;

        //Creating the Packets
        thread=new ThReaderUDP(socket);        //Thread that will be reading all the received data from DirServer
        thread.start();
        //HeartBeat for DirectoryServer
        threadHeartBeat=new ThSendHeartBeat(serverAddr,serverPortHB,socket.getPort(),name);
        threadHeartBeat.start();

        //socketToServer=new Socket(serverAddr,thread.getPort());

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
