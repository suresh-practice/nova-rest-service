package com.suresh.practice.nova.dto;

import com.suresh.practice.nova.entity.NovaProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovaProductDto {

	private Long productId;
	
	private String productName;
	
	private String productDescription;
	
	private Double productPrice;
	
	public NovaProductDto(NovaProduct product) {
		this.productId = product.getId();
	}
}
