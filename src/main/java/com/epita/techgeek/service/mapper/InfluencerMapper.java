package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.InfluencerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Influencer} and its DTO {@link InfluencerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InfluencerMapper extends EntityMapper<InfluencerDTO, Influencer> {


    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "removeCategory", ignore = true)
    Influencer toEntity(InfluencerDTO influencerDTO);

    default Influencer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Influencer influencer = new Influencer();
        influencer.setId(id);
        return influencer;
    }
}
