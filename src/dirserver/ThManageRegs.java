package dirserver;

import common.registry.ServerRegistry;

import java.util.ArrayList;

/**
 * Created by Samuel on 07/01/2017.
 */
public class ThManageRegs extends Thread{
    private ArrayList<ServerRegistry> myRegistries;
    ThManageRegs(ArrayList<ServerRegistry> serverRegistries){
        myRegistries = serverRegistries;
    }

    @Override
    public void run() {
        System.out.println("batats");
    }
}
