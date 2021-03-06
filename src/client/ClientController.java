package client;

import common.heartbeat.ClientHeartBeat;
import common.Msg;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static client.threads.ThTextUI.MAX_SIZE;

/**
 * Created by Samuel on 13/12/2016.
 */

public class ClientController {

    private Client myClient;
    private int remoteServerPort;

    Socket socketToRemServer = null;
    ObjectOutputStream objectOutput = null;
    ObjectInputStream objectInput = null;

    ClientController(Client x){
        myClient=x;
    }

    // Client Methods

    public void regClient(String name, String pass){
        myClient.getMyUserID().sethBeat(new ClientHeartBeat(name+pass,myClient.getServerPortHB(), myClient.readerPort()));
        myClient.setRegistedFlagTrue();
        myClient.startThreadHB();   //The HeartBeat Thread will start only when the userID is prepared
        return;
    }

    public void loginClient(String name,String pass){
        myClient.getMyUserID().sethBeat(new ClientHeartBeat(name+pass,myClient.getServerPortHB(),myClient.readerPort()));
        myClient.getMyUserID().setNameAndPassword(name+pass);
        myClient.setRegistedFlagTrue();
        return;
    }

    // DirectoryServer Comunication Methods

    public int getRemoteServerPort() {
        return remoteServerPort;
    }

    public void setRemoteServerPort(int remoteServerPort) {
        this.remoteServerPort = remoteServerPort;
    }

