package client.threads;


import client.Client;
import client.ClientController;
import common.registry.ClientRegistry;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by diogomiguel on 25/11/16.
 */

public class ThTextUI extends Thread {

    public static final int MAX_SIZE = 1024;
    private Client myClient;
    private ClientRegistry myUserID;
    private ClientController myClientController;

    public ThTextUI(Client x){
        myClient = x;
        myClientController = x.getController();
        myUserID = myClient.getMyUserID();
    }

    @Override
    public void run() {

        String commandStr;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            ArrayList<String> argCommand = new ArrayList<>();
            int x=0;
            System.out.print("$> ");
            System.out.flush();

            try {
                commandStr = bufferedReader.readLine();
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

                else if(argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                    if (myClient.getRegistedFlag() == false) {
                        if (argCommand.size() == 3) {

                            myClientController.regClient(argCommand.get(1).toString(), argCommand.get(2).toString());
                            myClientController.sendPacketToDirServer(argCommand);
                            String answer = myClientController.receiveAnswerPacketDirServer();
                            System.out.println(answer);

                            continue;

                        } else
                            System.out.println("SYNTAX ERROR FOR COMMAND REGISTER");

                    } else {
                        System.out.println("You are already registered");
                        continue;
                    }
                }

                else if(argCommand.get(0).equalsIgnoreCase("MAN")) {
                    System.out.println(myClientController.readObjectFromFile("../SMDProject/src/client/manual.txt"));
                    continue;

                }else if(argCommand.get(0).equalsIgnoreCase("LOGIN")){
                    if(myClient.getLoginFlag()==false) {
                        if (argCommand.size() == 3) {
                            myClientController.sendPacketToDirServer(argCommand);
                            String answer= myClientController.receiveAnswerPacketDirServer();
                            System.out.println(answer);
                            if(answer.compareTo("Login successfully!")==0) {
                                myClient.setloginFlagTrue();
                                myClientController.loginClient(argCommand.get(1).toString(), argCommand.get(2).toString());
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
                        System.out.println("Logout Successfully!");
                        continue;
                    }
                }




                else if(argCommand.get(0).equalsIgnoreCase("CONNECT")){

                    //TODO----------------------------TRATAR COMANDOS PARA O SERVREMOTO------------------------------------
                    //TODO dpois aqui dentro eu proponho que façamos um ciclo infinito de leitura de comando para o servRemoto em questao
                    //TODO ,pq se nao para tratar os comandos temos tinhamos de colocar o nome do servidor sempre antes o que nao fazer muito sentido
                    //TODO dpois caso o cliente queira sair ha um comando "EXIT" damos um break e volta para aqui e pode se ligar a outro servRemoto
                    //-----------------------------------------------------------------------------------------------------


                        if(myClient.getRegistedFlag()==false) {
                            System.out.println("To use this command you need to be logged in");
                            continue;
                        }

                        myClientController.sendPacketToDirServer(argCommand);
                        String answer= myClientController.receiveAnswerPacketDirServer();
                        System.out.println("Porto: " + answer);

                        if(myClientController.connectToRemServer(answer)) {
                            System.out.println("Connection to " + argCommand.get(1) + " " + argCommand.get(2) + "Succeded");

                            int sPort = Integer.parseInt(answer);
                            myClientController.setRemoteServerPort(sPort); // Guarda o porto do Server Remoto no cliente

                            myClientController.comandToRemServer(argCommand.get(1));

                        }
                        else
                            System.out.println("You can't connect to that server");


                }else if(argCommand.get(0).equalsIgnoreCase("SLIST")) {
                    if(myClient.getRegistedFlag()==false){
                       System.out.println("To use this command you need to be logged in");
                       continue;
                    }else{

                    }
                    //TODO receber arrayList com objetos do tipo remoteServ (E preciso os portos e IP para estabelecer uma ligaçao direta com os servidores)
                    myClientController.sendPacketToDirServer(argCommand);
                    String answer= myClientController.receiveAnswerPacketDirServer();
                    System.out.println(answer);
                } else if(argCommand.get(0).equalsIgnoreCase("CLIST")) {
                if(myClient.getRegistedFlag()==false){
                        System.out.println("To use this command you need to be logged in");
                        continue;
                    }
                    //TODO receber arrayList com objetos do tipo cliente(e preciso portos e IP para usar troca de msgs ou difusao)
                        myClientController.sendPacketToDirServer(argCommand);
                        String answer= myClientController.receiveAnswerPacketDirServer();
                        System.out.println(answer);
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
