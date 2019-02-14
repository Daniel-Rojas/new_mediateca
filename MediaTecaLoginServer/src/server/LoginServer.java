package server;

import database.LoginDatabase;
import java.io.IOException;
import java.net.ServerSocket;

public class LoginServer {

    private LoginDatabase loginDatabase;
    private int portNumber;

    public LoginServer(int portNumber) {
        this.portNumber = portNumber;
        loginDatabase = new LoginDatabase();
    }

    public void listen() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.portNumber);
            System.out.println("MediaTeca Login Server listening on port " + this.portNumber);
            while(true) {
                LoginHandler loginHandler = new LoginHandler(serverSocket.accept(), loginDatabase);
                new Thread(loginHandler).start();
            }
        } catch (IOException i) {
            this.portNumber++;
            System.out.println("Error: Could not create LoginServer trying on port " + this.portNumber);
            listen();
        }
    }
}
