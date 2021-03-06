package com.epam.esm.dto.entityDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class CertificateDTO extends EntityDTO {

    @NotBlank
    @Size(max = 150)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;


    @NotNull
    @Max(value = 150)
    @Min(value = 1)
    private int duration;

    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<TagDTO> tags = new HashSet<>();

    public CertificateDTO(String name, String description, double price, int duration) {
        super();
        this.name = name;
        this.description = description;
        this.price = BigDecimal.valueOf(price);
        this.duration = duration;
    }

    public CertificateDTO(){
        super();
    }
}
