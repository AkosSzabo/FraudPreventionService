package com.akosszabo.demo.fp.controller;

import com.akosszabo.demo.fp.service.FraudPreventionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FraudPreventionController.class)
public class FraudPreventionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FraudPreventionService fraudPreventionService;

    @Test
    public void testBadRequest() throws Exception {
        mvc.perform(post("/api/prevention/check").content("--")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNotFoundError() throws Exception {
        mvc.perform(post("/api/prevention/check2").content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testValidRequest() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010-02-15 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account2\",\n" +
                        "\"payeeAccountNumber\" : \"account3\",\n" +
                        "\"dollarAmount\" : 1000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testMissingDate() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"userAccountNumber\"  : \"account2\",\n" +
                        "\"payeeAccountNumber\" : \"account3\",\n" +
                        "\"dollarAmount\" : 1000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidDate() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010t-02-15 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account2\",\n" +
                        "\"payeeAccountNumber\" : \"account3\",\n" +
                        "\"dollarAmount\" : 1000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAccountNumberMissing() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010-02-15 12:11:23\",\n" +
                        "\"payeeAccountNumber\" : \"account3\",\n" +
                        "\"dollarAmount\" : 1000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testpayeeAccountNumberMissing() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010-02-15 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account2\",\n" +
                        "\"dollarAmount\" : 1000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAmountMissing() throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010-02-15 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account2\",\n" +
                        "\"payeeAccountNumber\" : \"account3\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAmountInvalid()
            throws Exception {
        mvc.perform(post("/api/prevention/check")
                .content("{\"dateTime\" : \"2010-02-15 12:11:23\",\n" +
                        "\"userAccountNumber\"  : \"account2\",\n" +
                        "\"payeeAccountNumber\" : \"account3\",\n" +
                        "\"dollarAmount\" : -1000.2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

