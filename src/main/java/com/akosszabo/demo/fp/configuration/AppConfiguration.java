package com.akosszabo.demo.fp.configuration;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
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

    @Bean
    public Map<FraudCheckCode, FraudPreventionRule> ruleMap() {
        final HashMap<FraudCheckCode, FraudPreventionRule> map = new HashMap<>();
        map.put(FraudCheckCode.ACCOUNT,new AccountBasedFraudPreventionRule());
        map.put(FraudCheckCode.AMOUNT, new AmountBasedFraudPreventionRule());
        map.put(FraudCheckCode.FREQUENCY, new FrequencyBasedFraudPreventionRule());
        return map;
    }

}
