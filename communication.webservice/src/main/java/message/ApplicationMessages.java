package message;

import java.util.List;

public class ApplicationMessages {

    private List<ApplicationMessage> messages;

    public ApplicationMessages() {
        super();
    }

    public ApplicationMessages(List<ApplicationMessage> messages) {
        this.messages = messages;
    }

    public List<ApplicationMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ApplicationMessage> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ApplicationMessages{" +
                "messages=" + messages +
                '}';
    }
}
