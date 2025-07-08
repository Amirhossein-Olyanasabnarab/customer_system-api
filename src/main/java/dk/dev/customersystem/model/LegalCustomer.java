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
public class LegalCustomer extends Customer {
    private String industry;

    public LegalCustomer() {
        this.setType(CustomerType.LEGAL);
    }
}
