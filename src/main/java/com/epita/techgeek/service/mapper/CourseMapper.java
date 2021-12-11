package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {


    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "removeModule", ignore = true)
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
