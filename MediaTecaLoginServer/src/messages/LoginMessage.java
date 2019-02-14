package messages;

public class LoginMessage {

    private String message;
    private String clientType;
    private String email;
    private String password;
    private String ipAddress;
    private String passCode;

    public LoginMessage(String message) {
        //{"clientType":"SERVER","email":"user@gmail.com","password":"example","ipAddress":"127.0.0.1"}
        //{"clientType":"USER","email":"user@gmail.com","password":"example","ipAddress":"127.0.0.1","passCode":"123456789"}
        this.message = message;
        parseMessage();
    }

    private void parseMessage() {
        String messageString = this.message;
        String noBraces = removeBrackets(messageString);
        parseAttributes(noBraces);
    }

    private String removeBrackets(String string) {
        if (string.charAt(0) == '{' && string.charAt(string.length() - 1) == '}') {
            string = string.substring(1,string.length() - 2);
        }
        return string;
    }

    private String removeQuotes(String string) {
        if (string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"') {
            string = string.substring(1,string.length() - 2);
        }
        return string;
    }

    private void parseAttributes(String string) {
        String[] categories = string.split(",");
        for (String category : categories) {
            String[] attributeValuePair = category.split(":");
            String attribute = removeQuotes(attributeValuePair[0]);
            String value = removeQuotes(attributeValuePair[1]);
            setValues(attribute, value);
        }
    }

    private void setValues(String attribute,String value) {
        switch(attribute) {
            case "clientType":
                this.clientType = value;
                break;
            case "email":
                this.email = value;
                break;
            case "password":
                this.password = value;
                break;
            case "ipAddress":
                this.ipAddress = value;
                break;
            case "passCode":
                this.passCode = value;
                break;
            default:
                //do nothing
                break;
        }
    }

    public String getClientType() { return this.clientType; }

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public String getIpAddress() { return this.ipAddress; }

    public String getPassCode() { return this.passCode; }

}
