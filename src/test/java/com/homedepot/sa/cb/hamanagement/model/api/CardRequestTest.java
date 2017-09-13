package com.homedepot.sa.cb.hamanagement.model.api;

/**
 * Created by pxk2457 on 11/10/16.
 */
import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.model.api.*;
import com.homedepot.sa.cb.hamanagement.validators.CreateCardRequest;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCardRequest;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CardRequestTest {

    private Validator validator;
    private TestData testData;

    @Before
    public void setUp(){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
        this.testData = new TestData();
    }

    @Test
    public void testCardRequestAttributesNotNull(){
        CardRequest request = new CardRequest();
        Set<ConstraintViolation<CardRequest>> violations = this.validator.validate(request);
        assertTrue(violations.size()==2);
        assertTrue(violations.stream().anyMatch(violation -> "error.cardRequest.card.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.cardRequest.userId.required".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testShippingAddressRequiredForCreateRequest(){
        CardRequest request = new CardRequest();
        request.setCard(testData.createCard());
        request.setUserId("Test");
        Set<ConstraintViolation<CardRequest>> violations = this.validator.validate(request,CreateCardRequest.class);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.cardRequest.shippingAddress.required".equals(violation.getMessageTemplate())));
    }


    @Test
    public void testRequestStatusRequiredForUpdateRequest(){
        CardRequest request = new CardRequest();
        Card card = new Card();
        card.setUserId("Test");
        card.setCrossReferenceNumber("1234567890123456");
        request.setUserId("Test");
        request.setCard(card);
        Set<ConstraintViolation<CardRequest>> violations = this.validator.validate(request,UpdateCardRequest.class);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.cardRequest.cardRequestStatus.required".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testUserIdMaximumSize(){
        CardRequest request = testData.createCardRequest();
        request.setUserId("12345678901234567890123456");
        Set<ConstraintViolation<CardRequest>> violations = this.validator.validate(request);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.cardRequest.userId.invalid".equals(violation.getMessageTemplate())));

    }

    @Test
    public void testUserIdBlankSpace(){
        CardRequest request = testData.createCardRequest();
        request.setUserId("   ");
        Set<ConstraintViolation<CardRequest>> violations = this.validator.validate(request);
        System.out.println(violations.toString());
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.cardRequest.userId.invalid".equals(violation.getMessageTemplate())));

    }

    @Test
    public void testUserIdEmptyString(){
        CardRequest request = testData.createCardRequest();
        request.setUserId("");
        request.setShippingMode(ShippingType.STANDARD);
        Set<ConstraintViolation<CardRequest>> violations = this.validator.validate(request);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.cardRequest.userId.invalid".equals(violation.getMessageTemplate())));

    }

    @Test
    public void testValidCardRequest(){
        CardRequest request = testData.createCardRequest();
        Set<ConstraintViolation<CardRequest>> violations = this.validator.validate(request);
        assertTrue(violations.isEmpty());
        assertNotNull(request.getShippingAddress());
        assertNotNull(request.getCard());
        assertNotNull(request.getUserId());
        assertNotNull(request.getShippingMode());
    }

    @Test
    public void testToStringMethod(){
        CardRequest cardRequest = testData.createCardRequest();
        String cardRequestString = cardRequest.toString();
        assertNotNull(cardRequestString);
        assertEquals(cardRequestString.toString(),"CardRequest{card=Card{sourceSystem=ERP, serviceAccountNumber='134567', buyerId='HI123456', serviceAccountName='BUSINESS', cardHolderFirstName='CardRequestTestFirstName', cardHolderLastName='CardRequestTestLastName', nickName='TEST HELLO 325', brandName=BARNETT, eReceiptEmail='test.test@gmail.com', cardStatus=ACTIVE, crossReferenceNumber='1234567890123456', expirationDate='0319', limit=CardLimit{frequency=DAILY, amount=85000.0}, userId='unittest'}, userId='test', shippingAddress=Address{companyName='The Home Depot', addressLine1='3375 SpringHill Pkwy', addressLine2='Apt 325', city='Smyrna', stateCode='GA', zip='30080'}, shippingMode=STANDARD, cardRequestStatus=CREATED}");

    }




}
