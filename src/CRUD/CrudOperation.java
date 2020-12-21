package CRUD;

import java.util.HashMap;

public class CrudOperation {


    /**
     * helper function to print data
     * @param t T generic
     * @param <T> T generic
     * @return the string that will be send or show
     */
    public final <T> String show(T t){
        return t.toString();
    }



    /**
     * helper function to modify data
     * @param db the data that we stored it
     * @param key the id of the object we want to modify
     * @param newDta the new data which will be set
     * @param <T> T generic
     * @return a boolean value (true/false)
     */
    public <T> boolean modify(HashMap<String, T> db, String key, T newDta){
        return db.replace(key, db.get(key), newDta);
    }




    /**
     * helper function to delete some data.
     * @param db the data that we stored it
     * @param key the id of the object we want to modify
     * @param <T> T generic
     * @return a boolean value (true/false)
     */
    public <T> boolean delete(HashMap<String, T> db, String key){
        return db.remove(key, db.get(key));
    }





}
