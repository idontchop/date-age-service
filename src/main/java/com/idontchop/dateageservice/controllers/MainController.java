package com.idontchop.dateageservice.controllers;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idontchop.dateageservice.dtos.RestMessage;
import com.idontchop.dateageservice.entities.Age;
import com.idontchop.dateageservice.service.AgeService;

/**
 * /age/{user}/{birthday}
 * 
 * @author micro
 *
 */
@RestController
public class MainController {
	
	@Value ("${spring.application.type}")
	private String matchType;
	
	@Value ("${server.port}")
	private String serverPort;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Autowired
	AgeService ageService;
	
	@GetMapping("/age/{name}")
	public Age getAge ( @PathVariable (name = "name", required=true) String name) {
		return ageService.getAge(name);
	}
	
	@RequestMapping ( value = "/age/{name}/{birthday}",
			method = { RequestMethod.POST, RequestMethod.PUT } )
	public Age newAge ( @PathVariable ( name = "name", required=true) String name,
						@PathVariable ( name = "birthday", required=true) Date birthday ) {
		return ageService.newAge(name, birthday);
		
	}
	

	@GetMapping("/helloWorld")
	public RestMessage helloWorld () {
		String serverAddress,serverHost;
		try {
			serverAddress = NetworkInterface.getNetworkInterfaces().nextElement()
					.getInetAddresses().nextElement().getHostAddress();
		} catch (SocketException e) {
			serverAddress = e.getMessage();
		}
		try {
			serverHost = NetworkInterface.getNetworkInterfaces().nextElement()
					.getInetAddresses().nextElement().getHostName();
		} catch (SocketException e) {
			serverHost = e.getMessage();
		}
		return RestMessage.build("Hello from " + matchType)
				.add("service", appName)
				.add("host", serverHost)
				.add("address", serverAddress)
				.add("port", serverPort);
			
	}
}
