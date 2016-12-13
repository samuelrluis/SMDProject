package dirserver;

import common.Registries;

import java.util.ArrayList;

/**
 * Created by Samuel on 26/11/2016.
 */
public class ThManageRegs extends Thread {
    ArrayList<Registries> regList=null;

    ThManageRegs(ArrayList<Registries> reg){
        regList=reg;
    }

    @Override
    public void run() {
        while(true){
            if(regList.size()>0){
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
    public String getListServ (){
        int x=0;
        String List = null;
        while(regList.get(x)!=null){
            List += regList.get(x).getName()+ "\t";
        }
        return List;
    }
}
