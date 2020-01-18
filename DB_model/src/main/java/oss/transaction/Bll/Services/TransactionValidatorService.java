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
            validateArgumentsForPaymentType(getTransactionTypeFromRequest(transaction), transaction);
            //validateModel(transaction);
            //validateReferenceNumber(transaction.getReferenceNumber());
            //validateUsageCode(transaction.getUsageCode());
            //validateSWIFT(transaction.getSwiftCode());
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    private void validateArgumentsForPaymentType(TransactionType paymentType, TransactionToCreateDto transaction) throws Exception {
        if (paymentType.getValue() == TransactionType.Internal.getValue() || paymentType.getValue() == TransactionType.National.getValue()) {
            // Validate model
            validateModel(transaction);
            // referenceNumber
            validateReferenceNumber(transaction.getReferenceNumber());
            // Validate UsageCode
            validateUsageCode(transaction.getUsageCode());
        }
        else if (paymentType.getValue() == TransactionType.International.getValue()) {
            // Validate SWIFT - send req to bank
            validateSWIFT(transaction.getSwiftCode());

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

    /*
 Provjerava je li IBAN ispravan: Prva dva znaka moraju bit velika slova, sve ostalo moraju bit brojke i max duzina IBAN-a je 34
  */
    public void validateIban(String iban) throws Exception {
        if(!iban.substring(0,2).matches("[A-Z][A-Z]") | !iban.substring(2).matches("[0-9]+") | iban.length() > 34) {
            throw new RuntimeException("Invalid IBAN format!");
        }

    }

    //provjerava je li ispravan poziv na broj - uvjet je da ima najvise 22 znaka i moraju bit brojevi
    public void validateReferenceNumber(String referenceNumber) throws Exception {
        if(referenceNumber.length() > 22 | !referenceNumber.matches("[0-9]+"))
            throw new Exception("Reference number is not valid!");
    }

    //provjerava je li sifra namjene ispravna - uvjet je da je duzine 4 velika slova
    public void validateUsageCode(String usageCode) throws Exception {
        if(usageCode.length() != 4 | !usageCode.matches("[A-Z]+"))
            throw new Exception("Usage code is not valid!");
    }

    //provjerava je li SWIFT kod ispravan, uvjeti su: 8 znakova, prvih 6 znakova samo velika slova, zadnjih 5 znakova slova i brojevi
    public void validateSWIFT(String swift) throws Exception {
        if(swift.length() != 8 | !swift.substring(0,6).matches("[A-Z]+") | !swift.substring(7).matches("[A-Z,0-9]+")) {
            throw new Exception("SWIFT code is not valid!");
        }
    }

}
