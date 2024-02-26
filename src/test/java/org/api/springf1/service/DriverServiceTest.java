package org.api.springf1.service;

import org.api.springf1.dto.DriverDTO;
import org.api.springf1.dto.DriverResponse;
import org.api.springf1.model.Driver;
import org.api.springf1.repository.DriverRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    DriverRepository driverRepository;

    @InjectMocks
    DriverServiceImpl driverService;

    Driver driver;
    DriverDTO driverDTO;

    @BeforeEach
    public void setUp() {
        driver = Driver.builder().id(12L).code("CMT").forename("Carolina").surname("Maldonado").nationality("Española").build();
        driverDTO = DriverDTO.builder().id(12L).code("CMT").forename("Carolina").surname("Maldonado").build();
    }

    @Test
    public void shouldReturnDriverDTOWhenFindDriverByCode() {
        when(driverRepository.findByCodeIgnoreCase("CMT")).thenReturn(Optional.of(driver));
        driverDTO = driverService.getDriverByCode("CMT");
        Assertions.assertNotNull(driverDTO);
        assertEquals("CMT", driverDTO.code());
        verify(driverRepository, times(1)).findByCodeIgnoreCase("CMT");
    }

    @Test
    void shouldReturnDriverResponseWhenGetAllDrivers() {
        Pageable pageable = PageRequest.of(0, 10);
        when(driverRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(driver)));
        DriverResponse driverResponse = driverService.getDrivers(0, 10);
        Assertions.assertNotNull(driverResponse);
        assertEquals(1, driverResponse.content().size());
        verify(driverRepository, times(1)).findAll(pageable);
    }
}
