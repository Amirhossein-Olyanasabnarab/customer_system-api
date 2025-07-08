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
public class LegalCustomerDto extends CustomerDto {

    @Schema(description = "Industry of customer", example = "Tech")
    private String industry;

    public LegalCustomerDto(){
        this.setType(CustomerType.LEGAL);
    }
}
