package com.homedepot.sa.cb.hamanagement.controller;

import com.homedepot.sa.cb.hamanagement.config.Configurations;
import com.homedepot.sa.cb.hamanagement.exception.ApplicationException;
import com.homedepot.sa.cb.hamanagement.exception.BadRequestException;
import com.homedepot.sa.cb.hamanagement.exception.NotFoundException;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.api.Card;
import com.homedepot.sa.cb.hamanagement.model.api.CardRequest;
import com.homedepot.sa.cb.hamanagement.model.api.ErrorResponse;
import com.homedepot.sa.cb.hamanagement.service.CardService;
import com.homedepot.sa.cb.hamanagement.validators.CreateCardRequest;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCard;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCardRequest;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Created   by n84qvs on 11/7/16.
 */
@RestController
@RequestMapping(path = "/api/cards/v1")
@Api(value = "Card API")
@PreAuthorize("isAuthenticated()")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private Configurations configurations;

    private static final Logger LOGGER = Logger.getLogger(CardController.class);

    /**
     * API for creating new house account card
     * @param cardRequest
     * @return ResponseEntity
     * @throws ApplicationException
     * @throws BadRequestException
     */
    @RequestMapping(method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value="Create new House Account Card", notes="Initial version.",response = Card.class)
    @ApiResponses({
            @ApiResponse(code = 201, message="Card created", response = Card.class),
            @ApiResponse(code = 400, message="Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message="Card not created", response = ErrorResponse.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
     public ResponseEntity<Card> create(@Validated(CreateCardRequest.class) @RequestBody CardRequest cardRequest) throws ApplicationException{
        LOGGER.info("Card request:" + cardRequest);
        // Call service layer to save new cardRequest into database
        Card card = cardService.create(cardRequest);
        LOGGER.info("Card response:" + card);
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }

    /**
     * API for getting card details by cross reference number
     * @param cardId
     * @return ResponseEntity
     * @throws NotFoundException
     */
    @RequestMapping(method = RequestMethod.GET,
           produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value="Retrieve House Account Card by Card Id", notes="Initial version.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Card found" ,responseHeaders={@ResponseHeader(name="Last-Modified",
                    description = "Last card updated time", response = Card.class)}),
            @ApiResponse(code = 404, message="Card not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message="Not able to retrieve requested card", response = ErrorResponse.class)
    })
    public ResponseEntity<Card> search(@ApiParam(required = true, name = "cardId") @RequestParam("cardId") String cardId) throws ApplicationException{
        LOGGER.info("Card Id:" + cardId);
        HouseAccountCreditCardAccount houseAccount = cardService.retrieveHouseAccount(cardId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Last-Modified",houseAccount.getLastUpdatedTimeStamp().toString());
        Card card = cardService.getCard(houseAccount);
        LOGGER.info("Last-Modified:"+ houseAccount.getLastUpdatedTimeStamp().toString() + " | Card Response:" + card);
        return new ResponseEntity<>(card,headers,HttpStatus.OK);
    }


    /**
     * API for updating card details
     * @param card
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.PATCH,
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value="Update House Account card", notes="Initial version.")
    @ApiResponses({
            @ApiResponse(code = 200, message="Card updated", response = Void.class),
            @ApiResponse(code = 404, message="Card not found", response = ErrorResponse.class),
            @ApiResponse(code = 412, message="Precondition Failed", response = ErrorResponse.class),
            @ApiResponse(code = 400, message="Precondition Failed", response = ErrorResponse.class),
            @ApiResponse(code = 500, message="Card not updated", response = ErrorResponse.class)
    })

    public ResponseEntity<Void> updateCard(@RequestHeader("If-Unmodified-Since") String ifNotModifiedSince,@RequestBody @Validated({UpdateCardRequest.class, UpdateCard.class}) Card card) throws ApplicationException{
        LOGGER.info("If-Unmodified-Since:"+ifNotModifiedSince+ " | Card update request:" + card);
        cardService.updateCard(card,ifNotModifiedSince);
        LOGGER.info("Card updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @ApiIgnore
    @RequestMapping(method = RequestMethod.GET, path="/configurations",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getConfigurations() throws ApplicationException{
       return configurations.toString();
    }

}
