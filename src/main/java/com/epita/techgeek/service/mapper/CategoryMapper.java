package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {SupporterMapper.class, InfluencerMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "supporter.id", target = "supporterId")
    @Mapping(source = "influencer.id", target = "influencerId")
    CategoryDTO toDto(Category category);

    @Mapping(target = "module", ignore = true)
    @Mapping(source = "supporterId", target = "supporter")
    @Mapping(source = "influencerId", target = "influencer")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
