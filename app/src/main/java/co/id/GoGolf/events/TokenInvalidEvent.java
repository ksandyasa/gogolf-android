package co.id.GoGolf.events;

/**
 * Created by prumacadmin on 10/2/16.
 */
public class TokenInvalidEvent {
    private String message;

    public TokenInvalidEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
