package client;

import common.UserID;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by diogomiguel on 25/11/16.
 */
public class ThTextUI extends Thread {
    Client myClient;
    UserID myUserID;

    ThTextUI(Client x){
        myClient=x;
    }

    @Override
    public void run() {
        String commandStr;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        myUserID=myClient.getMyUserID();

        while(true){
            ArrayList<String> argCommand = new ArrayList<>();
            int x=0;
            System.out.println("Command: ");
            System.out.flush();
            try {
                commandStr = br.readLine();

            StringTokenizer tok = new StringTokenizer(commandStr," ");

            while (tok.hasMoreTokens()){
                String token = tok.nextToken();
                argCommand.add(token);
            }
                if(argCommand.get(0).equalsIgnoreCase("EXIT"))
                    break;
                else if(argCommand.get(0).equalsIgnoreCase("HELP")) {
                    System.out.println("Manual");
                    readObjectFromFile("..\\manual.txt");
                }else if(argCommand.get(0).equalsIgnoreCase("USER")){//teste
                    System.out.println(myClient.getMyUserID().toString());
                }else if(argCommand.get(0).equalsIgnoreCase("LOGIN")){

                }else if(argCommand.get(0).equalsIgnoreCase("REGISTER")){
                    myUserID.setUsername(argCommand.get(1));
                    myUserID.setPassword(argCommand.get(2));
                }
            } catch (IOException e) {
                System.out.printf("Não foi encontrado o ficheiro do Manual");
            }
        }
    }

    public static Object readObjectFromFile(String filename) {
        Object object = null;

        try {
            InputStream inputStream = new BufferedInputStream(new
                    FileInputStream(filename));
            ObjectInput objectInput = new ObjectInputStream(inputStream);
            object = objectInput.readObject();
            objectInput.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return object;
    }
}
