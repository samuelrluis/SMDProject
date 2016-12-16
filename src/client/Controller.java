package client;

import common.ClientHeartBeat;
import common.Msg;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import static client.ThTextUI.MAX_SIZE;

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

    public void sendPacket(ArrayList<String> argCommand){
        DatagramSocket socketToDir;
        DatagramPacket packetToDir;
        ByteArrayOutputStream b0ut;
        ObjectOutputStream out;
        String command=null;
        socketToDir=myClient.getSocketToDir();

        if(argCommand.get(0).equalsIgnoreCase("SLIST"))
            command = new String("SLIST");
        else if(argCommand.get(0).equalsIgnoreCase("REGISTER"))
            command = new String("REGISTER" + " " + argCommand.get(1) + " " +argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("LOGIN"))
            command = new String("LOGIN" + " " + argCommand.get(1) + " " +argCommand.get(2));

        //TODO Create a message serializable here
        try {
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
        myClient.getMyUserID().sethBeat(new ClientHeartBeat(name,pass,myClient.getServerPortHB()));
        myClient.setRegistedFlagTrue();
        myClient.startThreadHB();   //The HeartBeat Thread will start only when the userID is prepared
        return;
    }

    public void loginClient(String name,String pass){
        myClient.getMyUserID().setName(name);
        myClient.getMyUserID().setPassword(pass);
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
