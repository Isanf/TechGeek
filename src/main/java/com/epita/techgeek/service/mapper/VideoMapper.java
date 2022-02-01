package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.VideoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Video} and its DTO {@link VideoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface VideoMapper extends EntityMapper<VideoDTO, Video> {

    @Mapping(source = "module.id", target = "moduleId")
    VideoDTO toDto(Video video);

    @Mapping(source = "moduleId", target = "module")
    Video toEntity(VideoDTO videoDTO);

    default Video fromId(Long id) {
        if (id == null) {
            return null;
        }
        Video video = new Video();
        video.setId(id);
        return video;
    }
}
