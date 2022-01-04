package br.com.efrozza.awsprojeto01.controller;

import br.com.efrozza.awsprojeto01.model.Product;
import br.com.efrozza.awsprojeto01.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> findAll (){
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id){

        Optional<Product> optionalProduct = productRepository.findById(id);

        if  (optionalProduct.isPresent()) {
            return new ResponseEntity<Product>(optionalProduct.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
}