    public String receiveAnswerPacketDirServer(){
        DatagramSocket socketToDir;
        DatagramPacket packetReadDir;
        packetReadDir = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
        socketToDir=myClient.getSocketDirServer();

        try {
            socketToDir.receive(packetReadDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String answer = new String(packetReadDir.getData(),0,packetReadDir.getLength());
        return answer;
    }

    public void sendCommandRMI(ArrayList<String> argCommand){
        if(argCommand.get(0).equalsIgnoreCase("SLISTRMI")){
            try {
                System.out.println(myClient.getRemoteInterface().getListServRMI());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPacketToDirServer(ArrayList<String> argCommand){

        DatagramSocket socketToDir;
        DatagramPacket packetToDir;
        ByteArrayOutputStream b0ut;
        ObjectOutputStream out;
        String command = null;
        socketToDir=  myClient.getSocketDirServer();

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
        else if(argCommand.get(0).equalsIgnoreCase("CHAT"))
            command = new String("CHAT"+" "+argCommand.get(1)+" "+argCommand.get(2));

        try {
            //Create a Serializable Message with the command to send to DirServer
            Msg msg = new Msg(command,myClient.getMyUserID().gethBeat()); //Create Serializable Msg
            b0ut = new ByteArrayOutputStream();
            out = new ObjectOutputStream(b0ut);
            out.writeObject(msg);
            out.flush();
            packetToDir = new DatagramPacket(b0ut.toByteArray(),b0ut.size(),myClient.getServerAddr(), myClient.getServerPortCommand());
            socketToDir.send(packetToDir);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean connectToRemServer(String wantedPort){

        try {
            int serverPort = Integer.parseInt(wantedPort);

            if (serverPort != 0) {
                try {
                    socketToRemServer = new Socket(myClient.getServerAddr(), serverPort);
                    Msg msg = new Msg("Just Connect to this Server", myClient.getMyUserID().gethBeat()); //Create Serializable Msg

                    objectOutput = new ObjectOutputStream(socketToRemServer.getOutputStream());
                    objectOutput.writeObject(msg);
                    objectOutput.flush();
                    System.out.println("Enviou msg TCP");

                    objectInput = new ObjectInputStream(socketToRemServer.getInputStream());
                    msg = (Msg) objectInput.readObject();
                    //System.out.println(msg.toString());

                } catch (IOException e) {
                    return false;
                }
                return true;
            } else
                return false;
        }catch (Exception e){return false;}
    }

    // RemoteServer Comunication Methods

    public String receiveAnswerMsgRemServer(){

        ObjectInputStream in;
        Msg msg = null;
        try {
            //Read Message from RemServer
            in = new ObjectInputStream(socketToRemServer.getInputStream());
            msg = (Msg) in.readObject();
            System.out.println(msg.getCommand());
            //System.out.println("recebi mensagem de " + msg.gethBeatSer().getName().toString());
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg.toString();
    }

    public void sendMsgToRemServer(ArrayList<String> argCommand){


        DatagramSocket socketToRem;
        DatagramPacket packetToRem;
        ByteArrayOutputStream b0ut;
        ObjectOutputStream out;
        String command = new String();

        //Socket socketToRem =  myClient.getSocketRemServer();

        if(argCommand.get(0).equalsIgnoreCase("REGISTER")){
            command = new String("REGISTER" + " " + argCommand.get(1) + " " +argCommand.get(2));
        }else if(argCommand.get(0).equalsIgnoreCase("LOGIN"))
            command = new String("LOGIN" + " " + argCommand.get(1) + " " +argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("SHOWDIR"))
            command = new String("SHOWDIR" + " " + argCommand.get(1));
        else if(argCommand.get(0).equalsIgnoreCase("SHOWFILES"))
            command = new String("SHOWFILES" + " " + argCommand.get(1));
        else if(argCommand.get(0).equalsIgnoreCase("NEWFILE"))
            command = new String("NEWFILE" + " " + argCommand.get(1) + " " + argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("MOVFILE")) //TODO Não Implementado!!!
            command = new String("MOVFILE" + " " + argCommand.get(1) + " " + argCommand.get(2) + " " + argCommand.get(3));
        else if(argCommand.get(0).equalsIgnoreCase("COPYFILE"))
            command = new String("COPYFILE" + " " + argCommand.get(1) + " " + argCommand.get(2) + " " + argCommand.get(3));
        else if(argCommand.get(0).equalsIgnoreCase("RENAMEFILE"))
            command = new String("RENAMEFILE" + " " + argCommand.get(1) + " " + argCommand.get(2) + " " + argCommand.get(3));
        else if(argCommand.get(0).equalsIgnoreCase("DELFILE"))
            command = new String("DELFILE" + " " + argCommand.get(1) + " " + argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("DELDIR"))
            command = new String("DELDIR" + " " + argCommand.get(1) + " " + argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("RENAMEDIR"))
            command = new String("RENAMEDIR" + " " + argCommand.get(1) + " " + argCommand.get(2) + " " + argCommand.get(3));


        //if (this.remoteServerPort != 0) {

        try {
            objectOutput = new ObjectOutputStream(socketToRemServer.getOutputStream());
            Msg msg = new Msg(command,myClient.getMyUserID().gethBeat());

            objectOutput.writeObject(msg);
            objectOutput.flush();

            System.out.println("Enviou msg TCP para o porto: " + remoteServerPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ------------- Comandos para o Remote Server ----------------

    public void comandToRemServer(String ServerName){
        String commandStr;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do{
            ArrayList<String> argCommand = new ArrayList<>();
            int x=0;
            System.out.print(ServerName +" "+"$> ");
            System.out.flush();

            try {
                commandStr = br.readLine();
                StringTokenizer tok = new StringTokenizer(commandStr," ");

                while (tok.hasMoreTokens()){
                    String token = tok.nextToken();
                    argCommand.add(token);
                }

                try {
                    if (argCommand.get(0).equalsIgnoreCase("EXIT")) {
                        break;
                    } else if (argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                        if (argCommand.size() == 3) {

                            this.sendMsgToRemServer(argCommand);
                            System.out.println();
                            System.out.print("Answer:       ");
                            this.receiveAnswerMsgRemServer();

                            myClient.setClientUsername(argCommand.get(1)+argCommand.get(2));
                            System.out.println(myClient.getClientUsername());

                            continue;

                        } else
                            System.out.println("SYNTAX ERROR FOR COMMAND REGISTER");

                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("LOGIN"))
                    {
                        //TODO falta implementar a verificaçao no ficheiro de registos
                        //mais notas:
                        //TODO A quando o login tem de ser verificado se exite ja uma diretoria do respetivo cliente,
                        //TODO se nao existir tem de ser criada, se existir é aberta/mostrada a area de trabalho desse cliente

                        if (argCommand.size() == 3) {

                            myClient.setClientUsername(argCommand.get(1));

                            this.sendMsgToRemServer(argCommand);
                            String answer= this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                            myClient.setClientUsername(argCommand.get(1)+argCommand.get(2));
                        }
                        else {
                            System.out.println("SYNTAX ERROR FOR COMMAND LOGIN");
                        }
                        continue;


                    } else if (argCommand.get(0).equalsIgnoreCase("SHOWDIR")){
                        //TODO mostrar todos os conteudos da diretoria
                        //mais notas:
                        //TODO (so da diretoria dos cliente em questao, nao pode ser visivel as diretorias dos outros clientes)
                        //TODO Comando SHOWDIR mostra a pasta base
                        //TODO Comando SHOWDIR+"espaço"+"caminho" mostra os doc's nessa pasta especifica

                        if (argCommand.size() == 1) {

                            argCommand.add(1,myClient.getClientUsername());

                            this.sendMsgToRemServer(argCommand);
                            String answer= this.receiveAnswerMsgRemServer();
                            System.out.println(answer);
                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND SHOWDIR");
                        }
                        continue;

                    }else if(argCommand.get(0).equalsIgnoreCase("SHOWFILES")){ // TODO não sei o que se passa com isto

                        if (argCommand.size() == 1) {

                            argCommand.add(1, myClient.getClientUsername());

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);
                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND SHOWFILES");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("NEWFILE")){

                        if(argCommand.size() == 2){

                            argCommand.add(2, myClient.getClientUsername()); // Adiciona o Username á 3ª posicao

                            System.out.println(argCommand.get(0)+argCommand.get(1)+argCommand.get(2));

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND NEWFILE");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("UPLOAD")){
                        //TODO upload ficheiro, utilizar protocolo TCP
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("DOWNLOAD")){
                        //TODO download ficheiro, utilizar protocolo TCP

                        DownloadFileClient DownFile = new DownloadFileClient();

                        DownFile.FileDownload("localhost","/Users/MarceloCortesao/Desktop",argCommand.get(1));




                        continue;

                    } else if (argCommand.get(0).equalsIgnoreCase("MOVFILE")){
                        //TODO implementar movimentaçao de ficheiros entre diretorias
                        //TODO MOVFILE+"espaço"+"caminho"+"espaço"+"caminho"
                        if(argCommand.size() == 3){

                            argCommand.add(3, myClient.getClientUsername()); // Adiciona o Username á 4ª posicao

                            System.out.println(argCommand.get(0)+argCommand.get(1)+argCommand.get(2)+argCommand.get(3));

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND MOVFILE");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("COPYFILE")){
                        //TODO implementar copia de ficheiros entre diretorias
                        //TODO COPYFILE+"espaço"+"caminho"+"espaço"+"caminho"
                        if(argCommand.size() == 3){

                            argCommand.add(3, myClient.getClientUsername()); // Adiciona o Username á 4ª posicao

                            System.out.println(argCommand.get(0)+argCommand.get(1)+argCommand.get(2)+argCommand.get(3));

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND COPYFILE");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("DELFILE")) {
                        //TODO eliminar ficheiro
                        if(argCommand.size() == 2){

                            argCommand.add(2, myClient.getClientUsername()); // Adiciona o Username á 3ª posicao

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND DELFILE");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("DELDIR")) {
                        //TODO eliminar ficheiro
                        //TODO ATENÇAO SO VALIDO PARA DIRETORIAS VAZIAS
                        if(argCommand.size() == 2){

                            argCommand.add(2, myClient.getClientUsername()); // Adiciona o Username á 3ª posicao

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND DELDIR");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("RENAMEFILE")) {
                        //TODO mudar nome de ficheiro
                        //TODO RENAMEFILE+"espaço"+"nomeFile"+"espaço"+"nomeFile"
                        if(argCommand.size() == 3){

                            argCommand.add(3, myClient.getClientUsername()); // Adiciona o Username á 4ª posicao

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND RENAMEFILE");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("RENAMEDIR")) {
                        //TODO mudar nome de diretoria
                        //TODO RENAMEDIR+"espaço"+"nomeDir"+"espaço"+"nomeDir"
                        if(argCommand.size() == 3){

                            argCommand.add(3, myClient.getClientUsername()); // Adiciona o Username á 4ª posicao

                            this.sendMsgToRemServer(argCommand);
                            String answer = this.receiveAnswerMsgRemServer();
                            System.out.println(answer);

                        }else {
                            System.out.println("SYNTAX ERROR FOR COMMAND RENAMEDIR");
                        }
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("CREATEDIR")) {
                        //TODO mudar nome de diretoria
                        //TODO RENAMEDIR+"espaço"+"nomeDir"
                        continue;
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

    // Misc Methods

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