package com.nttdata.controller;

import com.nttdata.entity.Product;
import com.nttdata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase Controller de la entidad Product
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * Metodo que retorna una lista de productos
     * @return lista de la entidad product
     */
    @GetMapping("/all")
    public ResponseEntity<List<Product>> listProduct(){
        List<Product> product = productService.getAll();
        return ResponseEntity.ok(product);
    }

    /**
     * Metodo que retorna una una entidad de product
     * @param id Identificador del producto
     * @return Retorna una entidad producto
     */
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
