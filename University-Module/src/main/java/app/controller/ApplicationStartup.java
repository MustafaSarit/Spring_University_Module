package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import app.model.AppService;


// This class runs automatically when application started. It adds all universities from given URL  


@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private AppService service;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		service.autoAddUniversities();
	}

}
