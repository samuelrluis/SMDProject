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
    private Client myClient;
    private CliRegistry myUserID;
    private Controller myController;

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
            System.out.print("$> ");
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
                    if (myClient.getLoginFlag() == true) {
                        System.out.println("You have to logout first!");
                        continue;
                    }else
                        System.exit(0);

                }

                else if(argCommand.get(0).equalsIgnoreCase("MAN")) {
                    System.out.println(myController.readObjectFromFile("../SMDProject/src/client/manual.txt"));
                    continue;
                }else if(argCommand.get(0).equalsIgnoreCase("LOGIN")){
                    if(myClient.getLoginFlag()==false) {
                        if (argCommand.size() == 3) {
                            myController.sendPacket(argCommand);
                            String answer=myController.receiveAnswerPacket();
                            System.out.println(answer);
                            if(answer.compareTo("Login successfully")==0) {
                                myClient.setloginFlagTrue();
                                myController.loginClient(argCommand.get(1).toString(), argCommand.get(2).toString());
                            }
                        }
                        else {
                            System.out.println("SYNTAX ERROR FOR COMMAND LOGIN");
                        }
                        continue;
                    }
                    else {
                        System.out.println("You are already Logged");
                        continue;
                    }
                }else if(argCommand.get(0).equalsIgnoreCase("LOGOUT")){
                    if (myClient.getRegistedFlag() == false) {
                        System.out.println("You are not Logged");
                            continue;
                    }else
                    {
                        myClient.setloginFlagFalse(); // falta notificar o servidor
                        System.out.println("Successfully logout!");
                        continue;
                    }
                }


                else if(argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                    if (myClient.getRegistedFlag() == false) {
                        if (argCommand.size() == 3) {
                            myController.regClient(argCommand.get(1).toString(), argCommand.get(2).toString());
                            myController.sendPacket(argCommand);
                            String answer = myController.receiveAnswerPacket();
                            System.out.println(answer);
                            continue;
                        } else
                            System.out.println("SYNTAX ERROR FOR COMMAND REGISTER");

                    } else {
                        System.out.println("You are already registered");
                        continue;
                    }
                }

                else if(argCommand.get(0).equalsIgnoreCase("CONNECT")){
                        if(myClient.getRegistedFlag()==false) {
                            System.out.println("To use this command you need to be logged in");
                            continue;
                        }
                        myController.sendPacket(argCommand);
                        String answer=myController.receiveAnswerPacket();
                        System.out.println(answer);
                        if(myController.connectToRemServer(answer))
                            System.out.printf("Connection to " + argCommand.get(1) + argCommand.get(2) + "Succeded" );
                        else
                            System.out.printf("You can't connect to that server");
                    //TODO----------------------------TRATAR COMANDOS PARA O SERVREMOTO------------------------------------
                    //TODO dpois aqui dentro eu proponho que façamos um ciclo infinito de leitura de comando para o servRemoto em questao
                    //TODO ,pq se nao para tratar os comandos temos tinhamos de colocar o nome do servidor sempre antes o que nao fazer muito sentido
                    //TODO dpois caso o cliente queira sair ha um comando "EXIT" damos um break e volta para aqui e pode se ligar a outro servRemoto
                    //-----------------------------------------------------------------------------------------------------






                }else if(argCommand.get(0).equalsIgnoreCase("SLIST")) {
                    if(myClient.getRegistedFlag()==false){
                       System.out.println("To use this command you need to be logged in");
                       continue;
                    }else{

                    }
                    //TODO receber arrayList com objetos do tipo remoteServ (E preciso os portos e IP para estabelecer umam ligaçao direta com os servidores)
                    myController.sendPacket(argCommand);
                    String answer=myController.receiveAnswerPacket();
                    System.out.println(answer);
                } else if(argCommand.get(0).equalsIgnoreCase("CLIST")) {
                if(myClient.getRegistedFlag()==false){
                        System.out.println("To use this command you need to be logged in");
                        continue;
                    }
                    //TODO receber arrayList com objetos do tipo cliente(e preciso portos e IP para usar troca de msgs ou difusao)
//                    myController.sendPacket(argCommand);
//                    String answer=myController.receiveAnswerPacket();
//                    System.out.println(answer);
                } else if (argCommand.get(0).equalsIgnoreCase("CLIMSG")){
                        if(myClient.getRegistedFlag()==false){
                            System.out.println("To use this command you need to be logged in");
                            continue;
                        }
                    //TODO implementar msgs, para um cliente especifico "CLIMSG"+"espaço"+"idDoCli"+"texto", em difusao "CLIMSG"+"espaço"+"texto"
                    //TODO ATENÇAO QUE A TROCA DE MSGS NAO PODE SER DIRETA ENTRE CLIENTES, TEMOS DE USAR COMO INTERMEDIARIO O SERVIDOR DE DIRETORIA
                    continue;

                }else{
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
        }
    }
}
