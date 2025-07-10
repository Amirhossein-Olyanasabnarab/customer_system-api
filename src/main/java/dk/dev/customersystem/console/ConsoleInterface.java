package dk.dev.customersystem.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dev.customersystem.dto.CustomerDto;
import dk.dev.customersystem.dto.LegalCustomerDto;
import dk.dev.customersystem.dto.RealCustomerDto;
import dk.dev.customersystem.enums.CustomerType;
import dk.dev.customersystem.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Profile("console")
public class ConsoleInterface {

   private final CustomerFacade facade;
   private final Scanner scanner;
   private final ObjectMapper objectMapper;

    @Autowired
    public ConsoleInterface(CustomerFacade facade, ObjectMapper objectMapper) {
        this.facade = facade;
        this.scanner = new Scanner(System.in);
        this.objectMapper = objectMapper;
    }

    public void start() {
        while (true) {
            System.out.println("\nCustomer Management System");
            System.out.println("1. View all customers");
            System.out.println("2. View customer by ID");
            System.out.println("3. Add a new customer");
            System.out.println("4. Update a customer");
            System.out.println("5. Delete a customer");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> viewAllCustomers();
                case 2 -> viewCustomerById();
                case 3 -> addCustomer();
                case 4 -> updateCustomer();
                case 5 -> deleteCustomer();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void deleteCustomer() {
        System.out.println("Please enter customer Id for deleting customer:");
        Long id = Long.parseLong(scanner.nextLine());
        boolean isDelete = facade.deleteCustomer(id);
        System.out.println(isDelete ? "Customer deleted successfully!" : "Customer could not be deleted!");
    }

    private void updateCustomer() {
        System.out.print("Enter customer ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());
        CustomerDto customer = facade.getCustomerById(id);
        if(customer == null){
            System.out.println("Customer not found.");
        } else if (CustomerType.REAL.equals(customer.getType())) {
            RealCustomerDto realCustomer = (RealCustomerDto) customer;
            System.out.print("Enter name: ");
            realCustomer.setName(scanner.nextLine());
            System.out.print("Enter family: ");
            realCustomer.setFamily(scanner.nextLine());
            System.out.print("Enter phone number: ");
            realCustomer.setPhoneNumber(scanner.nextLine());
            System.out.print("Enter nationality: ");
            realCustomer.setNationality(scanner.nextLine());
            realCustomer.setType(CustomerType.REAL);
            CustomerDto updatedCustomer = facade.updateCustomer(id, realCustomer);
            System.out.println("Customer updated: ");
            printJsonObject(updatedCustomer);
        } else if (CustomerType.LEGAL.equals(customer.getType())) {
            LegalCustomerDto legalCustomer = (LegalCustomerDto) customer;
            System.out.print("Enter name: ");
            legalCustomer.setName(scanner.nextLine());
            System.out.println("Enter family: ");
            legalCustomer.setFamily(scanner.nextLine());
            System.out.print("Enter phone number: ");
            legalCustomer.setPhoneNumber(scanner.nextLine());
            System.out.print("Enter industry: ");
            legalCustomer.setIndustry(scanner.nextLine());
            legalCustomer.setType(CustomerType.LEGAL);
            CustomerDto updatedCustomer = facade.updateCustomer(id, legalCustomer);
            System.out.println("Customer updated: ");
            printJsonObject(updatedCustomer);
        }
    }

    private void printJsonObject(CustomerDto customer) {
        try {
            System.out.println(customer != null ?
                    objectMapper.writeValueAsString(customer) : "Customer not found.");
        } catch (JsonProcessingException e) {
            System.err.println("Error converting customer to JSON: " + e.getMessage());
        }
    }

    private void addCustomer() {
        System.out.print("Enter customer type (REAL/LEGAL): ");
        String type = scanner.nextLine().toUpperCase();

        if ("REAL".equals(type)) {
            RealCustomerDto realCustomer = new RealCustomerDto();
            System.out.print("Enter name: ");
            realCustomer.setName(scanner.nextLine());
            System.out.print("Enter family: ");
            realCustomer.setFamily(scanner.nextLine());
            System.out.print("Enter phone number: ");
            realCustomer.setPhoneNumber(scanner.nextLine());
            System.out.print("Enter nationality: ");
            realCustomer.setNationality(scanner.nextLine());
            realCustomer.setType(CustomerType.REAL);
            CustomerDto addedCustomer = facade.addCustomer(realCustomer);
            System.out.println("Customer added: ");
            printJsonObject(addedCustomer);
        } else if ("LEGAL".equals(type)) {
            LegalCustomerDto legalCustomer = new LegalCustomerDto();
            System.out.print("Enter name: ");
            legalCustomer.setName(scanner.nextLine());
            System.out.print("Enter family: ");
            legalCustomer.setFamily(scanner.nextLine());
            System.out.print("Enter phone number: ");
            legalCustomer.setPhoneNumber(scanner.nextLine());
            System.out.print("Enter industry: ");
            legalCustomer.setIndustry(scanner.nextLine());
            legalCustomer.setType(CustomerType.LEGAL);
            CustomerDto addedCustomer = facade.addCustomer(legalCustomer);
            System.out.println("Customer added: ");
            printJsonObject(addedCustomer);
        } else {
            System.out.println("Invalid customer type.");
        }
    }

    private void viewCustomerById() {
        System.out.print("Enter customer ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        CustomerDto customer = facade.getCustomerById(id);
        printJsonObject(customer);
    }

    private void viewAllCustomers() {
        List<CustomerDto> customers = facade.getAllCustomers();
        customers.forEach(this::printJsonObject);
    }
}
