package com.suresh.practice.nova.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.practice.nova.exception.NovaServiceException;
import com.suresh.practice.nova.service.NovaProductService;

@RestController
@RequestMapping("/")
public class NovaProductController {
	
	@Autowired
	private NovaProductService novaProductService;

	@GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> fetchProduct(@PathVariable Long productId) throws NovaServiceException {
		
		JSONObject response = novaProductService.retrieveProductInfo(productId);
		
		return ResponseEntity.ok(response.toMap());
	}
	
	@PostMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> storeProduct(@PathVariable Long productId, 
			@RequestBody String productInfo) throws NovaServiceException {
		
		JSONObject response = novaProductService.storeProductInfo(productId, productInfo);
		
		return ResponseEntity.ok(response.toMap());
	}
	
	@PutMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> updateProduct(@PathVariable Long productId, 
							@RequestBody String productInfo) throws NovaServiceException {
		
		JSONObject response = novaProductService.updateProductInfo(productId, productInfo);
		
		return ResponseEntity.ok(response.toMap());
	}
}
