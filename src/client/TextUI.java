package client;

import common.UserID;

import java.io.*;

/**
 * Created by diogomiguel on 25/11/16.
 */
public class TextUI {

    String line;
    String args[];
    UserID user;

    TextUI(String args[]){

        if (args.length <1 || args[0].toUpperCase().equals("HELP")) {
            //readObjectFromFile("/Users/diogomiguel/GitHub/SMDProject/src/client/manual.txt");
            System.out.println("Manual");
            return;
        }

        if (args[0].toUpperCase().equals("LOGIN"))
            if (user.verifyLogin(args[1],args[2])==true);

        if (args[0].toUpperCase().equals("SLIST"));





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
