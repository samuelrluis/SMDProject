package dirserver;

import common.*;

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
public class ServerController {
    private DirectoryServer Serv;
    private DatagramPacket packetRead;
    private DatagramPacket packetWrite;
    private DatagramSocket socket;

    public ServerController(DirectoryServer x){
        Serv=x;
        socket=Serv.getSocketUDP();
        packetRead=Serv.getPacket();
    }

    private void updateRegistries(){

    }

    private void receivedCommand(Msg message){
        ArrayList<String> argCommand=new ArrayList<>();

        String commandStr = message.getCommand();
        ClientHeartBeat hBeat = message.gethBeat();
        StringTokenizer tok = new StringTokenizer(commandStr," ");

        while (tok.hasMoreTokens()){
            String token = tok.nextToken();
            argCommand.add(token);
        }
        //---------------------- trata comandos
        try{
            if(argCommand.get(0).equalsIgnoreCase("REGISTER")){
                CliRegistry cli = new CliRegistry(hBeat,333);
                cli.writeObjectToFile();
                packetWrite = new DatagramPacket("Registered successfully\0".getBytes(), "Registered successfully\0".length(), packetRead.getAddress(),packetRead.getPort()); //Create a Packet
                socket.send(packetWrite);
            }
            else if(argCommand.get(0).equalsIgnoreCase("LOGIN")) {
                //TODO falta implementar a verificaçao no ficheiro de registos
                packetWrite = new DatagramPacket("Login successfully\0".getBytes(), "Login successfully\0".length(), packetRead.getAddress(),packetRead.getPort());
                socket.send(packetWrite);
            }
            else if(argCommand.get(0).equalsIgnoreCase("SLIST")){
                System.out.println("List of Servers \n" + Serv.getListServ());
                packetWrite =new DatagramPacket((Serv.getListServ()).getBytes(),(Serv.getListServ()).length(),packetRead.getAddress(),packetRead.getPort());
                socket.send(packetWrite);
            }
            else if(argCommand.get(0).equalsIgnoreCase("CLIST")){
                System.out.println("List of Clients \n" + Serv.getListClient());
                packetWrite =new DatagramPacket((Serv.getListClient()).getBytes(),(Serv.getListClient()).length(),packetRead.getAddress(),packetRead.getPort());
                socket.send(packetWrite);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receivedHeartBeatClient(ClientHeartBeat hBeat) {
        int i=0;
        Boolean foundReg=false;
        ArrayList<CliRegistry> cliRegestries = Serv.getCliRegistries();
        if(cliRegestries.size()==0)
            cliRegestries.add(new CliRegistry(hBeat, System.nanoTime()));

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
                cliRegestries.add(new CliRegistry(hBeat, System.nanoTime()));
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
        //This is the principal function in our DirServer Controller;
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
