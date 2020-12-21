package Client;

import java.io.IOException;

public class Main {


    public static void main(String... args)  {
        try {
            (new Client()).start();
        } catch (IOException e) {
            System.out.println("\n connection closed.... ==>" + e.getMessage());
            System.exit(0);
        }

    }



}
