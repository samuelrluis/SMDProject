package dirserver;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Samuel on 02/01/2017.
 */
public class DirectoryServerRMI extends java.rmi.server.UnicastRemoteObject
    implements RemoteServices{

    private DirectoryServerController myServerController = null;
    private ArrayList<ServerListener> listenersList = null;

    protected DirectoryServerRMI(DirectoryServerController controller) throws RemoteException {
        myServerController = controller;
        listenersList = new ArrayList<ServerListener>();
    }

    @Override
    public String getListServRMI() throws RemoteException {
        return myServerController.getListServ();
    }

    @Override
    public void addListener(ServerListener listener) throws RemoteException {
        listenersList.add(listener);
        notifyListeners();
    }

    public void notifyListeners() throws RemoteException{
        //System.out.println("Observadores: " + observers.size());
        for (int i=0;i < listenersList.size(); i++){
            if(listenersList.get(i)!=null){
                try{
                    listenersList.get(i).printServersList();
                }catch (ConnectException e){
                    continue; //If some Listener quit
                }
            }
        }
    }
}
