package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.FrameworkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Framework} and its DTO {@link FrameworkDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface FrameworkMapper extends EntityMapper<FrameworkDTO, Framework> {

    @Mapping(source = "module.id", target = "moduleId")
    FrameworkDTO toDto(Framework framework);

    @Mapping(source = "moduleId", target = "module")
    Framework toEntity(FrameworkDTO frameworkDTO);

    default Framework fromId(Long id) {
        if (id == null) {
            return null;
        }
        Framework framework = new Framework();
        framework.setId(id);
        return framework;
    }
}
