package dk.dev.customersystem.dao.impl;

import dk.dev.customersystem.dao.CustomerDao;
import dk.dev.customersystem.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Primary
@Repository
@Profile("jpa")
public interface CustomerJpaDao extends JpaRepository<Customer,Long>, CustomerDao {
}
