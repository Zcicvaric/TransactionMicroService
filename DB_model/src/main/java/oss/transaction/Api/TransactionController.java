package oss.transaction.Api;


import org.springframework.http.HttpHeaders;
import oss.transaction.Bll.Auth.Model.JwtUser;
import oss.transaction.Bll.Auth.Security.JwtLibrary;
import oss.transaction.Bll.Dtos.TransactionToCancelDto;
import oss.transaction.Bll.Services.DisplayRequestHeadersServlet;
import oss.transaction.Bll.Services.TransactionService;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Repositories.TransactionsRepository;
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
    private TransactionsRepository transactionsRepository;

    @Autowired
    private TransactionService transactionService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/add")
    public @ResponseBody
    Transaction addNewTransaction(@RequestBody TransactionToCreateDto transactionToCreateDto, @RequestHeader("Authorisation") String token) {
        Transaction transactionToReturn = transactionService.AddNewTransaction(transactionToCreateDto, JwtLibrary.getUserId(token));
        return transactionToReturn;
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Transaction> getAllTransactions(@RequestHeader("Authorisation") String token) {
        return transactionsRepository.findByStornoIDIsNullAndUserIdEquals(JwtLibrary.getUserId(token));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/completed")
    public @ResponseBody
    Iterable<Transaction> getAllFinishedTransactions(@RequestHeader("Authorisation") String token) {
        return transactionsRepository.findByCanceledIsFalseAndUserIdEquals(JwtLibrary.getUserId(token));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/canceled")
    public @ResponseBody
    Iterable<Transaction> getAllCanceledTransactions(@RequestHeader("Authorisation") String token) {
        return transactionsRepository.findByCanceledIsTrueAndUserIdEquals(JwtLibrary.getUserId(token));
    }

    @PostMapping(path = "/cancel")
    public @ResponseBody
    Transaction cancelTransaction(@RequestBody TransactionToCancelDto transactionToCancel, @RequestHeader("Authorisation") String token) throws IOException {
        Transaction transaction=transactionService.createCancelTransaction(transactionToCancel.getUid(),JwtLibrary.getUserId(token));
        return transaction;
    }


    }







