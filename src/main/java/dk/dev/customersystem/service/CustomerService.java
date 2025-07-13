package dk.dev.customersystem.service;

import dk.dev.customersystem.dao.CustomerDao;
import dk.dev.customersystem.enums.CustomerType;
import dk.dev.customersystem.exception.CustomerNotFoundException;
import dk.dev.customersystem.model.Customer;
import dk.dev.customersystem.model.RealCustomer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

//    @PostConstruct
//    public void init() {
//
//        addCustomer(RealCustomer.builder()
//                .id(1L)
//                .name("Oliver")
//                .family("Kahn")
//                .phoneNumber("+49 123 45 67")
//                .type(CustomerType.REAL)
//                .nationality("German")
//                .build()
//        );
//    }

    public Customer addCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        if (customerDao.existsById(id)) {
            updatedCustomer.setId(id);
            return customerDao.save(updatedCustomer);
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        if (customerDao.existsById(id)) {
            customerDao.deleteById(id);
        }else
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
    }

    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerDao.findById(id);
    }

    public List<Customer> findByName(String name) {
        return customerDao.findByNameIgnoreCase(name);
    }
}
