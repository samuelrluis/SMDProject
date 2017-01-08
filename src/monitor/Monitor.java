package monitor;

import dirserver.RemoteServices;
import dirserver.ServerListener;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Samuel on 08/01/2017.
 */
public class Monitor extends UnicastRemoteObject
    implements ServerListener{

    private static RemoteServices monitor = null;

    public Monitor() throws RemoteException {}

    @Override
    public void printServersList() throws RemoteException {

    }

    public static void main(String[] args) throws RemoteException{
        Monitor monitorServers = new Monitor();

        if (args.length != 1) {
            System.out.println("Sintax Error monitor.Monitor [DirectoryServerIP]");
            return;
        }

        String dirAddr = args[0];

        try{
            String registration ="rmi://"+dirAddr+"/ServerListener";
            Remote remoteInterface = Naming.lookup(registration);
            monitor = (RemoteServices)remoteInterface;
            monitor.addListener(monitorServers);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }


    }
}
