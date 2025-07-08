package dk.dev.customersystem.model;

import dk.dev.customersystem.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class RealCustomer extends Customer {
    private String nationality;

    public RealCustomer() {
        this.setType(CustomerType.REAL);
    }
}
