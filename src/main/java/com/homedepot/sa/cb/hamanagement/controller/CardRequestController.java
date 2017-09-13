package com.homedepot.sa.cb.hamanagement.controller;

import com.homedepot.sa.cb.hamanagement.exception.ApplicationException;
import com.homedepot.sa.cb.hamanagement.model.api.CardRequest;
import com.homedepot.sa.cb.hamanagement.service.CardService;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by mxm8528 on 1/19/17.
 */
@RestController
@RequestMapping(path = "/api/cardRequests/v1")
@PreAuthorize("isAuthenticated()")
@ApiIgnore
public class CardRequestController {

    @Autowired
    private CardService cardService;

    /**
     * @param cardRequest
     * @return
     * @throws ApplicationException
     */
    @RequestMapping(method = RequestMethod.PATCH,
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Void> updateCardRequest(@RequestBody @Validated(UpdateCardRequest.class) CardRequest cardRequest) throws ApplicationException{
        cardService.updateCardRequest(cardRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
