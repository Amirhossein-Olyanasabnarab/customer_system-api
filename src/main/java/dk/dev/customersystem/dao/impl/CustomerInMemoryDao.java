package dk.dev.customersystem.dao.impl;

import dk.dev.customersystem.dao.CustomerDao;
import dk.dev.customersystem.model.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository

public class CustomerInMemoryDao implements CustomerDao {

    private final AtomicLong currentId = new AtomicLong(0);
    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final Validator validator;
    @Autowired
    public CustomerInMemoryDao(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Customer save(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        if(!existsById(customer.getId())) {
            Long id = currentId.incrementAndGet();
            customer.setId(id);
        }
        customers.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        customers.remove(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if (!existsById(id)) {
            return Optional.empty();
        }else{
            return Optional.of(customers.get(id));
        }
    }

    @Override
    public List<Customer> findAll() {
        return customers.values()
                .stream().toList();
    }

    @Override
    public List<Customer> findByNameIgnoreCase(String name) {
        return customers.values()
                .stream()
                .filter(customer -> customer.getName() != null &&
                        customer.getName().equalsIgnoreCase(name))
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }else
            return customers.containsKey(id);
    }

    @Override
    public boolean existsByNameIgnoreCaseAndFamilyIgnoreCase(String name, String family) {
        return customers.values()
                .stream()
                .anyMatch(customer -> customer.getName().equalsIgnoreCase(name)
                && customer.getFamily().equalsIgnoreCase(family));
    }
}
