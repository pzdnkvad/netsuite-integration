package com.vse.bp.netsuite_integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NetsuiteConfig {
    @Value("${netsuite.account}") private String account;
    @Value("${netsuite.consumerKey}") private String consumerKey;
    @Value("${netsuite.consumerSecret}") private String consumerSecret;
    @Value("${netsuite.token}") private String token;
    @Value("${netsuite.tokenSecret}") private String tokenSecret;
    @Value("${netsuite.endpointUrl}") private String endpointUrl;

    public String getAccount() { return account; }
    public String getConsumerKey() { return consumerKey; }
    public String getConsumerSecret() { return consumerSecret; }
    public String getToken() { return token; }
    public String getTokenSecret() { return tokenSecret; }
    public String getEndpointUrl() { return endpointUrl; }
}