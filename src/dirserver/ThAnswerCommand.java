package dirserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Samuel on 30/11/2016.
 */
public class ThAnswerCommand extends Thread {
    public static final int MAX_SIZE = 256;
    DatagramSocket socket=null;
    DatagramPacket packetRead,packetWrite;

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
