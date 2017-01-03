package client.threads;


import client.Client;
import client.ClientController;
import common.registry.ClientRegistry;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                        if (myClient.getLoginFlag() == false) {
                            System.out.println("You have to logout first!");
                            continue;
                        }else
                            System.exit(0);
                    }
                    else if(argCommand.get(0).equalsIgnoreCase("TEST")){



                        //String nomeCliente = new String("NomeCliente");
                        //File dir = new File("../SMDProject/cliFolders/"+ nomeCliente);
                        /*
                         //TODO Criar diretoria
                        if(dir.mkdir()){
                            System.out.println("Criado com sucesso");
                        }
                        */

                        //TODO Verificar se existe diretoria
                        /*
                        if(dir.exists()){
                            System.out.println("Existe!");
                        }
                        */

                        //TODO Listar subpastas na diretoria
                        /*
                        File dir = new File("../SMDProject/cliFolders");
                        String[] directories = dir.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File current, String name) {
                                return new File(current, name).isDirectory();
                            }
                        });
                        System.out.println(Arrays.toString(directories));
                        */
                        //TODO Listar ficheiros e subpastas na diretoria
                        /*
                        File dir = new File("../SMDProject/cliFolders");
                        String[] files = dir.list();
                        if (files.length == 0) {
                            System.out.println("The directory is empty");
                        } else {
                            for (String aFile : files) {
                                System.out.println(aFile);
                            }
                        }
                        */
                        //TODO criar ficheiro
                        /*
                        String nomeFicheiro = new String("nomeFicheiro");
                        File file = new File("../SMDProject/cliFolders/"+ nomeFicheiro +".txt");
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("Estou a escrever num ficheiro novo");
                        bw.close();
                        */
                        //TODO copiar ficheiro de uma diretoria para outra
                        /*
                        InputStream inStream = null;
                        OutputStream outStream = null;

                        try{

                            String nomeFicheiro = new String("nomeFicheiro");
                            String nomePasta = new String("1stFolder");
                            inStream = new FileInputStream("../SMDProject/cliFolders/" + nomeFicheiro + ".txt");
                            File file = new File("../SMDProject/cliFolders/" + nomePasta + "/" + nomeFicheiro + ".txt");
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            outStream = new FileOutputStream("../SMDProject/cliFolders/1stFolder/" + nomeFicheiro + ".txt");
                            byte[] buffer = new byte[1024];
                            int length;

                            while ((length = inStream.read(buffer)) > 0){
                                outStream.write(buffer, 0, length);
                            }
                            inStream.close();
                            outStream.close();
                            System.out.println("File is copied successful!");
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        */
                       //TODO renomiar o ficheiro
                        /*
                        try{
                            String nomeFicheiroOriginal = new String("nomeFicheiro");
                            String nomeFicheiroModificado = new String("ficheiroNomeModificado");
                            File afile =new File("../SMDProject/cliFolders/"+ nomeFicheiroOriginal + ".txt");

                            if(afile.renameTo(new File("../SMDProject/cliFolders/"+ nomeFicheiroModificado + ".txt"))){
                                System.out.println("File is moved successful!");
                            }else{
                                System.out.println("File is failed to move!");
                            }

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        */
                        //TODO apagar ficheiro
                        /*
                        String nomeFicheiro = new String("nomeFicheiro");
                        File dir = new File("../SMDProject/cliFolders/"+ nomeFicheiro + ".txt");
                        if(dir.delete()){
                            System.out.println("Apagou com sucesso");
                        }
                        */
                        //TODO apagar diretoria (vazia)
                        /*
                        File file = new File("../SMDProject/cliFolders/1stFolder/pastaSemNome");
                        if(file.delete()){
                            System.out.println("apagado com sucesso");
                        }
                        */
                        //TODO renomiar diretorias
                        /*
                        File dirOriginal =new File("../SMDProject/cliFolders/NomeCliente/pastaSemNome");
                        File dirModificada =new File("../SMDProject/cliFolders/NomeCliente/pastaPasta");
                        if ( dirOriginal.isDirectory() ) {
                            dirOriginal.renameTo(dirModificada);
                            System.out.println("Modificada com sucesso");
                        }
                        */
                    }

                else if(argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                    if (myClient.getRegistedFlag() == false) {
                        if (argCommand.size() == 3) {
                            myClient.setRegistedFlagTrue();
                            myClientController.regClient(argCommand.get(1).toString(), argCommand.get(2).toString());
                            myClientController.sendPacketToDirServer(argCommand);
                            String answer = myClientController.receiveAnswerPacketDirServer();
                            System.out.println(answer);
                            if(answer.equalsIgnoreCase("Login successfully")) {
                                myClient.setRegistedFlagTrue();
                                continue;
                            }
                            continue;
                        }
                        else {
                            System.out.println("SYNTAX ERROR FOR COMMAND REGISTER");
                        }
                    } else {
                        System.out.println("You are already registered");
                        continue;
                    }
                }

                else if (argCommand.get(0).equalsIgnoreCase("CHAT")) {
                    if(myClient.getLoginFlag()==false){
                        System.out.println("To use this command you need to be logged in");
                        continue;
                    }else{
                        myClientController.sendPacketToDirServer(argCommand);
                    }

                }

                else if(argCommand.get(0).equalsIgnoreCase("SLISTRMI")){
                    if(myClient.getLoginFlag()==false){
                        System.out.println("To use this command you need to be logged in");
                        continue;
                    }else{
                        myClientController.sendCommandRMI(argCommand);
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
                            if(answer.equalsIgnoreCase("Login successfully")) {
                                myClientController.loginClient(argCommand.get(1).toString(), argCommand.get(2).toString());
                                myClient.setRegistedFlagTrue();
                                myClient.setloginFlagTrue();
                                continue;
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
                    if (myClient.getLoginFlag()== false) {
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

                        if(myClient.getLoginFlag()==false) {
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
                    if(myClient.getLoginFlag()==false){
                       System.out.println("To use this command you need to be logged in");
                       continue;
                    }else{
                        //TODO receber arrayList com objetos do tipo remoteServ (E preciso os portos e IP para estabelecer uma ligaçao direta com os servidores)
                        myClientController.sendPacketToDirServer(argCommand);
                        String answer= myClientController.receiveAnswerPacketDirServer();
                        System.out.println(answer);
                    }
                } else if(argCommand.get(0).equalsIgnoreCase("CLIST")) {
                if(myClient.getLoginFlag()==false){
                        System.out.println("To use this command you need to be logged in");
                        continue;
                    }
                    //TODO receber arrayList com objetos do tipo cliente(e preciso portos e IP para usar troca de msgs ou difusao)
                        myClientController.sendPacketToDirServer(argCommand);
                        String answer= myClientController.receiveAnswerPacketDirServer();
                        System.out.println(answer);
                } else if (argCommand.get(0).equalsIgnoreCase("CLIMSG")){
                        if(myClient.getLoginFlag()==false){
                            System.out.println("You can't see the logged clients without login");
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
