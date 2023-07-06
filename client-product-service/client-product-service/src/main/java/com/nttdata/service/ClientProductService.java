package com.nttdata.service;


import com.nttdata.entity.ClientProduct;
import com.nttdata.entity.ClientProductLog;
import com.nttdata.entity.Constant;
import com.nttdata.entity.Message;
import com.nttdata.feignclient.ClientFeignClient;
import com.nttdata.feignclient.ProductFeignClient;
import com.nttdata.model.Client;
import com.nttdata.model.Product;
import com.nttdata.repository.ClientProductLogRepository;
import com.nttdata.repository.ClientProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Servicio de la entidad ClientProduct para acceder al repository
 */
@Service
public class ClientProductService {

    @Autowired
    private ClientProductRepository clientProductRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private ClientProductLogRepository clientProductLogRepository;

    /**
     * Metodo que busca todos los datos de la entidad ClientProduct
     * @return retorna una lista de la entidad ClientProduct
     */
    public Flux<ClientProduct> getAll(){
        return clientProductRepository.findAll();
    }
    /**
     * Meotodo que busca un cliente producto
     * @param id Identificador de la entidad ClientProduct
     * @return retorna un objeto de la entidad ClientProduct
     */
    public Mono<ClientProduct> getClientProductById(Long id){
        return clientProductRepository.findById(id);
    }
    /**
     * Metodo que realiza la insercion y actualizacion de la entidad ClientProduct
     * @param clientProduct Objeto de la entidad ClientProduct
     * @return retorna el objeto de la entidad ClientProduct insertado o actualizado
     */
    public Mono<ClientProduct> saveClientProduct(ClientProduct clientProduct) {
        boolean save = true;
        Message message= new Message();
        ClientProduct newClientProduct = new ClientProduct();
        WebClient webClientClient = WebClient.builder().baseUrl(Constant.urlClient).build();
        WebClient webClientProduct = WebClient.builder().baseUrl(Constant.urlProduct).build();
        Client client = null;
        Product product = null;
        List<ClientProduct> clientProducts = null;

        try {
            clientProducts = this.clientProductRepository.findAll().collectList().toFuture().get();
            client = webClientClient
                    .get()
                    .uri(Constant.getClientById+clientProduct.getId_client())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Client.class)
                    .onErrorResume(e -> Mono.empty())
                    .delaySubscription(Duration.ofMillis(150))
                    .toFuture()
                    .get();

            product = webClientProduct
                    .get()
                    .uri(Constant.getProductById+clientProduct.getId_product())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .onErrorResume(e -> Mono.empty())
                    .delaySubscription(Duration.ofMillis(150))
                    .toFuture()
                    .get();
        //Client client = clientFeignClient.getClientById(clientProduct.getId_client());
        //Product product = productFeignClient.getProductById(clientProduct.getId_product());

        List<ClientProduct> clientProductsCredit = clientProducts
                                            .stream()
                                            .filter(clientProduct1 -> clientProduct1.getId_client() == clientProduct.getId_client() && clientProduct1.getId_product() == 6)
                                            .collect(Collectors.toList());
        System.out.println("clientProductsCredit " + clientProductsCredit);
        Predicate<List<ClientProduct>> clientHasCreditCard = hasCredit -> hasCredit.isEmpty() && hasCredit.size() == 0;
        Predicate<ClientProduct> isCreditProduct = isProduct -> isProduct.getId_product() < 4;
        Predicate<Client> existClient = eClient -> eClient == null;
        Predicate<Product> existProduct = eProduct -> eProduct == null;
        if (existProduct.test(product)){
            message.setCode("002");
            message.setDescription("The product doesnt exist");
            newClientProduct.setMessage(message);
            save = false;
        }
        if (existClient.test(client)){
            message.setCode("002");
            message.setDescription("The client doesnt exist");
            newClientProduct.setMessage(message);
            save = false;
        }
        System.out.println("isCreditProduct" + isCreditProduct.test(clientProduct));
        if (isCreditProduct.test(clientProduct)){
            message.setCode("002");
            message.setDescription("The passive products need an account created");
            newClientProduct.setMessage(message);
            save = false;
        }
        System.out.println("getId_product" +clientProduct.getId_product());
         if (clientProduct.getId_product().equals(8L) || clientProduct.getId_product().equals(7L)){
             if (clientHasCreditCard.test(clientProductsCredit)){
                 message.setCode("002");
                 message.setDescription("The client must have a credit card");
                 newClientProduct.setMessage(message);
                 save = false;
             }
        }
       if (save){
            clientProduct.setId(sequenceGeneratorService.getSequenceNumber(ClientProduct.SEQUENCE_NAME));
            message = new Message("001", "Client product created");
            try {
                newClientProduct = this.save(clientProduct).toFuture().get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
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

    public Mono<ClientProduct> save(ClientProduct clientProduct){
        ClientProductLog currentCliProLog= new ClientProductLog();
        BeanUtils.copyProperties(clientProduct, currentCliProLog, "created");
        currentCliProLog.setCreation_date(new Date());
        currentCliProLog.setId(sequenceGeneratorService.getSequenceNumber(ClientProductLog.SEQUENCE_NAME));
        currentCliProLog.setId_client_product(clientProduct.getId());
        try {
            ClientProductLog newCurrentAccountLog =clientProductLogRepository.save(currentCliProLog).toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return clientProductRepository.save(clientProduct);
    }

    public Mono<ClientProduct> update(ClientProduct clientProduct){
        ClientProductLog currentCliProLog= new ClientProductLog();
        BeanUtils.copyProperties(clientProduct, currentCliProLog, "created");
        currentCliProLog.setModification_date(new Date());
        currentCliProLog.setId(sequenceGeneratorService.getSequenceNumber(ClientProductLog.SEQUENCE_NAME));
        currentCliProLog.setId_client_product(clientProduct.getId());
        try {
            ClientProductLog newCurrentAccountLog =clientProductLogRepository.save(currentCliProLog).toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return clientProductRepository.save(clientProduct);
    }

    public Flux<ClientProductLog> getAllClientProductLog(){
        return this.clientProductLogRepository.findAll();
    }
}
