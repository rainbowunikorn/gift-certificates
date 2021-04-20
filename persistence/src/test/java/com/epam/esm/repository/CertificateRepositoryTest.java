package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("dev")
@Transactional
class CertificateRepositoryTest {

    private static final long TEST_CERTIFICATE_ID = 5L;
    public static final long EXISTING_CERTIFICATE_ID = 4L;

    private static final String TEST_CERTIFICATE_NAME = "test";
    public static final String EXISTING_CERTIFICATE_NAME = "The fourth";

    private final Certificate TEST_CERTIFICATE = new Certificate.
            Builder("test", "certificate for test",
            BigDecimal.valueOf(10.0), 10)
            .id(5L)
            .build();

    private final Certificate EXISTING_CERTIFICATE = new Certificate.
            Builder("The fourth", "The fourth certificate",
            BigDecimal.valueOf(14.0), 12)
            .id(4L)
            .createDate(LocalDateTime.of(2020,6,18,12,25,19))
            .lastUpdateDate(LocalDateTime.of(2021,3,10,7,51,49))
            .build();

    private final CertificateRepositoryImpl certificateRepository;

    @Autowired
    public CertificateRepositoryTest(CertificateRepositoryImpl certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Test
    void testSaveShouldReturnCertificateWhenSaved() {
        Certificate expected = TEST_CERTIFICATE;
        Certificate actual = certificateRepository.save(expected);
        assertEquals(actual, expected);
    }

    @Test
    void testUpdateShouldReturnCertificateWhenUpdated() {
        Certificate expected = TEST_CERTIFICATE;
        Certificate actual = certificateRepository.update(TEST_CERTIFICATE_ID, expected);
        assertEquals(actual, expected);
    }

    @Test
    void testDeleteByIdShouldReturnTrueWhenCertificateDeleted(){
        boolean actual = certificateRepository.deleteById(EXISTING_CERTIFICATE_ID);
        assertTrue(actual);
    }

    @Test
    void testDeleteByIdShouldReturnFalseWhenCertificateNotDeleted(){
        boolean actual = certificateRepository.deleteById(TEST_CERTIFICATE_ID);
        assertFalse(actual);
    }

    @Test
    void testGetByIdShouldReturnOptionalCertificateWhenFound(){
        Optional<Certificate> certificateOptional = certificateRepository.getById(EXISTING_CERTIFICATE_ID);
        assertEquals(certificateOptional, Optional.of(EXISTING_CERTIFICATE));
    }

    @Test
    void testGetByIdShouldReturnOptionalEmptyWhenNotFound(){
        Optional<Certificate> certificateOptional = certificateRepository.getById(TEST_CERTIFICATE_ID);
        assertEquals(certificateOptional, Optional.empty());
    }

    @Test
    void testGetByNameShouldReturnOptionalCertificateWhenFound(){
        Optional<Certificate> certificateOptional = certificateRepository.getByName(EXISTING_CERTIFICATE_NAME);
        assertEquals(certificateOptional, Optional.of(EXISTING_CERTIFICATE));
    }

    @Test
    void testGetByNameShouldReturnOptionalEmptyWhenNotFound(){
        Optional<Certificate> certificateOptional = certificateRepository.getByName(TEST_CERTIFICATE_NAME);
        assertEquals(certificateOptional, Optional.empty());
    }
}
