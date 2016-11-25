package dirserver;

/**
 * Created by Samuel on 25/11/2016.
 */
public abstract class Registries {
    String name;
    int entryTime;
    int udpPort,tcpPort;

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name+" "+udpPort+" "+tcpPort;
    }
}
