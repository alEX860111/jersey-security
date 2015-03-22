package com.example.service;

import java.util.Arrays;
import java.util.List;

import com.example.domain.Product;

final class ProductService implements IProductService {

    public List<Product> getProducts() {
        Product product = new Product();
        product.setName("product1");
        return Arrays.asList(product);
    }

}
