package dk.dev.customersystem.dao.impl;

import dk.dev.customersystem.dao.CustomerDao;
import dk.dev.customersystem.enums.CustomerType;
import dk.dev.customersystem.model.Customer;
import dk.dev.customersystem.model.LegalCustomer;
import dk.dev.customersystem.model.RealCustomer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@Primary
@Profile("jdbc")
public class CustomerJdbcDao implements CustomerDao {


    private final JdbcTemplate jdbc;
    private final Validator validator;

    @Autowired
    public CustomerJdbcDao(JdbcTemplate jdbc, Validator validator) {
        this.jdbc = jdbc;
        this.validator = validator;
    }

    private Customer insert(Customer customer) {
        String customerSql = "INSERT INTO customer (name, family, phone_number, type) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(customerSql, new String[]{"id"});
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getFamily());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getType().name());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        customer.setId(id);

        if (customer instanceof RealCustomer realCustomer) {
            String realCustomerSql = "INSERT INTO real_customer (id, nationality) VALUES (?, ?)";
            jdbc.update(realCustomerSql, id, realCustomer.getNationality());
        } else if (customer instanceof LegalCustomer legalCustomer) {
            String legalCustomerSql = "INSERT INTO legal_customer (id, industry) VALUES (?, ?)";
            jdbc.update(legalCustomerSql, id, legalCustomer.getIndustry());
        }

        return customer;
    }

    private Customer update(Customer customer) {
        String customerSql = "UPDATE customer SET name = ?, phone_number = ?, type = ? WHERE id = ?";
        jdbc.update(customerSql, customer.getName(), customer.getFamily(),
                customer.getPhoneNumber(), customer.getType().name(), customer.getId());

        if (customer instanceof RealCustomer realCustomer) {
            String realCustomerSql = "UPDATE real_customer SET nationality = ? WHERE id = ?";
            jdbc.update(realCustomerSql, realCustomer.getNationality(), customer.getId());
        } else if (customer instanceof LegalCustomer legalCustomer) {
            String legalCustomerSql = "UPDATE legal_customer SET industry = ? WHERE id = ?";
            jdbc.update(legalCustomerSql, legalCustomer.getIndustry(), customer.getId());
        }

        return customer;
    }

    @Override
    public Customer save(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        if (existsById(customer.getId())) {
            return update(customer);
        } else {
            return insert(customer);
        }
    }

    @Override
    public void deleteById(Long id) {
        String customerSql = "DELETE FROM customer WHERE id = ?";
        jdbc.update(customerSql, id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if(!existsById(id)) {
            return Optional.empty();
        }
        String customerSql = "SELECT * FROM customer WHERE id = ?";
        Map<String, Object> customerRow = jdbc.queryForMap(customerSql, id);

        CustomerType type = CustomerType.valueOf((String) customerRow.get("type"));
        Customer customer;

        if (type == CustomerType.REAL) {
            String realCustomerSql = "SELECT * FROM real_customer WHERE id = ?";
            Map<String, Object> realCustomerRow = jdbc.queryForMap(realCustomerSql, id);
            customer = RealCustomer.builder()
                    .id(id)
                    .name((String) customerRow.get("name"))
                    .family((String) customerRow.get("family"))
                    .phoneNumber((String) customerRow.get("phone_number"))
                    .type(type)
                    .nationality((String) realCustomerRow.get("nationality"))
                    .build();
        } else {
            String legalCustomerSql = "SELECT * FROM legal_customer WHERE id = ?";
            Map<String, Object> legalCustomerRow = jdbc.queryForMap(legalCustomerSql, id);
            customer = LegalCustomer.builder()
                    .id(id)
                    .name((String) customerRow.get("name"))
                    .family((String) customerRow.get("family"))
                    .phoneNumber((String) customerRow.get("phone_number"))
                    .type(type)
                    .industry((String) legalCustomerRow.get("industry"))
                    .build();
        }

        return Optional.of(customer);
    }

    @Override
    public List<Customer> findAll() {
        String customerSql = "SELECT * FROM customer";
        return jdbc.query(customerSql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            CustomerType type = CustomerType.valueOf(rs.getString("type"));

            if (type == CustomerType.REAL) {
                String realCustomerSql = "SELECT * FROM real_customer WHERE id = ?";
                Map<String, Object> realCustomerRow = jdbc.queryForMap(realCustomerSql, id);
                return RealCustomer.builder()
                        .id(id)
                        .name(rs.getString("name"))
                        .family(rs.getString("family"))
                        .phoneNumber(rs.getString("phone_number"))
                        .type(type)
                        .nationality((String) realCustomerRow.get("nationality"))
                        .build();
            } else {
                String legalCustomerSql = "SELECT * FROM legal_customer WHERE id = ?";
                Map<String, Object> legalCustomerRow = jdbc.queryForMap(legalCustomerSql, id);
                return LegalCustomer.builder()
                        .id(id)
                        .name(rs.getString("name"))
                        .family(rs.getString("family"))
                        .phoneNumber(rs.getString("phone_number"))
                        .type(type)
                        .industry((String) legalCustomerRow.get("industry"))
                        .build();
            }
        });
    }

    @Override
    public List<Customer> findByNameIgnoreCase(String name) {
        String customerSql = "SELECT * FROM customer WHERE LOWER(name) = LOWER(?)";
        return jdbc.query(customerSql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            CustomerType type = CustomerType.valueOf(rs.getString("type"));

            if (type == CustomerType.REAL) {
                String realCustomerSql = "SELECT * FROM real_customer WHERE id = ?";
                Map<String, Object> realCustomerRow = jdbc.queryForMap(realCustomerSql, id);
                return RealCustomer.builder()
                        .id(id)
                        .name(rs.getString("name"))
                        .family(rs.getString("family"))
                        .phoneNumber(rs.getString("phone_number"))
                        .type(type)
                        .nationality((String) realCustomerRow.get("nationality"))
                        .build();
            } else {
                String legalCustomerSql = "SELECT * FROM legal_customer WHERE id = ?";
                Map<String, Object> legalCustomerRow = jdbc.queryForMap(legalCustomerSql, id);
                return LegalCustomer.builder()
                        .id(id)
                        .name(rs.getString("name"))
                        .family(rs.getString("family"))
                        .phoneNumber(rs.getString("phone_number"))
                        .type(type)
                        .industry((String) legalCustomerRow.get("industry"))
                        .build();
            }
        }, name);
    }

    @Override
    public boolean existsById(Long id) {
        String customerSql = "SELECT COUNT(*) FROM customer WHERE id = ?";
        Integer count = jdbc.queryForObject(customerSql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByNameIgnoreCaseAndFamilyIgnoreCase(String name, String family) {
        String sql = "SELECT COUNT(*) FROM customer WHERE LOWER(name) = LOWER(?) AND LOWER(family) = LOWER(?) ";
        Integer count = jdbc.queryForObject(sql, Integer.class, name, family);
        return count != null && count > 0;
    }
}
