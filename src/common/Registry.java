package common;

/**
 * Created by Samuel on 25/11/2016.
 */
public abstract class Registry {
    String name;
    long entryTime;

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setEntryTime() {
        this.entryTime = System.nanoTime();
    }
}
