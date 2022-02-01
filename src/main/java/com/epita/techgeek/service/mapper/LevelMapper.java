package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.LevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Level} and its DTO {@link LevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LevelMapper extends EntityMapper<LevelDTO, Level> {


    @Mapping(target = "student", ignore = true)
    Level toEntity(LevelDTO levelDTO);

    default Level fromId(Long id) {
        if (id == null) {
            return null;
        }
        Level level = new Level();
        level.setId(id);
        return level;
    }
}
