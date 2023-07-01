package com.nttdata.service;

import com.nttdata.entity.Account;
import com.nttdata.entity.AccountDetail;
import com.nttdata.entity.Message;
import com.nttdata.entity.QueryBalance;
import com.nttdata.feignclient.*;
import com.nttdata.interfaces.MathOperation;
import com.nttdata.model.*;
import com.nttdata.repository.AccountDetailRepository;
import com.nttdata.repository.AccountRepository;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Servicio de la entidad Account para acceder al repository
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    ClientFeignClient clientFeignClient;


    private ProductFeignClient productFeignClient;


    private PersonFeignClient personFeignClient;


    private ClientProductFeignClient clientProductFeignClient;


    private AccountHistoryFeignClient accountHistoryFeignClient;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Metodo que busca todas los datos de la entidad Account
     * @return retorna una lista de objetos de la entidad Account
     */
    public Flux<Account> getAll(){
        return accountRepository.findAll();
    }

    /**
     * Metodo que busca las cuentas y muestra el saldo actual de ellas
     * @param id_client Identificador de la entidad Account
     * @return retorna una lista de la entidad QueryBalance
     */
    public Flux<QueryBalance> availableBalanceAccount(Long id_client){
        Flux<Account> lstAccount = this.getAll();
        List<QueryBalance> lstQueryBalance = new ArrayList<QueryBalance>();

        Observable<Account> accountObservable = Observable.fromIterable(lstAccount.toIterable());
        accountObservable
                .filter(account -> account.getId_client() == id_client)
                .map(account -> account.getBalance())
                .subscribe(
                        balance -> lstQueryBalance.add(new QueryBalance(null, null, balance)),
                        error -> System.out.println("error " + error.getMessage()),
                        () -> System.out.println("finaliza observable")
                );
        return Flux.fromIterable(lstQueryBalance);
    }
    /**
     * Metodo que busca los datos client product y muestra el credito actual de ellas
     * @param idClient Identificador de la entidad ClientProduct
     * @return retorna una lista de la entidad QueryBalance
     */
    public Flux<QueryBalance> listClientsProducts(Long idClient){
        Flux<ClientProduct> lstClientProduct = clientProductFeignClient.listClientsProducts();
        List<QueryBalance> lstQueryBalance = new ArrayList<QueryBalance>();

        lstClientProduct
                .filter(clientProduct -> clientProduct.getId_client() == idClient)
                .map(clientProduct -> clientProduct.getCredit())
                .subscribe(
                        credit -> lstQueryBalance.add(new QueryBalance(null, null, credit)),
                        error -> System.out.println("error " + error.getMessage()),
                        () -> System.out.println("finaliza")
                );
        return Flux.fromIterable(lstQueryBalance);
    }

    /**
     * Metodo que busca un objeto de la entidad Account
     * @param id Identificador de la entidad Account
     * @return retorna un objeto del tipo Account
     */
    public Mono<Account> getAccountById(Long id){
        Mono<Account> account = accountRepository.findById(id);
        try {
            System.out.println("id " + account.block().getId());
            account.toFuture().get().setAccountDetail(accountDetailRepository.findAccountDetailByAccount(account.block().getId()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    /**
     * Metodo que realiza la validacion, insercion y actualizacion de la entidad Account
     * @param account Objeto de la entidad Account
     * @return retorna el objeto de la entidad account insertado o actualizado
     */
    public Mono<Account> save(Account account) {
        boolean save = true;
        Message message = new Message();
        Account newAccount = new Account();
        try {
        Flux<Account> listAccount= accountRepository.findAll();
        Flux<Account> listAccountSearched = listAccount.filter(accountFilter -> accountFilter.getId_client() == account.getId_client())/*.collect(Collectors.toList())*/;

        Mono<Client> client = clientFeignClient.getClientById(account.getId_client());
        Product product = productFeignClient.getProductById(account.getId_product()).toFuture().get();

        List<AccountDetail> lPersonDoesntExist = this.checkIfPersonNotExists(account.getAccountDetail());
        Predicate<List<AccountDetail>> lPersonDoesntExistTest = aDetail -> !aDetail.isEmpty() && aDetail.size() > 0;
        Predicate<Flux<Account>> hasOneOrMoreAccount = accounts -> {
            try {
                return !accounts.collectList().toFuture().get().isEmpty() && accounts.collectList().toFuture().get().size() > 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        };
        Predicate<Client> existClient = eClient -> eClient == null;
        Predicate<Product> existProduct = eProduct -> eProduct == null;
        Predicate<Person> existPerson = ePerson -> ePerson == null;
        Predicate<Client> clientIsPersonalType = isPersonal -> isPersonal.getId_client_type() == 1 && isPersonal.getStatus() == 1;
        Predicate<Account> accountPersonalType = isAccPersonalType -> isAccPersonalType.getId_product() == 1 || isAccPersonalType.getId_product() == 2 || isAccPersonalType.getId_product() == 3;
        Predicate<Account> accountNotPersonalType = Predicate.not(accountPersonalType);
        Predicate<Client> clientIsEmpType = isEmp -> isEmp.getId_client_type() == 2 && isEmp.getStatus() == 1;
        List<AccountDetail> lstAccountHasAuthoridedSigner = account.getAccountDetail().stream().filter(authorized_signer -> authorized_signer.getAuthorized_signature() == 1).collect(Collectors.toList());
        Predicate<List<AccountDetail>> hasAutoridedSigner = isActive -> isActive.isEmpty() && isActive.size() == 0;
        Predicate<Account> accountEmpType = isAccPersonalType -> isAccPersonalType.getId_product() != 2;
        /*if (existProduct.test(product)){
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
        } else if (hasOneOrMoreAccount.test(listAccountSearched)){
                message.setCode("002");
                message.setDescription("The client has a active account");
                newAccount.setMessage(message);
                save = false;
            }
        } else if (clientIsEmpType.test(client)){
            if (accountEmpType.test(account)){
                message.setCode("002");
                message.setDescription("The empresarial client only can has a cuenta corriente");
                newAccount.setMessage(message);
                save = false;
            }
            System.out.println("lstAccountHasAuthoridedSigner " + lstAccountHasAuthoridedSigner.size());
            if (hasAutoridedSigner.test(lstAccountHasAuthoridedSigner)){
                message.setCode("002");
                message.setDescription("The empresarial customer must have at least one signer assigned.");
                newAccount.setMessage(message);
                save = false;
            } if (lPersonDoesntExistTest.test(lPersonDoesntExist)){
                message.setCode("002");
                message.setDescription("The persons : "+ lPersonDoesntExist.stream().map(x-> x.getId_person()).collect(Collectors.toList()) +" doesnt exists");
                newAccount.setMessage(message);
                save = false;
            }
        }*/

        if (save){

            message = new Message("001", "Account created");
            account.setId(sequenceGeneratorService.getSequenceNumber(Account.SEQUENCE_NAME));
            newAccount = accountRepository.save(account).toFuture().get();
            newAccount.setMessage(message);
            Long idAccountTmp = newAccount.getId();

             Predicate<Long> existId = (i) -> i > 0;
             if (existId.test(newAccount.getId())){
                 List<AccountDetail> listAccountDetail = account.getAccountDetail().stream().map(accountDetail -> {accountDetail.setId_account(idAccountTmp); return accountDetail;}).collect(Collectors.toList());
                 listAccountDetail.forEach((x) -> accountDetailRepository.save(x));
             }
             newAccount = this.getAccountById(idAccountTmp).toFuture().get();

        }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(newAccount);
    }
    /**
     * Metodo que verifica si la persona existe
     * @param lstAccountOrigin Objeto de la entidad AccountDetail
     * @return retorna una lista de objetos de la entidad AccountDetail
     */
    private List<AccountDetail> checkIfPersonNotExists(List<AccountDetail> lstAccountOrigin){
        List<AccountDetail> listNotExistPerson = new ArrayList<AccountDetail>();
        lstAccountOrigin.forEach((x) -> {
            Person person = null;
            try {
                person = personFeignClient.getPersonById(x.getId_person()).toFuture().get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (person==null){
                listNotExistPerson.add(x);
            }
        });
        return listNotExistPerson;
    }

    /**
     * Metodo que realiza la operacion de deposito,retiro, carga de consumo y pago
     * @param accountOperation objeto de la entidad Account
     * @return retorna un mensaje de operacion
     */
    public Mono<Message> doOperation(Account accountOperation){
        boolean save = true;
        Message message = new Message();
        if (accountOperation.getId() != null){
            Optional<Account> account = accountRepository.findById(accountOperation.getId()).blockOptional();
            if (account.isPresent()){
                AccountHistory accountHistory = new AccountHistory();
                AccountHistory accountHistoryUpdated = new AccountHistory();
                Predicate<BigDecimal> isLessToEqualToZero = value -> value.compareTo(BigDecimal.ZERO) == -1;
                Predicate<Account> isAccountProductNull = value -> value.getId_client_product() == null;
                Predicate<Account> isClientProductNull = Predicate.not(isAccountProductNull);
                MathOperation sum = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.add(amount);
                MathOperation substract = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.subtract(amount);
                BigDecimal amountUpdated = BigDecimal.ZERO;
                if (accountOperation.getOperation_type() == 1){
                    amountUpdated = this.operate(account.get().getBalance(), accountOperation.getAmount(),substract);
                } else if (accountOperation.getOperation_type() == 2){
                    amountUpdated = this.operate(account.get().getBalance(), accountOperation.getAmount(),sum);
                }
                System.out.println("isLessToEqualToZero" + amountUpdated);
                if (isLessToEqualToZero.test(amountUpdated)){
                    save = false;
                    message = new Message("001", "Account balance must not be less than zero");
                }

                if (save){
                    accountHistory.setAmount(accountOperation.getAmount());
                    accountHistory.setId_account(accountOperation.getId());
                    accountHistory.setId_client_product(accountOperation.getId_client_product());
                    accountHistory.setStatus(1);
                    accountHistory.setOperation_date(new Date());
                    accountHistory.setOperation_terminal(accountOperation.getModification_terminal());
                    accountHistory.setOperation_type(accountOperation.getOperation_type());
                    account.get().setBalance(amountUpdated);
                    Account accountUpdated = accountRepository.save(account.get()).block();
                    try {
                        accountHistoryUpdated = accountHistoryFeignClient.save(accountHistory).toFuture().get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    message = new Message("001", "Operation performed successfully");
                }
            } else {
                message = new Message("002", "The client doesnt exist");
            }
        } else if (accountOperation.getId_client_product() != null){
            Optional<ClientProduct> clientProduct = Optional.ofNullable(clientProductFeignClient.getClientProductById(accountOperation.getId_client_product()).block());
            if(clientProduct.isPresent()){
                AccountHistory accountHistory = new AccountHistory();
                AccountHistory accountHistoryUpdated = new AccountHistory();
                Predicate<BigDecimal> isLessToEqualToZero = value -> value.compareTo(BigDecimal.ZERO) == -1;
                Predicate<Account> isAccountProductNull = value -> value.getId_client_product() == null;
                Predicate<Account> isClientProductNull = Predicate.not(isAccountProductNull);

                MathOperation sum = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.add(amount);
                MathOperation substract = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.subtract(amount);

                BigDecimal amountUpdated = BigDecimal.ZERO;
                if (accountOperation.getOperation_type() == 3){ /*Pago de Producto*/
                    amountUpdated = this.operate(clientProduct.get().getCredit(), accountOperation.getAmount(),sum);
                } else if (accountOperation.getOperation_type() == 4){ /*Cargo de consumo*/
                    amountUpdated = this.operate(clientProduct.get().getCredit(), accountOperation.getAmount(),substract);
                }
                System.out.println("isLessToEqualToZero" + amountUpdated);
                if (isLessToEqualToZero.test(amountUpdated)){
                    save = false;
                    message = new Message("001", "The credit must not be less than zero");
                }
                Predicate<BigDecimal> isGreaterToCreditLimit = value -> clientProduct.get().getCredit_limit().compareTo(value) == -1;

                if (isLessToEqualToZero.test(amountUpdated)){
                    save = false;
                    message = new Message("001", "The credit must not be less than zero");
                } else if (isGreaterToCreditLimit.test(amountUpdated)) {
                    System.out.println(clientProduct.get().getCredit_limit().compareTo(amountUpdated) == 1);
                    System.out.println(clientProduct.get().getCredit_limit() + " " + amountUpdated);

                    save = false;
                    message = new Message("001", "The amount must not exceed the credit limit");
                }
                if (save){
                    accountHistory.setAmount(accountOperation.getAmount());
                    accountHistory.setId_account(accountOperation.getId());
                    accountHistory.setId_client_product(accountOperation.getId_client_product());
                    accountHistory.setStatus(1);
                    accountHistory.setOperation_date(new Date());
                    accountHistory.setOperation_terminal(accountOperation.getModification_terminal());
                    accountHistory.setOperation_type(accountOperation.getOperation_type());
                    clientProduct.get().setCredit(amountUpdated);
                    ClientProduct clientProductUpdated = clientProductFeignClient.save(clientProduct.get()).block();
                    try {
                        accountHistoryUpdated = accountHistoryFeignClient.save(accountHistory).toFuture().get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    message = new Message("001", "Operation performed successfully");
                }

            }else {
                message = new Message("002", "The client product doesnt exist");
            }
        }


        return Mono.just(message);
    }

    /**
     * Metodo que permite realizar una operacion matematica
     * @param currentAmount monto actual
     * @param amount monto de saldo o credito
     * @param mathOperation operacion matematica
     * @return retorna un valor de tipo BidDecimal
     */
    private BigDecimal operate(BigDecimal currentAmount, BigDecimal amount, MathOperation mathOperation){
        return mathOperation.operation(currentAmount,amount);
    }

    /**
     * Metodo que busca los movimientos de las cuentas por cliente
     * @param account Objeto de la entidad Account
     * @return retorna una lista historica de movimientos
     */
    public Flux<AccountHistory> listMovements(Account account){
        Flux<Account> lstAccount = this.getAll();
        Flux<ClientProduct> lstClientProduct = this.clientProductFeignClient.listClientsProducts();
        List<Account> lstAccountResult = new ArrayList<Account>();
        List<ClientProduct> lstClientProductResult = new ArrayList<ClientProduct>();
        AtomicReference<List<AccountHistory>> lstResultHistAccounts = new AtomicReference<>(new ArrayList<AccountHistory>());
        AtomicReference<List<AccountHistory>> lstResultHistClientProduct = new AtomicReference<>(new ArrayList<AccountHistory>());

        lstAccount
                .filter(acccount0 -> acccount0.getId_client() == account.getId_client())
                .subscribe(
                        accountTmp -> lstAccountResult.add(accountTmp),
                        error -> System.out.println("error " + error.getMessage()),
                        () -> {
                            lstResultHistAccounts.set((List<AccountHistory>) accountHistoryFeignClient.listAccountHistoryByAccount(lstAccountResult));
                            System.out.println("finalizo la consulta al microservicio accountHistoryFeignClient listAccountHistoryByAccount");
                        }
                );

        lstClientProduct
                .filter(clientProduct -> clientProduct.getId_client() == account.getId_client())
                .subscribe(
                        clientProductTmp -> lstClientProductResult.add(clientProductTmp),
                        error -> System.out.println("error " + error.getMessage()),
                        () -> {
                            lstResultHistClientProduct.set((List<AccountHistory>) accountHistoryFeignClient.listAccountHistoryByClientProduct(lstClientProductResult));
                            System.out.println("finalizo la consulta al microservicio accountHistoryFeignClient listAccountHistoryByClientProduct");
                        }
                );

        //System.out.println("lstResultHistAccounts"+lstResultHistAccounts.get().size());
        //System.out.println("lstResultHistClientProduct"+lstResultHistClientProduct.get().size());

        List<AccountHistory> listAccountHistoryFlatMap = Stream.of(lstResultHistAccounts.get(),lstResultHistClientProduct.get())
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("lstResultHistClientProduct"+listAccountHistoryFlatMap.size());
        return Flux.fromIterable(listAccountHistoryFlatMap);
    }

}
