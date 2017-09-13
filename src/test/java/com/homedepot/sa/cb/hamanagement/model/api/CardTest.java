package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.validators.CreateCardRequest;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCard;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCardRequest;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by n84qvs on 11/28/16.
 */
public class CardTest {

    private Validator validator;
    private Card validCard;
    private TestData testData;


    @Before
    public void setUp(){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
        this.testData = new TestData();
        this.validCard=testData.createCard();


    }

    @Test
    public void testRequiredFieldsForCreate(){
        Card cardWithOutRequiredFields = new Card();
        Set<ConstraintViolation<Card>> violations = this.validator.validate(cardWithOutRequiredFields, CreateCardRequest.class);
        assertTrue(violations.stream().anyMatch(violation -> "error.card.brandName.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.sourceSystem.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountNumber.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.buyerId.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderFirstName.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderLastName.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountName.required".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testRequiredFieldsForUpdate(){
        Card cardWithOutRequiredFields = new Card();
        Set<ConstraintViolation<Card>> violationsCR = this.validator.validate(cardWithOutRequiredFields, UpdateCardRequest.class);
        Set<ConstraintViolation<Card>> violations = this.validator.validate(cardWithOutRequiredFields, UpdateCard.class);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.card.userId.required".equals(violation.getMessageTemplate())));
        assertTrue(violationsCR.size()==1);
        assertTrue(violationsCR.stream().anyMatch(violation -> "error.card.crossReferenceNumber.required".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testEmptyCrossReferenceNumberForCreate(){
        Card cardWithOutRequiredFields = testData.createCard();
        Set<ConstraintViolation<Card>> violations = this.validator.validate(cardWithOutRequiredFields, CreateCardRequest.class);
        assertTrue(violations.size()==0);

    }

    @Test
    public void testEmptyRequiredForUpdate(){
        Card card = new Card();
        card.setCrossReferenceNumber("");
        card.setUserId("");
        Set<ConstraintViolation<Card>> violationsCR = this.validator.validate(card, UpdateCardRequest.class);
        Set<ConstraintViolation<Card>> violations = this.validator.validate(card, UpdateCard.class);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.card.userId.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violationsCR.size()==1);
        assertTrue(violationsCR.stream().anyMatch(violation -> "error.card.crossReferenceNumber.invalid".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testInvalidRequiredFieldsForUpdate(){
        Card card = new Card();
        card.setCrossReferenceNumber("1234567  0123456");
        card.setUserId("12345678901234567890123456");
        Set<ConstraintViolation<Card>> violationsCR = this.validator.validate(card, UpdateCardRequest.class);
        Set<ConstraintViolation<Card>> violations = this.validator.validate(card, UpdateCard.class);
        assertTrue(violations.size()==1);
        assertTrue(violations.stream().anyMatch(violation -> "error.card.userId.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violationsCR.size()==1);
        assertTrue(violationsCR.stream().anyMatch(violation -> "error.card.crossReferenceNumber.invalid".equals(violation.getMessageTemplate())));
    }




    @Test
    public void testEmptyForRequiredFieldsForCreate() {
        Card cardWithEmptyValueForRequiredFields = new Card();
        cardWithEmptyValueForRequiredFields.setServiceAccountNumber("");
        cardWithEmptyValueForRequiredFields.setBuyerId("");
        cardWithEmptyValueForRequiredFields.setServiceAccountName("");
        cardWithEmptyValueForRequiredFields.setCardHolderFirstName("");
        cardWithEmptyValueForRequiredFields.setCardHolderLastName("");
        Set<ConstraintViolation<Card>> violations = this.validator.validate(cardWithEmptyValueForRequiredFields);
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountNumber.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.buyerId.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderFirstName.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderLastName.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountName.invalid".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testBlankValues(){
        Card cardWithBlankSpaceValues = new Card();
        cardWithBlankSpaceValues.setServiceAccountNumber("        ");
        cardWithBlankSpaceValues.setBuyerId("  ");
        cardWithBlankSpaceValues.setCardHolderFirstName("  ");
        cardWithBlankSpaceValues.setCardHolderLastName("  ");
        cardWithBlankSpaceValues.setServiceAccountName("  ");
        Set<ConstraintViolation<Card>> violations = this.validator.validate(cardWithBlankSpaceValues);
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountNumber.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.buyerId.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderFirstName.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderLastName.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountName.invalid".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testEmptyValuesAcceptedForNotRequiredFields(){
        Card cardWithEmptyValuesForNotRequiredFields = validCard;
        cardWithEmptyValuesForNotRequiredFields.setNickName("");
        cardWithEmptyValuesForNotRequiredFields.seteReceiptEmail("test@gmail.com");
        Set<ConstraintViolation<Card>> violations = this.validator.validate(cardWithEmptyValuesForNotRequiredFields);
        assertTrue(violations.isEmpty());
    }


    @Test
    public void testMaximumSizeForValues(){
        Card cardWithGreaterThanMaximumSizeValues = new Card();
        cardWithGreaterThanMaximumSizeValues.setServiceAccountNumber("abcdefghijk"); //Length 11
        cardWithGreaterThanMaximumSizeValues.setBuyerId("abcdefghijk"); // Length 11
        cardWithGreaterThanMaximumSizeValues.setCardHolderFirstName("abcdefghijklmnopqrstuvwxyzabcdefghijklmnn"); //Length 41
        cardWithGreaterThanMaximumSizeValues.setCardHolderLastName("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghi"); //Length 61
        cardWithGreaterThanMaximumSizeValues.setServiceAccountName("abcdefghijklmnopqrstuvwxyzabcde"); //Length 31
        cardWithGreaterThanMaximumSizeValues.setNickName("abcdefghijklmnopqrstuvwxyzabcde"); //Length = 31
        cardWithGreaterThanMaximumSizeValues.seteReceiptEmail("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmno@gmail.com"); //Length = 255
        Set<ConstraintViolation<Card>> violations = this.validator.validate(cardWithGreaterThanMaximumSizeValues);
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountNumber.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.buyerId.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderFirstName.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.cardHolderLastName.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.eReceiptEmail.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.serviceAccountName.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.card.nickName.invalid".equals(violation.getMessageTemplate())));


    }



    @Test
    public void testValidCard(){
        Set<ConstraintViolation<Card>> violations = this.validator.validate(validCard);
        assertTrue(violations.isEmpty());
        assertNotNull(validCard.getBrandName());
        assertNotNull(validCard.getServiceAccountName());
        assertNotNull(validCard.getBuyerId());
        assertNotNull(validCard.getCardHolderFirstName());
        assertNotNull(validCard.getCardHolderLastName());
        assertNotNull(validCard.getCardStatus());
        assertNotNull(validCard.geteReceiptEmail());
        assertNotNull(validCard.getLimit());
        assertNotNull(validCard.getNickName());
        assertNotNull(validCard.getServiceAccountNumber());
        assertNotNull(validCard.getSourceSystem());
        assertNotNull(validCard.getExpirationDate());

    }


    @Test
    public void testToStringMethod(){
        Card card = testData.createCard();
        String cardString = card.toString();
        assertNotNull(cardString);
        assertEquals(card.toString(),"Card{sourceSystem=ERP, serviceAccountNumber='134567', buyerId='HI123456', serviceAccountName='BUSINESS', cardHolderFirstName='CardRequestTestFirstName', cardHolderLastName='CardRequestTestLastName', nickName='TEST HELLO 325', brandName=BARNETT, eReceiptEmail='test.test@gmail.com', cardStatus=ACTIVE, crossReferenceNumber='1234567890123456', expirationDate='0319', limit=CardLimit{frequency=DAILY, amount=85000.0}, userId='unittest'}");

    }








}
