package pe.com.fractal.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.fractal.model.Customer;

@Repository
public interface CustomerDAO extends ReactiveMongoRepository<Customer, String> {

}
