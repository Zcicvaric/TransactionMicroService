package oss.transaction.Api;


import oss.transaction.Bll.Services.DisplayRequestHeadersServlet;
import oss.transaction.Bll.Services.TransactionService;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Repositories.TransactionsRepository;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.UID;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(path="/transactions")
public class TransactionController {
    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private TransactionService transactionService;



    @PostMapping(path = "/add")
    public @ResponseBody
    Transaction addNewTransaction(@RequestBody TransactionToCreateDto transactionToCreateDto) {
        Transaction transactionToReturn = transactionService.AddNewTransaction(transactionToCreateDto);
        return transactionToReturn;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Transaction> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    @GetMapping(path = {"id/{id}"})
    public @ResponseBody
    Transaction getTransactionById(@PathVariable long id) {
        return transactionsRepository.findById(id);
    }

    @DeleteMapping(path = {"{id}"})
    public @ResponseBody
    String deleteTransactionById(@PathVariable long id) {
        transactionsRepository.deleteById(id);
        return "Deleted a transaction!";
    }

    @GetMapping(path = {"receiver/{receiverIBAN}"})
    public @ResponseBody
    Iterable<Transaction> getAllTransactionsWithReceiverIBAN(@PathVariable String receiverIBAN) {
        return transactionsRepository.findByReceiverIBAN(receiverIBAN);
    }

    @PostMapping(path = "/cancel")
    public @ResponseBody
    Transaction cancelTransaction(@RequestBody String uid) throws IOException {
        Transaction transaction=transactionService.createCancelTransaction(uid);
        System.out.print(uid);
        return transaction;

    }

    }







