package dirserver;

import dirserver.*;
import common.CliRegistry;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Samuel on 30/11/2016.
 */
public class ThAnswerCommand extends Thread {
    public static final int MAX_SIZE = 1024;
    DatagramSocket socket=null;
    DatagramPacket packetRead,packetWrite;
    String commandStr;
    ArrayList<String> argCommand;
    DirectoryServer ServL;


    ThAnswerCommand(DatagramSocket s,DirectoryServer SerVList){
        this.socket=s;
        this.ServL=SerVList;
        packetRead = new DatagramPacket(new byte[MAX_SIZE],0, MAX_SIZE);
    }

 @Override
    public void run() {
        while(true) {
            try {
                //TODO fazer um switch para cada tipo de resposta
                socket.receive(packetRead);
                argCommand=new ArrayList<>();
                System.out.println("recebi");// test
               // String answer = new String(packetRead.getData(),0,packetRead.getLength());

                commandStr = new String(packetRead.getData(),0,packetRead.getLength());
                StringTokenizer tok = new StringTokenizer(commandStr," ");

                while (tok.hasMoreTokens()){
                    String token = tok.nextToken();
                    argCommand.add(token);
                }
                //---------------------- trata comandos
                if(argCommand.get(0).equalsIgnoreCase("REGISTER")){
                    CliRegistry cli = new CliRegistry(argCommand.get(1)+argCommand.get(2),111,222,333);
                    cli.writeObjectToFile();
                    packetWrite = new DatagramPacket("Registered successfully\0".getBytes(), "Registered successfully\0".length(), packetRead.getAddress(),packetRead.getPort()); //Create a Packet
                    socket.send(packetWrite);
//                    System.out.println(answer);// test

                    continue;

                }
                else if(argCommand.get(0).equalsIgnoreCase("SLIST")){
                    System.out.println(ServL.getListServ());
                    packetWrite =new DatagramPacket((ServL.getListServ()).getBytes(),(ServL.getListServ()).length(),packetRead.getAddress(),packetRead.getPort());
                    socket.send(packetWrite);
                    continue;
                }





                System.out.println(ServL.getListServ());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


/*
    @Override
    public void run() {
        while(true) {
            try {
                //TODO fazer um switch para cada tipo de resposta

                socket.receive(packetRead);
                System.out.println("recebi");
                String answer = new String(packetRead.getData());
                packetWrite = new DatagramPacket("this is the anwser".getBytes(), "this is the anwser".length(), packetRead.getAddress(),packetRead.getPort()); //Create a Packet
                socket.send(packetWrite);
                System.out.println(answer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
*/

