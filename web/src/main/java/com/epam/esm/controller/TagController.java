package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.link.LinkBuilder;
import com.epam.esm.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The controller to provide CRD operations on {@link TagDTO}.
 */
@RestController
@RequestMapping(value = "/v2/tags")
public class TagController {
    private final TagService tagService;
    private final LinkBuilder<TagDTO> tagDTOLinkBuilder;

    public TagController(TagService tagService,
                         LinkBuilder<TagDTO> tagDTOLinkBuilder) {
        this.tagService = tagService;
        this.tagDTOLinkBuilder = tagDTOLinkBuilder;
    }

    /**
     * Fids all tags.
     *
     * @return the list of all tags
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<TagDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<TagDTO> tagDTOPage = tagService.findAll(pageable);
        tagDTOPage.getContent().forEach(tagDTOLinkBuilder::buildEntityLink);
        return tagDTOPage;
    }

    /**
     * Find tag by id.
     *
     * @param id the id of tag
     * @return the tag with queried id {@link TagDTO}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO findById(@PathVariable Long id) {
        TagDTO tagDTO = tagService.getById(id);
        tagDTOLinkBuilder.buildEntityLink(tagDTO);
        return tagDTO;
    }

    /**
     * Delete tag.
     *
     * @param id the id of tag to remove
     * @return no content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.remove(id);
    }

    /**
     * Create tag.
     *
     * @param tagDTO the tag to add
     * @return the added tag {@link TagDTO} and link to it
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@RequestBody @Valid TagDTO tagDTO) {
        TagDTO createdTag = tagService.create(tagDTO);
        tagDTOLinkBuilder.buildEntityLink(createdTag);
        return createdTag;
    }
}
