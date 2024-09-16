package appserver;

public enum ApplicationType {
    TECH_SUPPORT(1);

    private int id;

    ApplicationType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}