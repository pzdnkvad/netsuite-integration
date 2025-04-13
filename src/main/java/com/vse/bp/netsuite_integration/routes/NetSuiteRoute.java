package com.vse.bp.netsuite_integration.routes;

import com.vse.bp.netsuite_integration.config.NetsuiteConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Component
public class NetSuiteRoute extends RouteBuilder {
    private final NetsuiteConfig config;
    private final MeterRegistry meterRegistry;

    @Autowired
    public NetSuiteRoute(NetsuiteConfig config, MeterRegistry meterRegistry) {
        this.config = config;
        this.meterRegistry = meterRegistry;
    }



    @Override
    public void configure() throws Exception {

        Counter netsuiteCounter = Counter.builder("netsuite.requests.total")
                .description("Number of requests sent to NetSuite")
                .register(meterRegistry);

        from("timer:first-timer?repeatCount=1")
                .routeId("singleRunRoute")
                .process(exchange -> {
                    netsuiteCounter.increment();
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

                    //log.info("Generated Signature: {}", signature);

                    String soapBody = generateSoapBody(
                            config.getAccount(),
                            config.getConsumerKey(),
                            config.getToken(),
                            nonce,
                            timestamp,
                            signature
                    );
                    //log.info("Generated SOAP Request: \n{}", soapBody);

                    exchange.getIn().setHeader("Content-Type", "text/xml; charset=utf-8");
                    exchange.getIn().setHeader("SOAPAction", "search");
                    exchange.getIn().setBody(soapBody);
                })
                .to("micrometer:timer:netsuite.request.duration?action=start")
                .to(config.getEndpointUrl())
                .to("micrometer:timer:netsuite.request.duration?action=stop")
                .log("Response: ${body}")
                .delay(100)
                .process(exchange -> exchange.getContext().getRouteController().stopRoute("singleRunRoute"));
    }


    private String generateNonce() {
        int length = new Random().nextInt(59) + 6; // length between 6 and 64
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, Math.min(length, uuid.length()));
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

    private String generateSoapBody(String account, String consumerKey, String token, String nonce, String timestamp, String signature) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:tns=\"urn:platform_2023_2.webservices.netsuite.com\" " +
                "xmlns:platformMsgs=\"urn:messages_2023_2.platform.webservices.netsuite.com\" " +
                "xmlns:platformFaults=\"urn:faults_2023_2.platform.webservices.netsuite.com\" " +
                "xmlns:platformCommon=\"urn:common_2023_2.platform.webservices.netsuite.com\" " +
                "xmlns:platformCore=\"urn:core_2023_2.platform.webservices.netsuite.com\" " +
                "xmlns:setupCustom=\"urn:customization_2023_2.setup.webservices.netsuite.com\">\n" +
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
                "   <searchPreferences xsi:type=\"SearchPreferences\">\n" +
                "     <bodyFieldsOnly>false</bodyFieldsOnly>\n" +
                "     <returnSearchColumns>true</returnSearchColumns>\n" +
                "     <pageSize>20</pageSize>\n" +
                "   </searchPreferences>\n" +
                " </soap:Header>\n" +
                " <soap:Body>\n" +
                "   <search xmlns=\"urn:messages_2023_2.platform.webservices.netsuite.com\">\n" +
                "     <searchRecord xsi:type=\"ns1:SubsidiarySearchBasic\" xmlns:ns1=\"urn:common_2023_2.platform.webservices.netsuite.com\">\n" +
                "     </searchRecord>\n" +
                "   </search>\n" +
                " </soap:Body>\n" +
                "</soap:Envelope>";
    }


}