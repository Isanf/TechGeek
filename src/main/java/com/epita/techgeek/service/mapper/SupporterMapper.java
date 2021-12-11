package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.SupporterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Supporter} and its DTO {@link SupporterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupporterMapper extends EntityMapper<SupporterDTO, Supporter> {


    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "removeCategory", ignore = true)
    Supporter toEntity(SupporterDTO supporterDTO);

    default Supporter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Supporter supporter = new Supporter();
        supporter.setId(id);
        return supporter;
    }
}
