package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class Persistent extends ImmutablePersistent {

    static final long serialVersionUID = 2L;

    public int getEntityVersion() {
        return entityVersion;
    }

    public void setEntityVersion(int entityVersion) {
        this.entityVersion = entityVersion;
    }

    @Version
    @Column(name = "entity_version", nullable = false)
    protected int entityVersion;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "update_time", nullable = true)
    protected Date updateTime;

    public Date getLastUpdateTime() {
        Date updateTime = getUpdateTime();
        return (updateTime != null) ? updateTime : getCreateTime();
    }
}