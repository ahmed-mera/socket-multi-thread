package Client;

import Common.Common;
import Constants.Constants;
import DB.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    private final Common common = new Common();
    private final Socket socket;
    private boolean isAlive;

    public Client() throws IOException {
        this.socket = new Socket("localhost", 2020);
        this.isAlive = true;
    }




    /**
     * function to serve client
     * @throws IOException type {@linkplain IOException }
     */
    public void start() throws IOException {
        this.checkTime();
        String price;

        this.register();

        while (this.isAlive) {

            String choose = this.menu();


            switch (choose) {
                case "1":
                    this.common.sendData(this.socket, choose);
                    this.print(this.common.readData(this.socket));


                    while (!choose.equals("0") && this.isAlive) {
                        System.out.print(" \tscegli un oggetto:_");
                        choose = this.common.cleanSpace((new BufferedReader(new InputStreamReader(System.in))).readLine());

                        this.common.clear();

                        while (this.isAlive) {
                            this.common.sendData(this.socket, "1.1");
                            System.out.println(this.common.readData(this.socket) + "\n");

                            //join on asta
                            System.out.print("enter your price of asta or 0 to Exit:_ ");
                            price = this.common.cleanSpace((new BufferedReader(new InputStreamReader(System.in))).readLine());

                            if (price.equals("0")) break;

                            if (Data.offer.containsKey(price))
                                System.out.println("number is chose, try again");
                            else {
                                this.common.sendData(this.socket, "1-" + choose + "-" + price);
                                String response = this.common.readData(this.socket);
                                if (response.equalsIgnoreCase("object does not exist")) break;
                                System.out.println("\n" + response + "\n");
                            }
                        }

                        choose = "0";
                    }
                    break;

                case "2":
                    this.common.sendData(this.socket, choose);
                    System.out.println("\n" + this.common.readData(this.socket) + "\n");
                    break;

                case "4":
                    this.common.sendData(this.socket, choose);
                    System.out.println(this.common.readData(this.socket));
                    System.exit(0);
                    break;

                case "3":
                    this.common.sendData(this.socket, choose);
                    this.register();
                    System.out.println(this.common.readData(this.socket));
                    break;

                case "0":
                    this.common.sendData(this.socket, choose);
                    this.socket.close();
                    System.out.println("Connection closed");
                    return;
            }

        }

    }


    /**
     * function to print the menu
     * @return the choose of client
     * @throws IOException type {@linkplain IOException }
     */
    public String menu() throws IOException {
        this.common.clear();
        System.out.print(Constants.MENU);
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }




    public void register() throws IOException {
        this.common.clear();// clear console

        // set name of user
        System.out.print(this.common.readData(this.socket));
        this.common.sendData(this.socket, (new BufferedReader(new InputStreamReader(System.in))).readLine());

        this.common.clear();// clear console

        //set surname of user
        System.out.print(this.common.readData(this.socket));
        this.common.sendData(this.socket, (new BufferedReader(new InputStreamReader(System.in))).readLine());
    }


    public void print(String array){
        Arrays.stream(array.split("\\."))
                .iterator()
                    .forEachRemaining(System.out::println);
    }


    public void checkTime() {

        Timer timer = new Timer();

          Client c = this;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                c.timeout();
            }
        }, 0, 1000);


    }



    public void timeout(){
        if (Data.timer == Data.timeEnd) {
            this.isAlive = false;
            try {
                System.out.println("\n\t\t\t\t\t\t " + this.common.readData(this.socket));
                this.socket.close();
                System.out.println("Connection closed");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
