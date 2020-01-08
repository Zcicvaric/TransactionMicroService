package oss.transaction.Bll.Services;

import org.springframework.stereotype.Service;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Bll.Enums.PaymentType;
import oss.transaction.Bll.Interfaces.ITransactionService;
import oss.transaction.Bll.Interfaces.ITransactionValidatorService;

@Service
public class TransactionValidatorService implements ITransactionValidatorService {

    public void validateTransaction(TransactionToCreateDto transaction) throws Exception {
        try {
            validateIban(transaction.getPayerIBAN());
            validateIban(transaction.getReceiverIBAN());
            validateArgumentsForPaymentType(getPaymentTypeFromRequest(transaction), transaction);
            validatePersonFunds();
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    private void validateIban(String iban) throws Exception {

    }

    private void validateArgumentsForPaymentType(PaymentType paymentType, TransactionToCreateDto transaction) throws Exception {
        if (paymentType.getValue() == PaymentType.Internal.getValue() || paymentType.getValue() == PaymentType.National.getValue()) {
            // Validate model
            // referenceNumber
            // Validate UsageCode
        }
        else if (paymentType.getValue() == PaymentType.International.getValue()) {
            // Validate SWIFT - send req to bank
        }
    }

    private void validatePersonFunds() throws Exception {
        // TODO: poslat req racunima u kojem pitamo ima li platitelj x love na racunu
    }

    private PaymentType getPaymentTypeFromRequest(TransactionToCreateDto transaction) {
        return PaymentType.National;
    }

}
