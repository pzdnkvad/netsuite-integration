package com.vse.bp.netsuite_integration.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Component
public class NetSuiteRoute extends RouteBuilder {
    String account = "your_account";
    String consumerKey = "your_consumer_key";
    String token = "your_token";
    String consumerSecret = "your_consumer_secret";
    String tokenSecret = "your_token_secret";

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")  // Запускается один раз
                .routeId("singleRunRoute")
                .process(exchange -> {
                    String nonce = generateNonce();
                    String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
                    String signature = generateSignature(account, consumerKey, consumerSecret, token, tokenSecret, nonce, timestamp);

                    // Логирование
                    log.info("Generated Signature: {}", signature);

                    // Установка заголовков
                    exchange.getIn().setHeader("account", account);
                    exchange.getIn().setHeader("consumerKey", consumerKey);
                    exchange.getIn().setHeader("token", token);
                    exchange.getIn().setHeader("nonce", nonce);
                    exchange.getIn().setHeader("timestamp", timestamp);
                    exchange.getIn().setHeader("signature", signature);

                    // Вывод в body для логирования в Camel
                    exchange.getIn().setBody("Generated Signature: " + signature);
                })
                .log("${body}")  // Выводит сгенерированную подпись в лог
                .delay(100)
                .process(exchange -> exchange.getContext().getRouteController().stopRoute("singleRunRoute"));
    }


    private String generateNonce() {
        int length = new Random().nextInt(59) + 6; // length between 6 and 64
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length);
    }

    private String generateSignature(String account, String consumerKey, String consumerSecret, String tokenKey, String tokenSecret, String nonce, String timestamp) {
        try {
            String key = consumerSecret + "&" + tokenSecret;
            String baseString = account + "&" + consumerKey + "&" + tokenKey + "&" + nonce + "&" + timestamp;

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hash = mac.doFinal(baseString.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generating OAuth signature", e);
        }
    }


}