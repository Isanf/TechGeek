package com.epita.techgeek.service.mapper;


import com.epita.techgeek.domain.*;
import com.epita.techgeek.service.dto.UploadfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Uploadfile} and its DTO {@link UploadfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UploadfileMapper extends EntityMapper<UploadfileDTO, Uploadfile> {



    default Uploadfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Uploadfile uploadfile = new Uploadfile();
        uploadfile.setId(id);
        return uploadfile;
    }
}
