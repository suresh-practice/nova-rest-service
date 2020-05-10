package com.suresh.practice.nova.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.suresh.practice.nova.configuration.MockServiceFiegnClient;
import com.suresh.practice.nova.entity.NovaProduct;
import com.suresh.practice.nova.exception.NovaServiceException;
import com.suresh.practice.nova.repository.NovaProductRepo;
import com.suresh.practice.nova.service.NovaProductService;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class NovaProductServiceImpl implements NovaProductService {

	@Autowired
	private NovaProductRepo novaProductRepo;
	
	@Autowired
	private MockServiceFiegnClient feignClient;
	
	@Override
	public JSONObject retrieveProductInfo(Long productId) throws NovaServiceException {
		
		preRetrieveProductInfo(productId);
		
		return doRetrieveProductInfo(productId);
	}

	private void preRetrieveProductInfo(Long productId) throws NovaServiceException {
		
		checkValidProductId(productId);
		
		Optional<NovaProduct> product = fetchProductIdFromRepo(productId);
		
		if (!product.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID Not found!");
		}
	}

	private void checkValidProductId(Long productId) throws NovaServiceException {
		if (productId <= 0) {
			throw new NovaServiceException("Invalid Product ID!");
		}
	}

	private JSONObject doRetrieveProductInfo(Long productId) {
		
		JSONObject productResponse = new JSONObject();
		
		productResponse.put("productId", productId);
		
		aggregateServiceResults(productId, productResponse);
		
		return productResponse;
	}

	private void aggregateServiceResults(Long productId, JSONObject productResponse) {
		try {
			CompletableFuture<String> productPriceCompletable = getProductPriceAsync(productId);
			
			CompletableFuture<String> productCatalogCompletable = getProductCatalogAsync(productId);

			CompletableFuture.allOf(productPriceCompletable, productCatalogCompletable).join();
			
			String priceDetails = productPriceCompletable.get();
			String catalogDetails = productCatalogCompletable.get();
			
			JSONObject priceJson = new JSONObject(priceDetails);
			JSONObject catalogJson = new JSONObject(catalogDetails);
			
			productResponse.put("price", (priceJson.getJSONArray("results").get(0)));
			
			productResponse.put("catalog", (catalogJson.getJSONArray("results").get(0)));
			
		} catch (InterruptedException | ExecutionException e) {
			//add error logger
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID Not found!");
		}
	}
	
	@Async("asyncExecutor")
    public CompletableFuture<String> getProductPriceAsync(Long productId) throws InterruptedException {
		
		return CompletableFuture.completedFuture(feignClient.getPrice(productId));
	}
	
	@Async("asyncExecutor")
    public CompletableFuture<String> getProductCatalogAsync(Long productId) throws InterruptedException {
		
		return CompletableFuture.completedFuture(feignClient.getCatalog(productId));
	}

	private Optional<NovaProduct> fetchProductIdFromRepo(Long productId) {
		return novaProductRepo.findById(productId);
	}

	@Override
	public JSONObject storeProductInfo(Long productId, String productInfo) throws NovaServiceException {
		
		preStoreProductInfo(productId, productInfo);
		
		JSONObject receivedPayload = doStoreProductInfo(productInfo);
		
		return receivedPayload;
	}

	private JSONObject doStoreProductInfo(String productInfo) {
		JSONObject receivedPayload = new JSONObject(productInfo);
		
		Long pId = receivedPayload.getLong("productId");
		
		NovaProduct novaProduct = new NovaProduct();
		novaProduct.setId(pId);
		
		this.novaProductRepo.save(novaProduct);
		return receivedPayload;
	}

	private void preStoreProductInfo(Long productId, String productInfo) throws NovaServiceException {
		checkValidProductId(productId);
		
		checkValidProductInfo(productInfo);
	}

	private void checkValidProductInfo(String productInfo) {
		if (StringUtils.isEmpty(productInfo)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Product Info!");
		}
	}

	@Override
	public JSONObject updateProductInfo(Long productId, String productInfo) throws NovaServiceException {
		return this.storeProductInfo(productId, productInfo);
	}
}