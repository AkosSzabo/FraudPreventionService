package com.akosszabo.demo.fp.converter;

import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.domain.request.TransactionFraudCheckRequest;
import com.akosszabo.demo.fp.entity.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class RequestToTransactionContextConverter implements Converter<TransactionFraudCheckRequest, TransactionContext> {

    @Override
    public TransactionContext convert(final TransactionFraudCheckRequest request) {
        final TransactionContext result = new TransactionContext();
        result.setDateTime(request.getDateTime());
        result.setDollarAmount(request.getDollarAmount());
        result.setUserAccountNumber(request.getUserAccountNumber());
        result.setDestinationAccountNumber(request.getDestinationAccountNumber());
        return result;
    }
}