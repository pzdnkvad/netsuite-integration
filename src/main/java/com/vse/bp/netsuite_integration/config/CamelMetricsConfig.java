package com.vse.bp.netsuite_integration.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.component.micrometer.eventnotifier.MicrometerExchangeEventNotifier;
import org.apache.camel.component.micrometer.messagehistory.MicrometerMessageHistoryFactory;
import org.apache.camel.component.micrometer.routepolicy.MicrometerRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelMetricsConfig {

    @Bean
    public CamelContextConfiguration camelContextConfiguration(MeterRegistry meterRegistry) {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.addRoutePolicyFactory(new MicrometerRoutePolicyFactory());

                camelContext.setMessageHistoryFactory(new MicrometerMessageHistoryFactory());

                camelContext.getManagementStrategy().addEventNotifier(new MicrometerExchangeEventNotifier());
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }

}
