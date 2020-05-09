package com.suresh.practice.nova.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.practice.nova.dto.NovaProductDto;
import com.suresh.practice.nova.exception.NovaServiceException;
import com.suresh.practice.nova.service.NovaProductService;

@RestController
@RequestMapping("/")
public class NovaProductController {

	private NovaProductService novaProductService;
	
	@Autowired
	public NovaProductController(final NovaProductService novaProductServiceParam) {
		this.novaProductService = novaProductServiceParam;
	}
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "All is well";
	}

	@GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> fetchProduct(@PathVariable Long productId) throws NovaServiceException {
		Map<String, Object> responseObject = new HashMap<>(1);
		
		long startTime = System.nanoTime();
		
		List<NovaProductDto> prodList = novaProductService.retrieveProductInfo(productId);
		
		long timeElapsed = System.nanoTime() - startTime;
		responseObject.put("results", prodList);
		responseObject.put("executionTime", timeElapsed / 1000000 + " milliseconds");
		
		return new ResponseEntity<>(responseObject, HttpStatus.OK);
	}
}
