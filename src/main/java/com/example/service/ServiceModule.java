package com.example.service;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public final class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IProductService.class).to(ProductService.class);
		bind(IUserService.class).to(UserService.class).in(Singleton.class);
		bind(IRestUserService.class).to(RestUserService.class);
	}

}
