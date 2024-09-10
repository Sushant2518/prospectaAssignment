package com.prospecta.service;

import java.util.List;

import com.prospecta.model.Product;


public interface ProductServices {

	public List<Product> findProductsByCategory(String category);

	public Product createProduct(Product product);
}
