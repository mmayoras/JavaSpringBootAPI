package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.model.api.CardRequestStatusType;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by mxm8528 on 1/12/17.
 */
public class CardRequestTypeTest {

    @Test
    public void testCardStatusTypeEnum(){
        CardRequestStatusType.valueOf(CardRequestStatusType.CREATED.toString()).getDescription();
    }

}
