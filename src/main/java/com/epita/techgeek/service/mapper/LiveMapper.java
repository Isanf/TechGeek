package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.LiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Live} and its DTO {@link LiveDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface LiveMapper extends EntityMapper<LiveDTO, Live> {

    @Mapping(source = "module.id", target = "moduleId")
    LiveDTO toDto(Live live);

    @Mapping(source = "moduleId", target = "module")
    Live toEntity(LiveDTO liveDTO);

    default Live fromId(Long id) {
        if (id == null) {
            return null;
        }
        Live live = new Live();
        live.setId(id);
        return live;
    }
}
