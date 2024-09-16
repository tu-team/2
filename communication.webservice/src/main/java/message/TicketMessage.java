package message;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("TICKET_MESSAGE")
public class TicketMessage extends ApplicationMessage {
    protected String name;

    protected String computer;

    protected String office;

    protected String priority;

    protected String request;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
