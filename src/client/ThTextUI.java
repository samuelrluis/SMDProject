package client;

import common.CliRegistry;


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
    public static final int MAX_SIZE = 1024;
    Client myClient;
    CliRegistry myUserID;
    Controller myController;

    ThTextUI(Client x){
        myClient=x;
        myController=x.getController();
        myUserID=myClient.getMyUserID();
    }

    @Override
    public void run() {
        String commandStr;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
                    continue;
                else if(argCommand.get(0).equalsIgnoreCase("HELP")) {
                    System.out.println(myController.readObjectFromFile("../SMDProject/src/client/manual.txt"));
                    continue;
                }else if(argCommand.get(0).equalsIgnoreCase("LOGIN")){
                    //TODO implementar login dentro do controlador da mesma forma poderando sempre o uso da classe controlador se necessário
                    continue;
                }else if(argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                    if (myClient.getRegistedFlag() == false) {
                        if(argCommand.size()==3)
                            myController.regClient(argCommand.get(1).toString(),argCommand.get(2).toString());
                        else{
                            System.out.println("SYNTAX ERROR FOR COMMAND REGISTER");
                        }

                        myController.sendPacket(argCommand);
                        String answer=myController.receiveAnswerPacket();
                        System.out.println(answer);

                    } else {
                        System.out.println("You are already registered");
                        continue;
                    }
                }else if(argCommand.get(0).equalsIgnoreCase("SLIST")) {
                    myController.sendPacket(argCommand);
                    String answer=myController.receiveAnswerPacket();
                    System.out.println(answer);
                } else{
                    System.out.println("Command not found");
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
                    continue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
