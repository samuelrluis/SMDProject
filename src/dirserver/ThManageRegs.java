package dirserver;

import common.registry.ServerRegistry;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
        int seconds= 0;
        while(true) {
            try {
                Thread.sleep(1000);

                for (int i = 0; i < myRegistries.size(); i++) {
                    if(!compareTime(myRegistries.get(i).getEntryTime())){
                        myRegistries.remove(i);
                        System.out.println("foi removido" + myRegistries.get(i).getName());
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean compareTime(long entryTime){
        long entryInSeconds;
        long timeNowInSeconds;
        long diffInSeconds;

        entryInSeconds = TimeUnit.SECONDS.convert(entryTime, TimeUnit.NANOSECONDS);
        timeNowInSeconds = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

        diffInSeconds = timeNowInSeconds - entryInSeconds;

        if(diffInSeconds > 33){
            return false;
        }

        return true;
    }
}
