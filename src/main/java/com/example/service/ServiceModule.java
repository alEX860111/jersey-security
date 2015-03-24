package com.example.service;

import com.google.inject.AbstractModule;

public final class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IProductService.class).to(ProductService.class);
    }

}
