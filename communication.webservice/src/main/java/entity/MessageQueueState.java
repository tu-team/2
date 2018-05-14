package entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "message_queue_state",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_mqstate_nodeid", columnNames = {"node_id"})
        }
)
public class MessageQueueState extends Persistent {

    public MessageQueueState() {
    }

    public boolean isPushOnly() {
        return pushOnly;
    }

    public void setPushOnly(boolean pushOnly) {
        this.pushOnly = pushOnly;
    }

    @Column(name = "push_only", nullable = false)
    private boolean pushOnly;

    public int getResetCounter() {
        return resetCounter;
    }

    public void setResetCounter(int resetCounter) {
        this.resetCounter = resetCounter;
    }

    @Column(name = "reset_counter", nullable = false)
    private int resetCounter;

    public int getStartSequenceNumber() {
        return startSequenceNumber;
    }

    public void setStartSequenceNumber(int startSequenceNumber) {
        this.startSequenceNumber = startSequenceNumber;
    }

    @Column(name = "start_seq_no", nullable = false)
    private int startSequenceNumber;

    public int getLastReceivedSequenceNumber() {
        return lastReceivedSequenceNumber;
    }

    public void setLastReceivedSequenceNumber(int lastReceivedSequenceNumber) {
        this.lastReceivedSequenceNumber = lastReceivedSequenceNumber;
    }

    @Column(name = "last_received_seq_no", nullable = false)
    private int lastReceivedSequenceNumber;

    public Date getLastPullTime() {
        return lastPullTime;
    }

    public void setLastPullTime(Date lastPullTime) {
        this.lastPullTime = lastPullTime;
    }

    @Column(name = "last_pull_time", nullable = true)
    private Date lastPullTime;

    public Integer getPullInterval() {
        return pullInterval;
    }

    public void setPullInterval(Integer pullInterval) {
        this.pullInterval = pullInterval;
    }

    @Column(name = "pull_interval", nullable = true)
    private Integer pullInterval;

    public byte[] getReceiveBitSet() {
        return receiveBitSet;
    }

    public void setReceiveBitSet(byte[] receiveBitSet) {
        this.receiveBitSet = receiveBitSet;
    }

    @Column(name = "receive_bit_set", nullable = true)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] receiveBitSet;

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "messageQueueState")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Message> messages = new HashSet<>();

    @Override
    public String toString() {
        return "MessageQueueState{" +
                ", resetCounter=" + getResetCounter() +
                ", startSequenceNumber=" + getStartSequenceNumber() +
                ", lastReceivedSequenceNumber=" + getLastReceivedSequenceNumber() +
                ", lastPullTime=" + getLastPullTime() +
                ", pullInterval=" + getPullInterval() +
                '}';
    }
}