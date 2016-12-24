package client;

import common.heartbeat.ClientHeartBeat;
import common.Msg;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static client.threads.ThTextUI.MAX_SIZE;

/**
 * Created by Samuel on 13/12/2016.
 */
public class Controller {
    private Client myClient;

    Controller(Client x){
        myClient=x;
    }

    public String receiveAnswerPacket(){
        DatagramSocket socketToDir;
        DatagramPacket packetReadDir;
        packetReadDir = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
        socketToDir=myClient.getSocketToDir();

        try {
            socketToDir.receive(packetReadDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String answer = new String(packetReadDir.getData(),0,packetReadDir.getLength());
        return answer;
    }

    public void sendPacketToDirServer(ArrayList<String> argCommand){
        DatagramSocket socketToDir;
        DatagramPacket packetToDir;
        ByteArrayOutputStream b0ut;
        ObjectOutputStream out;
        String command=null;
        socketToDir=myClient.getSocketToDir();

        if(argCommand.get(0).equalsIgnoreCase("SLIST"))
            command = new String("SLIST");
        else if(argCommand.get(0).equalsIgnoreCase("CLIST"))
            command = new String("CLIST");
        else if(argCommand.get(0).equalsIgnoreCase("REGISTER"))
            command = new String("REGISTER" + " " + argCommand.get(1) + " " +argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("LOGIN"))
            command = new String("LOGIN" + " " + argCommand.get(1) + " " +argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("CONNECT"))
            command = new String ("CONNECT" + " " + argCommand.get(1) + " " + argCommand.get(2));

        try {
            //Create a Serializable Message with the command to send to DirServer
            Msg msg = new Msg(command,myClient.getMyUserID().gethBeat()); //Create Serializable Msg
            b0ut = new ByteArrayOutputStream();
            out = new ObjectOutputStream(b0ut);
            out.writeObject(msg);
            out.flush();
            packetToDir = new DatagramPacket(b0ut.toByteArray(),b0ut.size(),myClient.getServerAddr(), myClient.getServerPortCommand());
            socketToDir.send(packetToDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void regClient(String name, String pass){
        myClient.getMyUserID().sethBeat(new ClientHeartBeat(name+pass,myClient.getServerPortHB()));
        myClient.setRegistedFlagTrue();
        myClient.startThreadHB();   //The HeartBeat Thread will start only when the userID is prepared
        return;
    }

    public void comandToRem(String ServerName){
        String commandStr;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do{
            ArrayList<String> argCommand = new ArrayList<>();
            int x=0;
            System.out.print(ServerName +" "+ "$> ");
            System.out.flush();

            try {
                commandStr = br.readLine();
                StringTokenizer tok = new StringTokenizer(commandStr," ");

                while (tok.hasMoreTokens()){
                    String token = tok.nextToken();
                    argCommand.add(token);
                }

                try{
                    if(argCommand.get(0).equalsIgnoreCase("EXIT")){
                        break;
                    }
                    else{
                        System.out.println("Command not found");
                        continue;
                    }
                }catch (IndexOutOfBoundsException e){}
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }while (true);
    }


    public boolean connectToRemServer(String wantedPort){
        Socket socketToRemServer=null;
        try {
            int serverPort = Integer.parseInt(wantedPort);
            InetSocketAddress serverAddr = new InetSocketAddress("127.0.0.1", serverPort);


            if (serverPort != 0) {
                try {
                    socketToRemServer = new Socket("127.0.0.1", serverPort);
                    //myClient.getSocketTCP().bind((serverAddr));
                    Msg msg = new Msg("Just Connect to this Server", myClient.getMyUserID().gethBeat()); //Create Serializable Msg

                    ObjectOutputStream objectOutput = new ObjectOutputStream(socketToRemServer.getOutputStream());
                    objectOutput.writeObject(msg);
                    objectOutput.flush();
                    System.out.println("Enviou msg TCP");

                } catch (IOException e) {
                    return false;
                }
                return true;
            } else
                return false;
        }catch (Exception e){return false;}
    }

    public void loginClient(String name,String pass){
        myClient.getMyUserID().sethBeat(new ClientHeartBeat(name+pass,myClient.getServerPortHB()));
        myClient.getMyUserID().setNameAndPassword(name+pass);
        myClient.setRegistedFlagTrue();
        return;
    }

    public String readObjectFromFile(String file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return everything;
        } finally {
            br.close();
        }
    }
}
