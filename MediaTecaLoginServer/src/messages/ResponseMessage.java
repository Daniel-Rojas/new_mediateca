package messages;

public class ResponseMessage {

    private int loginSuccess;
    private String username;
    private String response;

    public ResponseMessage(boolean loginSuccess, String username, String response) {
        if (loginSuccess) {
            this.loginSuccess = 1;
        }
        else {
            this.loginSuccess = 0;
        }
        this.username = username;
        this.response = response;
    }

    public String buildMessage() {
        String loginSuccess = "\"loginSuccess\":" + this.loginSuccess;
        String username = "\"username\":\"" + this.username + "\"";
        String response = "\"response\":\"" + this.response + "\"";
        return "{" + loginSuccess + "," + username + "," + response + "}";
    }
}
