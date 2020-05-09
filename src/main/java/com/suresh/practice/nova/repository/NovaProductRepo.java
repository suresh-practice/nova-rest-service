package com.suresh.practice.nova.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.suresh.practice.nova.entity.NovaProduct;

@Repository
public interface NovaProductRepo extends PagingAndSortingRepository<NovaProduct, Long> {
	
}
