package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.model.api.CardStatusType;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by n84qvs on 12/5/16.
 * Useless test. Written for coverage.
 */
public class CardStatusTypeTest {

    @Test
    public void testCardStatusTypeEnum(){
        CardStatusType.valueOf(CardStatusType.ACTIVE.toString());
    }

    @Test
    public void testLimitFrequencyGetByDescription()
    {
        assertTrue(CardStatusType.valueOf(CardStatusType.ACTIVE.toString()).getDescription().equals("ACTIVE"));

    }


    @Test
    public void testGetByCode(){
        assertTrue(CardStatusType.getByCode(100).getDescription().equals("ACTIVE"));
        assertTrue(CardStatusType.getByCode(200).getDescription().equals("INACTIVE"));
        assertNull(CardStatusType.getByCode(50));
    }
}
