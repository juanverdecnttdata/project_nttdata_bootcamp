package com.nttdata.service;

import com.nttdata.entity.Product;
import com.nttdata.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de la entidad Product para acceder al repository
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Metodo que busca todos los productos
     * @return retorna una lista de productos
     */
    public List<Product> getAll(){
        return productRepository.findAll();
    }
    /**
     * Metodo que busca una entidad de producto
     * @return retorna una entidad producto
     */
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
}
