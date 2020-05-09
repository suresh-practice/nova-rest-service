package com.suresh.practice.nova.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suresh.practice.nova.dto.NovaProductDto;
import com.suresh.practice.nova.entity.NovaProduct;
import com.suresh.practice.nova.exception.NovaServiceException;
import com.suresh.practice.nova.repository.NovaProductRepo;
import com.suresh.practice.nova.service.NovaProductService;

@Service
public class NovaProductServiceImpl implements NovaProductService {

	private NovaProductRepo novaProductRepo;
	
	@Autowired
	public NovaProductServiceImpl(final NovaProductRepo novaProductRepoParam) {
		this.novaProductRepo = novaProductRepoParam;
	}
	
	@Override
	public List<NovaProductDto> retrieveProductInfo(Long productId) throws NovaServiceException {
		
		preRetrieveProductInfo(productId);
		
		List<NovaProductDto> productDtoList = doRetrieveProductInfo(productId);
				
		return productDtoList;
	}

	private void preRetrieveProductInfo(Long productId) throws NovaServiceException {
		if (productId <= 0) {
			throw new NovaServiceException("Invalid Product ID!");
		}
	}

	private List<NovaProductDto> doRetrieveProductInfo(Long productId) {
		List<NovaProductDto> productDtoList = new ArrayList<>();
		
		Optional<NovaProduct> product = novaProductRepo.findById(productId);
		
		if (product.isPresent()) {
			NovaProductDto productDto = new NovaProductDto(product.get());
			productDtoList.add(productDto);
		}
		
		return productDtoList;
	}

}
