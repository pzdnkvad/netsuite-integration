package com.vse.bp.netsuite_integration.routes;

import com.vse.bp.netsuite_integration.config.NetsuiteConfig;
import com.vse.bp.netsuite_integration.mapper.ExpenseTransformer;
import com.vse.bp.netsuite_integration.model.ExpenseReportFlat;
import com.vse.bp.netsuite_integration.model.ExpenseReportXml;
import com.vse.bp.netsuite_integration.util.XmlMarshallerUtil;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.stereotype.Component;

@Component
public class ExpenseReportToNetSuiteRoute extends RouteBuilder {

    private final NetsuiteConfig config;

    public ExpenseReportToNetSuiteRoute(NetsuiteConfig config) {
        this.config = config;
    }

    @Override
    public void configure() throws Exception {
        JacksonDataFormat jsonFormat = new JacksonDataFormat(ExpenseReportFlat.class);

        onException(HttpOperationFailedException.class)
                .handled(true)
                .process(exchange -> {
                    HttpOperationFailedException ex = exchange.getProperty(
                            Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);
                    String errorMessage = "Failed to send expense report to NetSuite. HTTP Status: "
                            + ex.getStatusCode() + ". Response body: " + ex.getResponseBody();
                    exchange.getIn().setBody(errorMessage);
                })
                .setHeader("subject", constant("NetSuite Expense Report Error"))
                .to("smtp://{{mail.host}}?to={{mail.to}}&from={{mail.from}}&username={{mail.username}}&password={{mail.password}}&contentType=text/plain");

        from("timer:fetchExpenseReport?period=60000")
                .routeId("expenseReportToNetSuiteRoute")
                .to(config.getApiUrl())
                .unmarshal(jsonFormat)
                .process(exchange -> {
                    ExpenseReportFlat flat = exchange.getIn().getBody(ExpenseReportFlat.class);
                    ExpenseReportXml xmlObj = ExpenseTransformer.transformToXml(flat);
                    String xmlString = XmlMarshallerUtil.marshalExpenseReport(xmlObj);

                    String nonce = generateNonce();
                    String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
                    String signature = generateSignature(
                            config.getAccount(),
                            config.getConsumerKey(),
                            config.getConsumerSecret(),
                            config.getToken(),
                            config.getTokenSecret(),
                            nonce,
                            timestamp
                    );

                    String soapRequest = generateSoapBody(
                            config.getAccount(),
                            config.getConsumerKey(),
                            config.getToken(),
                            nonce,
                            timestamp,
                            signature,
                            xmlString
                    );

                    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/xml; charset=utf-8");
                    exchange.getIn().setHeader("SOAPAction", "add");
                    exchange.getIn().setBody(soapRequest);
                })
                .to(config.getEndpointUrl());
    }

    private String generateNonce() {
        // length between 6 and 64
        String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        int length = Math.min(64, Math.max(6, uuid.length()));
        return uuid.substring(0, length);
    }

    private String generateSignature(
            String account,
            String consumerKey,
            String consumerSecret,
            String tokenKey,
            String tokenSecret,
            String nonce,
            String timestamp) {
        try {
            String key = consumerSecret + "&" + tokenSecret;
            String baseString = account + "&" + consumerKey + "&" + tokenKey
                    + "&" + nonce + "&" + timestamp;

            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec spec = new javax.crypto.spec.SecretKeySpec(
                    key.getBytes(java.nio.charset.StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(spec);

            byte[] hash = mac.doFinal(baseString.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return java.util.Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generating OAuth signature", e);
        }
    }

    private String generateSoapBody(
            String account,
            String consumerKey,
            String token,
            String nonce,
            String timestamp,
            String signature,
            String expenseXml) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:platformMsgs=\"urn:messages_2023_2.platform.webservices.netsuite.com\" " +
                "xmlns:platformCore=\"urn:core_2023_2.platform.webservices.netsuite.com\">\n" +
                " <soap:Header>\n" +
                "   <platformMsgs:tokenPassport>\n" +
                "     <platformCore:account>" + account + "</platformCore:account>\n" +
                "     <platformCore:consumerKey>" + consumerKey + "</platformCore:consumerKey>\n" +
                "     <platformCore:token>" + token + "</platformCore:token>\n" +
                "     <platformCore:version>1.0</platformCore:version>\n" +
                "     <platformCore:nonce>" + nonce + "</platformCore:nonce>\n" +
                "     <platformCore:timestamp>" + timestamp + "</platformCore:timestamp>\n" +
                "     <platformCore:signature algorithm=\"HMAC_SHA256\">" + signature + "</platformCore:signature>\n" +
                "   </platformMsgs:tokenPassport>\n" +
                " </soap:Header>\n" +
                " <soap:Body>\n" +
                "   <platformMsgs:add>\n" +
                "     <platformMsgs:record xsi:type=\"tranEmp:ExpenseReport\" xmlns:tranEmp=\"urn:employees_2023_2.transactions.webservices.netsuite.com\">\n" +
                expenseXml +
                "     </platformMsgs:record>\n" +
                "   </platformMsgs:add>\n" +
                " </soap:Body>\n" +
                "</soap:Envelope>";
    }
}
