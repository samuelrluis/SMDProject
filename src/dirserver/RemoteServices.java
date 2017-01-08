package dirserver;

/**
 * Created by Samuel on 02/01/2017.
 */
public interface RemoteServices extends java.rmi.Remote {
    String getListServRMI() throws java.rmi.RemoteException;
    void addListener(ServerListener listener) throws java.rmi.RemoteException;
    void removeListener(ServerListener listener) throws java.rmi.RemoteException;
}
