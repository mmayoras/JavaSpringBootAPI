package com.homedepot.sa.cb.hamanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.homedepot.sa.cb.hamanagement.HAManagementApplication;
import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.model.api.Card;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.repositories.CardRepository;
import com.homedepot.sa.cb.hamanagement.repositories.CardRequestRepository;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

/**
 * Created by pxk2457 on 11/9/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HAManagementApplication.class,loader=SpringApplicationContextLoader.class)
@WebAppConfiguration
@Ignore
public class CardControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private CardRequestRepository cardRequestRepository;

    @Autowired
    private CardRepository cardRepository;

    @Value("${vcap.services.api_security_config.credentials.enabled}")
    private String securityEnabled;

    @Value("${gateway.token}")
    private String authorizationToken;

    private TestData testData ;

    @Before
    public void setUp() {
        if("true".equalsIgnoreCase(securityEnabled)) {
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.context)
                    .apply(springSecurity())
                    .build();
        }else{
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.context)
                    .build();
        }

        testData = new TestData();

    }

    @Test
    public void testEmptyJsonRequest() throws Exception {
        String cardRequest = "";
        this.mockMvc
                .perform(post("/api/cards/v1")
                        .content(cardRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorCode").value("request.invalid"));
    }

    @Test
    public void testNoHeaderRequest() throws Exception {
        String cardRequest = "";
        if("true".equalsIgnoreCase(securityEnabled)) {
            this.mockMvc
                    .perform(post("/api/cards/v1")
                            .content(cardRequest)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }else{
            this.mockMvc
                    .perform(post("/api/cards/v1")
                            .content(cardRequest)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void testUnsupportedMediaTypeHeader() throws Exception {
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"123\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.error[0].errorCode").value("mediaType.unsupported"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("Retry with supported media types application/json or application/xml."));
    }



    @Test
    public void testCreateCardMissingCard() throws Exception
    {
        String cardRequest = "{\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorMessage").value("Card detail is required for creating new card. Retry with card detail."))
                .andExpect(jsonPath("$.error[0].errorCode").value("cardRequest.card.required"));
    }

    @Test
    public void testCreateCardMissingShippingAddress() throws Exception
    {
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"7878\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"Jilly\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"543678\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorMessage").value("Shipping address is required for creating new card. Retry with shipping address."))
                .andExpect(jsonPath("$.error[0].errorCode").value("cardRequest.shippingAddress.required"));
    }

    @Test
    public void testCreateCardMissingUserId() throws Exception
    {
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"345\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"56478\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorMessage").value("User Id is required. Retry with user id."))
                .andExpect(jsonPath("$.error[0].errorCode").value("cardRequest.userId.required"));
    }

    @Test
    public void testCreateCardDetailsRequired() throws Exception
    {
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", hasSize(7)))
                .andExpect(jsonPath("$.error[*].errorMessage", containsInAnyOrder("Brand name is required for creating new card. Retry with brand name.",
                        "Buyer Id is required for creating new card. Retry with buyer id.",
                        "Cardholder last name is required for creating new card. Retry with cardholder last name.",
                        "Service Account Name is required for creating new card. Retry with service account name.",
                        "Source system from which card was generated is required for creating new card. Retry with source system.",
                        "Service account number is required for creating new card. Retry with service account number.",
                        "Cardholder first name is required for creating new card. Retry with cardholder first name.")))
                .andExpect(jsonPath("$.error[*].errorCode", containsInAnyOrder("card.brandName.required",
                        "card.buyerId.required",
                        "card.cardHolderLastName.required",
                        "card.serviceAccountName.required",
                        "card.sourceSystem.required",
                        "card.serviceAccountNumber.required",
                        "card.cardHolderFirstName.required")));
    }

    @Test
    public void testCreateCardDetailsInvalid() throws Exception
    {
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"serviceAccountName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"\",\n" +
                "       \"buyerId\": \"\",\n" +
                "       \"cardHolderFirstName\": \"\",\n" +
                "       \"cardHolderLastName\": \"\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"lskudfhlaksiipoipoipipoipoioipioioipopipipipiuhdflkasuhdflkaushdflkaushfdlkuashdflkuhasdlkuhadsflkaushdflkaushdflkuahsldkfuhaslkdfuhlaksudhflkasuhdflkaushdflkasudhflkasudfhalsdfhfasjdfajsdfaslkdfjalfkdsjfksjdaiipopipoipoipoipopioipoipoipoipoioiuoiuoiuoiuoiuoiuiuiii\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"1234567891011121314151617181920\",\n" +
                "       \"serviceAccountNumber\": \"ABCDER\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", hasSize(8)))
                .andExpect(jsonPath("$.error[*].errorMessage", containsInAnyOrder("Email id for eReceipt can be maximum of 254 characters. Retry with valid email id.",
                        "Card holder first name can be maximum of 40 letters/numbers. Retry with valid card holder first name.",
                        "Service account name can be maximum of 30 letters/numbers. Retry with valid service account name.",
                        "Service account number can be maximum of 10 numbers without white space. Retry with valid service account number.",
                        "Buyer Id can be maximum of 10 letters/numbers without white space. Retry with valid buyer id.",
                        "Card holder last name can be maximum of 40 letters/numbers. Retry with valid card holder last name.",
                        "Nick name of the card can be maximum of 30 letters/numbers. Retry with valid card nick name.",
                        "Brand name is required for creating new card. Retry with brand name.")))
                .andExpect(jsonPath("$.error[*].errorCode", containsInAnyOrder("card.brandName.required",
                        "card.cardHolderFirstName.invalid",
                        "card.serviceAccountName.invalid",
                        "card.serviceAccountNumber.invalid",
                        "card.buyerId.invalid",
                        "card.cardHolderLastName.invalid",
                        "card.nickName.invalid",
                        "card.eReceiptEmail.invalid")));
    }

    @Test
    public void testCreateCardAddressDetailsRequired() throws Exception {

        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"325674\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"companyName\": \"string\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", hasSize(4)))
                .andExpect(jsonPath("$.error[*].errorMessage", containsInAnyOrder("Address line 1 is required for shipping the card to be created. Retry with address line 1 in shipping address.",
                        "City is required for shipping the card to be created. Retry with city in shipping address.",
                        "State code is required for shipping the card to be created. Retry with state code in shipping address.",
                        "Zip is required for shipping the card to be created. Retry with zip code in shipping address.")))
                .andExpect(jsonPath("$.error[*].errorCode", containsInAnyOrder("address.addressLine1.required",
                        "address.city.required",
                        "address.stateCode.required",
                        "address.zip.required")));
    }

    @Test
    public void testCreateCardAddressDetailsInvalid() throws Exception {

        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.00,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"1234567\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"\",\n" +
                "       \"addressLine2\": \"1234567891011121314151617181920\",\n" +
                "       \"city\": \"\",\n" +
                "       \"companyName\": \"1234567891011121314151617181920\",\n" +
                "       \"stateCode\": \"\",\n" +
                "       \"zip\": \"\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", hasSize(6)))
                .andExpect(jsonPath("$.error[*].errorMessage", containsInAnyOrder("Address line 1 can be maximum of 30 characters. Retry with valid address line 1.",
                        "Address line 2 can be maximum of 30 characters. Retry with valid address line 2.",
                        "City can be maximum of 30 characters. Retry with valid city.",
                        "State code must be 2 letters. Retry with valid state code.",
                        "Zip code can be combination of at least 5 numbers, followed by 4 numbers with or without dash (-) in between them (xxxxx or xxxxxxxxx or xxxxx-xxxx). Retry with valid zip code.",
                        "Company name can be maximum of 30 characters. Retry with valid company name.")))
                .andExpect(jsonPath("$.error[*].errorCode", containsInAnyOrder("address.addressLine1.invalid",
                        "address.addressLine2.invalid",
                        "address.city.invalid",
                        "address.stateCode.invalid",
                        "address.zip.invalid",
                        "address.companyName.invalid")));
    }





    @Test
    public void testJsonMappingException() throws Exception
    {
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS123\",\n" +
                "       \"serviceAccountName\": \"string\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"string\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"1234567\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"string\",\n" +
                "       \"zip\": \"string\"\n" +
                "   },\n" +
              "}";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorCode").value("card.brandName.invalid"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("Brand name in the request is not supported by API. Retry with supported brand names."));
    }


    @Test
    public void testEmptyXMLRequest() throws Exception {
        String cardRequest = "";
        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(xpath("/errors/error/errorCode").string("request.invalid"))
                .andExpect(xpath("/errors/error/errorMessage").string("Request sent was not as per API definition. Retry with valid request."));
    }

    @Test
    public void testShippingAddressRequiredInXMLFormat() throws Exception {
        String cardRequest = "<?xml version=\"1.0\"?>"+
                "<cardRequest>"+
                "<card>"+
                "<brandName>SUPPLYWORKS</brandName>"+
                "<serviceAccountName>ABC</serviceAccountName>"+
                " <buyerId>"+testData.generateBuyerId()+"</buyerId>\n" +
                "<cardHolderFirstName>string</cardHolderFirstName>"+
                "<cardHolderLastName>string</cardHolderLastName>"+
                "<cardStatus>ACTIVE</cardStatus>"+
                "<eReceiptEmail>test@gmail.com</eReceiptEmail>"+
                "<limit>"+
                "<amount>1.1</amount>"+
                "<frequency>DAILY</frequency>"+
                "</limit>"+
                "<nickName>string</nickName>"+
                "<serviceAccountNumber>1234567</serviceAccountNumber>"+
                "<sourceSystem>ISS</sourceSystem>"+
                "</card>"+
                "<shippingMode>STANDARD</shippingMode>"+
                "<userId>string</userId>"+
                "</cardRequest>";

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(xpath("/errors/error/errorCode").string("cardRequest.shippingAddress.required"))
                .andExpect(xpath("/errors/error/errorMessage").string("Shipping address is required for creating new card. Retry with shipping address."));
    }


    @Test
    public void testCrossReferenceNumberParameterForSearch() throws Exception{
        this.mockMvc.perform(get("/api/cards/v1?cardId=").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error[0].errorCode").value("card.notFound"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("Requested card is not found. Check the card id and retry."));
    }

    @Test
    public void testMissingRequiredParameterForSearch() throws Exception{
        this.mockMvc.perform(get("/api/cards/v1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorCode").value("cardId.required"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("Request parameter cardId(Cross reference number) is required for viewing a card. Retry with cardId."));
    }

    @Test
    public void testSearchCardWithoutRequiredRequestParameter() throws Exception{


        this.mockMvc.perform(get("/api/cards/v1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorCode").value("cardId.required"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("Request parameter cardId(Cross reference number) is required for viewing a card. Retry with cardId."));
    }


    @Test
    public void testSearchCardWitInvalidRequestParameter() throws Exception{


        this.mockMvc.perform(get("/api/cards/v1?cardId=").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error[0].errorCode").value("card.notFound"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("Requested card is not found. Check the card id and retry."));
    }




    @Test
    public void testUpdateWithoutRequiredHeaderValue() throws Exception{
        String request = getCardRequestJson();
        this.mockMvc.perform(patch("/api/cards/v1").content(request).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error[0].errorCode").value("If-Unmodified-Since.required"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("If-Unmodified-Since value is required in request header for updating a card. Retry with required value."));

    }

    @Test
    public void testUpdateWithoutRequiredFields() throws Exception {
        String card = "{\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account123\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"Jimmy\",\n" +
                "       \"cardHolderLastName\": \"Poje\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"eReceiptEmail\": \"nobody@noreply.co\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"1234567\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   }";

        this.mockMvc.perform(patch("/api/cards/v1").content(card).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("If-Unmodified-Since", "2017-01-12 16:22:29.39")
                .header("Authorization", authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", hasSize(2)))
                .andExpect(jsonPath("$.error[*].errorMessage", containsInAnyOrder("Cross reference number is required for updating a card. Retry with cross reference number.","User Id is required for updating card. Retry with user id.")))
                .andExpect(jsonPath("$.error[*].errorCode", containsInAnyOrder("card.userId.required","card.crossReferenceNumber.required")));
    }


    @Test
    public void testUpdateWithInvalidRequiredFields() throws Exception {
        String card = "{\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account123\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"Jimmy\",\n" +
                "       \"cardHolderLastName\": \"Poje\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"eReceiptEmail\": \"nobody@noreply.co\",\n" +
                "       \"crossReferenceNumber\": \"AB123456678\",\n" +
                "       \"userId\": \"12345678901234567890123456\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"1234567\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   }";

        this.mockMvc.perform(patch("/api/cards/v1").content(card).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("If-Unmodified-Since", "2017-01-12 16:22:29.39")
                .header("Authorization", authorizationToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", hasSize(2)))
                .andExpect(jsonPath("$.error[*].errorMessage", containsInAnyOrder("Cross reference number must be 16 numbers. Retry with valid cross reference number.","User id can be maximum of 24 letters/numbers. Retry with valid user id.")))
                .andExpect(jsonPath("$.error[*].errorCode", containsInAnyOrder("card.userId.invalid","card.crossReferenceNumber.invalid")));
    }





    private String getCardRequestJson(){
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account123\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"Jimmy\",\n" +
                "       \"cardHolderLastName\": \"Poje\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"nobody@noreply.co\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \"123456789\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";
        return cardRequest;
    }



    // Testing Tandem and database connections

    @Test
    public void testCreateCardSuccess() throws Exception{

        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"COPPERFIELD CHIMNEY SUPPLY\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"Jill\",\n" +
                "       \"cardHolderLastName\": \"Bill\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"Test\",\n" +
                "       \"serviceAccountNumber\": \""+testData.generateServiceAccountNumber()+"\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"mmx\"\n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Card cardResponse = mapper.readValue(result.getResponse().getContentAsString(),Card.class);
        HouseAccountCreditCardAccount cardAccount = cardRepository.findOne(cardResponse.getCrossReferenceNumber());
        Integer id = cardAccount.getHouseAccountCreditCardRequest().getRequestId();
        cardRequestRepository.delete(id);
    }


    @Test
    public void testCreateCardLimitDetailsNull() throws Exception{

        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \""+testData.generateServiceAccountNumber()+"\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Card cardResponse = mapper.readValue(result.getResponse().getContentAsString(),Card.class);
        assertNotNull(cardResponse.getCrossReferenceNumber());
        HouseAccountCreditCardAccount cardAccount = cardRepository.findOne(cardResponse.getCrossReferenceNumber());
        Integer id = cardAccount.getHouseAccountCreditCardRequest().getRequestId();
        cardRequestRepository.delete(id);
    }


    @Test
    public void testCreateCardXML() throws Exception
    {
        String cardRequest = "<?xml version=\"1.0\"?>\n" +
                "<cardRequest>\n" +
                " <card>\n" +
                "   <brandName>SUPPLYWORKS</brandName>\n" +
                "   <serviceAccountName>SA23456</serviceAccountName>\n" +
                "   <buyerId>"+testData.generateBuyerId()+"</buyerId>\n" +
                "   <cardHolderFirstName>Anderson</cardHolderFirstName>\n" +
                "   <cardHolderLastName>Jimmy</cardHolderLastName>\n" +
                "   <eReceiptEmail>test@gmail.com</eReceiptEmail>\n" +
                "   <limit>\n" +
                "     <amount>1.1</amount>\n" +
                "     <frequency>DAILY</frequency>\n" +
                "   </limit>\n" +
                "   <nickName>string</nickName>\n" +
                "   <serviceAccountNumber>"+testData.generateServiceAccountNumber()+"</serviceAccountNumber>\n" +
                "   <sourceSystem>ISS</sourceSystem>\n" +
                " </card>\n" +
                " <shippingAddress>\n" +
                "   <addressLine1>2245 Paces Ferry Road</addressLine1>\n" +
                "   <addressLine2>Apt 456</addressLine2>\n" +
                "   <city>Atlanta</city>\n" +
                "   <companyName>30339</companyName>\n" +
                "   <stateCode>GA</stateCode>\n" +
                "   <zip>30004</zip>\n" +
                " </shippingAddress>\n" +
                " <shippingMode>STANDARD</shippingMode>\n" +
                " <userId>IntgTest</userId>\n" +
                "</cardRequest>";

        MvcResult result = this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andExpect(xpath("/card/crossReferenceNumber").exists())
                .andReturn();

        XmlMapper xmlMapper = new XmlMapper();
        Card cardResponse = xmlMapper.readValue(result.getResponse().getContentAsString(),Card.class);
        assertNotNull(cardResponse.getCrossReferenceNumber());
        HouseAccountCreditCardAccount cardAccount = cardRepository.findOne(cardResponse.getCrossReferenceNumber());
        int id = cardAccount.getHouseAccountCreditCardRequest().getRequestId();
        cardRequestRepository.delete(id);
    }


    @Test
    public void testCreateExistingCard() throws Exception
    {
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \""+testData.generateServiceAccountNumber()+"\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andReturn();

        this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andReturn();


        ObjectMapper mapper = new ObjectMapper();
        Card cardResponse = mapper.readValue(result.getResponse().getContentAsString(),Card.class);
        assertNotNull(cardResponse.getCrossReferenceNumber());
        HouseAccountCreditCardAccount cardAccount = cardRepository.findOne(cardResponse.getCrossReferenceNumber());
        Integer id = cardAccount.getHouseAccountCreditCardRequest().getRequestId();
        cardRequestRepository.delete(id);
    }

    @Test
    public void testSearchCard() throws Exception{
        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"James\",\n" +
                "       \"cardHolderLastName\": \"Poje\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"serviceAccountNumber\": \""+testData.generateServiceAccountNumber()+"\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"mxm\"\n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Card cardResponse = mapper.readValue(result.getResponse().getContentAsString(),Card.class);
        assertNotNull(cardResponse.getCrossReferenceNumber());

        this.mockMvc.perform(get("/api/cards/v1?cardId="+cardResponse.getCrossReferenceNumber()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isOk());

        HouseAccountCreditCardAccount cardAccount = cardRepository.findOne(cardResponse.getCrossReferenceNumber());
        Integer id = cardAccount.getHouseAccountCreditCardRequest().getRequestId();
        cardRequestRepository.delete(id);
    }


    @Test
    public void testUpdateCardAndCardRquestXMLAndJSON() throws Exception
    {
        String serviceAccountNumber = testData.generateServiceAccountNumber();
        String buyerId = testData.generateBuyerId();

        String cardRequest = "<?xml version=\"1.0\"?>\n" +
                "<cardRequest>\n" +
                " <card>\n" +
                "   <brandName>SUPPLYWORKS</brandName>\n" +
                "   <serviceAccountName>MA23457</serviceAccountName>\n" +
                "   <buyerId>"+buyerId+"</buyerId>\n" +
                "   <cardHolderFirstName>Anderson</cardHolderFirstName>\n" +
                "   <cardHolderLastName>Jimmy</cardHolderLastName>\n" +
                "   <eReceiptEmail>test@gmail.com</eReceiptEmail>\n" +
                "   <limit>\n" +
                "     <amount>1.1</amount>\n" +
                "     <frequency>DAILY</frequency>\n" +
                "   </limit>\n" +
                "   <nickName>string</nickName>\n" +
                "   <serviceAccountNumber>"+serviceAccountNumber+"</serviceAccountNumber>\n" +
                "   <sourceSystem>ISS</sourceSystem>\n" +
                " </card>\n" +
                " <shippingAddress>\n" +
                "   <addressLine1>2245 Paces Ferry Road</addressLine1>\n" +
                "   <city>Atlanta</city>\n" +
                "   <companyName>30339</companyName>\n" +
                "   <stateCode>GA</stateCode>\n" +
                "   <zip>30004</zip>\n" +
                " </shippingAddress>\n" +
                " <shippingMode>STANDARD</shippingMode>\n" +
                " <userId>IntgTest</userId>\n" +
                "</cardRequest>";

        MvcResult result = this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andExpect(xpath("/card/crossReferenceNumber").exists())
                .andReturn();

        XmlMapper xmlMapper = new XmlMapper();
        Card cardResponse = xmlMapper.readValue(result.getResponse().getContentAsString(),Card.class);

        MvcResult getResult = this.mockMvc.perform(get("/api/cards/v1?cardId="+cardResponse.getCrossReferenceNumber()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isOk())
                .andReturn();

        String lastModifiedTimeStamp = getResult.getResponse().getHeader("Last-Modified").toString();

        String card = "{\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account123\",\n" +
                "       \"buyerId\": \""+buyerId+"\",\n" +
                "       \"cardHolderFirstName\": \"Jimmy\",\n" +
                "       \"cardHolderLastName\": \"Poje\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"" + cardResponse.getCrossReferenceNumber() + "\",\n" +
                "       \"userId\": \"IntgTest\",\n" +
                "       \"eReceiptEmail\": \"nobody@noreply.co\",\n" +
                "       \"limit\": {\n" +
                "           \"amount\": 0.01,\n" +
                "           \"frequency\": \"DAILY\"\n" +
                "       },\n" +
                "       \"nickName\": \"IntTest\",\n" +
                "       \"serviceAccountNumber\": \""+serviceAccountNumber+"\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   }";

        this.mockMvc.perform(patch("/api/cards/v1").content(card).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("If-Unmodified-Since", "2016-01-12 16:22:29.39")
                .header("Authorization", authorizationToken))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.error[0].errorCode").value("If-Unmodified-Since.expired"))
                .andExpect(jsonPath("$.error[0].errorMessage").value("Card you are trying to update has been modified by some one else (If-Unmodified-Since time in header is past than Last-Modified time in database). View the cared again and update if required."));


        this.mockMvc.perform(patch("/api/cards/v1").content(card).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("If-Unmodified-Since", lastModifiedTimeStamp)
                .header("Authorization", authorizationToken))
                .andExpect(status().isOk());

        String updateCardRequest = "{\n" +
                "  \"card\": {\n" +
                "    \"crossReferenceNumber\": \"" + cardResponse.getCrossReferenceNumber() + "\"\n" +
                "  },\n" +
                "  \"cardRequestStatus\": \"CREATED\",\n" +
                "  \"userId\": \"mxm\"\n" +
                "}";

        this.mockMvc.perform(patch("/api/cardRequests/v1").content(updateCardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationToken))
                .andExpect(status().isOk());

        HouseAccountCreditCardAccount cardAccount = cardRepository.findOne(cardResponse.getCrossReferenceNumber());
        Integer id = cardAccount.getHouseAccountCreditCardRequest().getRequestId();
        cardRequestRepository.delete(id);
    }


    @Test
    public void testCreateCardWithNoFrequencyAndNoAmount() throws Exception{

        String cardRequest = "{\n" +
                "   \"card\": {\n" +
                "       \"brandName\": \"SUPPLYWORKS\",\n" +
                "       \"serviceAccountName\": \"THD House Account\",\n" +
                "       \"buyerId\": \""+testData.generateBuyerId()+"\",\n" +
                "       \"cardHolderFirstName\": \"string\",\n" +
                "       \"cardHolderLastName\": \"string\",\n" +
                "       \"cardStatus\": \"ACTIVE\",\n" +
                "       \"crossReferenceNumber\": \"string\",\n" +
                "       \"eReceiptEmail\": \"test@gmail.com\",\n" +
                "       \"limit\": {\n" +
                "       },\n" +
                "       \"nickName\": \"string\",\n" +
                "       \"serviceAccountNumber\": \""+testData.generateServiceAccountNumber()+"\",\n" +
                "       \"sourceSystem\": \"ISS\"\n" +
                "   },\n" +
                "   \"shippingAddress\": {\n" +
                "       \"addressLine1\": \"string\",\n" +
                "       \"addressLine2\": \"string\",\n" +
                "       \"city\": \"string\",\n" +
                "       \"companyName\": \"string\",\n" +
                "       \"stateCode\": \"GA\",\n" +
                "       \"zip\": \"12345\"\n" +
                "   },\n" +
                "   \"shippingMode\": \"STANDARD\",\n" +
                "   \"userId\": \"string\"\n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/api/cards/v1").content(cardRequest).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization",authorizationToken))
                .andExpect(status().isCreated())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Card cardResponse = mapper.readValue(result.getResponse().getContentAsString(),Card.class);

        HouseAccountCreditCardAccount cardAccount = cardRepository.findOne(cardResponse.getCrossReferenceNumber());
        Integer id = cardAccount.getHouseAccountCreditCardRequest().getRequestId();
        cardRequestRepository.delete(id);

    }



}
