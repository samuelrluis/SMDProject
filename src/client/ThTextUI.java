package client;

import common.UserID;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by diogomiguel on 25/11/16.
 */
public class ThTextUI extends Thread {
    public static final int MAX_SIZE = 256;
    Client myClient;
    UserID myUserID;
    DatagramPacket packetToDir = null , packetReadDir = null;

    ThTextUI(Client x){
        myClient=x;
        packetReadDir = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
    }

    @Override
    public void run() {
        DatagramSocket socketToDir=null;

        String commandStr;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        myUserID=myClient.getMyUserID();

        socketToDir=myClient.getSocketToDir();

        while(true){
            ArrayList<String> argCommand = new ArrayList<>();
            int x=0;
            System.out.println("Command: ");
            System.out.flush();
            try {
                commandStr = br.readLine();

            StringTokenizer tok = new StringTokenizer(commandStr," ");

            while (tok.hasMoreTokens()){
                String token = tok.nextToken();
                argCommand.add(token);
            }
                if(argCommand.get(0).equalsIgnoreCase("EXIT"))
                    break;
                else if(argCommand.get(0).equalsIgnoreCase("HELP")) {
                    System.out.println("Manual");
                    break;
                }else if(argCommand.get(0).equalsIgnoreCase("USER")){//teste
                    System.out.println(myClient.getMyUserID().toString());
                    break;
                }else if(argCommand.get(0).equalsIgnoreCase("LOGIN")){
                    break;

                }else if(argCommand.get(0).equalsIgnoreCase("REGISTER")){
<<<<<<< HEAD
                    myUserID.setUsername(argCommand.get(1));
                    myUserID.setPassword(argCommand.get(2));
=======
                    myUserID.setUsername(argCommand.get(1));// test
                    myUserID.setPassword(argCommand.get(2));// test
                    break;
>>>>>>> origin/master


                }else if(argCommand.get(0).equalsIgnoreCase("SLIST")) {
                    System.out.println(myClient.getServerAddr().toString() + " " + myClient.getServerDirCommandPort());
                    packetToDir=new DatagramPacket("SLIST".getBytes(),"SLIST".length(),myClient.getServerAddr(), myClient.getServerPortCommand()); //Create a Packet
                    socketToDir.send(packetToDir);
                    socketToDir.receive(packetReadDir);
                    System.out.println("recebi");
                    String answer = new String(packetReadDir.getData());
                    System.out.println(answer);
                    break;
                }




            } catch (IOException e) {
                System.out.printf("Não foi encontrado o ficheiro do Manual");
            }
        }
    }

    public static Object readObjectFromFile(String filename) {
        Object object = null;

        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
            ObjectInput objectInput = new ObjectInputStream(inputStream);
            object = objectInput.readObject();
            objectInput.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return object;
    }
}
