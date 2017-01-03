package dirserver;

import common.*;
import common.heartbeat.ClientHeartBeat;
import common.heartbeat.ServerHeartBeat;
import common.registry.ClientRegistry;
import common.registry.ServerRegistry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by MarceloCortesao on 15/12/16.
 */
public class DirectoryServerController {

    //DirServer
    private DirectoryServer Serv;

    private DatagramPacket packetRead;
    private DatagramPacket packetWrite;
    private DatagramSocket socket = null;

    public DirectoryServerController(DirectoryServer x){
        Serv=x;
        socket=Serv.getSocketUDP();
        packetRead=Serv.getPacket();
    }

    private void receivedCommand(Msg message) {
        ArrayList<String> argCommand = new ArrayList<>();

        String commandStr = message.getCommand();
        ClientHeartBeat hBeat = message.gethBeat();
        StringTokenizer tok = new StringTokenizer(commandStr, " ");

        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            argCommand.add(token);
        }
        //--------------------------- Trata comandos ----------------------------//
        try {
            if (argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                ClientRegistry cli = new ClientRegistry(hBeat, 333);
                cli.writeObjectToFile("../SMDProject/src/dirserver/savefiles/saveCliRegistry.obj");
                packetWrite = new DatagramPacket("Registered successfully".getBytes(), "Registered successfully".length(), packetRead.getAddress(), packetRead.getPort()); //Create a Packet
                socket.send(packetWrite);
            } else if (argCommand.get(0).equalsIgnoreCase("LOGIN")) {
                ClientRegistry cli = new ClientRegistry();
                if (cli.checkCliOnFile(argCommand.get(1) + argCommand.get(2), "../SMDProject/src/dirserver/savefiles/saveCliRegistry.obj") == true) {
                    packetWrite = new DatagramPacket("Login successfully".getBytes(), "Login successfully".length(), packetRead.getAddress(), packetRead.getPort());
                } else if (cli.checkCliOnFile(argCommand.get(1) + argCommand.get(2), "../SMDProject/src/dirserver/savefiles/saveCliRegistry.obj") == false) {
                    packetWrite = new DatagramPacket("Login failed".getBytes(), "Login failed".length(), packetRead.getAddress(), packetRead.getPort());
                }
                socket.send(packetWrite);
            } else if (argCommand.get(0).equalsIgnoreCase("SLIST")) {
                System.out.println("List of Servers \n" + Serv.getListServ());
                packetWrite = new DatagramPacket((Serv.getListServ()).getBytes(), (Serv.getListServ()).length(), packetRead.getAddress(), packetRead.getPort());
                socket.send(packetWrite);
            } else if (argCommand.get(0).equalsIgnoreCase("CLIST")) {
                System.out.println("List of Clients \n" + Serv.getListClient());
                packetWrite = new DatagramPacket((Serv.getListClient()).getBytes(), (Serv.getListClient()).length(), packetRead.getAddress(), packetRead.getPort());
                socket.send(packetWrite);
            }else if(argCommand.get(0).equalsIgnoreCase("CHAT")){

                    int port = 0;
                    if(argCommand.get(1).equalsIgnoreCase("ALL")){
                        for(int i=0;i<Serv.getCliRegistries().size();i++){
                            port = Serv.getCliRegistries().get(i).gethBeat().getReaderPort();
                            System.out.println(port);
                            packetWrite = new DatagramPacket((argCommand.get(2).getBytes()), (argCommand.get(2).length()), packetRead.getAddress(),port);
                            socket.send(packetWrite);
                        }
                    }else {
                        for (int i = 0; i < Serv.getCliRegistries().size(); i++) {
                            if (argCommand.get(1).equalsIgnoreCase(Serv.getCliRegistries().get(i).getName())){
                                port = Serv.getCliRegistries().get(i).gethBeat().getReaderPort();
                                packetWrite = new DatagramPacket((argCommand.get(2).getBytes()), (argCommand.get(2).length()), packetRead.getAddress(), port);
                                socket.send(packetWrite);
                            }
                        }
                    }



            }

            else if (argCommand.get(0).equalsIgnoreCase("CONNECT")) {

                ArrayList<ServerRegistry> serverRegistries = Serv.getServerRegistries();

                try {

                    int wantedServerTcp = serverRegistries.get((Integer.parseInt(argCommand.get(2)))).gethBeat().getTcpPort();
                    String wantedServer = wantedServerTcp + "";
                    packetWrite = new DatagramPacket(wantedServer.getBytes(), wantedServer.length(), packetRead.getAddress(), packetRead.getPort());
                    System.out.println(wantedServer.toString());
                    socket.send(packetWrite);

                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Server not found");
                    socket.send(packetWrite);
                }
                //TODO com isto temos todos os comandos do servDiretoria implementados :)
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receivedHeartBeatClient(ClientHeartBeat hBeat) {
        int i=0;
        Boolean foundReg=false;
        ArrayList<ClientRegistry> cliRegestries = Serv.getCliRegistries();
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
        ArrayList<ServerRegistry> serverRegistries = Serv.getServerRegistries();

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

    public String getListServ(){
        int x=0;
        StringBuilder List = new StringBuilder();

        if(Serv.getServerRegistries().size()>0){
            for(int i = 0;i<Serv.getServerRegistries().size();i++) {
                List.append(Serv.getServerRegistries().get(i).getName()+"\n");
            }
            return List.toString();
        }else
            return "No Server's Connected";
    }

    public String getListClients(){
        int x=0;
        StringBuilder List = new StringBuilder();
        for(int i = 0;i<Serv.getCliRegistries().size();i++) {
            List.append(Serv.getCliRegistries().get(i).getName()+"\n");
        }
        if (List==null)
            return "No Clients Connected";
        return List.toString();
    }
}
