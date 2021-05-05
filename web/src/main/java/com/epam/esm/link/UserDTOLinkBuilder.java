package com.epam.esm.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDTOLinkBuilder implements LinkBuilder<UserDTO> {
    @Override
    public void toModel(UserDTO userDTO) {
        userDTO.add(linkTo(UserController.class).withRel("users"));
        userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
    }
}