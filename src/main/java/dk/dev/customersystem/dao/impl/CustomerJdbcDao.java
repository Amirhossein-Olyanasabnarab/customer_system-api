package dk.dev.customersystem.dao.impl;

import dk.dev.customersystem.dao.CustomerDao;
import dk.dev.customersystem.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class CustomerJdbcDao implements CustomerDao {
    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public List<Customer> findByNameIgnoreCase(String name) {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
