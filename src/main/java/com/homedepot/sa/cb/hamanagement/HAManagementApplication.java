package com.homedepot.sa.cb.hamanagement;

import com.homedepot.sa.cb.hamanagement.config.Configurations;
import com.homedepot.sa.cb.hamanagement.exception.InternalServerException;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.service.*;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;

import javax.net.ssl.SSLSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;



@EnableSwagger2
@SpringBootApplication
@ComponentScan({"com.homedepot"})
public class HAManagementApplication extends WebMvcConfigurerAdapter {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Configurations configurations;

    public static void main(String[] args) {

        SpringApplication.run(HAManagementApplication.class, args);
    }



    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                .apiInfo(new ApiInfo("House Account Card Management API",
                        "APIs to create a new house account card, retrieve and update existing house account cards."
                        ,"1","", new Contact("Payment Services - HD Proprietary", "", ""),"",""))
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.homedepot.sa.cb.hamanagement.controller"))
                    .paths(PathSelectors.any())
                    .build();

    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",// url
                UiConfiguration.Constants.NO_SUBMIT_METHODS);
    }


    @Bean
    public RetryTemplate retryTemplate(){
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(configurations.getTandemRetryMaximumAttempts());
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(configurations.getTandemBackOffPeriod());
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }


    @Bean(name = "restTemplate")
    @Profile({"default","development"})
    public RestTemplate restTemplateDefault() throws InternalServerException {
        return new RestTemplate(clientHttpRequestFactoryDefault());
    }

    @Bean(name = "restTemplate")
    @Profile({"acceptance","production"})
    public RestTemplate restTemplate() throws InternalServerException {
        return new RestTemplate(clientHttpRequestFactory());
    }



    private ClientHttpRequestFactory clientHttpRequestFactoryDefault() throws InternalServerException{
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(configurations.getTandemReadTimeout());
        factory.setConnectTimeout(configurations.getTandemConnectTimeout());
        return factory;
    }


    private ClientHttpRequestFactory clientHttpRequestFactory() throws InternalServerException{
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient());
        factory.setReadTimeout(configurations.getTandemReadTimeout());
        factory.setConnectTimeout(configurations.getTandemConnectTimeout());
        return factory;
    }


    private HttpClient httpClient() throws InternalServerException{

        String keystorePassword = configurations.getKeyStorePassword();
        Resource keyStoreResource = resourceLoader.getResource("classpath:"+configurations.getCertificateFile());

        try{
            KeyStore trustStore = KeyStore.getInstance("JCEKS");
            // Note: Java 8 use TLS1.2 by default.
            SSLContextBuilder sslcontextBuilder = SSLContexts.custom().useProtocol("TLS")
                    .loadTrustMaterial(trustStore, (X509Certificate[] chain, String authType)-> true);
            FileInputStream fis = new FileInputStream(keyStoreResource.getFile());
            KeyStore ks = KeyStore.getInstance("JCEKS");
            ks.load(fis, keystorePassword.toCharArray());
            sslcontextBuilder.loadKeyMaterial(ks, keystorePassword.toCharArray());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontextBuilder.build(),(String hostname, SSLSession session) -> true);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslConnectionSocketFactory).build();
            PoolingHttpClientConnectionManager poolingManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolingManager.setMaxTotal(100);
            poolingManager.setDefaultMaxPerRoute(25);
            HttpClientBuilder builder = HttpClients.custom();
            builder.setConnectionManager(poolingManager);
            return builder.build();

        }catch (GeneralSecurityException | IOException e){
            throw new InternalServerException("Failed to create http client.",e.getMessage(),e);
        }

    }





}
