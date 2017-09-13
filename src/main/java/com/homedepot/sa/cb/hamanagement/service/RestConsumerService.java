package com.homedepot.sa.cb.hamanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by n84qvs on 1/13/17.
 */

@Service
public class RestConsumerService {

    private final RestTemplate restTemplate;

    @Autowired
    public RestConsumerService(@Qualifier("restTemplate") RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    public String post(String url,String request) throws RestClientException{
        try{
            return restTemplate.postForObject(url, request, String.class);
        }catch(RestClientException re){
            throw re;
        }
   }
}
