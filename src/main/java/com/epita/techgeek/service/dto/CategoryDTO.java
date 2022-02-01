package com.epita.techgeek.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epita.techgeek.domain.Category} entity.
 */
public class CategoryDTO implements Serializable {
    
    private Long id;

    private String title;

    private String description;


    private Long supporterId;

    private Long influencerId;
    
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

    public Long getSupporterId() {
        return supporterId;
    }

    public void setSupporterId(Long supporterId) {
        this.supporterId = supporterId;
    }

    public Long getInfluencerId() {
        return influencerId;
    }

    public void setInfluencerId(Long influencerId) {
        this.influencerId = influencerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (categoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", supporterId=" + getSupporterId() +
            ", influencerId=" + getInfluencerId() +
            "}";
    }
}
