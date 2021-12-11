package com.epita.techgeek.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epita.techgeek.domain.Framework} entity.
 */
public class FrameworkDTO implements Serializable {
    
    private Long id;

    private String title;

    private String description;


    private Long moduleId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FrameworkDTO frameworkDTO = (FrameworkDTO) o;
        if (frameworkDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), frameworkDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FrameworkDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", moduleId=" + getModuleId() +
            "}";
    }
}
