package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {LevelMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "level.id", target = "levelId")
    StudentDTO toDto(Student student);

    @Mapping(source = "levelId", target = "level")
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "removeModule", ignore = true)
    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
