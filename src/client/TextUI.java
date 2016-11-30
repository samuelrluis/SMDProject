package client;

import common.UserID;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by diogomiguel on 25/11/16.
 */
public class TextUI {

    String line;
    String args[];
    UserID user;

    TextUI(ArrayList<String> args[]){

        if (args.length <1 || args.equals("HELP")) {
            readObjectFromFile("..\\src\\client\\manual.txt");
            System.out.println("Manual");
            return;
        }

        //if (args[0].toUpperCase().equals("LOGIN"))
        //    if (user.verifyLogin(args[1],args[2])==true)
        //        System.out.println("Op Logi");

        //if (args[0].toUpperCase().equals("SLIST")){

        //}
    }

    public static void showTheAnswer (){
        // Trata respostas do Serv

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
