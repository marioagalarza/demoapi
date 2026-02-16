package ar.com.mercadolibre.api.exception;

import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final Tracer tracer;

    public GlobalExceptionHandler(Tracer tracer) {
        this.tracer = tracer;
    }

    // 1. Capturar errores de "No encontrado" (Personalizado)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 2. Capturar CUALQUIER otro error inesperado (El famoso 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        log.error("Error interno inesperado detectado: ", ex);
        return buildResponse("Ocurrió un error interno inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método privado para armar el JSON de respuesta
    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        // EL TOQUE DE ORO: Extraemos el TraceID actual de Micrometer
        String traceId = (tracer.currentSpan() != null)
                ? tracer.currentSpan().context().traceId()
                : "N/A";
        body.put("trace_id", traceId);

        return new ResponseEntity<>(body, status);
    }
}
