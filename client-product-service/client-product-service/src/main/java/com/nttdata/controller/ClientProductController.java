package com.nttdata.controller;

import com.nttdata.entity.ClientProduct;
import com.nttdata.entity.Message;
import com.nttdata.service.ClientProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientProduct")
public class ClientProductController {
    @Autowired
    private ClientProductService clientProductService;

    @GetMapping("/all")
    public ResponseEntity<List<ClientProduct>> listClientsProducts(){
        List<ClientProduct> clientProducts = clientProductService.getAll();
        return ResponseEntity.ok(clientProducts);
    }

    @PostMapping("/save")
    public ResponseEntity<ClientProduct> saveClient(@RequestBody ClientProduct clientProduct){
        Message message = new Message();
        ClientProduct newClientProduct = new ClientProduct();
        newClientProduct = clientProductService.save(clientProduct);
        return ResponseEntity.ok(newClientProduct);
    }

    @GetMapping("/getClientProductById/{id}")
    public ResponseEntity<ClientProduct> getClientProductById(@PathVariable("id") Long id){
        ClientProduct clientProduct = clientProductService.getClientProductById(id);
        return ResponseEntity.ok(clientProduct);
    }
}
