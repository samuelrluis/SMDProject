package dirserver;

import java.rmi.RemoteException;

/**
 * Created by Samuel on 02/01/2017.
 */
public class DirectoryServerRMI extends java.rmi.server.UnicastRemoteObject
    implements RemoteServices{

    DirectoryServerController myServerController = null;


    protected DirectoryServerRMI(DirectoryServerController controller) throws RemoteException {
        myServerController = controller;
    }

    @Override
    public String getListServRMI() throws RemoteException {
        return myServerController.getListServ();
    }
}
