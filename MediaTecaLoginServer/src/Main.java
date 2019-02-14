import server.LoginServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting MediaTeca Login Server...");
        LoginServer loginServer = new LoginServer(8888);
        loginServer.listen();
    }
}
