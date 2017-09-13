package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.model.api.LimitFrequencyType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by n84qvs on 12/5/16.
 * Useless test. Written for coverage.
 */
public class LimitFrequencyTypeTest {

    @Test
    public void testLimitFrequencyTypeEnum(){
        LimitFrequencyType.valueOf(LimitFrequencyType.DAILY.toString());
    }

    @Test
    public void testLimitFrequencyGetByCode()
    {
        assertTrue(LimitFrequencyType.getByCode(100).getDescription().equals("DAILY"));
        assertTrue(LimitFrequencyType.getByCode(200).getDescription().equals("WEEKLY"));
        assertTrue(LimitFrequencyType.getByCode(300).getDescription().equals("MONTHLY"));
        assertNull(LimitFrequencyType.getByCode(400));
    }


}
