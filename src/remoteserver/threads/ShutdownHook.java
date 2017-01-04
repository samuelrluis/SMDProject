package remoteserver.threads;

/**
 * Created by diogomiguel on 04/01/17.
 */
public class ShutdownHook extends Thread{

    @Override
    public void run()
    {
            System.out.println("Bye!");
    }


}
