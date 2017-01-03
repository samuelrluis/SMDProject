package remoteserver;

import common.*;
import common.heartbeat.ClientHeartBeat;
import common.heartbeat.ServerHeartBeat;
import common.registry.ClientRegistry;
import common.registry.ServerRegistry;
import dirserver.DirectoryServer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by MarceloCortesao on 21/12/16.
 */
public class RemoteServerController {

    //Remote Server
    private RemoteServer myServ;

    //Common
    private ClientRegistry clientRegistry;

    private DatagramPacket packetRead;
    private DatagramPacket packetWrite;
    private DatagramSocket socket;


    public void receivedCommand(Msg message) {
        ArrayList<String> argCommand = new ArrayList<>();

        String commandStr = message.getCommand();
        ClientHeartBeat hBeat = message.gethBeat();
        StringTokenizer tok = new StringTokenizer(commandStr, " ");

        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            argCommand.add(token);
        }
        //---------------------- trata comandos ----------------------
        try {
            if (argCommand.get(0).equalsIgnoreCase("REGISTER")) {


                //TODO implementar registos dos clientes neste servidor remoto,
                //TODO(temos de decidir se usamos o mesmo mecanismo que no servDiretoria e gravamos no ficheiro ou se usamos so um arrayList)
                /*
                //FIXTHIS
                System.out.println("Não está a chegar aqui!");

                ClientRegistry cli = new ClientRegistry(hBeat, 333);
                cli.writeObjectToFile("../SMDProject/src/remoteserver/savefiles/saveCliRegistry.obj");
                */



            } else if (argCommand.get(0).equalsIgnoreCase("LOGIN")) {
                //TODO falta implementar a verificaçao no ficheiro de registos
                /*
                ClientRegistry cli = new ClientRegistry();
                if (cli.checkCliOnFile(argCommand.get(1) + argCommand.get(2), "../SMDProject/src/remoteserver/savefiles/saveCliRegistry.obj") == true) {
                    packetWrite = new DatagramPacket("Login successfully".getBytes(), "Login successfully".length(), packetRead.getAddress(), packetRead.getPort());
                } else if (cli.checkCliOnFile(argCommand.get(1) + argCommand.get(2), "../SMDProject/src/remoteserver/savefiles/saveCliRegistry.obj") == false) {
                    packetWrite = new DatagramPacket("Login failed".getBytes(), "Login failed".length(), packetRead.getAddress(), packetRead.getPort());
                }
                socket.send(packetWrite);
/               */
                System.out.println("Logado com Sucesso!!");

                // A quando o login tem de ser verificado se exite ja uma diretoria do respetivo cliente,
                // se nao existir tem de ser criada, se existir é aberta/mostrada a area de trabalho desse cliente

            } else if (argCommand.get(0).equalsIgnoreCase("SHOWDIR")){
                //mostrar todos os conteudos da diretoria
                //mais notas:
                //(so da diretoria dos cliente em questao, nao pode ser visivel as diretorias dos outros clientes)
                //Comando SHOWDIR mostra a pasta base
                // Comando SHOWDIR+"espaço"+"caminho" mostra os doc's nessa pasta especifica

            }else if (argCommand.get(0).equalsIgnoreCase("UPLOAD")){
                //TODO upload ficheiro, utilizar protocolo TCP

            }else if (argCommand.get(0).equalsIgnoreCase("DOWNLOAD")){
                //TODO download ficheiro, utilizar protocolo TCP

            } else if (argCommand.get(0).equalsIgnoreCase("MOVFILE")){
                //implementar movimentaçao de ficheiros entre diretorias
                //MOVFILE+"espaço"+"caminho"+"espaço"+"caminho"

            }else if (argCommand.get(0).equalsIgnoreCase("COPYFILE")){
                //implementar copia de ficheiros entre diretorias
                //COPYFILE+"espaço"+"caminho"+"espaço"+"caminho"

            }else if (argCommand.get(0).equalsIgnoreCase("DELFILE")) {
                //eliminar ficheiro

            }else if (argCommand.get(0).equalsIgnoreCase("DELDIR")) {
                //eliminar ficheiro
                //ATENÇAO SO VALIDO PARA DIRETORIAS VAZIAS

            }else if (argCommand.get(0).equalsIgnoreCase("RENAMEFILE")) {
                // mudar nome de ficheiro
                // RENAMEFILE+"espaço"+"nomeFile"+"espaço"+"nomeFile"

            }else if (argCommand.get(0).equalsIgnoreCase("RENAMEDIR")) {
                //TODO mudar nome de diretoria
                //TODO RENAMEDIR+"espaço"+"nomeDir"+"espaço"+"nomeDir"
            }else if (argCommand.get(0).equalsIgnoreCase("CREATEDIR")) {
                //mudar nome de diretoria

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void regClient(String name, String pass){


        return;
    }


    private void receivedHeartBeatClient(ClientHeartBeat hBeat) {
        int i=0;
        Boolean foundReg=false;
        ArrayList<ClientRegistry> cliRegestries = myServ.getCliRegistries();
        if(cliRegestries.size()==0)
            cliRegestries.add(new ClientRegistry(hBeat, System.nanoTime()));

        else{
            while(i<cliRegestries.size()) {
                if (cliRegestries.get(i).gethBeat().getName().equalsIgnoreCase(hBeat.getName())) {
                    cliRegestries.get(i).setEntryTime();
                    foundReg = true;
                    break;
                } else
                    i++;
            }

            if(foundReg==false)
                cliRegestries.add(new ClientRegistry(hBeat, System.nanoTime()));
        }
    }

    private void receiveHeartBeatServer(ServerHeartBeat hBeat ){
        int i=0;
        Boolean foundReg=false;
        ArrayList<ServerRegistry> serverRegistries = myServ.getServerRegistries();

        if(serverRegistries.size()==0)
            serverRegistries.add(new ServerRegistry(hBeat, System.nanoTime()));

        else{
            while(i<serverRegistries.size()) {
                if (serverRegistries.get(i).gethBeat().getName().equalsIgnoreCase(hBeat.getName())) {
                    serverRegistries.get(i).setEntryTime();
                    foundReg = true;
                    break;
                } else
                    i++;
            }

            if(foundReg==false)
                serverRegistries.add(new ServerRegistry(hBeat, System.nanoTime()));
        }
    }



    public void answeringDatagram(){

        while(true){
            try{
                socket.receive(packetRead);
                ByteArrayInputStream Bin = new ByteArrayInputStream(packetRead.getData());
                ObjectInputStream in = new ObjectInputStream(Bin);
                in.close();
                Object message = in.readObject();

                if(message instanceof ServerHeartBeat)
                    receiveHeartBeatServer((ServerHeartBeat) message); //This will receive th HB from Server
                else if(message instanceof ClientHeartBeat)
                    receivedHeartBeatClient((ClientHeartBeat) message); //This will receive th HB from Client
                else if(message instanceof Msg)
                    receivedCommand((Msg) message);

            } catch (IOException e){
                e.printStackTrace();
                System.out.println("IO");
            } catch (ClassNotFoundException e){
                System.out.printf("Class");
                e.printStackTrace();
            }
        }
    }










}
