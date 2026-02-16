package ar.com.mercadolibre.api;

import ar.com.mercadolibre.api.model.ProductDTO;
import ar.com.mercadolibre.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa los mocks de Mockito
public class ProductServiceTest {

    @Mock
    private RestTemplate restTemplate; // Simulamos el cliente HTTP

    @InjectMocks
    private ProductService productService; // Inyecta el mock de arriba aquí

    /*@Test
    void shouldReturnProduct_WhenApiReturnsData() {
        // 1. GIVEN (Preparación)
        String productId = "MLA123";
        ProductDTO mockApiResponse = new ProductDTO(productId, "Celular", BigDecimal.valueOf(50000.0));

        // Configuramos el comportamiento del Mock
        // "Cuando llamen a la API con este ID, devuelve mi objeto mockeado"
        when(restTemplate.getForObject(anyString(), eq(ProductDTO.class)))
                .thenReturn(mockApiResponse);

        // 2. WHEN (Ejecución)
        ProductDTO result = productService.findById(productId);

        // 3. THEN (Verificación)
        assertNotNull(result);
        assertEquals("MLA123", result.getId());
        assertEquals(BigDecimal.valueOf(1500.0), result.getPrice());

        // Verificamos que el restTemplate realmente se llamó una vez
        verify(restTemplate, times(1)).getForObject(anyString(), eq(ProductDTO.class));
    }*/
}
