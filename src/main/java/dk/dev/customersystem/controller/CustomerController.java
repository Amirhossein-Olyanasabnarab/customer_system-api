package dk.dev.customersystem.controller;

import dk.dev.customersystem.dto.CustomerDto;
import dk.dev.customersystem.dto.LegalCustomerDto;
import dk.dev.customersystem.dto.RealCustomerDto;
import dk.dev.customersystem.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Operation(summary = "Delete a customer by id", description = "Remove a customer from customer system")
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        boolean isDeleted = facade.deleteCustomer(id);
        if (isDeleted) {
            return "Customer with id " + id + " was deleted";
        }else
            return "Customer with id " + id + " could not be deleted";
    }

    @Operation(summary = "Add a new customer", description = "Create a new customer")
    @PostMapping
    public CustomerDto addCustomer(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "customer object to be added",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            oneOf = {
                                    RealCustomerDto.class,
                                    LegalCustomerDto.class
                            }
                    ),
                    examples = {
                            @ExampleObject(
                                    name = "Real Customer Example",
                                    value = "{"
                                            + "\"name\": \"John\","
                                            + "\"family\": \"Doe\","
                                            + "\"phoneNumber\": \"+1234567890\","
                                            + "\"type\": \"REAL\","
                                            + "\"nationality\": \"British\""
                                            + "}"
                            ),
                            @ExampleObject(
                                    name = "Legal Customer Example",
                                    value = "{"
                                            + "\"name\": \"John\","
                                            + "\"family\": \"Doe\","
                                            + "\"phoneNumber\": \"+1234567890\","
                                            + "\"type\": \"LEGAL\","
                                            + "\"industry\": \"Tech\""
                                            + "}"
                            )
                    }
            )
    )
            @RequestBody CustomerDto customer) {
        return facade.addCustomer(customer);
    }


    @Operation(summary = "Update an existing customer", description = "Update the details of an existing customer")
    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id,
                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                              description = "Updated customer object",
                                              required = true,
                                              content = @Content(
                                                      mediaType = "application/json",
                                                      schema = @Schema(
                                                              oneOf = {
                                                                      RealCustomerDto.class,
                                                                      LegalCustomerDto.class
                                                              }
                                                      ),
                                                      examples = {
                                                              @ExampleObject(
                                                                      name = "Real Customer Example",
                                                                      value = "{"
                                                                              + "\"name\": \"John\","
                                                                              + "\"family\": \"Doe\","
                                                                              + "\"phoneNumber\": \"+1234567890\","
                                                                              + "\"type\": \"REAL\","
                                                                              + "\"nationality\": \"British\""
                                                                              + "}"
                                                              ),
                                                              @ExampleObject(
                                                                      name = "Legal Customer Example",
                                                                      value = "{"
                                                                              + "\"name\": \"John\","
                                                                              + "\"family\": \"Doe\","
                                                                              + "\"phoneNumber\": \"+1234567890\","
                                                                              + "\"type\": \"LEGAL\","
                                                                              + "\"industry\": \"Tech\""
                                                                              + "}"
                                                              )
                                                      }
                                              )
                                      )
                                      @RequestBody CustomerDto customer) {
        return facade.updateCustomer(id, customer);
    }
}
