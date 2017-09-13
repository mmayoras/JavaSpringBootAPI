package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.model.api.SystemType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by n84qvs on 12/5/16.
 * Useless test. Written for coverage.
 */
public class SystemTypeTest {

    @Test
    public void testSystemTypeEnum(){
        SystemType.valueOf(SystemType.ERP.toString());
    }

}
