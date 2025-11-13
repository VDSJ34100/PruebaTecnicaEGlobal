package com.valerian.evaluacionplataformas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "api2", url = "http://localhost:8081/api/transacciones")
public interface Api2Client {
    @PostMapping
    Map<String, Object> enviar(@RequestBody Map<String, Object> datos);
}

