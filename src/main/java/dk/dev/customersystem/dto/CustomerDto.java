package dk.dev.customersystem.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dk.dev.customersystem.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = RealCustomerDto.class, name = "REAL"),
        @JsonSubTypes.Type(value = LegalCustomerDto.class, name = "LEGAL")
})

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer entity representing a customer")

public abstract class CustomerDto {

    @Schema(description = "Unique identifier for the customer", example = "1")
    private Long id;

    @Schema(description = "Name of the customer", example = "John")
    @NotNull(message = "Name can not be empty.")
    private String name;

    @Schema(description = "Family of the customer", example = "Doe")
    @NotNull(message = "Family can not be empty.")
    private String family;

    @Schema(description = "Phone number of the customer", example = "1234567890")
    private String phoneNumber;
    private CustomerType type;
}
