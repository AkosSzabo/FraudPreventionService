package com.akosszabo.demo.fp.resource;

import com.akosszabo.demo.fp.converter.CheckResultsToResponseConverter;
import com.akosszabo.demo.fp.converter.RequestToTransactionContextConverter;
import com.akosszabo.demo.fp.domain.request.TransactionFraudCheckRequest;
import com.akosszabo.demo.fp.domain.response.TransactionFraudCheckResponse;
import com.akosszabo.demo.fp.service.FraudPreventionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Log
public class FraudPreventionResource {

    @Autowired
    private FraudPreventionService fraudPreventionService;
    @Autowired
    private CheckResultsToResponseConverter responseConverter;
    @Autowired
    private RequestToTransactionContextConverter requestConverter;

    @RequestMapping(value = "/api/prevention/check", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionFraudCheckResponse checkTransaction(@RequestBody @Valid final TransactionFraudCheckRequest request) {
        log.info(request.toString());

        //log.info(repo.findLastNTransactionsForAccounts(transactionFraudCheckRequest.getUserAccountNumber(),transactionFraudCheckRequest.getDestinationAccountNumber(),3).size() + "");
        return responseConverter.convert(fraudPreventionService.checkTransaction(requestConverter.convert(request)));
    }



}