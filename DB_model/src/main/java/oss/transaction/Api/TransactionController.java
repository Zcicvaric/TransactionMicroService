package oss.transaction.Api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import oss.transaction.Bll.Auth.Security.JwtLibrary;
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
    public Transaction getTransactionById(@PathVariable long transactionId, @RequestHeader("Authorisation") String token) {
        return transactionService.getTransaction(transactionId, JwtLibrary.getUserId(token));
    }

    @GetMapping(path = "")
    @ResponseBody
    public Iterable<Transaction> getAllTransactions(@RequestHeader("Authorisation") String token) {
        return transactionService.getAllTransactions(JwtLibrary.getUserId(token));
    }

    @GetMapping(path = "/completed")
    @ResponseBody
    public Iterable<Transaction> getCompletedTransactions(@RequestHeader("Authorisation") String token) {
        return transactionService.getCompletedTransactions(JwtLibrary.getUserId(token));
    }

    @GetMapping(path = "/canceled")
    @ResponseBody
    public Iterable<Transaction> getCanceledTransactions(@RequestHeader("Authorisation") String token) {
        return transactionService.getCanceledTransactions(JwtLibrary.getUserId(token));
    }

    @PostMapping(path = "/create")
    @ResponseBody
    public ResponseEntity createTransaction(@RequestBody TransactionToCreateDto transactionToCreateDto, @RequestHeader("Authorisation") String token) {
        try {
            transactionValidatorService.validateTransaction(transactionToCreateDto);
            Transaction transactionToReturn = transactionService.createTransaction(transactionToCreateDto, JwtLibrary.getUserId(token));
            return new ResponseEntity(transactionToReturn, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/cancel")
    @ResponseBody
    public ResponseEntity cancelTransaction(@RequestBody TransactionToCancelDto transactionToCancel, @RequestHeader("Authorisation") String token) throws IOException {
        try {
            transactionService.cancelTransaction(transactionToCancel.getUid(),JwtLibrary.getUserId(token));
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}







