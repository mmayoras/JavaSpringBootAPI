package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.TestData;
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

/**
 * Created by n84qvs on 11/23/16.
 */
public class AddressTest {
    private Validator validator;
    private TestData testData;

    @Before
    public void setUp(){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
        this.testData = new TestData();
     }

    @Test
    public void testAddressAttributesRequired(){
        Address address = new Address();
        Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
        assertTrue(violations.size()==5);
        assertTrue(violations.stream().anyMatch(violation -> "error.address.stateCode.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.zip.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.city.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.addressLine1.required".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.companyName.required".equals(violation.getMessageTemplate())));
    }

    @Test
    public void testAddressAttributesEmpty(){
        Address address = new Address();
        address.setAddressLine1("");
        address.setCity("");
        address.setStateCode("");
        address.setZip("");
        address.setCompanyName("");
        Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
        assertTrue(violations.stream().anyMatch(violation -> "error.address.stateCode.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.zip.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.city.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.addressLine1.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.companyName.invalid".equals(violation.getMessageTemplate())));

    }

    @Test
    public void testAddressAttributesBlank(){
        Address address = new Address();
        address.setAddressLine1("  ");
        address.setCity("  ");
        address.setStateCode("  ");
        address.setZip("  ");
        address.setCompanyName("   ");
        Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
        assertTrue(violations.stream().anyMatch(violation -> "error.address.stateCode.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.zip.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.city.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.addressLine1.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.companyName.invalid".equals(violation.getMessageTemplate())));

    }

    @Test
    public void testEmptyValuesAcceptedForNotRequiredFields(){
        Address address = testData.createAddress();
        address.setAddressLine2("");
        Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testAddressAttributesMaximumSize(){
        Address address = new Address();
        address.setAddressLine1("abcdefghijklmnopqrstuvwxyzabcde");
        address.setAddressLine2("abcdefghijklmnopqrstuvwxyzabcde");
        address.setCity("abcdefghijklmnopqrstuvwxyzabcde");
        address.setStateCode("GAA");
        address.setZip("12345-12345");
        address.setCompanyName("abcdefghijklmnopqrstuvwxyzabcde");
        Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
        assertTrue(violations.size()==6);
        assertTrue(violations.stream().anyMatch(violation -> "error.address.stateCode.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.zip.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.city.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.addressLine2.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.addressLine1.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.companyName.invalid".equals(violation.getMessageTemplate())));


    }

    @Test
    public void testAddressAttributesMinimumValue(){
        Address address = new Address();
        address.setAddressLine1("3375 SpringHill Pkwy");
        address.setCity("Smyrna");
        address.setStateCode("G");
        address.setZip("300");
        address.setCompanyName("TEst");
        Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
        assertTrue(violations.size()==2);
        assertTrue(violations.stream().anyMatch(violation -> "error.address.stateCode.invalid".equals(violation.getMessageTemplate())));
        assertTrue(violations.stream().anyMatch(violation -> "error.address.zip.invalid".equals(violation.getMessageTemplate())));

    }

    @Test
    public void testValidAddress(){
        Address address = testData.createAddress();
        Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
        assertTrue(violations.isEmpty());
        assertNotNull(address.getAddressLine1());
        assertNotNull(address.getAddressLine2());
        assertNotNull(address.getCity());
        assertNotNull(address.getZip());
        assertNotNull(address.getStateCode());
        assertNotNull(address.getCompanyName());
    }

    @Test
    public void testToStringMethod(){
        Address address = testData.createAddress();
        String addressString = address.toString();
        assertNotNull(addressString);
        assertEquals(addressString.toString(),"Address{companyName='The Home Depot', addressLine1='3375 SpringHill Pkwy', addressLine2='Apt 325', city='Smyrna', stateCode='GA', zip='30080'}");

    }





}
