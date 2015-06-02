package com.example.service;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public final class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IUserService.class).to(UserService.class).in(Singleton.class);
	}

}
