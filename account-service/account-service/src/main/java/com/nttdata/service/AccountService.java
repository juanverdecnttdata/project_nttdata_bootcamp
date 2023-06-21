package com.nttdata.service;

import com.nttdata.entity.Account;
import com.nttdata.entity.Message;
import com.nttdata.feignclient.ClientFeignClient;
import com.nttdata.feignclient.ProductFeignClient;
import com.nttdata.model.Client;
import com.nttdata.model.Product;
import com.nttdata.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientFeignClient clientFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id){
        return accountRepository.findById(id).orElse(null);
    }
    /*Metodo usado para la validacion de informacion y guardado de la entidad en la base de datos*/
    public Account save(Account account) {
        boolean save = true;
        Message message = new Message();
        Account newAccount = new Account();

        List<Account> listAccount= this.getAll();
        List<Account> listSearched = listAccount.stream().filter(accountFilter -> accountFilter.getId_client() == account.getId_client()).collect(Collectors.toList());

        Client client = clientFeignClient.getClientById(account.getId_client());
        Product product = productFeignClient.getProductById(account.getId_product());

        Predicate<List<Account>> hasOneOrMoreAccount = accounts -> !accounts.isEmpty() && accounts.size() > 0;
        Predicate<Client> existClient = eClient -> eClient == null;
        Predicate<Product> existProduct = eProduct -> eProduct == null;
        Predicate<Client> clientIsPersonalType = isPersonal -> isPersonal.getId_client_type() == 1 && isPersonal.getStatus() == 1;
        Predicate<Account> accountPersonalType = isAccPersonalType -> isAccPersonalType.getId_product() == 1 || isAccPersonalType.getId_product() == 2 || isAccPersonalType.getId_product() == 3;
        Predicate<Account> accountNotPersonalType = Predicate.not(accountPersonalType);

        if (existProduct.test(product)){
            message.setCode("002");
            message.setDescription("The product doesnt exist");
            newAccount.setMessage(message);
            save = false;
        } else if (existClient.test(client)){
            message.setCode("002");
            message.setDescription("The client doesnt exist");
            newAccount.setMessage(message);
            save = false;
        } else if (clientIsPersonalType.test(client)){
            if (accountNotPersonalType.test(account)){
                message.setCode("002");
                message.setDescription("The product "+product.getName()+" cant be assigned to a personal client");
                newAccount.setMessage(message);
                save = false;
        } else if (hasOneOrMoreAccount.test(listSearched)){
                message.setCode("002");
                message.setDescription("The client has a active account");
                newAccount.setMessage(message);
                save = false;
            }
        }
        if (save){
             message = new Message("001", "Account created");
             newAccount = accountRepository.save(account);
             newAccount.setMessage(message);
        }
        return newAccount;
    }

}
