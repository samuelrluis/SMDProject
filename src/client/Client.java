package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Samuel on 30/10/2016.
 */
public class Client {
    public static final int MAX_SIZE = 256;
    public static void main(String args[]){
        ThreadHeartBeat threadHeartBeat;
        String name ="Samuel";

        //TCP
        Socket socketToServer = null;
        PrintWriter pout;
        InputStream in;
        //UDP
        ThreadReaderUDP thread=null;
        DatagramSocket socket=null;
        DatagramPacket packetWrite=null;
        DatagramPacket packetRead=null;

        String msg;
        int serverPort = -1;
        InetAddress serverAddr = null;

    try{
        serverAddr = InetAddress.getByName(args[0]);    //Get the IP Server
        serverPort = Integer.parseInt(args[1]);         //Get the Directory Server Port
        socket = new DatagramSocket();                  //Create the Client Socket

        //HeartBeat for DirectoryServer
        threadHeartBeat=new ThreadHeartBeat(serverAddr,serverPort,name,socket.getPort());
        threadHeartBeat.start();

        //Creating the Packets
        packetRead = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);      //Creating the packet that will be received from DirServerv
        thread=new ThreadReaderUDP(packetRead,socket);        //Thread that will be reading all the received data from DirServer
        thread.start();

        while(true){
            System.out.print("");
            if(thread.isPortAvailable()==true)
                break;
        }

        socketToServer=new Socket(serverAddr,thread.getPort());
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
