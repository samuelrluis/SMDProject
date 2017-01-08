package dirserver;

/**
 * Created by Samuel on 08/01/2017.
 */
public interface ServerListener extends java.rmi.Remote {
    void printServersList() throws java.rmi.RemoteException;
}
