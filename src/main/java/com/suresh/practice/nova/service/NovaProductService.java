package com.suresh.practice.nova.service;

import java.util.List;

import com.suresh.practice.nova.dto.NovaProductDto;
import com.suresh.practice.nova.exception.NovaServiceException;

public interface NovaProductService {

	List<NovaProductDto> retrieveProductInfo(Long productId) throws NovaServiceException;
}
