package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.ModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Module} and its DTO {@link ModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CourseMapper.class, ProfessorMapper.class, VolunteerMapper.class, StudentMapper.class})
public interface ModuleMapper extends EntityMapper<ModuleDTO, Module> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "professor.id", target = "professorId")
    @Mapping(source = "volunteer.id", target = "volunteerId")
    @Mapping(source = "student.id", target = "studentId")
    ModuleDTO toDto(Module module);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "frameworks", ignore = true)
    @Mapping(target = "removeFramework", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "removeLanguage", ignore = true)
    @Mapping(target = "live", ignore = true)
    @Mapping(target = "video", ignore = true)
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "professorId", target = "professor")
    @Mapping(source = "volunteerId", target = "volunteer")
    @Mapping(source = "studentId", target = "student")
    Module toEntity(ModuleDTO moduleDTO);

    default Module fromId(Long id) {
        if (id == null) {
            return null;
        }
        Module module = new Module();
        module.setId(id);
        return module;
    }
}
