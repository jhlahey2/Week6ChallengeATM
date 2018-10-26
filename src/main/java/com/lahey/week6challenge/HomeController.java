package com.lahey.week6challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;

@Controller
public class HomeController {

    HashMap<Integer, Account> accountMap = new HashMap<Integer, Account>();

    @Autowired
    TransactionRepository transactionRepository;

    @RequestMapping("/")
    public String listTransactions(Model model){

        model.addAttribute("transactions", transactionRepository.findAll());
        return "showtransactions";
    }

    @GetMapping("/add")
    public String courseForm(Model model){

        model.addAttribute("transaction", new Transaction());
        return "transactionform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Transaction transaction, BindingResult result){

        Account tempAccount = null;

        if(result.hasErrors()){

            return "transactionform";
        }
        //if account exists get it, if not create it
        if( accountMap.containsKey(transaction.getAccountNumber()) ){

            tempAccount = accountMap.get(transaction.getAccountNumber());
        }
        else {

            tempAccount = new Account(transaction.getAccountNumber());
            accountMap.put(transaction.getAccountNumber(), tempAccount);

            System.out.printf("process form adding Account: %d \n",transaction.getAccountNumber() );
        }

        //update balance
        if(transaction.getAction().equalsIgnoreCase("deposit")){

            tempAccount.setBalance(tempAccount.getBalance() + transaction.getAmount());

        }else if(transaction.getAction().equalsIgnoreCase("withdrawal")){

            tempAccount.setBalance(tempAccount.getBalance() - transaction.getAmount());

        }else {

            System.out.println("Only type in \"deposit\" or \"withdrawal\" ");
        }


        //save the transaction to the repository
        transactionRepository.save(transaction);
        return "redirect:/";
    }

    @RequestMapping("/detail/{acct}")
    public String showAccount(@PathVariable("acct") int acct, Model model){

        System.out.printf(" finding account %d", acct);

        Account tempAccount = accountMap.get(acct);

        model.addAttribute("account", tempAccount);

//        model.addAttribute("course", courseRepository.findById(id).get());

        return "showaccount";
    }


}//end public class HomeController
