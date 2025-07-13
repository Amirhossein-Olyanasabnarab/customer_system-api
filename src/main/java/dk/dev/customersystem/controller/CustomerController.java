package dk.dev.customersystem.controller;

import dk.dev.customersystem.dto.CustomerDto;
import dk.dev.customersystem.dto.LegalCustomerDto;
import dk.dev.customersystem.dto.RealCustomerDto;
import dk.dev.customersystem.exception.CustomerNotFoundException;
import dk.dev.customersystem.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {
                                            RealCustomerDto.class,
                                            LegalCustomerDto.class
                                    }
                            )
                    )),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            CustomerDto customerDto = facade.getCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerDto);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new dk.dev.customersystem.dto.ErrorResponse(HttpStatus.NOT_FOUND.value(),
                            e.getMessage()));
        }
    }

    @Operation(summary = "Delete a customer by id", description = "Remove a customer from customer system")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remove a customer from the customer system"),
            @ApiResponse(responseCode = "204", description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            facade.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Customer deleted successfully");
        } catch (CustomerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new dk.dev.customersystem.dto.ErrorResponse(HttpStatus.NOT_FOUND.value(),
                            exception.getMessage()));
        }
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

    @Operation(summary = "Get customers by name", description = "Retrieve a list of customers by their name")
    @GetMapping("/name/{name}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            oneOf = {
                                                    RealCustomerDto.class,
                                                    LegalCustomerDto.class
                                            }
                                    )
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
            ),
            @ApiResponse(responseCode = "404",
                    description = "No customers found with the given name", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
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
    })
    public ResponseEntity<?> getCustomerByName(@PathVariable String name) {
        try {
            List<CustomerDto> customers = facade.getCustomersByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(customers);
        }catch (CustomerNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new dk.dev.customersystem.dto.ErrorResponse(HttpStatus.NOT_FOUND.value(),
                            exception.getMessage()));
        }
    }
}
