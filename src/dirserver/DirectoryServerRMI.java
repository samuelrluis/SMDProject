package dirserver;

import java.rmi.RemoteException;

/**
 * Created by Samuel on 02/01/2017.
 */
public class DirectoryServerRMI extends java.rmi.server.UnicastRemoteObject
    implements RemoteServices{


    protected DirectoryServerRMI() throws RemoteException {

    }

    @Override
    public String getListServRMI() throws RemoteException {
        return "teste RMI";
    }
}
