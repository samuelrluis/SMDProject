package dirserver;

import common.CliRegistry;
import common.Registries;
import common.ServerRegistry;

import java.util.ArrayList;

/**
 * Created by Samuel on 26/11/2016.
 */
public class ThManageRegs extends Thread {
    ArrayList<CliRegistry> cliRegistries = null;
    ArrayList<ServerRegistry> serverRegistries = null;

    ThManageRegs(ArrayList<CliRegistry> regClients, ArrayList<ServerRegistry> regServers){
        cliRegistries=regClients;
        serverRegistries=regServers;

    }

    @Override
    public void run() {
        while(true){
            if(regList.size()>0){
                System.out.println("Registos");
                for(int i=0;i<regList.size();i++){
                    System.out.println(regList.get(i));
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
