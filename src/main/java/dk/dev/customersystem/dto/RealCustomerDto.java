package dk.dev.customersystem.dto;

import dk.dev.customersystem.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class RealCustomerDto extends CustomerDto {

    @Schema(description = "Nationality of customer", example = "British")
    private String nationality;

    public RealCustomerDto(){
        this.setType(CustomerType.REAL);
    }
}
