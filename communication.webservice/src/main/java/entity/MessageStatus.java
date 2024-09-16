package entity;

import java.util.HashMap;

public enum MessageStatus {

    PUSH,
    PULL;

    private static final HashMap<String, MessageStatus> lookup = new HashMap<>();

    static {
        for (MessageStatus status : MessageStatus.values()) {
            lookup.put(status.name(), status);
        }
    }

    public static MessageStatus getNotificationStatus(String name) {
        return lookup.get(name);
    }

}