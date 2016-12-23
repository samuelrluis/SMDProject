package remoteserver;

import common.*;
import dirserver.DirectoryServer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by MarceloCortesao on 21/12/16.
 */
public class RemoteServController {

    private DirectoryServer Serv;
    private DatagramPacket packetRead;
    private DatagramPacket packetWrite;
    private DatagramSocket socket;

    public RemoteServController(DirectoryServer x){
        Serv=x;
        socket=Serv.getSocketUDP();
        packetRead=Serv.getPacket();
    }

    private void receivedCommand(Msg message) {
        ArrayList<String> argCommand = new ArrayList<>();

        String commandStr = message.getCommand();
        ClientHeartBeat hBeat = message.gethBeat();
        StringTokenizer tok = new StringTokenizer(commandStr, " ");

        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            argCommand.add(token);
        }
        //---------------------- trata comandos ----------------------
        try {
            if (argCommand.get(0).equalsIgnoreCase("REGISTER")) {
                //TODO implementar registos dos clientes neste servidor remoto,
                // TODO(temos de decidir se usamos o mesmo mecanismo que no servDiretoria e gravamos no ficheiro ou se usamos so um arrayList)

            } else if (argCommand.get(0).equalsIgnoreCase("LOGIN")) {
                //TODO falta implementar a verificaçao no ficheiro de registos
                //mais notas:
                //TODO A quando o login tem de ser verificado se exite ja uma diretoria do respetivo cliente,
                //TODO se nao existir tem de ser criada, se existir é aberta/mostrada a area de trabalho desse cliente

            } else if (argCommand.get(0).equalsIgnoreCase("SHOWDIR")){
                //TODO mostrar todos os conteudos da diretoria
                //mais notas:
                //TODO (so da diretoria dos cliente em questao, nao pode ser visivel as diretorias dos outros clientes)
                //TODO Comando SHOWDIR mostra a pasta base
                //TODO Comando SHOWDIR+"espaço"+"caminho" mostra os doc's nessa pasta especifica

            }else if (argCommand.get(0).equalsIgnoreCase("UPLOAD")){
                //TODO upload ficheiro, utilizar protocolo TCP

            }else if (argCommand.get(0).equalsIgnoreCase("DOWNLOAD")){
                //TODO download ficheiro, utilizar protocolo TCP

            } else if (argCommand.get(0).equalsIgnoreCase("MOVFILE")){
                //TODO implementar movimentaçao de ficheiros entre diretorias
                //TODO MOVFILE+"espaço"+"caminho"+"espaço"+"caminho"

            }else if (argCommand.get(0).equalsIgnoreCase("COPYFILE")){
                //TODO implementar copia de ficheiros entre diretorias
                //TODO COPYFILE+"espaço"+"caminho"+"espaço"+"caminho"

            }else if (argCommand.get(0).equalsIgnoreCase("DELFILE")) {
                //TODO eliminar ficheiro

            }else if (argCommand.get(0).equalsIgnoreCase("DELDIR")) {
                //TODO eliminar ficheiro
                //TODO ATENÇAO SO VALIDO PARA DIRETORIAS VAZIAS

            }else if (argCommand.get(0).equalsIgnoreCase("RENAMEFILE")) {
                //TODO mudar nome de ficheiro
                //TODO RENAMEFILE+"espaço"+"nomeFile"+"espaço"+"nomeFile"

            }else if (argCommand.get(0).equalsIgnoreCase("RENAMEDIR")) {
                //TODO mudar nome de diretoria
                //TODO RENAMEDIR+"espaço"+"nomeDir"+"espaço"+"nomeDir"
            }else if (argCommand.get(0).equalsIgnoreCase("CREATEDIR")) {
                //TODO mudar nome de diretoria
                //TODO RENAMEDIR+"espaço"+"nomeDir"
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }








}
