package com.epam.esm.dto;

import com.epam.esm.validator.SortOrderType;
import com.epam.esm.validator.SortType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CertificatePageQueryDTO implements Serializable {

    private List<@Size(max = 50) String> tags;

    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    @SortType
    private String sortBy = "name";

    @SortOrderType
    private String order = "asc";
}
