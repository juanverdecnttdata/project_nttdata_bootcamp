package com.nttdata.service;

import com.nttdata.entity.ClientProduct;
import com.nttdata.entity.ClientProductDebtAccount;
import com.nttdata.entity.ClientProductLog;
import com.nttdata.entity.ClientProductUserWallet;
import com.nttdata.entity.Constant;
import com.nttdata.entity.Message;
import com.nttdata.model.*;
import com.nttdata.producer.KafkaStringProducer;
import com.nttdata.repository.ClientProductDebtAccountRepository;
import com.nttdata.repository.ClientProductLogRepository;
import com.nttdata.repository.ClientProductRepository;
import com.nttdata.repository.ClientProductUserWalletRepository;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



/**
 * Servicio de la entidad ClientProduct para acceder al repository.
 */
@Service
public class ClientProductService {

  @Autowired
  private ClientProductRepository clientProductRepository;
  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;
  @Autowired
  private ClientProductLogRepository clientProductLogRepository;
  @Autowired
  private ClientProductDebtAccountRepository clientProductDebtAccountRepository;
  @Autowired
  private ClientProductUserWalletRepository clientProductUserWalletRepository;

  private final KafkaStringProducer kafkaStringProducer;

  public ClientProductService(KafkaStringProducer kafkaStringProducer) {
    this.kafkaStringProducer = kafkaStringProducer;
  }

  /**
   * Metodo que busca todos los datos de la entidad ClientProduct.
   *
   * @return retorna una lista de la entidad ClientProduct.
   */
  public Flux<ClientProduct> getAll() {
    return clientProductRepository.findAll();
  }

  /**
   * Meotodo que busca un cliente producto.
   *
   * @param id Identificador de la entidad ClientProduct.
   * @return retorna un objeto de la entidad ClientProduct.
   */
  public Mono<ClientProduct> getClientProductById(Long id) {
    return clientProductRepository.findById(id);
  }

  /**
   * Metodo que realiza la insercion y actualizacion de la entidad ClientProduct.
   *
   * @param clientProduct Objeto de la entidad ClientProduct.
   * @return retorna el objeto de la entidad ClientProduct insertado o actualizado.
   */
  public Mono<ClientProduct> saveClientProduct(ClientProduct clientProduct) {
    boolean save = true;
    Message message = new Message();
    ClientProduct newClientProduct = new ClientProduct();
    WebClient webClientClient = WebClient.builder().baseUrl(Constant.urlClient).build();
    WebClient webClientProduct = WebClient.builder().baseUrl(Constant.urlProduct).build();
    WebClient webClientAccount = WebClient.builder().baseUrl(Constant.urlAccount).build();
    Client client = null;
    Product product = null;
    Account account = null;
    List<ClientProduct> clientProducts = null;

    try {
      clientProducts = this.clientProductRepository.findAll().collectList().toFuture().get();
      client = webClientClient
          .get()
          .uri(Constant.getClientById + clientProduct.getId_client())
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Client.class)
          .onErrorResume(e -> Mono.empty())
          .delaySubscription(Duration.ofMillis(150))
          .toFuture()
          .get();

      product = webClientProduct
          .get()
          .uri(Constant.getProductById + clientProduct.getId_product())
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Product.class)
          .onErrorResume(e -> Mono.empty())
          .delaySubscription(Duration.ofMillis(150))
          .toFuture()
          .get();
      List<ClientProduct> clientProductsCredit = clientProducts
          .stream()
          .filter(clientProduct1 -> clientProduct1.getId_client() == clientProduct
              .getId_client() && clientProduct1.getId_product() == 6)
          .collect(Collectors.toList());

      List<ClientProduct> clientProductsDebt = clientProducts
          .stream()
          .filter(clientProduct1 -> clientProduct1.getId_client() == clientProduct
              .getId_client() && clientProduct1.getCredit() != clientProduct1.getCredit_limit())
          .collect(Collectors.toList());

