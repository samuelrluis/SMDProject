package client;

import common.heartbeat.ClientHeartBeat;
import common.Msg;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
    ObjectInputStream objectInput =null ;

    ClientController(Client x){
        myClient=x;
    }

    // Client Methods

    public void regClient(String name, String pass){
        myClient.getMyUserID().sethBeat(new ClientHeartBeat(name+pass,myClient.getServerPortHB()));
        myClient.setRegistedFlagTrue();
        myClient.startThreadHB();   //The HeartBeat Thread will start only when the userID is prepared
        return;
    }

    public void loginClient(String name,String pass){
        myClient.getMyUserID().sethBeat(new ClientHeartBeat(name+pass,myClient.getServerPortHB()));
        myClient.getMyUserID().setNameAndPassword(name+pass);
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

    public boolean sendComandToRemServer(String wantedPort){

        try {
            int serverPort = Integer.parseInt(wantedPort);
            InetSocketAddress serverAddr = new InetSocketAddress("127.0.0.1", serverPort);


            if (serverPort != 0) {
                try {
                    socketToRemServer = new Socket("127.0.0.1", serverPort);
<<<<<<< HEAD
                    //myClient.getSocketTCP().bind((serverAddr));
                    Msg msg = new Msg("TryConnection", myClient.getMyUserID().gethBeat()); //Create Serializable Msg
=======
                  
>>>>>>> origin/master

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

    public boolean sendComandToRemServer(String wantedPort, ArrayList<String> argCommand){

        try {
            int serverPort = Integer.parseInt(wantedPort);
            InetSocketAddress serverAddr = new InetSocketAddress("127.0.0.1", serverPort);

<<<<<<< HEAD


             String command = null;

             if(argCommand.get(0).equalsIgnoreCase("REGISTER"))
                 command = new String("REGISTER" + " " + argCommand.get(1) + " " +argCommand.get(2));
             else if(argCommand.get(0).equalsIgnoreCase("LOGIN"))
                 command = new String("LOGIN" + " " + argCommand.get(1) + " " +argCommand.get(2));
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0

            if (serverPort != 0) {
                try {
                    socketToRemServer = new Socket("127.0.0.1", serverPort);
                    //myClient.getSocketTCP().bind((serverAddr));
                    Msg msg = new Msg(command, myClient.getMyUserID().gethBeat()); //Create Serializable Msg

                    ObjectOutputStream objectOutput = new ObjectOutputStream(socketToRemServer.getOutputStream());
                    objectOutput.writeObject(msg);
                    objectOutput.flush();
                    System.out.println("Enviou msg TCP");


<<<<<<< HEAD
<<<<<<< HEAD
                    objectInput = new ObjectInputStream(socketToRemServer.getInputStream());
                    msg = (Msg) objectInput.readObject();



=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0

                } catch (IOException e) {
                    return false;
                }
                return true;
            } else
                return false;
        }catch (Exception e){return false;}
    }


    public String receiveAnswerPacketRemServer(){

        DatagramSocket socketToRem;
        DatagramPacket packetReadRem;

        packetReadRem = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
        socketToRem = myClient.getSocketRemServer();

        try {
            socketToRem.receive(packetReadRem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String answer = new String(packetReadRem.getData(),0,packetReadRem.getLength());
        return answer;
    }


<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD


    public void comandToRemServer(String answerTo,String ServerName){
=======
        socketToRem =  myClient.getSocketRemServer();

=======
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
        DatagramSocket socketToRem;
        DatagramPacket packetToRem;
        ByteArrayOutputStream b0ut;
        ObjectOutputStream out;
        String command = null;
        socketToRem =  myClient.getSocketRemServer();


<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0

        if(argCommand.get(0).equalsIgnoreCase("REGISTER"))
            command = new String("REGISTER" + " " + argCommand.get(1) + " " +argCommand.get(2));
        else if(argCommand.get(0).equalsIgnoreCase("LOGIN"))
            command = new String("LOGIN" + " " + argCommand.get(1) + " " +argCommand.get(2));


        //if (this.remoteServerPort != 0) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
            try {
                //Create a Serializable Message with the command to send to DirServer
                Msg msg = new Msg(command, myClient.getMyUserID().gethBeat()); //Create Serializable Msg


                //b0ut = new ByteArrayOutputStream();
                //out = new ObjectOutputStream(socketToRemServer.getOutputStream());
                objectOutput.writeObject(msg);
                objectOutput.flush();

                //packetToRem = new DatagramPacket(b0ut.toByteArray(),b0ut.size(),myClient.getServerAddr(), this.remoteServerPort);
                //socketToRem.send(packetToRem);

            } catch (IOException e) {
                e.printStackTrace();

        //}

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
=======
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0
            }
        //}
    }
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0



    }

    public void comandToRemServer(String ServerName){
>>>>>>> origin/master
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

<<<<<<< HEAD
                            this.sendComandToRemServer(answerTo,argCommand);
                            String strAnswer = this.receiveAnswerPacketRemServer();
                            System.out.println(ServerName + strAnswer);
=======
                            this.sendPacketToRemServer(argCommand);
                            String answer = this.receiveAnswerPacketRemServer();

                            System.out.println(ServerName + answer);
>>>>>>> parent of beff17e... Merge pull request #26 from samuelrluis/diogo2.0

                            continue;

                        } else
                            System.out.println("SYNTAX ERROR FOR COMMAND REGISTER");

                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("LOGIN")) {
                        //TODO falta implementar a verificaçao no ficheiro de registos
                        //mais notas:
                        //TODO A quando o login tem de ser verificado se exite ja uma diretoria do respetivo cliente,
                        //TODO se nao existir tem de ser criada, se existir é aberta/mostrada a area de trabalho desse cliente
/*
                        if (argCommand.size() == 3) {
                            this.sendComandToRemServer(answerTo,argCommand);
                            String answer= this.receiveAnswerPacketRemServer();
                            System.out.println(answer);
                        }
                        else {
                            System.out.println("SYNTAX ERROR FOR COMMAND LOGIN");
                        }
                        continue;
<<<<<<< HEAD
*/
=======


>>>>>>> origin/master
                    } else if (argCommand.get(0).equalsIgnoreCase("SHOWDIR")){
                        //TODO mostrar todos os conteudos da diretoria
                        //mais notas:
                        //TODO (so da diretoria dos cliente em questao, nao pode ser visivel as diretorias dos outros clientes)
                        //TODO Comando SHOWDIR mostra a pasta base
                        //TODO Comando SHOWDIR+"espaço"+"caminho" mostra os doc's nessa pasta especifica
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("UPLOAD")){
                        //TODO upload ficheiro, utilizar protocolo TCP
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("DOWNLOAD")){
                        //TODO download ficheiro, utilizar protocolo TCP
                        continue;

                    } else if (argCommand.get(0).equalsIgnoreCase("MOVFILE")){
                        //TODO implementar movimentaçao de ficheiros entre diretorias
                        //TODO MOVFILE+"espaço"+"caminho"+"espaço"+"caminho"
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("COPYFILE")){
                        //TODO implementar copia de ficheiros entre diretorias
                        //TODO COPYFILE+"espaço"+"caminho"+"espaço"+"caminho"
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("DELFILE")) {
                        //TODO eliminar ficheiro
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("DELDIR")) {
                        //TODO eliminar ficheiro
                        //TODO ATENÇAO SO VALIDO PARA DIRETORIAS VAZIAS
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("RENAMEFILE")) {
                        //TODO mudar nome de ficheiro
                        //TODO RENAMEFILE+"espaço"+"nomeFile"+"espaço"+"nomeFile"
                        continue;

                    }else if (argCommand.get(0).equalsIgnoreCase("RENAMEDIR")) {
                        //TODO mudar nome de diretoria
                        //TODO RENAMEDIR+"espaço"+"nomeDir"+"espaço"+"nomeDir"
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
