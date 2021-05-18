package pe.com.fractal.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.fractal.model.Customer;
import pe.com.fractal.repositories.CustomerDAO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins="*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT, RequestMethod.DELETE} )
@RequestMapping("/api/customers")
public class CustomerController {

	private static final Logger LOG=LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerDAO repository;
	
	@PostMapping("/customer")
	public Mono<ResponseEntity<Mono<Customer>>> create(@Validated @RequestBody Customer body) {
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repository.insert(body)));
	}
	
	@GetMapping("/")
	public Mono<ResponseEntity<Flux<Customer>>> findAll() {
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repository.findAll().delaySequence(Duration.ofSeconds(4))));
	}
	
	@PutMapping("/customer/{id}")
	public Mono<ResponseEntity<Mono<Customer>>> update(@PathVariable String id,@Validated @RequestBody Customer body) {
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repository.save(body)));
	}
	
	@DeleteMapping("/customer/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {		
		return repository.deleteById(id)
				.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				.defaultIfEmpty((new ResponseEntity<Void>(HttpStatus.NOT_FOUND)));
	}
}