      Predicate<List<ClientProduct>> clientHasCreditCard = hasCredit -> hasCredit.isEmpty() && hasCredit.size() == 0;
      Predicate<ClientProduct> isCreditProduct = isProduct -> isProduct.getId_product() < 4;
      Predicate<List<ClientProduct>> clientHasDebt = debt -> debt.size() > 0;
      Predicate<Client> existClient = eClient -> eClient == null;
      Predicate<Product> existProduct = eProduct -> eProduct == null;
      if (existProduct.test(product)) {
        message.setCode("002");
        message.setDescription("The product doesnt exist");
        newClientProduct.setMessage(message);
        save = false;
      }
      if (existClient.test(client)) {
        message.setCode("002");
        message.setDescription("The client doesnt exist");
        newClientProduct.setMessage(message);
        save = false;
      }
      if (isCreditProduct.test(clientProduct)) {
        message.setCode("002");
        message.setDescription("The passive products need an account created");
        newClientProduct.setMessage(message);
        save = false;
      }
      if (clientProduct.getId_product().equals(8L) || clientProduct.getId_product().equals(7L)) {
        if (clientHasCreditCard.test(clientProductsCredit)) {
          message.setCode("002");
          message.setDescription("The client must have a credit card");
          newClientProduct.setMessage(message);
          save = false;
        }
      }
      if (clientHasDebt.test(clientProductsDebt)) {
        message.setCode("002");
        message.setDescription("Can't get a new product, the client has products with debts");
        newClientProduct.setMessage(message);
        save = false;
      }
      if (clientProduct.getId_account() != null && clientProduct.getId_client() != null && clientProduct.getId_product() != 9) {
        message.setCode("002");
        message.setDescription("Only can associate Debit Card to a account");
        newClientProduct.setMessage(message);
        save = false;
      } else {

        account = webClientAccount
            .get()
            .uri(Constant.getAccountById + clientProduct.getId_account())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Account.class)
            .onErrorResume(e -> Mono.empty())
            .delaySubscription(Duration.ofMillis(150))
            .toFuture()
            .get();

