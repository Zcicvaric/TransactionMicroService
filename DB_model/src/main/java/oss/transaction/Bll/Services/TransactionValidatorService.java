package oss.transaction.Bll.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oss.transaction.Bll.Dtos.TransactionToCancelDto;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Bll.Enums.TransactionType;
import oss.transaction.Bll.Interfaces.ITransactionValidatorService;
import oss.transaction.Dal.Models.Model;
import oss.transaction.Dal.Models.PaymentInstrument;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Repositories.ModelRepository;
import oss.transaction.Dal.Repositories.PaymentInstrumentRepository;
import oss.transaction.Dal.Repositories.TransactionsRepository;

@Service
public class TransactionValidatorService implements ITransactionValidatorService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private PaymentInstrumentRepository paymentInstrumentRepository;

    @Autowired
    private ModelRepository modelRepository;

    public void validateTransactionBeforeCancelation(TransactionToCancelDto transactionToCancel, long userId) throws Exception {
        Transaction transaction = transactionsRepository.findByUid(transactionToCancel.getUid());
        if(transaction == null) {
            throw new Exception("Wrong uid sent!");
        }
        else if (transaction.getUserId() != userId) {
            throw new Exception("You dont have a permission to cancel this transaction!");
        }
        else if(transaction.getCanceled()) {
            throw new Exception("Transaction is already canceled!");
        }
    }

    public void validateTransaction(TransactionToCreateDto transaction) throws Exception {
        try {
            validateIban(transaction.getPayerIBAN());
            validateIban(transaction.getReceiverIBAN());
            validateArgumentsForPaymentType(getTransactionTypeFromRequest(transaction), transaction);
            validatePersonFunds();
            validatePaymentInstrument(transaction);
            validateModel(transaction);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    private void validateIban(String iban) throws Exception {

    }

    private void validateArgumentsForPaymentType(TransactionType paymentType, TransactionToCreateDto transaction) throws Exception {
        if (paymentType.getValue() == TransactionType.Internal.getValue() || paymentType.getValue() == TransactionType.National.getValue()) {
            // Validate model
            // referenceNumber
            // Validate UsageCode
        }
        else if (paymentType.getValue() == TransactionType.International.getValue()) {
            // Validate SWIFT - send req to bank
        }
    }

    private void validatePersonFunds() throws Exception {
        // send request to account microservice
    }

    private TransactionType getTransactionTypeFromRequest(TransactionToCreateDto transaction) {
        return TransactionType.National;
    }

    private void validatePaymentInstrument(TransactionToCreateDto transaction) throws Exception {
        PaymentInstrument paymentInstrument = paymentInstrumentRepository.findById(transaction.getPaymentInstrumentId());
        if (paymentInstrument == null)
            throw new Exception("Payment instrument id is not valid!");
    }

    private void validateModel(TransactionToCreateDto transaction) throws Exception {
        Model model = modelRepository.findById(transaction.getModelId());
        if (model == null)
            throw new Exception("Model id is not valid!");
    }

}
