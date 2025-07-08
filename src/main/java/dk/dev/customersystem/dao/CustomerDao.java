package dk.dev.customersystem.dao;

import dk.dev.customersystem.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Customer save(Customer customer);
    void deleteById(Long id);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    List<Customer> findByNameIgnoreCase(String name);
    boolean existsById(Long id);
}
