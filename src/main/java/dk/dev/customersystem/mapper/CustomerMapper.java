package dk.dev.customersystem.mapper;

import dk.dev.customersystem.dto.CustomerDto;
import dk.dev.customersystem.dto.LegalCustomerDto;
import dk.dev.customersystem.dto.RealCustomerDto;
import dk.dev.customersystem.model.Customer;
import dk.dev.customersystem.model.LegalCustomer;
import dk.dev.customersystem.model.RealCustomer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    RealCustomer toEntity(RealCustomerDto dto);
    RealCustomerDto toDto(RealCustomer entity);

    LegalCustomer toEntity(LegalCustomerDto dto);
    LegalCustomerDto toDto(LegalCustomer entity);

    default Customer toEntity(Object dto){
        if(dto instanceof RealCustomerDto){
            return toEntity((RealCustomerDto)dto);
        }else if(dto instanceof LegalCustomerDto){
            return toEntity((LegalCustomerDto)dto);
        }
        throw new IllegalArgumentException("Unsupported type: " + dto.getClass().getName());
    }

    default CustomerDto toDto(Object entity){
        if(entity instanceof RealCustomer){
            return toDto((RealCustomer)entity);
        }else if(entity instanceof LegalCustomer){
            return toDto((LegalCustomer)entity);
        }
        throw new IllegalArgumentException("Unsupported type: " + entity.getClass().getName());
    }
}
