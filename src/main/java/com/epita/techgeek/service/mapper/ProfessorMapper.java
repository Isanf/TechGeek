package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.ProfessorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professor} and its DTO {@link ProfessorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfessorMapper extends EntityMapper<ProfessorDTO, Professor> {


    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "removeModule", ignore = true)
    Professor toEntity(ProfessorDTO professorDTO);

    default Professor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }
}
