package com.homedepot.sa.cb.hamanagement.model.db;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by PXK2457 on 1/5/2017.
 */
@MappedSuperclass
public class AuditFields implements Serializable{

    @Column(name = "LAST_UPD_SYSUSR_ID")
    protected String lastUpdatedSysUserId;

    @Column(name = "LAST_UPD_TS")
    private Date lastUpdatedTimeStamp;

    public String getLastUpdatedSysUserId() {
        return lastUpdatedSysUserId;
    }

    public void setLastUpdatedSysUserId(String lastUpdatedSysUserId) {
        this.lastUpdatedSysUserId = lastUpdatedSysUserId;
    }

    public Date getLastUpdatedTimeStamp() {
        return lastUpdatedTimeStamp;
    }

    public void setLastUpdatedTimeStamp(Date lastUpdatedTimeStamp) {
        this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
    }
}
