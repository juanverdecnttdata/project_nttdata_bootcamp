package com.nttdata.service;


import com.nttdata.entity.ClientProduct;
import com.nttdata.entity.Message;
import com.nttdata.feignclient.ClientFeignClient;
import com.nttdata.feignclient.ProductFeignClient;
import com.nttdata.model.Client;
import com.nttdata.model.Product;
import com.nttdata.repository.ClientProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class ClientProductService {

    @Autowired
    private ClientProductRepository clientProductRepository;

    @Autowired
    private ClientFeignClient clientFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    public List<ClientProduct> getAll(){
        return clientProductRepository.findAll();
    }

    public ClientProduct getClientProductById(Long id){
        return clientProductRepository.findById(id).orElse(null);
    }

    public ClientProduct save(ClientProduct clientProduct) {
        boolean save = true;
        Message message= new Message();
        ClientProduct newClientProduct = new ClientProduct();
        Client client = clientFeignClient.getClientById(clientProduct.getId_client());
        Product product = productFeignClient.getProductById(clientProduct.getId_product());
    System.out.println("clientProduct" + clientProduct.getId_product());
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
        if (save){
            message = new Message("001", "Client product created");
            newClientProduct = clientProductRepository.save(clientProduct);
            newClientProduct.setMessage(message);
        }
        return newClientProduct;
    }

}
