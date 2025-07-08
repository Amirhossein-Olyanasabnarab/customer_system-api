package dk.dev.customersystem.facade;

import dk.dev.customersystem.dto.CustomerDto;
import dk.dev.customersystem.mapper.CustomerMapper;
import dk.dev.customersystem.model.Customer;
import dk.dev.customersystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerFacade {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    @Autowired
    public CustomerFacade(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer entity = customerService.addCustomer(customerMapper.toEntity(customerDto));
        return customerMapper.toDto(entity);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer entity = customerMapper.toEntity(customerDto);
        entity = customerService.updateCustomer(id, entity);
        return entity != null ? customerMapper.toDto(entity) : null;
    }


    public boolean deleteCustomer(Long id) {
        return customerService.deleteCustomer(id);
    }

    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> entity = customerService.getCustomerById(id);
        return entity.map(customerMapper::toDto)
                .orElse(null);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public List<CustomerDto> getCustomersByName(String name) {
        return customerService.findByName(name)
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }
}
