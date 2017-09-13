package com.homedepot.sa.cb.hamanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by n84qvs on 3/14/17.
 */
public class RestConsumerServivceTest {

    @InjectMocks
    private RestConsumerService restConsumerService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RestClientException.class)
    public void testPostRestClientException() throws RestClientException{
        when(restTemplate.postForObject("url","request",String.class)).thenThrow(RestClientException.class);

        restConsumerService.post("url","request");
    }

    @Test
    public void testPost() throws RestClientException{
        when(restTemplate.postForObject("url","request",String.class)).thenReturn("Success");
        assertTrue(restConsumerService.post("url","request").equals("Success"));
    }

}
