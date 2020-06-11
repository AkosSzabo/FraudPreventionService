package com.akosszabo.demo.fp.configuration;

import com.akosszabo.demo.fp.domain.FraudCheckType;
import com.akosszabo.demo.fp.service.AccountBasedFraudPreventionRule;
import com.akosszabo.demo.fp.service.AmountBasedFraudPreventionRule;
import com.akosszabo.demo.fp.service.FraudPreventionRule;
import com.akosszabo.demo.fp.service.FrequencyBasedFraudPreventionRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new FuelTypeConverter());
//    }

    @Bean
    public Map<FraudCheckType, FraudPreventionRule> ruleMap() {
        final HashMap<FraudCheckType, FraudPreventionRule> map = new HashMap<>();
        map.put(FraudCheckType.ACCOUNT,new AccountBasedFraudPreventionRule());
        map.put(FraudCheckType.AMOUNT, new AmountBasedFraudPreventionRule());
        map.put(FraudCheckType.FREQUENCY, new FrequencyBasedFraudPreventionRule());
        return map;
    }

}
