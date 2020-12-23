package Server;

import Bean.Offer;
import Bean.RegistrationObject;
import DB.Data;
import MangeRequest.MangeRequestByThreads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Server {

    private static Server INSTANCE = null;
    private final ServerSocket serverSocket;
    private boolean isAlive;
    private final List<MangeRequestByThreads> listOfThread = new ArrayList<>();

    private Server() throws IOException {
        this.serverSocket = new ServerSocket(2020);
        System.out.println("Server listen on port 2020....");
        this.isAlive = true;
        this.populate();
        this.startTimer();
        this.checkTime(); // countdown
    }



    public void start() throws IOException {
        while (this.isAlive) {
            Socket socket = this.serverSocket.accept();
            System.out.println("New Connection " + socket.toString());

            MangeRequestByThreads mangeRequest = new MangeRequestByThreads(socket);
            this.listOfThread.add(mangeRequest);
            System.out.println("list of threads ==> " + Arrays.toString(listOfThread.toArray()));
            mangeRequest.start();
        }
    }



    public void checkTime() {
        Executors
                .newScheduledThreadPool(1)
                    .scheduleAtFixedRate(this::timeout, 0, 1, TimeUnit.SECONDS);
    }



    public void timeout(){
        if (Data.timer == Data.timeEnd) {
            this.isAlive = false;
            
            OptionalInt max = Arrays.stream(Data.offer.keySet().toArray(new String[0])).mapToInt(Integer::parseInt).max();

            Offer winner = Data.offer.get(String.valueOf(max.isPresent() ?  max.getAsInt() : null));
            Data.winner = winner;

            this.listOfThread.forEach(t -> t.winner(winner.getClient()) );

            System.out.println(winner.toString() + " con offerto di " + (max.isPresent() ? max.getAsInt() : null) + "\n");

            System.exit(0);
        }
    }



    /**
     * helper function to countdown timer by thread
     */
    public void startTimer(){
        Timer timer = new Timer();

        System.out.println("\n\t\t\t\t\t\t\t\t Time start \t" + (Data.timer/60) + ":"  + ((Data.timer + 59)  - (60 * 60)) + "\n");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (Data.timer  == Data.timeEnd){
                    System.out.println("\n\t\t\t\t\t\t\t\t\t Time finished");
                    timer.cancel();
                }

                Data.timer--;
            }
        }, 0, 1000);
    }


    public static Server INSTANCE() throws IOException {
        if (INSTANCE == null)
            INSTANCE = new Server();

        return INSTANCE;
    }


    public void populate(){
        Data.add("sedia", new RegistrationObject("sedia", 50));
        Data.add("tavolo", new RegistrationObject("tavolo", 150));
        Data.add("bimbo", new RegistrationObject("bimbo", 300));
    }
}
