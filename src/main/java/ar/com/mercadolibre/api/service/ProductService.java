package ar.com.mercadolibre.api.service;

import ar.com.mercadolibre.api.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    // ✅ Mockito usará este constructor para meter el Mock
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDTO findById(String id) {
        // Aquí iría la lógica de buscar en una DB o en otra API
        return new ProductDTO(id, "Ejemplo de Producto", BigDecimal.valueOf(1500.0));
    }
}
