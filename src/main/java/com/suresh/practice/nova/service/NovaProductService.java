package com.suresh.practice.nova.service;

import org.json.JSONObject;

import com.suresh.practice.nova.exception.NovaServiceException;

public interface NovaProductService {

	JSONObject retrieveProductInfo(Long productId) throws NovaServiceException;

	JSONObject storeProductInfo(Long productId, String productInfo) throws NovaServiceException;

	JSONObject updateProductInfo(Long productId, String productInfo) throws NovaServiceException;
}
