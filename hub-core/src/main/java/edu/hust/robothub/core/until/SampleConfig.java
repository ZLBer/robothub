package edu.hust.robothub.core.until;

public class SampleConfig extends Config {
    static volatile Config INSTANCE;


   public static Config getSelf() {

        if (INSTANCE == null) {
            synchronized (INSTANCE) {
                if (INSTANCE == null) {
                    INSTANCE = new SampleConfig();
                }
            }
        }
        return INSTANCE;
    }
}
