package com.epam.esm.service.impl;

import com.epam.esm.converter.DTOConverter;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificatePageQueryDTO;
import com.epam.esm.dto.CertificateRequestFieldDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.PageRequestDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.Repository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.CriteriaSpecification;
import com.epam.esm.specification.certificate.CertificateByParamsSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl extends AbstractService<CertificateDTO, Certificate>
        implements CertificateService {
    private final Repository<Tag> tagRepository;

    public CertificateServiceImpl(DTOConverter<Certificate, CertificateDTO> converter,
                                  Repository<Certificate> repository,
                                  TagRepository tagRepository) {
        super(converter, repository);
        this.tagRepository = tagRepository;
    }

    @Override
    public void remove(Long id) {
        Certificate certificate = converter.convertToEntity(getById(id));
        repository.delete(certificate);
    }

    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        String name = certificateDTO.getName();
        if (repository.getByName(name).isPresent()) {
            throw new EntityAlreadyExistsException(" (name = " + name + ")");
        }
        Certificate certificate = converter.convertToEntity(certificateDTO);
        Set<Tag> tags = getFullTags(certificate);
        if (tags != null) {
            createNotExistingTag(tags);
        }
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return converter.convertToDto(repository.save(certificate));
    }

    private Set<Tag> getFullTags(Certificate certificate) {
        return certificate.getTags().stream()
                .map(tag -> tagRepository.getById(tag.getId())
                        .orElseThrow(() -> new EntityNotFoundException(" (tagId = " + tag.getId() + ")")))
                .collect(Collectors.toSet());
    }

    private void createNotExistingTag(Set<Tag> tags) {
        if (CollectionUtils.isNotEmpty(tags)) {
            Set<Tag> newTags = new HashSet<>();
            for (Tag tag : tags) {
                if (!tagRepository.getByName(tag.getName()).isPresent()) {
                    newTags.add(tag);
                }
            }
            newTags.forEach(tagRepository::save);
        }
    }

    @Override
    public CertificateDTO update(Long id, CertificateDTO certificateDTO) {
        CertificateDTO formerCertificate = getById(id);
        String newName = certificateDTO.getName();
        if (repository.getByName(newName).isPresent()
                && !newName.equals(formerCertificate.getName())) {
            throw new EntityAlreadyExistsException(" (name = " + newName + ")");
        }
        Certificate certificate = converter.convertToEntity(certificateDTO);
        Set<Tag> updatedTags = certificate.getTags();
        if (updatedTags != null) {
            createNotExistingTag(updatedTags);
        }
        certificate.setId(id);
        certificate.setCreateDate(formerCertificate.getCreateDate());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return converter.convertToDto(repository.update(certificate));
    }


    @Override
    public PageDTO<CertificateDTO> findByParams(CertificatePageQueryDTO queryDTO, PageRequestDTO pageRequestDTO) {
        CriteriaSpecification<Certificate> specification = new CertificateByParamsSpecification(queryDTO.getTags(),
                queryDTO.getName(), queryDTO.getDescription(), queryDTO.getSortBy(), queryDTO.getOrder());
        List<Certificate> certificates = repository.getEntityListBySpecification(specification,
                pageRequestDTO.getPage(), pageRequestDTO.getSize());
        List<CertificateDTO> certificateDTOList = converter.convertToListDTO(certificates);
        long totalElements = repository.countEntities(specification);
        return new PageDTO<>(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize(),
                totalElements,
                certificateDTOList);
    }

    @Override
    public CertificateDTO updateField(Long id, CertificateRequestFieldDTO field) {
        Certificate certificate = repository.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(" (id = " + id + ")"));
        if (StringUtils.isNotEmpty(field.getName())) {
            certificate.setName(field.getName());
        }
        if (StringUtils.isNotEmpty(field.getDescription())) {
            certificate.setDescription(field.getDescription());
        }
        if (field.getPrice() != null) {
            certificate.setPrice(field.getPrice());
        }
        if (field.getDuration() != null) {
            certificate.setDuration(field.getDuration());
        }
        certificate.setLastUpdateDate(LocalDateTime.now());
        Certificate updatedCertificate = repository.save(certificate);
        return converter.convertToDto(updatedCertificate);
    }
}



