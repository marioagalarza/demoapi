package ar.com.mercadolibre.api.filter;

import io.micrometer.tracing.Tracer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TraceIdFilter implements Filter {

    private final Tracer tracer;

    public TraceIdFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Obtenemos el contexto actual o creamos uno si no existe
        var currentSpan = tracer.currentSpan();

        if (currentSpan != null) {
            String traceId = currentSpan.context().traceId();
            String spanId = currentSpan.context().spanId();

            // Seteamos manualmente el MDC para que el log lo vea
            MDC.put("traceId", traceId);
            MDC.put("spanId", spanId);

            try {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("X-Trace-Id", traceId);
                chain.doFilter(request, response);
            } finally {
                // Limpiamos al terminar para no contaminar otros hilos (Vital para un Lead)
                MDC.remove("traceId");
                MDC.remove("spanId");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}

