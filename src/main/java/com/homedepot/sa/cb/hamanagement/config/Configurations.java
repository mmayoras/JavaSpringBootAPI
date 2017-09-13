package com.homedepot.sa.cb.hamanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Created by n84qvs on 3/24/17.
 */
@Configuration
@RefreshScope
public class Configurations {

    @Value("${url.primary.tandem:}")
    private String tandemPrimaryUrl;

    @Value("${url.secondary.tandem:}")
    private String tandemSecondaryUrl;

    @Value("${keystore.pass:}")
    private String keyStorePassword;

    @Value("${keystore.file:}")
    private String certificateFile;

    @Value("${timeout.connect.tandem:}")
    private int tandemConnectTimeout;

    @Value("${timeout.read.tandem:}")
    private int tandemReadTimeout;

    @Value("${retry.attempts.tandem:}")
    private int tandemRetryMaximumAttempts;

    @Value("${retry.backoffperiod.tandem:}")
    private long tandemBackOffPeriod;

    public String getTandemPrimaryUrl() {
        return tandemPrimaryUrl;
    }

    public String getTandemSecondaryUrl() {
        return tandemSecondaryUrl;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public String getCertificateFile() {
        return certificateFile;
    }

    public int getTandemConnectTimeout() {
        return tandemConnectTimeout;
    }

    public int getTandemReadTimeout() {
        return tandemReadTimeout;
    }

    public int getTandemRetryMaximumAttempts() {
        return tandemRetryMaximumAttempts;
    }

    public long getTandemBackOffPeriod() {
        return tandemBackOffPeriod;
    }

    @Override
    public String toString() {
        return "Configurations{" +
                "tandemPrimaryUrl='" + tandemPrimaryUrl + '\'' +
                ", tandemSecondaryUrl='" + tandemSecondaryUrl + '\'' +
                ", keyStorePassword='" + keyStorePassword + '\'' +
                ", certificateFile='" + certificateFile + '\'' +
                ", tandemConnectTimeout=" + tandemConnectTimeout +
                ", tandemReadTimeout=" + tandemReadTimeout +
                ", tandemRetryMaximumAttempts=" + tandemRetryMaximumAttempts +
                ", tandemRetryWaitTime=" + tandemBackOffPeriod +
                '}';
    }
}


