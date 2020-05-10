package com.suresh.practice.nova.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "NOVAMOCKSERVICE")
public interface MockServiceFiegnClient {
	
	@GetMapping(path = "/novaMock/price/{productId}")
    String getPrice(@PathVariable(value="productId") Long productId);
	
	@GetMapping(path = "novaMock/catalog/{productId}")
	String getCatalog(@PathVariable(value="productId") Long productId);
}
