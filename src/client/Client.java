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
        ThReaderUDP thread=null;
        DatagramSocket socket=null;

        int serverPort = -1;
        InetAddress serverAddr = null;




    try{
        serverAddr = InetAddress.getByName(args[0]);    //Get the IP Server
        serverPort = Integer.parseInt(args[1]);         //Get the Directory Server Port
        socket = new DatagramSocket();                  //Create the Client Socket

        //Creating the Packets
        thread=new ThReaderUDP(socket);        //Thread that will be reading all the received data from DirServer
        thread.start();
        //HeartBeat for DirectoryServer
        threadHeartBeat=new ThSendHeartBeat(serverAddr,serverPort,socket.getPort(),name);
        threadHeartBeat.start();

        socketToServer=new Socket(serverAddr,thread.getPort());















        while(true){
            System.out.print("");
            if(thread.isPortAvailable()==true)
                break;
        }


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
