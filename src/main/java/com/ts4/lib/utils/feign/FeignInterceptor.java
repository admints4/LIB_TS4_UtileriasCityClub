package com.ts4.lib.utils.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import feign.Response;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Component
@Slf4j
public class FeignInterceptor implements ErrorDecoder,RequestInterceptor {

    private final Tracer tracer;
    private ErrorDecoder defaultErrorDecoder = new Default();
    public FeignInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void apply(RequestTemplate template) {
        String completeUrl = template.feignTarget().url() + template.request().url();
        String requestBody = template.body() != null ? new String(template.body()) : "N/A";
        log.info("CALL HTTP METHOD {} -> {} -> PROCESS {}",template.method(),completeUrl,requestBody);

        Span span = tracer.spanBuilder("Feign request").startSpan();
        span.setAttribute("http.method", template.method());
        span.setAttribute("http.url", completeUrl);
        span.setAttribute("http.requestBody", requestBody);
        span.setAttribute("feign.clientName", template.feignTarget().name());
        span.end();
    }

    public void logResponseInfo(ResponseEntity<?> responseEntity, String completeUrl,String feignClientName) {
        Span span = tracer.spanBuilder("Feign response").startSpan();
        span.setAttribute("http.statusCode", responseEntity.getStatusCodeValue());
        span.setAttribute("http.responseBody", responseEntity.getBody()!=null ? responseEntity.getBody().toString() : "NA");
        span.setAttribute("http.url", completeUrl);
        span.setAttribute("feign.clientName", feignClientName);
        span.end();

        log.info("RESPONSE STATUS={}, Body {}",responseEntity.getStatusCodeValue(),responseEntity.getBody()!=null ? responseEntity.getBody().toString() : "NA");
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new HttpClientErrorException(HttpStatus.valueOf(response.status()), "Cliente error en la llamada");
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new HttpServerErrorException(HttpStatus.valueOf(response.status()), "Error del servidor remoto");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
