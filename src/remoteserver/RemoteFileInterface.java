package remoteserver;

/**
 * Created by MarceloCortesao on 24/12/16.
 */

public interface RemoteFileInterface extends java.rmi.Remote
{
    public byte [] getFileChunk(String fileName, long offset) throws java.rmi.RemoteException;

}
