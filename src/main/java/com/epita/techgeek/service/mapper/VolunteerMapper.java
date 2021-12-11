package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.VolunteerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Volunteer} and its DTO {@link VolunteerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VolunteerMapper extends EntityMapper<VolunteerDTO, Volunteer> {


    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "removeModule", ignore = true)
    Volunteer toEntity(VolunteerDTO volunteerDTO);

    default Volunteer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        return volunteer;
    }
}
