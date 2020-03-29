package com.idontchop.dateageservice.controllers;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idontchop.dateageservice.dtos.ReduceRequest;
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
	
	@GetMapping("/age/reduce")
	public ResponseEntity<List<String>> reduce(@RequestBody @Valid ReduceRequest reduceRequest ) {
		
		List<Age> ageResults = ageService
				.reduceByAge(reduceRequest.getPotentials(),
						reduceRequest.getMinAge(),
						reduceRequest.getMaxAge());
		
		if ( ageResults.size() == 0 ) {
			return ResponseEntity.notFound().build();
		}
		
		List<String> results = 
				ageResults.stream().map(Age::getName).collect(Collectors.toList());
		
		return ResponseEntity.ok(results);
	}
	
	@GetMapping("/age/{name}")
	public Age getAge ( @PathVariable (name = "name", required=true) String name) {
		return ageService.getAge(name);
	}
	
	@RequestMapping ( value = "/age/{name}/{birthday}",
			method = { RequestMethod.POST, RequestMethod.PUT } )
	public Age newAge ( @PathVariable ( name = "name", required=true) String name,
						@PathVariable ( name = "birthday", required=true)
						@DateTimeFormat(iso=ISO.DATE) Date birthday ) {
		return ageService.newAge(name, birthday);
		
	}
	
	@DeleteMapping ( "/age/{name}" )
	public ResponseEntity<RestMessage> deleteAge ( @PathVariable ( name = "name", required=true) String name ) {
		
		try {
			return ResponseEntity.ok().body(RestMessage.build("OK"));
		} catch (IllegalArgumentException e ) {
			return ResponseEntity.notFound().build();
		}
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
