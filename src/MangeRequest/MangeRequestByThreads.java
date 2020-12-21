package MangeRequest;

import Bean.Offer;
import Bean.RegistrationClient;
import Bean.RegistrationObject;
import CRUD.CrudOperation;
import Common.Common;
import Constants.Constants;
import DB.Data;


import java.io.IOException;
import java.net.Socket;

public class MangeRequestByThreads extends Thread {

    private final Socket socket;
    private RegistrationClient client;
    private final Common common = new Common();
    private final CrudOperation crudOperation = new CrudOperation();


    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * {@code (name, socket)}, where {@code name} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public MangeRequestByThreads(String name, Socket socket) {
        super(name);
        this.socket = socket;
    }


    /**
     * Constructor
     * @param socket which use to connection
     */
    public MangeRequestByThreads(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run(){
        try {
            this.serveClient();
        } catch (IOException e) {
            System.out.println("error ==> " + e.getMessage());
        }
    }




    /**
     * function to serve client
     * @throws IOException type {@linkplain IOException }
     */
    public void serveClient() throws IOException {

        if (this.socket == null) return;

        this.__register();

        while (true) {
            String choose = common.readData(this.socket);

            if (choose.contains("1-")){
                String[] data = choose.split("-");
                RegistrationObject object = Data.objects.get(data[1]);

                if (object != null) {
                    if (Data.offer.containsKey(data[2]))
                        this.common.sendData(this.socket, "offer exists. you must choose another number");
                    else
                        this.common.sendData(this.socket, "data is added");

                    Data.add(data[2], new Offer(this.client, object));
                }else
                    this.common.sendData(this.socket, "object does not exist");

            }else {
                switch (choose) {

                    case "1":
                        this.common.sendData(this.socket, this.printObjects());
                        break;

                    case "1.1":
                        this.common.sendData(this.socket, this.printCurrentOffers());
                        break;
                    case "2":
                        this.common.sendData(this.socket, this.crudOperation.show(this.client));
                        break;

                    case "3":
                        this.client = this.registrationClient();
                        this.crudOperation.modify(Data.users, super.getName(), this.client);
                        this.common.sendData(this.socket, Constants.MODIFIED);
                        break;

                    case "4":
                        this.client = null;
                        this.crudOperation.delete(Data.users, super.getName());
                        this.common.sendData(this.socket, Constants.DELETED);
                        return;


                    case "0":
                        this.socket.close();
                        System.out.println("Connection closed");
                        return;
                }
            }
        }
    }





    /**
     * function to registration client
     * function to get the input from client
     * @return {@linkplain RegistrationClient}
     * @throws IOException type {@linkplain IOException }
     */
    public RegistrationClient registrationClient() throws IOException {

        String firstName, lastName;

        this.common.sendData(this.socket, Constants.FIRST_NAME);
        firstName = this.common.readData(this.socket);

        this.common.sendData(this.socket, Constants.LAST_NAME);
        lastName = this.common.readData(this.socket);

        return new RegistrationClient(firstName, lastName);
    }



    public void __register() throws IOException {
        this.client = this.registrationClient();
//        super.setName(this.client.getFirstName() + "-" + this.client.getLastName());
        Data.add(super.getName(), this.client);
    }


    public void winner(RegistrationClient client) {
        try {
            if (client.equals(this.client))
                this.common.sendData(this.socket, Data.winner.toString());
            else
                this.common.sendData(this.socket, "you are lose :(");

        }catch (IOException e){
            System.out.println("can not send data for user cause ==>" + e.getMessage());
        }

    }



    public String printObjects() {
        StringBuilder s = new StringBuilder("Oggetti all'asta:.");
        Data.objects.forEach((k, v) -> s.append("- ").append(k).append("."));
        s.append("0) Exit ");
        return s.toString();
    }




    public String printCurrentOffers() {
        StringBuilder s = new StringBuilder("current offer:\t\t");
        s.append("[ ");
        Data.offer.forEach((k, v) -> s.append(k).append(" ==> ").append(v.getClient().toString()).append(", "));
        s.append(" ]");
        return s.toString();
    }


}
