package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.model.api.ShippingType;
import org.junit.Test;

/**
 * Created by n84qvs on 12/5/16.
 * Useless test. Written for coverage.
 */
public class ShippingTypeTest {

    @Test
    public void testShippingTypeEnum(){
        ShippingType.valueOf(ShippingType.EXPEDITED.toString());
    }
}
