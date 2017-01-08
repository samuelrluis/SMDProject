package remoteserver;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

/**
 * Created by MarceloCortesao on 07/01/17.
 */
public class DownloadFileService  extends UnicastRemoteObject implements RemoteFileInterface
{
    public static final String SERVICE_NAME = "GetRemoteFile";
    public static final int MAX_CHUNCK_SIZE = 10000; //bytes

    protected File localDirectory;

    public DownloadFileService (File localDirectory) throws RemoteException
    {
        this.localDirectory = localDirectory;
    }

    public byte [] getFileChunk(String fileName, long offset) throws RemoteException
    {
        String requestedCanonicalFilePath = null;
        FileInputStream requestedFileInputStream = null;
        byte [] fileChunk = new byte[MAX_CHUNCK_SIZE];
        int nbytes;

        fileName = fileName.trim();
        //System.out.println("Recebido pedido para: " + fileName);

        try{


            requestedCanonicalFilePath = new File(localDirectory+File.separator+fileName).getCanonicalPath();

            if(!requestedCanonicalFilePath.startsWith(localDirectory.getCanonicalPath()+File.separator)){
                System.out.println("Nao e' permitido aceder ao ficheiro " + requestedCanonicalFilePath + "!");
                System.out.println("A directoria de base nao corresponde a " + localDirectory.getCanonicalPath()+"!");
                return null;
            }


            requestedFileInputStream = new FileInputStream(requestedCanonicalFilePath);
            System.out.println("Ficheiro " + requestedCanonicalFilePath + " aberto para leitura.");


            requestedFileInputStream.skip(offset);
            nbytes = requestedFileInputStream.read(fileChunk);

            if(nbytes == -1){//EOF
                return null;
            }


            if(nbytes < fileChunk.length){

                byte [] aux = new byte[nbytes];
                System.arraycopy(fileChunk, 0, aux, 0, nbytes);

                return aux;

            }

            return fileChunk;

        }catch(FileNotFoundException e){   //Subclasse de IOException
            System.out.println("Ocorreu a excepcao {" + e + "} ao tentar abrir o ficheiro " + requestedCanonicalFilePath + "!");
        }catch(IOException e){
            System.out.println("Ocorreu a excepcao de E/S: \n\t" + e);
        }finally{
            if(requestedFileInputStream != null){
                try {
                    requestedFileInputStream.close();
                } catch (IOException e) {}
            }
        }

        return null;
    }

    /*
     * Lanca e regista um servico com interface remota do tipo GetRemoteFileInterface
     * sob o nome dado pelo atributo estatico SERVICE_NAME.
     */

}
