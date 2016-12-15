package client;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import static client.ThTextUI.MAX_SIZE;

/**
 * Created by Samuel on 13/12/2016.
 */
public class Controller {
    Client myClient;

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
        String command=null;
        socketToDir=myClient.getSocketToDir();

        if(argCommand.get(0).equalsIgnoreCase("SLIST"))
            command = new String("SLIST");
        else if(argCommand.get(0).equalsIgnoreCase("REGISTER"))
            command = new String("REGISTER" + " " + argCommand.get(1) + " " +argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("LOGIN"))
            command = new String("LOGIN" + " " + argCommand.get(1) + " " +argCommand.get(2));

        packetToDir = new DatagramPacket(command.getBytes(),command.length(),myClient.getServerAddr(), myClient.getServerPortCommand());

        try {
            socketToDir.send(packetToDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void regClient(String name, String pass){
        myClient.myUserID.setName(name);
        myClient.myUserID.setPassword(pass);
        myClient.setRegistedFlagTrue();
        System.out.println("reg client name: " + name + myClient.myUserID.getName());
        myClient.startThreadHB();   //The HeartBeat Thread will start only when the userID is prepared
        return;
    }

    public void loginClient(String name,String pass){
        myClient.myUserID.setName(name);
        myClient.myUserID.setPassword(pass);
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
