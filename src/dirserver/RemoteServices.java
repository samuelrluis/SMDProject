package dirserver;

/**
 * Created by Samuel on 02/01/2017.
 */
public interface RemoteServices extends java.rmi.Remote {
    public String getListServRMI() throws java.rmi.RemoteException;
}
