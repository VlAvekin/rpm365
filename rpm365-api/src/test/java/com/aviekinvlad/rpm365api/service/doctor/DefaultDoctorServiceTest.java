package com.aviekinvlad.rpm365api.service.doctor;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.config.TestMassage;
import com.aviekinvlad.rpm365api.entity.DoctorEntity;
import com.aviekinvlad.rpm365api.entity.PatientEntity;
import com.aviekinvlad.rpm365api.repo.DoctorRepo;
import com.aviekinvlad.rpm365api.repo.PatientRepo;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({
        "classpath:application-test.yml",
        "classpath:test.error.massage.properties",
        "classpath:test.validation.massage.properties"
})
@ActiveProfiles("test")
public class DefaultDoctorServiceTest {

    @Autowired
    private TestMassage testMessage;
    @Autowired
    private DefaultDoctorService doctorService;

    @MockBean
    private DoctorRepo doctorRepo;

    @SneakyThrows
    @Test
    public void getDoctor() {
        final long id = 1L;

        final DoctorEntity expected = new DoctorEntity();
        expected.setId(id);

        final Optional<DoctorEntity> data = Optional.of(expected);

        Mockito.when(doctorRepo.findById(id)).thenReturn(data);

        final DoctorEntity actual = doctorService.getDoctor(id);

        Mockito.verify(doctorRepo, Mockito.times(1)).findById(id);

        assertEquals(expected, actual);
    }

    @Test
    public void getPatientThrow() {
        final long id = 1L;

        final String expected = testMessage.massage("test.error.doctor.empty", id);

        ErrorDTOException actual = assertThrows(ErrorDTOException.class, () -> doctorService.getDoctor(id));

        assertEquals(expected, actual.getMessage());
    }
}