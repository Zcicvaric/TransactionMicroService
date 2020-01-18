package oss.transaction.Api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import oss.transaction.Bll.Auth.Security.JwtLibrary;
import oss.transaction.Bll.Dtos.TransactionForDetailedDto;
import oss.transaction.Bll.Dtos.TransactionForListDto;
import oss.transaction.Bll.Dtos.TransactionToCancelDto;
import oss.transaction.Bll.Interfaces.ITransactionService;
import oss.transaction.Bll.Interfaces.ITransactionValidatorService;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@Controller
@RequestMapping(path="/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private ITransactionValidatorService transactionValidatorService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = {"/{transactionId}"})
    @ResponseBody
    public ResponseEntity getTransactionById(@PathVariable long transactionId, @RequestHeader("Authorisation") String token) {
        Transaction transaction = transactionService.getTransaction(transactionId, JwtLibrary.getUserId(token));
        if (transaction == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(transactionService.mapToDetailedTrasanction(transaction), HttpStatus.OK);
    }

    @GetMapping(path = "")
    @ResponseBody
    public Iterable<TransactionForListDto> getAllTransactions(@RequestHeader("Authorisation") String token) {
        return transactionService.mapToListTrasanction(transactionService.getAllTransactions(JwtLibrary.getUserId(token)));
    }

    @GetMapping(path = "/completed")
    @ResponseBody
    public Iterable<TransactionForListDto> getCompletedTransactions(@RequestHeader("Authorisation") String token) {
        return transactionService.mapToListTrasanction(transactionService.getCompletedTransactions(JwtLibrary.getUserId(token)));
    }

    @GetMapping(path = "/canceled")
    @ResponseBody
    public Iterable<TransactionForListDto> getCanceledTransactions(@RequestHeader("Authorisation") String token) {
        return transactionService.mapToListTrasanction(transactionService.getCanceledTransactions(JwtLibrary.getUserId(token)));
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public ResponseEntity createTransaction(@RequestBody TransactionToCreateDto transactionToCreateDto, @RequestHeader("Authorisation") String token) {
        try {
            transactionValidatorService.validateTransaction(transactionToCreateDto);
            TransactionForDetailedDto transactionToReturn = transactionService.createTransaction(transactionToCreateDto, JwtLibrary.getUserId(token));
            return new ResponseEntity(transactionToReturn, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/cancel")
    @ResponseBody
    public ResponseEntity cancelTransaction(@RequestBody TransactionToCancelDto transactionToCancel, @RequestHeader("Authorisation") String token) throws IOException {
        try {
            long userId = JwtLibrary.getUserId(token);
            transactionValidatorService.validateTransactionBeforeCancelation(transactionToCancel, userId);
            transactionService.cancelTransaction(transactionToCancel.getUid(), userId);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}







