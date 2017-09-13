package com.homedepot.sa.cb.hamanagement.model.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by n84qvs on 12/5/16.
 * Useless test. Written for coverage.
 */
public class BrandTypeTest {

    @Test
    public void testBrandTypeEnum(){
        BrandType.valueOf(BrandType.BARNETT.toString());
    }

    @Test
    public void testLimitFrequencyGetByDescription()
    {
        assertTrue(BrandType.valueOf(BrandType.BARNETT.toString()).getDescription().equals("BARNETT"));
    }

    @Test
    public void testGetByCode(){
        assertTrue(BrandType.getByCode("100").getDescription().equals("SUPPLYWORKS"));
        assertTrue(BrandType.getByCode("200").getDescription().equals("WILMAR"));
        assertTrue(BrandType.getByCode("300").getDescription().equals("MAINTENANCE U.S.A."));
        assertTrue(BrandType.getByCode("400").getDescription().equals("BARNETT"));
        assertTrue(BrandType.getByCode("500").getDescription().equals("COPPERFIELD CHIMNEY SUPPLY"));
        assertTrue(BrandType.getByCode("600").getDescription().equals("HARDWARE EXPRESS"));
        assertTrue(BrandType.getByCode("700").getDescription().equals("LERAN GAS PRODUCTS"));
        assertTrue(BrandType.getByCode("800").getDescription().equals("US LOCK"));
        assertTrue(BrandType.getByCode("900").getDescription().equals("RENOVATIONS PLUS"));
        assertNull(BrandType.getByCode("50"));
    }

    @Test
    public void testGetByDescription(){
        assertTrue(BrandType.getByDescription("SUPPLYWORKS").equals(BrandType.SUPPLYWORKS));
        assertTrue(BrandType.getByDescription("WILMAR").equals(BrandType.WILMAR));
        assertTrue(BrandType.getByDescription("MAINTENANCE U.S.A.").equals(BrandType.MAINTENANCEUSA));
        assertTrue(BrandType.getByDescription("BARNETT").equals(BrandType.BARNETT));
        assertTrue(BrandType.getByDescription("COPPERFIELD CHIMNEY SUPPLY").equals(BrandType.COPPERFIELDCHIMNEYSUPPLY));
        assertTrue(BrandType.getByDescription("HARDWARE EXPRESS").equals(BrandType.HARDWAREEXPRESS));
        assertTrue(BrandType.getByDescription("LERAN GAS PRODUCTS").equals(BrandType.LERANGASPRODUCTS));
        assertTrue(BrandType.getByDescription("US LOCK").equals(BrandType.USLOCK));
        assertTrue(BrandType.getByDescription("RENOVATIONS PLUS").equals(BrandType.RENOVATIONSPLUS));

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testInvalidDescription() throws IllegalArgumentException {
        exception.expect(IllegalArgumentException.class);
        BrandType.getByDescription("INVALIDBRANDYTYPE");
    }

}
