package com.nttdata.service;

import com.nttdata.entity.*;
import com.nttdata.interfaces.MathOperation;
import com.nttdata.model.*;
import com.nttdata.repository.AccountDetailRepository;
import com.nttdata.repository.AccountLogRepository;
import com.nttdata.repository.AccountRepository;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private AccountLogRepository accountLogRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Metodo que busca todas los datos de la entidad Account
     *
     * @return retorna una lista de objetos de la entidad Account
     */
    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    /**
     * Metodo que busca las cuentas y muestra el saldo actual de ellas
     *
     * @param id_client Identificador de la entidad Account
     * @return retorna una lista de la entidad QueryBalance
     */
    public Flux<QueryBalance> availableBalanceAccount(Long id_client) {
        List<Account> lstAccount = null;
        try {
            lstAccount = this.getAll().collectList().toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        List<QueryBalance> lstQueryBalance = new ArrayList<QueryBalance>();
        Observable<Account> accountObservable = Observable.fromIterable(lstAccount);
        accountObservable.filter(account -> account.getId_client() == id_client).map(account -> account.getBalance()).subscribe(balance -> lstQueryBalance.add(new QueryBalance(null, null, balance)), error -> System.out.println("error " + error.getMessage()), () -> System.out.println("finaliza observable"));
        return Flux.fromIterable(lstQueryBalance);
    }

    /**
     * Metodo que busca los datos client product y muestra el credito actual de ellas
     *
     * @param idClient Identificador de la entidad ClientProduct
     * @return retorna una lista de la entidad QueryBalance
     */
    public Flux<QueryBalance> listClientsProducts(Long idClient) {
        WebClient webClientProductClient = WebClient.builder().baseUrl(Constant.urlClientProduct).build();
        List<ClientProduct> lstClientProduct = null;
        try {
            lstClientProduct = webClientProductClient.get().uri(Constant.getAllClientProduct).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(ClientProduct.class).onErrorResume(e -> Mono.empty())
                    //.delaySubscription(Duration.ofSeconds(1))
                    .delaySubscription(Duration.ofMillis(150)).collectList().toFuture().get();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        List<QueryBalance> lstQueryBalance = new ArrayList<QueryBalance>();

        Observable<ClientProduct> productsObservable = Observable.fromIterable(lstClientProduct);
        productsObservable.filter(clientProduct -> clientProduct.getId_client() == idClient).map(clientProduct -> clientProduct.getCredit()).subscribe(credit -> lstQueryBalance.add(new QueryBalance(null, null, credit)), error -> System.out.println("error " + error.getMessage()), () -> System.out.println("finaliza"));
        return Flux.fromIterable(lstQueryBalance);
    }

    /**
     * Metodo que busca un objeto de la entidad Account
     *
     * @param id Identificador de la entidad Account
     * @return retorna un objeto del tipo Account
     */
    public Mono<Account> getAccountById(Long id) {
        Mono<Account> account = accountRepository.findById(id);
        Account newAccount = new Account();
        try {
            List<AccountDetail> accountDetails = accountDetailRepository.findAll().filter(accountDetail -> accountDetail.getId_account() == id).collectList().toFuture().get();
            newAccount = account.toFuture().get();
            newAccount.setAccountDetail(accountDetails);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(newAccount);
    }

    /**
     * Metodo que realiza la validacion, insercion y actualizacion de la entidad Account
     *
     * @param account Objeto de la entidad Account
     * @return retorna el objeto de la entidad account insertado o actualizado
     */
    public Mono<Account> saveAccount(Account account) {
        boolean save = true;
        Message message = new Message();
        Account newAccount = new Account();
        Client client = new Client();
        Product product = new Product();
        try {
            List<Account> listAccount = accountRepository.findAll().collectList().toFuture().get();
            List<Account> listAccountSearched = listAccount.stream().filter(accountFilter -> accountFilter.getId_client() == account.getId_client()).collect(Collectors.toList());

            WebClient webClientClient = WebClient.builder().baseUrl(Constant.urlClient).build();
            client = webClientClient.get().uri(Constant.getClientById + account.getId_client()).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Client.class).onErrorResume(e -> Mono.empty())
                    //.delaySubscription(Duration.ofSeconds(1))
                    .delaySubscription(Duration.ofMillis(150)).toFuture().get();

            WebClient webClientProduct = WebClient.builder().baseUrl(Constant.urlProduct).build();
            product = webClientProduct.get().uri(Constant.getProductById + account.getId_product()).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Product.class).onErrorResume(e -> Mono.empty()).delaySubscription(Duration.ofMillis(150)).toFuture().get();

            List<AccountDetail> lPersonDoesntExist = this.checkIfPersonNotExists(account.getAccountDetail());
            Predicate<List<AccountDetail>> lPersonDoesntExistTest = aDetail -> !aDetail.isEmpty() && aDetail.size() > 0;
            Predicate<List<Account>> hasOneOrMoreAccount = accounts -> !accounts.isEmpty() && accounts.size() > 0;

            Predicate<Client> existClient = eClient -> eClient == null || eClient.getId() == null;
            Predicate<Product> existProduct = eProduct -> eProduct == null || eProduct.getId() == null;
            Predicate<Person> existPerson = ePerson -> ePerson == null || ePerson.getId() == null;
            Predicate<Client> clientIsPersonalType = isPersonal -> isPersonal.getId_client_type() == 1 && isPersonal.getStatus() == 1;
            Predicate<Account> accountPersonalType = isAccPersonalType -> isAccPersonalType.getId_product() == 1 || isAccPersonalType.getId_product() == 2 || isAccPersonalType.getId_product() == 3;
            Predicate<Account> accountNotPersonalType = Predicate.not(accountPersonalType);
            Predicate<Client> clientIsEmpType = isEmp -> isEmp.getId_client_type() == 2 && isEmp.getStatus() == 1;
            List<AccountDetail> lstAccountHasAuthoridedSigner = account.getAccountDetail().stream().filter(authorized_signer -> authorized_signer.getAuthorized_signature() == 1).collect(Collectors.toList());
            Predicate<List<AccountDetail>> hasAutoridedSigner = isActive -> isActive.isEmpty() && isActive.size() == 0;
            Predicate<Account> accountEmpType = isAccPersonalType -> isAccPersonalType.getId_product() != 2;
            if (existProduct.test(product)) {
                message.setCode("002");
                message.setDescription("The product doesnt exist");
                newAccount.setMessage(message);
                save = false;
            } else if (existClient.test(client)) {
                message.setCode("002");
                message.setDescription("The client doesnt exist");
                newAccount.setMessage(message);
                save = false;
            } else if (clientIsPersonalType.test(client)) {
                if (accountNotPersonalType.test(account)) {
                    message.setCode("002");
                    message.setDescription("The product " + product.getName() + " cant be assigned to a personal client");
                    newAccount.setMessage(message);
                    save = false;
                } else if (hasOneOrMoreAccount.test(listAccountSearched)) {
                    message.setCode("002");
                    message.setDescription("The client has a active account");
                    newAccount.setMessage(message);
                    save = false;
                }
            } else if (clientIsEmpType.test(client)) {
                if (accountEmpType.test(account)) {
                    message.setCode("002");
                    message.setDescription("The empresarial client only can has a cuenta corriente");
                    newAccount.setMessage(message);
                    save = false;
                }
                System.out.println("lstAccountHasAuthoridedSigner " + lstAccountHasAuthoridedSigner.size());
                if (hasAutoridedSigner.test(lstAccountHasAuthoridedSigner)) {
                    message.setCode("002");
                    message.setDescription("The empresarial customer must have at least one signer assigned.");
                    newAccount.setMessage(message);
                    save = false;
                }
                if (lPersonDoesntExistTest.test(lPersonDoesntExist)) {
                    message.setCode("002");
                    message.setDescription("The persons : " + lPersonDoesntExist.stream().map(x -> x.getId_person()).collect(Collectors.toList()) + " doesnt exists");
                    newAccount.setMessage(message);
                    save = false;
                }
            }

            if (save) {
                System.out.println("save " + save);

                message = new Message("001", "Account created");
                account.setN_transactions(Constant.number_transaction_account);
                account.setId(sequenceGeneratorService.getSequenceNumber(Account.SEQUENCE_NAME));
                newAccount = this.save(account).toFuture().get();

                Long idAccountTmp = newAccount.getId();

                Predicate<Long> existId = (i) -> i > 0;
                if (existId.test(newAccount.getId()) && account.getAccountDetail() != null) {
                    System.out.println("idAccountTmp metodo If " + idAccountTmp);

                    List<AccountDetail> listAccountDetail = account.getAccountDetail().stream().map(accountDetail -> {
                        accountDetail.setId_account(idAccountTmp);
                        return accountDetail;
                    }).collect(Collectors.toList());

                    listAccountDetail.forEach((x) -> {
                        try {
                            x.setId(sequenceGeneratorService.getSequenceNumber(AccountDetail.SEQUENCE_NAME));
                            AccountDetail accountDetail = accountDetailRepository.save(x).toFuture().get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                newAccount = this.getAccountById(idAccountTmp).toFuture().get();
                newAccount.setMessage(message);
            }
        } catch (InterruptedException e) {
            message.setCode("-1");
            message.setDescription(e.getMessage());
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            message.setCode("-1");
            message.setDescription(e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            message.setCode("-1");
            message.setDescription(e.getMessage());
            throw new RuntimeException(e);
        }
        return Mono.just(newAccount);
    }

    /**
     * Metodo que verifica si la persona existe
     *
     * @param lstAccountOrigin Objeto de la entidad AccountDetail
     * @return retorna una lista de objetos de la entidad AccountDetail
     */
    private List<AccountDetail> checkIfPersonNotExists(List<AccountDetail> lstAccountOrigin) {
        AtomicReference<List<AccountDetail>> listNotExistPerson = new AtomicReference<>(new ArrayList<AccountDetail>());
        if (lstAccountOrigin != null) {
            lstAccountOrigin.forEach((x) -> {
                WebClient webClientPerson = WebClient.builder().baseUrl(Constant.urlPerson).build();
                Person person = new Person();
                try {
                    person = webClientPerson.get().uri(Constant.getPersonById + x.getId_person()).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Person.class).onErrorResume(e -> null).delaySubscription(Duration.ofMillis(75)).toFuture().get();

                    if (person.getId() == null) {
                        listNotExistPerson.get().add(x);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e) {
                    throw new NullPointerException(e.getMessage());
                }

            });
        }
        System.out.println("listNotExistPerson " + listNotExistPerson.get().size());
        return listNotExistPerson.get();
    }

    /**
     * Metodo que realiza la operacion de deposito,retiro, carga de consumo y pago
     *
     * @param accountOperation objeto de la entidad Account
     * @return retorna un mensaje de operacion
     */
    public Mono<Message> doOperation(Account accountOperation) {
        boolean save = true;
        boolean saveAutomaticOperation = false;
        Message message = new Message();
        Product product = new Product();
        BigDecimal amountComission = BigDecimal.ZERO;
        WebClient webClientProductClient = WebClient.builder().baseUrl(Constant.urlClientProduct).build();
        WebClient webClientProduct = WebClient.builder().baseUrl(Constant.urlProduct).build();
        WebClient webAccountHistoryClient = WebClient.builder().baseUrl(Constant.urlAccountHistory).build();
        try {
            if (accountOperation.getId() != null) {
                Account account = accountRepository.findById(accountOperation.getId()).toFuture().get();
                if (account != null) {
                    AccountHistory accountHistory = new AccountHistory();
                    AccountHistory accountHistoryAutomaticOp = new AccountHistory();
                    AccountHistory accountHistoryUpdated = new AccountHistory();
                    Predicate<BigDecimal> isLessToEqualToZero = value -> value.compareTo(BigDecimal.ZERO) == -1;
                    Predicate<Account> isAccountProductNull = value -> value.getId_client_product() == null;
                    Predicate<Account> isClientProductNull = Predicate.not(isAccountProductNull);
                    MathOperation sum = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.add(amount);
                    MathOperation substract = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.subtract(amount);
                    BigDecimal amountUpdated = BigDecimal.ZERO;
                    Integer currentNumberOfTransactions = account.getN_transactions();
                    if (accountOperation.getOperation_type() == 1) {
                        amountUpdated = this.operate(account.getBalance(), accountOperation.getAmount(), substract);
                    } else if (accountOperation.getOperation_type() == 2) {
                        amountUpdated = this.operate(account.getBalance(), accountOperation.getAmount(), sum);
                    }
                    if (account.getN_transactions() > 0) {
                        account.setN_transactions(currentNumberOfTransactions - 1);
                    } else {
                        saveAutomaticOperation = true;
                        product = webClientProduct
                                .get()
                                .uri(Constant.getProductById + account.getId_product())
                                //.body(BodyInserters.fromValue(accountHistory))
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(Product.class)
                                .onErrorResume(e -> Mono.empty())
                                .delaySubscription(Duration.ofMillis(150)).toFuture().get();
                        amountComission = product.getComission();
                        amountUpdated = this.operate(amountUpdated, amountComission, substract);
                    }

                    System.out.println("isLessToEqualToZero" + amountUpdated);
                    if (isLessToEqualToZero.test(amountUpdated)) {
                        save = false;
                        message = new Message("001", "Account balance must not be less than zero");
                    }

                    if (save) {
                        accountHistory.setAmount(accountOperation.getAmount());
                        accountHistory.setId_account(accountOperation.getId());
                        accountHistory.setId_client_product(accountOperation.getId_client_product());
                        accountHistory.setStatus(1);
                        accountHistory.setOperation_date(new Date());
                        accountHistory.setOperation_terminal(accountOperation.getModification_terminal());
                        accountHistory.setOperation_type(accountOperation.getOperation_type());
                        account.setBalance(amountUpdated);
                        Account accountUpdated = this.update(account).toFuture().get();

                        if (saveAutomaticOperation) {
                            accountHistoryAutomaticOp.setAmount(amountComission);
                            accountHistoryAutomaticOp.setId_account(accountOperation.getId());
                            accountHistoryAutomaticOp.setId_client_product(accountOperation.getId_client_product());
                            accountHistoryAutomaticOp.setStatus(1);
                            accountHistoryAutomaticOp.setOperation_date(new Date());
                            accountHistoryAutomaticOp.setOperation_terminal(accountOperation.getModification_terminal());
                            accountHistoryAutomaticOp.setOperation_type(Constant.id_type_operation_automatic_payment);
                        }
                        try {

                            accountHistoryUpdated = webAccountHistoryClient
                                    .post()
                                    .uri(Constant.saveAccountHistory)
                                    .body(BodyInserters.fromValue(accountHistory))
                                    .accept(MediaType.APPLICATION_JSON)
                                    .retrieve()
                                    .bodyToMono(AccountHistory.class)
                                    .onErrorResume(e -> Mono.empty())
                                    .delaySubscription(Duration.ofMillis(150)).toFuture().get();
                            if (saveAutomaticOperation) {
                                webAccountHistoryClient
                                        .post()
                                        .uri(Constant.saveAccountHistory)
                                        .body(BodyInserters.fromValue(accountHistoryAutomaticOp))
                                        .accept(MediaType.APPLICATION_JSON)
                                        .retrieve()
                                        .bodyToMono(AccountHistory.class)
                                        .onErrorResume(e -> Mono.empty())
                                        .delaySubscription(Duration.ofMillis(150)).toFuture().get();
                            }
                            //accountHistoryUpdated = accountHistoryFeignClient.save(accountHistory).toFuture().get();
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
            } else if (accountOperation.getId_client_product() != null) {

                //Optional<ClientProduct>  clientProduct=  Optional.ofNullable(accountRepository.findById(accountOperation.getId()).toFuture().get());
                Optional<ClientProduct> clientProduct = Optional.ofNullable(webClientProductClient.get().uri(Constant.getClientProductById + accountOperation.getId_client_product()).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ClientProduct.class).onErrorResume(e -> Mono.empty())
                        //.delaySubscription(Duration.ofSeconds(1))
                        .delaySubscription(Duration.ofMillis(150)).toFuture().get());

                //Optional<ClientProduct> clientProduct = Optional.ofNullable(clientProductFeignClient.getClientProductById(accountOperation.getId_client_product()).block());
                if (clientProduct.isPresent()) {
                    AccountHistory accountHistory = new AccountHistory();
                    AccountHistory accountHistoryUpdated = new AccountHistory();
                    Predicate<BigDecimal> isLessToEqualToZero = value -> value.compareTo(BigDecimal.ZERO) == -1;
                    Predicate<Account> isAccountProductNull = value -> value.getId_client_product() == null;
                    Predicate<Account> isClientProductNull = Predicate.not(isAccountProductNull);

                    MathOperation sum = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.add(amount);
                    MathOperation substract = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.subtract(amount);

                    BigDecimal amountUpdated = BigDecimal.ZERO;
                    if (accountOperation.getOperation_type() == 3) { /*Pago de Producto*/
                        amountUpdated = this.operate(clientProduct.get().getCredit(), accountOperation.getAmount(), sum);
                    } else if (accountOperation.getOperation_type() == 4) { /*Cargo de consumo*/
                        amountUpdated = this.operate(clientProduct.get().getCredit(), accountOperation.getAmount(), substract);
                    }
                    System.out.println("isLessToEqualToZero 2 : " + amountUpdated);
                    if (isLessToEqualToZero.test(amountUpdated)) {
                        save = false;
                        message = new Message("001", "The credit must not be less than zero");
                    }
                    Predicate<BigDecimal> isGreaterToCreditLimit = value -> clientProduct.get().getCredit_limit().compareTo(value) == -1;

                    if (isLessToEqualToZero.test(amountUpdated)) {
                        save = false;
                        message = new Message("001", "The credit must not be less than zero");
                    } else if (isGreaterToCreditLimit.test(amountUpdated)) {
                        System.out.println(clientProduct.get().getCredit_limit().compareTo(amountUpdated) == 1);
                        System.out.println(clientProduct.get().getCredit_limit() + " " + amountUpdated);

                        save = false;
                        message = new Message("001", "The amount must not exceed the credit limit");
                    }
                    if (save) {
                        System.out.println("3 ");
                        accountHistory.setAmount(accountOperation.getAmount());
                        accountHistory.setId_account(accountOperation.getId());
                        accountHistory.setId_client_product(accountOperation.getId_client_product());
                        accountHistory.setStatus(1);
                        accountHistory.setOperation_date(new Date());
                        accountHistory.setOperation_terminal(accountOperation.getModification_terminal());
                        accountHistory.setOperation_type(accountOperation.getOperation_type());
                        clientProduct.get().setCredit(amountUpdated);
                        //ClientProduct clientProductUpdated = clientProductFeignClient.save(clientProduct.get()).toFuture().get();
                        System.out.println("4 ");
                        ClientProduct clientProductUpdated = webClientProductClient.post().uri(Constant.saveClientProduct).body(BodyInserters.fromValue(clientProduct.get())).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ClientProduct.class).onErrorResume(e -> Mono.empty())
                                //.delaySubscription(Duration.ofSeconds(1))
                                .delaySubscription(Duration.ofMillis(150)).toFuture().get();
                        System.out.println("5");
                        try {
                            accountHistoryUpdated = webAccountHistoryClient.post().uri(Constant.saveAccountHistory).contentType(MediaType.APPLICATION_JSON).bodyValue(accountHistory).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(AccountHistory.class)
                                    //.onErrorResume(e -> Mono.empty())
                                    //.delaySubscription(Duration.ofSeconds(1))
                                    .delaySubscription(Duration.ofMillis(150)).toFuture().get();
                            System.out.println("6 ");
                            System.out.println("id registrado" + accountHistoryUpdated.getId());
                            //accountHistoryUpdated = accountHistoryFeignClient.save(accountHistory).toFuture().get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                        message = new Message("001", "Operation performed successfully");
                    }

                } else {
                    message = new Message("002", "The client product doesnt exist");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return Mono.just(message);
    }

    /**
     * Metodo que permite realizar una operacion matematica
     *
     * @param currentAmount monto actual
     * @param amount        monto de saldo o credito
     * @param mathOperation operacion matematica
     * @return retorna un valor de tipo BidDecimal
     */
    private BigDecimal operate(BigDecimal currentAmount, BigDecimal amount, MathOperation mathOperation) {
        return mathOperation.operation(currentAmount, amount);
    }

    /**
     * Metodo que busca los movimientos de las cuentas por cliente
     *
     * @param account Objeto de la entidad Account
     * @return retorna una lista historica de movimientos
     */
    public Flux<AccountHistory> listMovements(Account account) {
        List<ClientProduct> lstClientProduct = null;
        List<Account> lstAccount = null;
        List<AccountHistory> listAccountHistoryFlatMap = null;
        WebClient webClientProductClient = WebClient.builder().baseUrl(Constant.urlClientProduct).build();
        WebClient webAccountHistoryClient = WebClient.builder().baseUrl(Constant.urlAccountHistory).build();
        try {
            lstAccount = this.getAll().collectList().toFuture().get();

            lstClientProduct = webClientProductClient.get().uri(Constant.getAllClientProduct).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(ClientProduct.class).onErrorResume(e -> Mono.empty())
                    //.delaySubscription(Duration.ofSeconds(1))
                    .delaySubscription(Duration.ofMillis(150)).collectList().toFuture().get();

            List<Account> lstAccountResult = new ArrayList<Account>();
            List<ClientProduct> lstClientProductResult = new ArrayList<ClientProduct>();
            AtomicReference<List<AccountHistory>> lstResultHistAccounts = new AtomicReference<>(new ArrayList<AccountHistory>());
            AtomicReference<List<AccountHistory>> lstResultHistClientProduct = new AtomicReference<>(new ArrayList<AccountHistory>());

            Observable<Account> accountObservable = Observable.fromIterable(lstAccount);
            accountObservable.filter(acccount0 -> acccount0.getId_client() == account.getId_client()).subscribe(accountTmp -> lstAccountResult.add(accountTmp), error -> System.out.println("error " + error.getMessage()), () -> {
                lstResultHistAccounts.set(/*accountHistoryFeignClient.listAccountHistoryByAccount(lstAccountResult)*/
                        webAccountHistoryClient.post().uri(Constant.listAccountHistoryByAccount).body(BodyInserters.fromValue(lstAccountResult)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(AccountHistory.class).onErrorResume(e -> Mono.empty()).delaySubscription(Duration.ofMillis(150)).collectList().toFuture().get());
                System.out.println("finalizo la consulta al microservicio accountHistoryFeignClient listAccountHistoryByAccount");
            });

            Observable<ClientProduct> clientProductObservable = Observable.fromIterable(lstClientProduct);
            clientProductObservable.filter(clientProduct -> clientProduct.getId_client() == account.getId_client()).subscribe(clientProductTmp -> lstClientProductResult.add(clientProductTmp), error -> System.out.println("error " + error.getMessage()), () -> {
                lstResultHistClientProduct.set(/*accountHistoryFeignClient.listAccountHistoryByClientProduct(lstClientProductResult)*/
                        webAccountHistoryClient.post().uri(Constant.listAccountHistoryByClientProduct).body(BodyInserters.fromValue(lstClientProductResult)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(AccountHistory.class).onErrorResume(e -> Mono.empty()).delaySubscription(Duration.ofMillis(150)).collectList().toFuture().get()


                );
                System.out.println("finalizo la consulta al microservicio accountHistoryFeignClient listAccountHistoryByClientProduct");
            });

            System.out.println("lstResultHistAccounts" + lstResultHistAccounts.get().size());
            System.out.println("lstResultHistClientProduct" + lstResultHistClientProduct.get().size());

            listAccountHistoryFlatMap = Stream.of(lstResultHistAccounts.get(), lstResultHistClientProduct.get()).flatMap(List::stream).collect(Collectors.toList());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return Flux.fromIterable(listAccountHistoryFlatMap);
    }

    public Mono<Client> accountwebclient() {
        WebClient webClientClient = WebClient.builder().baseUrl(Constant.urlClient).build();
        Mono<Client> client = webClientClient.get().uri(Constant.getClientById + 1).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Client.class);
        return client;
    }


    public Mono<Message> transaction(Transaction transaction) {
        boolean isValid = true;
        Account accountOrigin = null;
        Account accountDestination = null;
        Message message = new Message();
        WebClient webAccountHistoryClient = WebClient.builder().baseUrl(Constant.urlAccountHistory).build();
        try {
            accountOrigin = this.accountRepository.findById(transaction.getId_account_origin()).toFuture().get();
            accountDestination = this.accountRepository.findById(transaction.getId_account_destination()).toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        Predicate<Account> existAccount = account -> account == null || account.getId() == null;
        Predicate<BigDecimal> isLessToEqualToZero = value -> value.compareTo(BigDecimal.ZERO) == -1 || value.compareTo(BigDecimal.ZERO) == 0;
        if (existAccount.test(accountOrigin)){
            isValid = false;
            message = new Message("002", "The origin account doesn't exists");
        } else if (existAccount.test(accountDestination)){
            isValid = false;
            message = new Message("002", "The destination account doesn't exists");
        } else if (isLessToEqualToZero.test(transaction.getAmount())){
            isValid = false;
            message = new Message("002", "The amount must be greater than zero");
        }
        if (isValid){
            MathOperation sum = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.add(amount);
            MathOperation substract = (BigDecimal currentAmount, BigDecimal amount) -> currentAmount.subtract(amount);
            BigDecimal amountUpdatedOrigin = BigDecimal.ZERO;
            BigDecimal amountUpdatedDestination = BigDecimal.ZERO;

            amountUpdatedOrigin = this.operate(accountOrigin.getBalance(), transaction.getAmount(), substract);
            amountUpdatedDestination = this.operate(accountDestination.getBalance(), transaction.getAmount(), sum);

            if (isLessToEqualToZero.test(amountUpdatedOrigin)){
                isValid = false;
                message = new Message("002", "The origin account dont have balance to transfer");
            }
             if (isValid){
                accountOrigin.setBalance(amountUpdatedOrigin);
                accountDestination.setBalance(amountUpdatedDestination);

                try {
                    Account accountDestinationUpdated = this.update(accountOrigin).toFuture().get();
                    Account accountOriginUpdated = this.update(accountDestination).toFuture().get();
                    AccountHistory accountHistoryOrigin = new AccountHistory();
                    accountHistoryOrigin.setAmount(transaction.getAmount());
                    accountHistoryOrigin.setId_account(accountOrigin.getId());
                    accountHistoryOrigin.setId_client_product(null);
                    accountHistoryOrigin.setStatus(1);
                    accountHistoryOrigin.setOperation_date(new Date());
                    accountHistoryOrigin.setOperation_terminal(transaction.getCreation_terminal());
                    accountHistoryOrigin.setOperation_type(Constant.id_type_operation_transfer);
                    accountHistoryOrigin.setId_account_destination(accountDestination.getId());

                    AccountHistory accountHistoryDestination = new AccountHistory();
                    accountHistoryDestination.setAmount(transaction.getAmount());
                    accountHistoryDestination.setId_account(accountDestination.getId());
                    accountHistoryDestination.setId_client_product(null);
                    accountHistoryDestination.setStatus(1);
                    accountHistoryDestination.setOperation_date(new Date());
                    accountHistoryDestination.setOperation_terminal(transaction.getCreation_terminal());
                    accountHistoryDestination.setOperation_type(Constant.id_type_operation_transfer);
                    accountHistoryDestination.setId_account_origin(accountOrigin.getId());

                    webAccountHistoryClient
                            .post()
                            .uri(Constant.saveAccountHistory)
                            .body(BodyInserters.fromValue(accountHistoryOrigin))
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(AccountHistory.class)
                            .onErrorResume(e -> Mono.empty())
                            .delaySubscription(Duration.ofMillis(150)).toFuture().get();

                    webAccountHistoryClient
                            .post()
                            .uri(Constant.saveAccountHistory)
                            .body(BodyInserters.fromValue(accountHistoryDestination))
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(AccountHistory.class)
                            .onErrorResume(e -> Mono.empty())
                            .delaySubscription(Duration.ofMillis(150)).toFuture().get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return Mono.just(message);
    }

    public Mono<Account> save(Account account) {
        System.out.println("save");
        account.setCreation_date(new Date());
        AccountLog currentAccountLog= new AccountLog();
        BeanUtils.copyProperties(account, currentAccountLog, "created");
        currentAccountLog.setId(sequenceGeneratorService.getSequenceNumber(AccountLog.SEQUENCE_NAME));
        currentAccountLog.setId_account(account.getId());
        try {
            AccountLog newCurrentAccountLog =accountLogRepository.save(currentAccountLog).toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return accountRepository.save(account);
    }

    public Mono<Account> update(Account account) {
        account.setModification_date(new Date());
        AccountLog currentAccountLog= new AccountLog();
        BeanUtils.copyProperties(account, currentAccountLog, "created");
        currentAccountLog.setId(sequenceGeneratorService.getSequenceNumber(AccountLog.SEQUENCE_NAME));
        currentAccountLog.setId_account(account.getId());
        try {
            AccountLog newCurrentAccountLog =accountLogRepository.save(currentAccountLog).toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return accountRepository.save(account);
    }

    public Flux<AccountLog> getAllAccountLog(){
        return this.accountLogRepository.findAll();
    }

    public Mono<BalanceSummary> getBalanceSumary(Long id_client){
        System.out.println("id_client" + id_client);
        BalanceSummary balanceSummary = new BalanceSummary();
        WebClient webClientProductClient = WebClient.builder().baseUrl(Constant.urlClientProduct).build();
        List<ClientProductLog> lstClientProduct = null;
        List<AccountLog> lstAccountLog = null;
        try {

           lstAccountLog = this.accountLogRepository.findAll().collectList().toFuture().get();
            lstClientProduct = webClientProductClient
                    .get()
                    .uri(Constant.getAllClientProductLog)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(ClientProductLog.class)
                    .onErrorResume(e -> Mono.empty())
                    .delaySubscription(Duration.ofMillis(150))
                    .collectList()
                    .toFuture()
                    .get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("log de cliente obtenido" + lstClientProduct);
        List<ClientProductLog> lstClientProductFiltered = lstClientProduct.stream().filter(clientProductLog -> clientProductLog.getId_client() == id_client).collect(Collectors.toList());
        List<AccountLog> lstAccountLogFiltered = lstAccountLog.stream().filter(clientProductLog -> clientProductLog.getId_client() == id_client).collect(Collectors.toList());
        balanceSummary.setLstClientProductLog(lstClientProductFiltered);
        balanceSummary.setListAccountLog(lstAccountLogFiltered);
        return Mono.just(balanceSummary);
    }

}
