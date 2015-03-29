package com.example.app;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.example.security.AuthenticationFilter;
import com.example.security.SecurityModule;
import com.example.security.token.SecurityTokenModule;
import com.example.service.ServiceModule;
import com.google.inject.Guice;

public final class MyApplication extends ResourceConfig {

	@Inject
	public MyApplication(ServiceLocator serviceLocator) {
		packages("com.example.api");
		register(RolesAllowedDynamicFeature.class);
		register(AuthenticationFilter.class);

		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);

		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(Guice.createInjector(new ServiceModule(), new SecurityModule(), new SecurityTokenModule()));
	}

}