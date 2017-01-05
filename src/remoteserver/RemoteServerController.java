package remoteserver;

import common.*;
import common.heartbeat.ClientHeartBeat;
import common.heartbeat.ServerHeartBeat;
import common.registry.ClientRegistry;
import common.registry.ServerRegistry;
import dirserver.DirectoryServer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
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


    public String receivedCommand(Msg message, RemoteServer Serv) {
        ArrayList<String> argCommand = new ArrayList<>();
        String commandStr = message.getCommand();
        ClientHeartBeat hBeat = message.gethBeat();
        StringTokenizer tok = new StringTokenizer(commandStr, " ");
        ClientRegistry cliR = new ClientRegistry();
        myServ=Serv;
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            argCommand.add(token);
        }
        //---------------------- trata comandos ----------------------
        try {
            if (argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                File dir = new File("../SMDProject/cliFolders/"+ argCommand.get(1));
                if(dir.exists()){
                    System.out.println("Existe!");
                }
                else if (!dir.exists()){
                    if(dir.mkdir()){
                        System.out.println("Foi criada uma diretoria parao cliente");
                    }
                }
                if (cliR.checkCliOnFile(argCommand.get(1)+ argCommand.get(2), "../SMDProject/servFolder/"+ myServ.getName() +".obj") == false) {
                    cliR= new ClientRegistry(message.gethBeat(),System.nanoTime());
                    cliR.writeObjectToFile("../SMDProject/servFolder/"+ myServ.getName() +".obj");
                    return "Registado com Sucesso.";

                }else if(cliR.checkCliOnFile(argCommand.get(1)+argCommand.get(2), "../SMDProject/servFolder/"+ myServ.getName() +".obj") == true){
                    return "Nao e possivel registar, Ja existe um User com este nome.";
                }

            } else if (argCommand.get(0).equalsIgnoreCase("LOGIN")) {
                if (cliR.checkCliOnFile(argCommand.get(1)+ argCommand.get(2), "../SMDProject/servFolder/"+ myServ.getName() +".obj") == true) {
                    File dir = new File("../SMDProject/cliFolders/" + argCommand.get(1));
                    if (!dir.exists()) {
                        return "A conteceu um erro grave,alguem removeu a sua diretoria!";
                    }
                    else{
                        cliR= new ClientRegistry(message.gethBeat(),System.nanoTime());
                        myServ.addClientToArray(cliR);
                        return "Login efectuado com sucesso!";
                    }
                }else if (cliR.checkCliOnFile(argCommand.get(1)+ argCommand.get(2), "../SMDProject/servFolder/"+ myServ.getName() +".obj") == false) {
                    return "Utilizador nao registado ou com credenciais erradas";
                }



            } else if (argCommand.get(0).equalsIgnoreCase("SHOWDIR")){
                //mostrar todos os conteudos da diretoria
                //mais notas:
                //(so da diretoria dos cliente em questao, nao pode ser visivel as diretorias dos outros clientes)
                //Comando SHOWDIR mostra a pasta base
                // Comando SHOWDIR+"espaço"+"caminho" mostra os doc's nessa pasta especifica

                if(argCommand.size() == 2) {
                    String path = "../SMDProject/cliFolders/";
                    path = path.concat(argCommand.get(1));

                    System.out.println(path);

                    File dir = new File(path);
                    String[] directories = dir.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File current, String name) {
                            return new File(current, name).isDirectory();
                        }
                    });

                    return Arrays.toString(directories);

                }



            }else if (argCommand.get(0).equalsIgnoreCase("SHOWFILES")){ // TODO não sei o que se passa com isto

                if (argCommand.size()==2){

                    String response = new String();

                    String path = "../SMDProject/cliFolders/";
                    path = path.concat(argCommand.get(1));
                    path = path.concat("/");

                    System.out.println(path);

                    File dir = new File(path);
                    String[] files = dir.list();

                    if (files.length == 0) {
                        System.out.println("The directory is empty");
                    } else {
                        for (String aFile : files) {
                            System.out.println(aFile);
                            response = response.concat(aFile);
                        }

                        return response;
                    }


                }

            }else if (argCommand.get(0).equalsIgnoreCase("NEWFILE")){

                if (argCommand.size()==3) {



                    System.out.println(argCommand.get(0)+argCommand.get(1)+argCommand.get(2));

                    String path = "../SMDProject/cliFolders/";
                    path = path.concat(argCommand.get(2)); // Monta o caminho com o Username
                    path = path.concat("/");

                    System.out.println(path);

                    File file = new File(path + argCommand.get(1) + ".txt");

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("Estou a escrever num ficheiro novo");
                    bw.close();

                    String response = "Ficheiro criado com sucesso!";

                    return response;

                }

            }

            else if (argCommand.get(0).equalsIgnoreCase("UPLOAD")){
                //TODO upload ficheiro, utilizar protocolo TCP

            }else if (argCommand.get(0).equalsIgnoreCase("DOWNLOAD")){
                //TODO download ficheiro, utilizar protocolo TCP

            } else if (argCommand.get(0).equalsIgnoreCase("MOVFILE")){
                //implementar movimentaçao de ficheiros entre diretorias
                //MOVFILE+"espaço"+"caminho"+"espaço"+"caminho"



            }else if (argCommand.get(0).equalsIgnoreCase("COPYFILE")){
                //implementar copia de ficheiros entre diretorias
                //COPYFILE+"espaço"+"caminho"+"espaço"+"caminho"

                String originDir = argCommand.get(1);
                String destinationDir = argCommand.get(2);
                String username = argCommand.get(3);

                String originFullPath = "../SMDProject/cliFolders/";
                originFullPath = originFullPath.concat(username);
                originFullPath = originFullPath.concat("/");
                originFullPath = originFullPath.concat(originDir);
                originFullPath = originFullPath.concat(".txt");

                String destinationFullPath = "../SMDProject/cliFolders/";
                destinationFullPath = destinationFullPath.concat(username);
                destinationFullPath = destinationFullPath.concat("/");
                destinationFullPath = destinationFullPath.concat(destinationDir);
                destinationFullPath = destinationFullPath.concat("/");
                destinationFullPath = destinationFullPath.concat(originDir);
                destinationFullPath = destinationFullPath.concat(".txt");

                System.out.println("Origem:\n" + originFullPath);
                System.out.println("Destino:\n" + destinationFullPath);

                InputStream inStream = null;
                OutputStream outStream = null;

                try{

                    inStream = new FileInputStream(originFullPath);
                    File file = new File(originFullPath);

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    outStream = new FileOutputStream(destinationFullPath);
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
        return null;
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

/*

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
*/









}
