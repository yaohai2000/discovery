package com.bhz.spring.ex3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
	
	@Override
	public void sale(Product product) {
		System.out.println("Sale product ,  price: " + product.getPrice());
	}
}
