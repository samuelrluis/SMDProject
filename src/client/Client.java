package client;

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

    public static void main(String args[]){
        ThSendHeartBeat threadHeartBeat;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String commandStr;
        String name ="Samuel";
        TextUI commandRequest;
        //TextUI textUI = new TextUI(args);

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

            //socketToServer=new Socket(serverAddr,thread.getPort());


            while(true){
                ArrayList<String> argCommand = new ArrayList<>();
                int x=0;
                System.out.println("Command: ");
                System.out.flush();
                commandStr = br.readLine();
                StringTokenizer tok = new StringTokenizer(commandStr," ");

                while (tok.hasMoreTokens()){
                    String token = tok.nextToken();
                    argCommand.add(token);
                    System.out.println(token); // teste

                }
                if(argCommand.get(0).toUpperCase().equals("EXIT"))
                    break;
                else {
                    //commandRequest = new TextUI(argCommand[]); // SAM E SO RESOLVER ESTAS DUAS LINHAS  ....
                    //commandRequest.showTheAnswer();
                }


            //if(thread.isPortAvailable()==true)
                //break;

            }


            in = socketToServer.getInputStream();
            pout = new PrintWriter(socketToServer.getOutputStream(), true);
            pout.println("primeira ligacao TCP");
            pout.flush();
            System.out.println("mandei msg");
/*          socket.receive(packetRead);
            String msgRecebida = new String(packetRead.getData(), 0, packetRead.getLength());
            System.out.println(msgRecebida);*/
        }catch (SocketException e){
            System.out.println("Erro na criação do socket ");
        }catch (IOException e){
            System.out.println("Erro na recepção de datagrama");
        }
    }
}
