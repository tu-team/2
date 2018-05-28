package message;

import org.hibernate.Session;
import entity.MessagePayload;

import java.util.List;

public interface MessagePayloadPersister<T extends MessagePayload, U extends MessagePayload> {

    U persistPayload(Session session, T payload);

    Class<T> getPayloadClass();

    List<U> postProcess();
}