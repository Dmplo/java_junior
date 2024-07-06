public class BroadcastMessageRequest extends AbstractRequest {

    public static final String TYPE = "sendMessageAllUsers";

    private String message;

    public BroadcastMessageRequest() {
        setType(TYPE);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
