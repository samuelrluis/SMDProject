package dirserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Samuel on 30/11/2016.
 */
public class ThAnswerCommand extends Thread {
    public static final int MAX_SIZE = 256;
    DatagramSocket socket=null;
    DatagramPacket packetRead,packetWrite;
    String commandStr;
    ArrayList<String> argCommand = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    ThAnswerCommand(DatagramSocket s){
        this.socket=s;
        packetRead = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
    }

 @Override
    public void run() {
        while(true) {
            try {
                //TODO fazer um switch para cada tipo de resposta
                socket.receive(packetRead);
                System.out.println("recebi");// teste
                String answer = new String(packetRead.getData());
                commandStr = new String(packetRead.getData());
                StringTokenizer tok = new StringTokenizer(commandStr," ");
                while (tok.hasMoreTokens()){
                    String token = tok.nextToken();
                    argCommand.add(token);
                }
                //---------------------- trata comandos
                System.out.println(argCommand.get(0));//teste
                System.out.println(argCommand.get(1));//teste
                System.out.println(argCommand.get(2));//teste
                if(argCommand.get(0).equalsIgnoreCase("REGISTER")){
                    ServerRegistry cli = new ServerRegistry(argCommand.get(1)+argCommand.get(2),111,222,333); // teste, guardar udp/tcp/entry certos
                    cli.writeObjectToFile();
                    packetWrite = new DatagramPacket("Registered successfully".getBytes(), "Registered successfully".length(), packetRead.getAddress(),packetRead.getPort()); //Create a Packet
                    socket.send(packetWrite);
                    System.out.println(answer);// teste
                    continue;
                }
                else
                    continue;


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

