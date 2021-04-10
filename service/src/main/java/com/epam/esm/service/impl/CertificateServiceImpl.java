package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.CertificateConverterDTO;
import com.epam.esm.dto.query.CertificatePageQueryDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.util.CertificateParamsRequestUtil;
import com.epam.esm.specification.CertificateAllSpecification;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.validator.CertificateDTOValidator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final Validator<CertificateDTO> validator;


    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository, CertificateDTOValidator validator) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    @Override
    public CertificateDTO getById(Long id) {
        Certificate certificate = certificateRepository.getById(id);
        addTagsToCertificate(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        validator.cleanErrorMessages();
        if (!validator.isValid(certificateDTO)) {
            throw new ServiceException(validator.getErrorMessage());
        }
        String certificateName = certificateDTO.getName();
        if (!certificateRepository.getByName(certificateName).isPresent()) {
            throw new ServiceException("The certificate with this name (" + certificateName + ") is already exists.");
        }
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        checkTags(certificate);
        certificate = certificateRepository.save(certificate);
        tagRepository.createCertificateTags(certificate);
        certificate = certificateRepository.getById(certificate.getId());
        addTagsToCertificate(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        validator.cleanErrorMessages();
        if (!validator.isValid(certificateDTO)){
            throw new ServiceException(validator.getErrorMessage());
        }
        String certificateName = certificateDTO.getName();
        if (!certificateRepository.getByName(certificateName).isPresent()) {
            throw new ServiceException("The certificate with this name (" + certificateName + ") is already exists.");
        }
        Certificate certificate = CertificateConverterDTO.convertToEntity(certificateDTO);
        certificateRepository.deleteCertificateTags(certificate.getId());
        checkTags(certificate);
        certificate = certificateRepository.update(certificate);
        tagRepository.createCertificateTags(certificate);
        certificate = certificateRepository.getById(certificate.getId());
        addTagsToCertificate(certificate);
        return CertificateConverterDTO.convertToDto(certificate);
    }

    private void checkTags(Certificate certificate) {
        Set<Tag> tags = certificate.getTags();
        if (!tags.isEmpty()) {
            tagRepository.createNewTags(tags);
        }
    }

    @Override
    public boolean remove(Long id) {
        return certificateRepository.deleteById(id);
    }

    @Override
    public List<CertificateDTO> executeQueryDTO(CertificatePageQueryDTO queryDTO) {
        CertificateParamsRequestUtil paramsRequestUtil = new CertificateParamsRequestUtil(queryDTO);
        String whereSQL = paramsRequestUtil.getWhereQueryWithParams();
        String sortSQL = paramsRequestUtil.getSortQueryWithParams();
        SqlSpecification specification = new CertificateAllSpecification(whereSQL, sortSQL);
        List<Certificate> certificates = certificateRepository.query(specification);
        addTagsToListCertificates(certificates);
        return CertificateConverterDTO.convertToListDTO(certificates);
    }

    private void addTagsToListCertificates(List<Certificate> certificates) {
        certificates.forEach(this::addTagsToCertificate);
    }

    private void addTagsToCertificate(Certificate certificate) {
        Set<Tag> tags = tagRepository.getTagsByCertificateId(certificate.getId());
        certificate.setTags(tags);
    }
}



