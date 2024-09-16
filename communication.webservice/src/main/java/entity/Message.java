package entity;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Table(name = "message",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_msg_mqstate_rstcnt_sqnum", columnNames = {"mq_state_id", "reset_counter", "sequence_number"})
        },
        indexes = {
                @Index(name = "idx_msg_mqstateid", columnList = "mq_state_id")
        }
)
public class Message extends Persistent {

    public Message() {
    }

    public Message(MessageQueueState messageQueueState, int resetCounter, int sequenceNumber) {
        this.messageQueueState = messageQueueState;
        this.resetCounter = resetCounter;
        this.sequenceNumber = sequenceNumber;
    }

    public MessageQueueState getMessageQueueState() {
        // force-load association on first access
        if (messageQueueState != null) messageQueueState.getResetCounter();
        return messageQueueState;
    }

    public void setMessageQueueState(MessageQueueState messageQueueState) {
        this.messageQueueState = messageQueueState;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mq_state_id", nullable = false, foreignKey = @ForeignKey(name = "fk_msg_mqstate_mqstateid"))
    protected MessageQueueState messageQueueState;

    public int getResetCounter() {
        return resetCounter;
    }

    public void setResetCounter(int resetCounter) {
        this.resetCounter = resetCounter;
    }

    @Column(name = "reset_counter", nullable = false)
    private int resetCounter;

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Column(name = "sequence_number", nullable = false)
    protected int sequenceNumber;

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "timestamp", nullable = true)
    private Date timestamp;

    public boolean isTimestampUnreliable() {
        return timestampUnreliable;
    }

    public void setTimestampUnreliable(boolean timestampUnreliable) {
        this.timestampUnreliable = timestampUnreliable;
    }

    @Column(name = "timestamp_unreliable", nullable = false)
    private boolean timestampUnreliable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message that = (Message) o;

        return Objects.equal(this.getResetCounter(), that.getResetCounter()) &&
                Objects.equal(this.getSequenceNumber(), that.getSequenceNumber()) &&
                Objects.equal(this.getMessageQueueState(), that.getMessageQueueState());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMessageQueueState(), getResetCounter(), getSequenceNumber());
    }

    @Override
    public String toString() {
        return "Message{" +
                "resetCounter=" + getResetCounter() +
                ", sequenceNumber=" + getSequenceNumber() +
                ", status=" + getStatus() +
                ", timestamp=" + getTimestamp() +
                ", timestampUnreliable=" + isTimestampUnreliable() +
                '}';
    }
}
