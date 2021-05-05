package com.epam.esm.converter;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDTOConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public UserDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> convertToListDTO(List<User> users) {
        List<UserDTO> userDTOList = new ArrayList<>();
        users.forEach(user -> {
            UserDTO userDTO = convertToDto(user);
            userDTOList.add(userDTO);
        });
        return userDTOList;
    }
}