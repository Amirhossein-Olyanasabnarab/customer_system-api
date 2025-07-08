package dk.dev.customersystem.controller;

import dk.dev.customersystem.dto.CustomerDto;
import dk.dev.customersystem.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerFacade facade;

    @Autowired
    public CustomerController(CustomerFacade facade) {
        this.facade = facade;
    }

    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers")
    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return facade.getAllCustomers();
    }

    @Operation(summary = "Get a customer by id", description = "Retrieve a customer by id")
    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return facade.getCustomerById(id);
    }
}
