package com.epam.esm.dto;

import com.epam.esm.entity.Tag;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Objects;


public class TagDTO {

    private Long id;

    @NotBlank(message = "name must not be empty")
    @Max(value = 50, message = "name cannot be more then 50 characters")
    private String name;

    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDTO() {
    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDTO tagDto = (TagDTO) o;
        return Objects.equals(id, tagDto.id) &&
                Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
