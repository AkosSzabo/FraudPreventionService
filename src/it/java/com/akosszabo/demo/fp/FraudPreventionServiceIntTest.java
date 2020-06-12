package com.akosszabo.demo.fp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = FraudPreventionServiceApplication.class)
@AutoConfigureMockMvc
public class FraudPreventionServiceIntTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCheckFlaggedForAccount() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010-02-15 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account2\",\n" +
                        "\"destinationAccountNumber\" : \"account3\",\n" +
                        "\"dollarAmount\" : 1000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flaggedForFraud").value(true))
                .andExpect(jsonPath("$.issues").isArray())
                .andExpect(jsonPath("$.issues", hasSize(1)))
                .andExpect(jsonPath("$.issues[0].message").value("No previous transactions for this payee"))
                .andExpect(jsonPath("$.issues[0].code").value("ACCOUNT"));
    }

    @Test
    public void testCheckNoFlag() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2020-06-10 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account1\",\n" +
                        "\"destinationAccountNumber\" : \"external1\",\n" +
                        "\"dollarAmount\" : 9000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flaggedForFraud").value(false))
                .andExpect(jsonPath("$.issues").isArray())
                .andExpect(jsonPath("$.issues", hasSize(0)));
    }

    @Test
    public void testCheckNoFlagSecondTransaction() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2020-06-10 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account3\",\n" +
                        "\"destinationAccountNumber\" : \"external1\",\n" +
                        "\"dollarAmount\" : 3000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flaggedForFraud").value(false))
                .andExpect(jsonPath("$.issues").isArray())
                .andExpect(jsonPath("$.issues", hasSize(0)));
    }

    @Test
    public void testCheckFlaggedForTwoIssues() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010-02-15 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account2\",\n" +
                        "\"destinationAccountNumber\" : \"external2\",\n" +
                        "\"dollarAmount\" : 10000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flaggedForFraud").value(true))
                .andExpect(jsonPath("$.issues").isArray())
                .andExpect(jsonPath("$.issues", hasSize(2)))
                .andExpect(content().string(containsString("\"code\":\"AMOUNT\"")))
                .andExpect(content().string(containsString("\"code\":\"FREQUENCY\"")));
    }



}
