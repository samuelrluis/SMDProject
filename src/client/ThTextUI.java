package client;

import common.UserID;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
            System.out.print("> ");
            System.out.flush();
            try {
                commandStr = br.readLine();
            StringTokenizer tok = new StringTokenizer(commandStr," ");

            while (tok.hasMoreTokens()){
                String token = tok.nextToken();
                argCommand.add(token);
            }
                if(argCommand.get(0).equalsIgnoreCase("EXIT"))
<<<<<<< HEAD
                    System.exit(0);
                else if(argCommand.get(0).equalsIgnoreCase("MAN")) {
                    System.out.println("Manual");
                    fileReader();
                }else if(argCommand.get(0).equalsIgnoreCase("USER")){//teste
                    System.out.println(myClient.getMyUserID().toString());
                }else if(argCommand.get(0).equalsIgnoreCase("LOGIN")){
                    if (argCommand.size()!=3) {
                        System.out.println("Please enter your USERNAME and PASSWORD as:");
                        System.out.println("> login username password");
                    }


                }else if(argCommand.get(0).equalsIgnoreCase("REGISTER")){

                    myUserID.setUsername(argCommand.get(1));// test
                    myUserID.setPassword(argCommand.get(2));// test


=======
                    continue;

                else if(argCommand.get(0).equalsIgnoreCase("HELP")) {
                    try {
                        System.out.println(readObjectFromFile("../SMDProject/src/client/manual.txt"));
                    }catch (Exception e){
                        System.out.println("File not found");
                    }
                    continue;

                }else if(argCommand.get(0).equalsIgnoreCase("USER")){  //teste
                    System.out.println(myClient.getMyUserID().toString());
                    continue;
>>>>>>> master

                }else if(argCommand.get(0).equalsIgnoreCase("LOGIN")){
                    continue;

                }else if(argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                    if (myClient.getRegistedFlag() == false) {
                        try {
                            myUserID.setUsername(argCommand.get(1));
                            myUserID.setPassword(argCommand.get(2));

                        } catch (Exception e) {
                            System.out.println("You need to define Username and Password");
                            continue;
                        }
                        packetToDir = new DatagramPacket(("REGISTER" +" "+ argCommand.get(1)+" "+ argCommand.get(2)).getBytes(),("REGISTER" +" "+ argCommand.get(1)+" "+ argCommand.get(2)).length(),myClient.getServerAddr(), myClient.getServerPortCommand());
                        myClient.setRegistedFlagTrue();
                        socketToDir.send(packetToDir);
                        socketToDir.receive(packetReadDir);
                        String answer = new String(packetReadDir.getData());
                        System.out.println(answer);
                        continue;
                    }
                    else {
                        System.out.println("You are already registered");
                        continue;
                    }

                }else if(argCommand.get(0).equalsIgnoreCase("SLIST")) {
                    /*
                    System.out.println(myClient.getServerAddr().toString() + " " + myClient.getServerDirCommandPort());
                    packetToDir=new DatagramPacket("SLIST".getBytes(),"SLIST".length(),myClient.getServerAddr(), myClient.getServerPortCommand()); //Create a Packet
                    socketToDir.send(packetToDir);
                    socketToDir.receive(packetReadDir);
                    System.out.println("recebi");
                    String answer = new String(packetReadDir.getData());
                    System.out.println(answer);
                    */
                    continue;
                }
                else{
                    System.out.println("Command not found");
                    continue;
                }

            } catch (IOException e) {
                System.out.printf("File not found");
            }
        }

<<<<<<< HEAD
    public static void fileReader() {

        Path wiki_path = Paths.get("/Users/diogomiguel/GitHub/SMDProject/src/client/", "manual.txt");

        Charset charset = Charset.forName("ISO-8859-1");
        try {
            List<String> lines = Files.readAllLines(wiki_path, charset);

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

=======

    }

    private String readObjectFromFile(String file) throws Exception {
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
>>>>>>> master
    }

}
