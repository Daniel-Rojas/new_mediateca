package server;

import database.LoginDatabase;
import messages.LoginMessage;
import messages.ResponseMessage;

import java.io.*;
import java.net.Socket;

public class LoginHandler implements Runnable {

    private LoginDatabase loginDatabase;
    private BufferedReader in;
    private PrintWriter out;

    public LoginHandler(Socket socket,LoginDatabase loginDatabase) {
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(),true);
            this.loginDatabase = loginDatabase;
        } catch (IOException i) {
            System.out.println("Error: Login Handler could not be created");
        }
    }

    public void run() {
        // send connection message
        this.out.println("Connected to MediaTeca Login Server");
        loginClient(receiveMessage());
    }

    private LoginMessage receiveMessage() {
        LoginMessage loginMessage;
        try {
            String message = this.in.readLine();
            loginMessage = new LoginMessage(message);
        } catch (IOException i) {
            loginMessage = null;
        }
        return loginMessage;
    }

    private void sendResponse(boolean loginSuccess,String username,String response) {
        ResponseMessage responseMessage = new ResponseMessage(loginSuccess,username,response);
        this.out.println(responseMessage.buildMessage());
    }


    private void loginClient(LoginMessage loginMessage) {
        ResponseMessage responseMessage;
        String email = loginMessage.getEmail();
        String password = loginMessage.getPassword();
        if (loginMessage.getClientType().equals("USER")) {
            String passcode = loginMessage.getPassCode();
            String username = loginDatabase.loginUser(email,password);
            if (username == null) {
                sendResponse(false,"","Email or Password is Incorrect!");
            }
            else {
                String serverIp = loginDatabase.getServerIp(passcode);
                sendResponse(true,username,serverIp);
            }
        }
        else if (loginMessage.getClientType().equals("SERVER")) {
            String ipAddress = loginMessage.getIpAddress();
            String username = loginDatabase.loginServer(email,password,ipAddress);
            if (username == null) {
                sendResponse(false,"","Email or Password is Incorrect!");
            }
            else {
                sendResponse(true,username,"");
            }

        }
    }
}
