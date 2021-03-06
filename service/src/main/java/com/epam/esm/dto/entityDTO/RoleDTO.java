package com.epam.esm.dto.entityDTO;

import com.epam.esm.entity.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends EntityDTO {
    @Pattern(regexp = "USER|ADMIN")
    private UserRole roleName;
}
