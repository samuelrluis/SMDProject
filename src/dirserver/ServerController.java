package dirserver;

import common.*;

import javax.xml.crypto.Data;
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

    private void receivedCommand(Message message){
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
                System.out.println("List de Servers" + Serv.getListServ());
                packetWrite =new DatagramPacket((Serv.getListServ()).getBytes(),(Serv.getListServ()).length(),packetRead.getAddress(),packetRead.getPort());
                socket.send(packetWrite);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receivedHeartBeatClient(ClientHeartBeat hBeat){
        ArrayList <CliRegistry> cliRegistries = Serv.getCliRegistries();

        if(cliRegistries.size()==0)
            cliRegistries.add(new CliRegistry(hBeat, System.nanoTime()));
        else{
            for (int i = 0; i < cliRegistries.size(); i++) {
                if (cliRegistries.get(i).getName().equalsIgnoreCase(hBeat.getName()))
                    cliRegistries.get(i).setEntryTime();
                else
                    cliRegistries.add(new CliRegistry(hBeat, System.nanoTime()));
            }
        }
    }

    private void receiveHeartBeatServer(ServerHeartBeat hBeat ){
        ArrayList<ServerRegistry> serverRegistries = Serv.getServerRegistries();

        if(serverRegistries.size()==0)
            serverRegistries.add(new ServerRegistry(hBeat, System.nanoTime()));
        else{
            for (int i = 0; i < serverRegistries.size(); i++) {
                if (serverRegistries.get(i).gethBeat().getName().equalsIgnoreCase(hBeat.getName())){
                    serverRegistries.get(i).setEntryTime();
                } else{
                    serverRegistries.add(new ServerRegistry(hBeat, System.nanoTime()));
                }
            }
        }
    }

    public void answeringDatagram(){
        //This is the principal function in our DirServer Controller;
        while(true){
            try{
                socket.receive(packetRead);
                ByteArrayInputStream Bin = new ByteArrayInputStream(packetRead.getData());
                ObjectInputStream in = new ObjectInputStream(Bin);

                Object message = in.readObject();

                if(message instanceof ServerHeartBeat)
                    receiveHeartBeatServer((ServerHeartBeat) message); //This will receive th HB from Server
                else if(message instanceof ClientHeartBeat)
                    receivedHeartBeatClient((ClientHeartBeat) message); //This will receive th HB from Client
                else if(message instanceof Message)
                    receivedCommand((Message) message);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getListServ(){
        int x=0;
        StringBuilder List = new StringBuilder();
        for(int i = 0;i<Serv.getServerRegistries().size();i++) {
            List.append(Serv.getServerRegistries().get(i).getName()+"\n");
        }
        if (List==null)
            return "No Server's Connected";
        return List.toString();
    }

    public String getListClients(){
        int x=0;
        StringBuilder List = new StringBuilder();
        for(int i = 0;i<Serv.getCliRegistries().size();i++) {
            List.append(Serv.getCliRegistries().get(i).getName()+"\n");
        }
        if (List==null)
            return "No Server's Connected";
        return List.toString();
    }

}
