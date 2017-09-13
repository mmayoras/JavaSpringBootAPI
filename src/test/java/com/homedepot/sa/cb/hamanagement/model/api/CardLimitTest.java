package com.homedepot.sa.cb.hamanagement.model.api;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.*;

/**
 * Created by mxm8528 on 11/28/16.
 */
public class CardLimitTest {
    private Validator validator;

    @Before
    public void setUp(){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void testCardLimitAmountTypeDouble()
    {
        CardLimit cl = new CardLimit();
        assertThat(cl.getAmount(), isA(double.class));
    }

    @Test
    public void testCardLimitAmountMaxSize()
    {
        CardLimit cl = new CardLimit();
        cl.setFrequency(LimitFrequencyType.DAILY);
        cl.setAmount(10000000000000.00);
        Set<ConstraintViolation<CardLimit>> violations = this.validator.validate(cl);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.creditLimit.amount.invalid".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testCardLimitMinimumValueIsZero(){
        CardLimit limit = new CardLimit();
        limit.setFrequency(LimitFrequencyType.WEEKLY);
        limit.setAmount(0);
        Set<ConstraintViolation<CardLimit>> violations = this.validator.validate(limit);
        assertTrue(violations.size()==0);

    }

}
