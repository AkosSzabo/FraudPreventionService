package com.akosszabo.demo.fp.configuration;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.service.AccountBasedFraudPreventionRule;
import com.akosszabo.demo.fp.service.AmountBasedFraudPreventionRule;
import com.akosszabo.demo.fp.service.FraudPreventionRule;
import com.akosszabo.demo.fp.service.FrequencyBasedFraudPreventionRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {



}
