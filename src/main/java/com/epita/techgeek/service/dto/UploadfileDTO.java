package com.epita.techgeek.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.epita.techgeek.domain.Uploadfile} entity.
 */
public class UploadfileDTO implements Serializable {
    
    private Long id;

    @Lob
    private byte[] myfile;

    private String myfileContentType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getMyfile() {
        return myfile;
    }

    public void setMyfile(byte[] myfile) {
        this.myfile = myfile;
    }

    public String getMyfileContentType() {
        return myfileContentType;
    }

    public void setMyfileContentType(String myfileContentType) {
        this.myfileContentType = myfileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadfileDTO uploadfileDTO = (UploadfileDTO) o;
        if (uploadfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadfileDTO{" +
            "id=" + getId() +
            ", myfile='" + getMyfile() + "'" +
            "}";
    }
}
