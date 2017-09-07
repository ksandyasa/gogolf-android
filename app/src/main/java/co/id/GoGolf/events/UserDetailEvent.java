package co.id.GoGolf.events;

import co.id.GoGolf.models.response.login.User;

/**
 * Created by dedepradana on 7/28/16.
 */
public class UserDetailEvent extends BaseEvent {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
