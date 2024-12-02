package com.projeto_banco.controllers;

import com.projeto_banco.dto.UpdateAccountValueDTO;
import com.projeto_banco.dto.RequestErrorDTO;
import com.projeto_banco.models.Account;
import com.projeto_banco.models.User;
import com.projeto_banco.repository.AccountRepository;
import com.projeto_banco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController
{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-user")
    public ResponseEntity<?> createAccount(@RequestBody User user){
        int userExisted = userRepository.checkExists(user.getName(), user.getPassword());

        if (userExisted == 0) {
            User newUser= userRepository.save(user);
            return ResponseEntity.ok(newUser);
        } else {
            RequestErrorDTO error = new RequestErrorDTO("Usuário já possui conta!", "BAD_REQUEST");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody Account account){
        Account accountExisted = accountRepository.getByUseId(account.getUserId());
        if (accountExisted == null) {
            Account newAccount = accountRepository.save(account);
            return ResponseEntity.ok(newAccount);
        } else {
            RequestErrorDTO error = new RequestErrorDTO("Usuário já possui conta!", "BAD_REQUEST");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/cash-withdrawal")
    public ResponseEntity<?> cashWithdrawal(@RequestBody UpdateAccountValueDTO updateAccountValueDTO){
        Optional<Account> accountExisted = accountRepository.findById(updateAccountValueDTO.accountId);
        if (updateAccountValueDTO.value<0) {
            RequestErrorDTO error = new RequestErrorDTO("Valor inválido", "BAD_REQUEST");
            return ResponseEntity.badRequest().body(error);
        }

        if (accountExisted.isPresent()) {
            Account account = accountExisted.get();
            double balance = account.getBalance() - updateAccountValueDTO.value;
            if (balance < (account.getExtraWithdrawal()* -1)) {
                RequestErrorDTO error = new RequestErrorDTO("Sem saldo disponível para esse saque!", "BAD_REQUEST");
                return ResponseEntity.badRequest().body(error);
            }
            account.setBalance(balance);
            accountRepository.save(account);
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cash-deposit")
    public ResponseEntity<?> cashDeposit(@RequestBody UpdateAccountValueDTO updateAccountValueDTO){
        if (updateAccountValueDTO.value<0) {
            RequestErrorDTO error = new RequestErrorDTO("Valor inválido", "BAD_REQUEST");
            return ResponseEntity.badRequest().body(error);
        }

        Optional<Account> accountExisted = accountRepository.findById(updateAccountValueDTO.accountId);
        if (accountExisted.isPresent()) {
            Account account = accountExisted.get();
            double balance = account.getBalance() + updateAccountValueDTO.value;
            account.setBalance(balance);
            accountRepository.save(account);
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Account> getByUser(@RequestBody User user){
        User userExisted= userRepository.getUser(user.getName(), user.getPassword());
        Account account = accountRepository.getByUseId(userExisted.getId());
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
