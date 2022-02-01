package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.LanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Language} and its DTO {@link LanguageDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {

    @Mapping(source = "module.id", target = "moduleId")
    LanguageDTO toDto(Language language);

    @Mapping(source = "moduleId", target = "module")
    Language toEntity(LanguageDTO languageDTO);

    default Language fromId(Long id) {
        if (id == null) {
            return null;
        }
        Language language = new Language();
        language.setId(id);
        return language;
    }
}
