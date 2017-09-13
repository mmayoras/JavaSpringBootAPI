package com.homedepot.sa.cb.hamanagement.model.api;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by n84qvs on 3/14/17.
 */
public class ErrorTest {

    @Test
    public void testValidCard(){
        Error error = new Error("error.test","Testing is failed");
        assertTrue(error.getErrorCode().equals("error.test"));
        assertTrue(error.getErrorMessage().equals("Testing is failed"));

    }
}
