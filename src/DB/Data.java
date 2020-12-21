package DB;

import Bean.Offer;
import Bean.RegistrationClient;
import Bean.RegistrationObject;

import java.util.HashMap;

public class Data {
    public static HashMap<String, RegistrationClient> users = new HashMap<>();
    public static HashMap<String, RegistrationObject> objects = new HashMap<>();
    public static HashMap<String, Offer> offer = new HashMap<>();
    public static long timer = 60 * 60; // for un hour
    public static final long timeEnd = (59 * 60); // for minute
    public static Offer winner = null;


    public static synchronized void add(String key, RegistrationObject object){
        if ((!Data.objects.containsKey(key))) {
            Data.objects.put(key, object);
        }
    }


    public static synchronized void add(String key, RegistrationClient client){
        if ((!Data.users.containsKey(key))) {
            Data.users.put(key, client);
        }
    }


    public static synchronized void add(String price, Offer offer){
        if ((!Data.offer.containsKey(price))) {
            Data.offer.put(price, offer);
        }
    }



}
