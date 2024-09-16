package entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Generic interface for all kinds of payloads that arrive via the MQ system.
 */
public interface MessagePayload extends Serializable {

    Message getMessage();

    void setMessage(Message notification);

    void setCreateTime(Date createTime);

}
