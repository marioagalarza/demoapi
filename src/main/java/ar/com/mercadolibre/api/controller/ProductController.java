package ar.com.mercadolibre.api.controller;

import ar.com.mercadolibre.api.model.ProductDTO;
import ar.com.mercadolibre.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String id) {
        log.info("Url: {} - Consultando el producto id: {}","v1/productos/",id);
        return ResponseEntity.ok(productService.findById(id));
    }
}
