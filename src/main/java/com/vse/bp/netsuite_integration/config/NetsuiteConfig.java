package com.vse.bp.netsuite_integration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration  // ✅ Говорим Spring, что это конфигурационный бин
@ConfigurationProperties(prefix = "netsuite")  // ✅ Загружаем свойства с префиксом "netsuite"
public class NetsuiteConfig {
    private String account;
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;
    private String endpointUrl;

    // Геттеры и сеттеры
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getConsumerKey() { return consumerKey; }
    public void setConsumerKey(String consumerKey) { this.consumerKey = consumerKey; }

    public String getConsumerSecret() { return consumerSecret; }
    public void setConsumerSecret(String consumerSecret) { this.consumerSecret = consumerSecret; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenSecret() { return tokenSecret; }
    public void setTokenSecret(String tokenSecret) { this.tokenSecret = tokenSecret; }

    public String getEndpointUrl() { return endpointUrl; }
    public void setEndpointUrl(String endpointUrl) { this.endpointUrl = endpointUrl; }
}