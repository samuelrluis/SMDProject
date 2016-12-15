package dirserver;

import common.CliRegistry;
import common.Registries;
import common.ServerRegistry;

import java.util.ArrayList;

/**
 * Created by Samuel on 26/11/2016.
 */
public class ThManageServerRegs extends Thread {
    ArrayList<ServerRegistry> serverRegistries = null;

    ThManageServerRegs(ArrayList<ServerRegistry> regServers){
        serverRegistries=regServers;
    }

    @Override
    public void run() {
        while(true){
            if(serverRegistries.size()>0){
                System.out.println("Registos Server");
                for(int i=0;i<serverRegistries.size();i++){
                    System.out.println(serverRegistries.get(i));
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
