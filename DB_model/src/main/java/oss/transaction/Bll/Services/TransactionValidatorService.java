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
import oss.transaction.Dal.Repositories.TransactionTypeRepository;
import oss.transaction.Dal.Repositories.TransactionsRepository;

@Service
public class TransactionValidatorService implements ITransactionValidatorService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private PaymentInstrumentRepository paymentInstrumentRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

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
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    private void validateArgumentsForPaymentType(TransactionType paymentType, TransactionToCreateDto transaction) throws Exception {
        if (paymentType.getValue() == TransactionType.Internal.getValue() || paymentType.getValue() == TransactionType.National.getValue()) {
            validateModel(transaction);
            validateReferenceNumber(transaction.getReferenceNumber());
            validateUsageCode(transaction.getUsageCode());
        }
        else if (paymentType.getValue() == TransactionType.International.getValue()) {
            validateSWIFT(transaction.getSwiftCode());
        }
    }

    private void validatePersonFunds() throws Exception {
        // send request to account microservice
    }

    private TransactionType getTransactionTypeFromRequest(TransactionToCreateDto transaction) {
        if(transaction.getPayerIBAN().substring(0,2).equals(transaction.getReceiverIBAN().substring(0,2))) {
            if(transaction.getPayerIBAN().substring(4,11).equals(transaction.getReceiverIBAN().substring(4,11))) {
                return TransactionType.Internal;
            }
            else {
                return TransactionType.National;
            }
        }
        else
            return TransactionType.International;
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

    public void validateIban(String iban) throws Exception {
        if(!iban.substring(0,2).matches("[A-Z][A-Z]") || !iban.substring(2).matches("[0-9]+") || iban.length() > 34) {
            throw new RuntimeException("Invalid IBAN format!");
        }

    }

    public void validateReferenceNumber(String referenceNumber) throws Exception {
        if (referenceNumber != null)
            if(referenceNumber.length() > 22 || !referenceNumber.matches("[0-9]+"))
                throw new Exception("Reference number is not valid!");
    }

    public void validateUsageCode(String usageCode) throws Exception {
        if (usageCode != null)
            if(usageCode.length() != 4 || !usageCode.matches("[A-Z]+"))
                throw new Exception("Usage code is not valid!");
    }

    public void validateSWIFT(String swift) throws Exception {
        if(swift == null || swift.length() != 8 || !swift.substring(0,6).matches("[A-Z]+") || !swift.substring(7).matches("[A-Z0-9]+")) {
            throw new Exception("SWIFT code is not valid!");
        }
    }

}
