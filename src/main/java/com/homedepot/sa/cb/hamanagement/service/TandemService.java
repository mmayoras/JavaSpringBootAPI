package com.homedepot.sa.cb.hamanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.homedepot.sa.cb.hamanagement.config.Configurations;
import com.homedepot.sa.cb.hamanagement.exception.ApplicationException;
import com.homedepot.sa.cb.hamanagement.exception.InternalServerException;
import com.homedepot.sa.cb.hamanagement.exception.TandemServiceException;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * Created by n84qvs on 3/2/17.
 */
@Service
public class TandemService {

    @Autowired
    private RestConsumerService restConsumerService;

    @Autowired
    private RetryTemplate retryTemplate;

    private static final Logger LOGGER = Logger.getLogger(TandemService.class);


    @Autowired
    private Configurations configurations;

    /**
     * This method is used for sending new HA credit card application request to Tandem
     * @param tandemCardRequest - String request in correct Tandem format
     * @return
     * @throws ApplicationException
     */
    public TandemCardResponse submitTandemRequest(TandemCardRequest tandemCardRequest) throws ApplicationException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String request = null;
        try{
            request = mapper.writeValueAsString(tandemCardRequest);
        }catch(JsonProcessingException je){
            throw new InternalServerException("error.jackson.serialization","Error occurred while serializing tandem card request to JSON.",je);
        }

        String response = sendTandemRequest(request);
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        try {
            TandemCardResponse tandemCardResponse = mapper.readValue(response, TandemCardResponse.class);
            if(("0").equals(tandemCardResponse.getStatusCode())){
                return tandemCardResponse;
            }else{
               throw new TandemServiceException("error.tandem",tandemCardResponse.getStatusCode(),tandemCardResponse.getStatusMessage());
            }
        }catch(IOException je){
            throw new InternalServerException("error.jackson.deserialization","Error occurred while deserializing JSON to tandem card response",je);
        }
    }


    public String sendTandemRequest(String request) throws InternalServerException{
        LOGGER.info("Tandem request:"+request);
        String response = retryTemplate.execute(retryContext -> restConsumerService.post(configurations.getTandemPrimaryUrl(),request),
                retryContext ->{
                    LOGGER.error("Failed posting request at tandem primary "+configurations.getTandemPrimaryUrl(),retryContext.getLastThrowable());
                    return retryTemplate.execute(recoveryContext -> restConsumerService.post(configurations.getTandemSecondaryUrl(),request),
                        recoveryContext ->{
                            throw new InternalServerException("error.restTemplate","Failed posting request at tandem secondary "+configurations.getTandemSecondaryUrl(),recoveryContext.getLastThrowable());
                    });
        });
        LOGGER.info("Tandem response:"+response.replaceAll("\\n",""));
        return response;
    }
}
