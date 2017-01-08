package client;


import remoteserver.RemoteFileInterface;

import java.io.*;
import java.rmi.*;

/**
 * Created by MarceloCortesao on 07/01/17.
 */
public class DownloadFileClient {

    public void FileDownload(String local,String path,String fName) {

        String objectUrl;
        File localDirectory;
        String fileName;

        String localFilePath;
        FileOutputStream localFileOutputStream;

        byte [] b;
        long offset;

        objectUrl = "rmi://"+local+"/GetRemoteFile";
        localDirectory = new File(path.trim());
        fileName = fName.trim();

        if(!localDirectory.exists()){
            System.out.println("A directoria " + localDirectory + " nao existe!");
            return;
        }

        if(!localDirectory.isDirectory()){
            System.out.println("O caminho " + localDirectory + " nao se refere a uma directoria!");
            return;
        }
        if(!localDirectory.canWrite()){
            System.out.println("Sem permissoes de escrita na directoria " + localDirectory);
            return;
        }

        try{

            localFilePath = new File(localDirectory.getPath()+File.separator+fileName).getCanonicalPath();
            localFileOutputStream = new FileOutputStream(localFilePath);

            System.out.println("Ficheiro " + localFilePath + " criado.");

            RemoteFileInterface fileService = (RemoteFileInterface)Naming.lookup(objectUrl);


            offset = 0;

            while((b = fileService.getFileChunk(fileName, offset)) !=null ){
                localFileOutputStream.write(b);
                offset+=b.length;
            }

            localFileOutputStream.close();

            System.out.println("Transferencia do ficheiro " + fileName + " concluida.");

        }catch(RemoteException e){
            System.out.println("Erro remoto - " + e);
        }catch(NotBoundException e){
            System.out.println("Servico remoto desconhecido - " + e);
        }catch(IOException e){
            System.out.println("Erro E/S - " + e);
        }catch(Exception e){
            System.out.println("Erro - " + e);
        }
    }
}
