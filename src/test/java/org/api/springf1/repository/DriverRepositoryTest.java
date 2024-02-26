package org.api.springf1.repository;

import org.api.springf1.model.Driver;
import org.api.springf1.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DriverRepositoryTest {

    @Autowired
    DriverRepository driverRepository;
    Driver driver;
    Driver driver2;

    @BeforeEach
    void setUp() {
        driver = Driver.builder().id(12L).code("CMT").forename("Carolina").surname("Maldonado").build();
        driver2 = Driver.builder().id(13L).code("CMT2").forename("Carolina2").surname("Maldonado2").build();
    }

    @Test
    void shouldReturnMoreThanOneDriverWhenSaveTwoDrivers() {
        driverRepository.save(driver);
        driverRepository.save(driver2);

        assertEquals(2, driverRepository.findAll().size());
    }

    @Test
    public void shouldReturnDriverNotNullWhenFindByCode() {
        driverRepository.save(driver);

        assertNotNull(driverRepository.findByCodeIgnoreCase("CMT"));
    }

    @Test
    public void shouldReturnDriverNotNullWhenUpdateDriver() {
        driverRepository.save(driver);
        driver.setCode("CAR");

        assertNotNull(driverRepository.save(driver));
    }

    @Test
    public void shouldReturnNullDriverWhenDelete() {
        driverRepository.save(driver);
        driverRepository.deleteByCodeIgnoreCase(driver.getCode());

        assertEquals(0, driverRepository.findAll().size());
    }
}