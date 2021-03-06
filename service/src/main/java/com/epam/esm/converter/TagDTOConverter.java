package com.epam.esm.converter;

import com.epam.esm.dto.entityDTO.TagDTO;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagDTOConverter implements DTOConverter<Tag, TagDTO>{
    private final ModelMapper modelMapper;

    @Override
    public Tag convertToEntity(TagDTO tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    @Override
    public TagDTO convertToDto(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }

    @Override
    public List<TagDTO> convertToListDTO(List<Tag> tags) {
        return tags.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