        if (account.getId_product() != 9) {
          message.setCode("002");
          message.setDescription("The account must has be of type product debit card");
          newClientProduct.setMessage(message);
          save = false;
        }
      }
      if (save) {
        clientProduct.setId(sequenceGeneratorService.getSequenceNumber(ClientProduct.SEQUENCE_NAME));
        message = new Message("001", "Client product created");
        ClientProductDebtAccount clientProductDebtAccount = new ClientProductDebtAccount();
        try {
          newClientProduct = this.save(clientProduct).toFuture().get();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        } catch (ExecutionException e) {
          throw new RuntimeException(e);
        }
        if (clientProduct.getId_product() == 9) {
          clientProductDebtAccount.setId(sequenceGeneratorService.getSequenceNumber(ClientProductDebtAccount.SEQUENCE_NAME));
          clientProductDebtAccount.setId_account(clientProduct.getId_account());
          clientProductDebtAccount.setId_client_product(newClientProduct.getId());
          clientProductDebtAccount.setStatus(1);
          clientProductDebtAccount.setCreation_terminal(clientProduct.getCreation_terminal());
          clientProductDebtAccount.setMain_account(1);
          this.saveClientProductDebtAcc(clientProductDebtAccount).subscribe();
        }
        newClientProduct.setMessage(message);
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    return Mono.just(newClientProduct);
  }

  public Mono<ClientProduct> save(ClientProduct clientProduct) {
    ClientProductLog currentCliProLog = new ClientProductLog();
    BeanUtils.copyProperties(clientProduct, currentCliProLog, "created");
    currentCliProLog.setCreation_date(new Date());
    currentCliProLog.setId(sequenceGeneratorService.getSequenceNumber(ClientProductLog.SEQUENCE_NAME));
    currentCliProLog.setId_client_product(clientProduct.getId());
    try {
      ClientProductLog newCurrentAccountLog = clientProductLogRepository.save(currentCliProLog).toFuture().get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    return clientProductRepository.save(clientProduct);
  }

  public Mono<ClientProduct> update(ClientProduct clientProduct) {
    ClientProductLog currentCliProLog = new ClientProductLog();
    BeanUtils.copyProperties(clientProduct, currentCliProLog, "created");
    currentCliProLog.setModification_date(new Date());
    currentCliProLog.setId(sequenceGeneratorService.getSequenceNumber(ClientProductLog.SEQUENCE_NAME));
    currentCliProLog.setId_client_product(clientProduct.getId());
    try {
      ClientProductLog newCurrentAccountLog = clientProductLogRepository.save(currentCliProLog).toFuture().get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    return clientProductRepository.save(clientProduct);
  }

  public Flux<ClientProductLog> getAllClientProductLog() {
    return this.clientProductLogRepository.findAll();
  }

  public Mono<SummaryClientProduct> clientProductByClientId(Long idClient) {
    List<ClientProduct> lstClientProductFiltered = new ArrayList<>();
    Client client = new Client();
    Message message = new Message();
    SummaryClientProduct response = new SummaryClientProduct();
    List<Product> lstProduct = new ArrayList<>();
    WebClient webClientClient = WebClient.builder().baseUrl(Constant.urlClient).build();
    try {
      client = webClientClient
          .get()
          .uri(Constant.getClientById + idClient)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Client.class)
          .onErrorResume(e -> Mono.empty())
          .delaySubscription(Duration.ofMillis(150))
          .toFuture()
          .get();

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    Predicate<Client> existClient = eClient -> eClient == null || eClient.getId() == null;
    Predicate<ClientProduct> equalClient = eClient -> eClient.getId_client().equals(idClient);
    if (existClient.test(client)) {
      message.setCode("002");
      message.setDescription("The client doesnt exists");
    } else {
      response.setId_client(idClient);
      try {
        List<ClientProduct> lstClientProduct = this.clientProductRepository.findAll().collectList().toFuture().get();
        lstClientProductFiltered = lstClientProduct.stream().filter(clientProduct -> equalClient.test(clientProduct)).collect(Collectors.toList());
        WebClient webClientProduct = WebClient.builder().baseUrl(Constant.urlProduct).build();
        if (!lstClientProductFiltered.isEmpty()) {
          lstClientProductFiltered.forEach(clientProduct -> {
            try {
              Product product = webClientProduct
                  .get()
                  .uri(Constant.getProductById + clientProduct.getId_product())
                  .accept(MediaType.APPLICATION_JSON)
                  .retrieve()
                  .bodyToMono(Product.class)
                  .onErrorResume(e -> Mono.empty())
                  .delaySubscription(Duration.ofMillis(150))
                  .toFuture()
                  .get();
              lstProduct.add(product);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            } catch (ExecutionException e) {
              throw new RuntimeException(e);
            }
          });
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
      response.setProducts(lstProduct);
    }

    response.setMessage(message);
    return Mono.just(response);

  }

  public Mono<ClientProductDebtAccount> saveClientProductDebtAcc(ClientProductDebtAccount clientProductDebtAccount) {
    clientProductDebtAccount.setCreation_date(new Date());
    return clientProductDebtAccountRepository.save(clientProductDebtAccount);
  }

  public Mono<ClientProductDebtAccount> assignAccountToDebitCard(ClientProductDebtAccount clientProductDebtAccount) {
    Client client = new Client();
    Message message = new Message();
    ClientProduct clientProduct = new ClientProduct();
    ClientProductDebtAccount response = new ClientProductDebtAccount();
    Account account = new Account();
    WebClient webClientAccount = WebClient.builder().baseUrl(Constant.urlAccount).build();
    boolean save = true;
    try {
      clientProduct = this.clientProductRepository.findById(clientProductDebtAccount.getId_client_product()).toFuture().get();
      account = webClientAccount
          .get()
          .uri(Constant.getAccountById + clientProductDebtAccount.getId_account())
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Account.class)
          .onErrorResume(e -> Mono.empty())
          .delaySubscription(Duration.ofMillis(150))
          .toFuture()
          .get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    Predicate<ClientProduct> existClient = eClientProduct -> eClientProduct == null || eClientProduct.getId() == null;
    Predicate<Account> existAccount = eAccount -> eAccount == null || eAccount.getId() == null;

    if (existClient.test(clientProduct)) {
      message.setCode("002");
      message.setDescription("The client product doesnt exists");
      save = false;
    } else if (clientProduct.getId_product() != 9) {
      message.setCode("002");
      message.setDescription("Only debit cards can be associated");
      save = false;
    } else if (existAccount.test(account)) {
      message.setCode("002");
      message.setDescription("The account doesnt exists");
      save = false;
    }
    if (save) {
      try {
        clientProductDebtAccount.setStatus(1);
        clientProductDebtAccount.setMain_account(0);
        clientProductDebtAccount.setId(sequenceGeneratorService.getSequenceNumber(ClientProductDebtAccount.SEQUENCE_NAME));
        message = new Message("001", "debit card associated with success");
        response = this.saveClientProductDebtAcc(clientProductDebtAccount).toFuture().get();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    }
    response.setMessage(message);
    return Mono.just(response);
  }

  public Flux<ClientProductDebtAccount> allClientProductAccountDebt() {
    return clientProductDebtAccountRepository.findAll();
  }

  public Mono<Account> getMainAccount(ClientProduct clientProduct) {
    Message message = new Message();
    ClientProduct clientProductTmp = new ClientProduct();
    ClientProductDebtAccount response = new ClientProductDebtAccount();
    Account account = new Account();
    WebClient webClientAccount = WebClient.builder().baseUrl(Constant.urlAccount).build();
    boolean isValid = true;
    try {
      clientProductTmp = this.clientProductRepository.findById(clientProduct.getId()).toFuture().get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    Predicate<ClientProduct> existClient = eClientProduct -> eClientProduct == null || eClientProduct.getId() == null;
    Predicate<Account> existAccount = eAccount -> eAccount == null || eAccount.getId() == null;

    if (existClient.test(clientProductTmp)) {
      message.setCode("002");
      message.setDescription("The client product doesnt exists");
      isValid = false;
    }
    try {
      List<ClientProductDebtAccount> lstCpdAccount = this.clientProductDebtAccountRepository.findAll().collectList().toFuture().get();
      List<ClientProductDebtAccount> lstCpdAccountFiltered = lstCpdAccount.stream().filter(clientProductDebtAccount -> clientProductDebtAccount.getMain_account() == 1 && clientProductDebtAccount.getId_client_product() == clientProduct.getId()).collect(Collectors.toList());
      if (lstCpdAccountFiltered.size() == 1) {

        account = webClientAccount
            .get()
            .uri(Constant.getAccountById + lstCpdAccountFiltered.get(0).getId_account())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Account.class)
            .onErrorResume(e -> Mono.empty())
            .delaySubscription(Duration.ofMillis(150))
            .toFuture()
            .get();

      } else {
        message.setCode("002");
        message.setDescription("The client product dont have a main account assigned");
        isValid = false;
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    return Mono.just(account);
  }


  public Mono<ClientProductUserWallet> assignUserToDebitCard(ClientProductUserWallet clientProductUserWallet) {
    Client client = new Client();
    Message message = new Message();
    ClientProduct clientProduct = new ClientProduct();
    ClientProductUserWallet response = new ClientProductUserWallet();
    User user = new User();
    WebClient webClientUser = WebClient.builder().baseUrl(Constant.urlUser).build();
    boolean save = true;
    try {
      clientProduct = this.clientProductRepository.findById(clientProductUserWallet.getIdClientProduct()).toFuture().get();
      user = webClientUser
          .get()
          .uri(Constant.getUserById + clientProductUserWallet.getIdUser())
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(User.class)
          .onErrorResume(e -> Mono.empty())
          .delaySubscription(Duration.ofMillis(150))
          .toFuture()
          .get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    Predicate<ClientProduct> existClient = eClientProduct -> eClientProduct == null || eClientProduct.getId() == null;
    Predicate<User> existUser = eUser -> eUser == null || eUser.getId() == null;

    if (existClient.test(clientProduct)) {
      message.setCode("002");
      message.setDescription("The client product doesnt exists");
      save = false;
    } else if (clientProduct.getId_product() != 9) {
      message.setCode("002");
      message.setDescription("Only debit cards can be associated");
      save = false;
    } else if (existUser.test(user)) {
      message.setCode("002");
      message.setDescription("The user doesnt exists");
      save = false;
    }
    if (save) {
      try {
        clientProductUserWallet.setStatus(1);
        clientProductUserWallet.setCreationDate(new Date());
        clientProductUserWallet.setId(sequenceGeneratorService.getSequenceNumber(ClientProductUserWallet.SEQUENCE_NAME));
        message = new Message("001", "debit card associated with success");
        response = this.clientProductUserWalletRepository.save(clientProductUserWallet).toFuture().get();
        String messageKafka = "El usuario " + user.getCellNumber() + " asocio su tarjeta de debito correctamente.";
        kafkaStringProducer.sendMessage(messageKafka);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    }
    response.setMessage(message);
    return Mono.just(response);
  }
}
