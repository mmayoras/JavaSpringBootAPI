package com.homedepot.sa.cb.hamanagement.model.db;

import com.ibm.db2.jcc.DBTimestamp;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mxm8528 on 1/13/17.
 */
public class AuditFieldsTest {

    @Test
    public void testValidAuditFields()
    {
        AuditFields auditFields = createAuditFields();
        assertNotNull(auditFields.getLastUpdatedSysUserId());
        assertNotNull(auditFields.getLastUpdatedTimeStamp());
    }

    public AuditFields createAuditFields()
    {
        AuditFields auditFields = new AuditFields();
        auditFields.setLastUpdatedSysUserId("testUser");
        auditFields.setLastUpdatedTimeStamp(new DBTimestamp(new Date().getTime()));
        return auditFields;
    }
}
