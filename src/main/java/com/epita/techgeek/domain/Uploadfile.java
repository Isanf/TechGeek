package com.epita.techgeek.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Uploadfile.
 */
@Entity
@Table(name = "uploadfile")
public class Uploadfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "myfile")
    private byte[] myfile;

    @Column(name = "myfile_content_type")
    private String myfileContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getMyfile() {
        return myfile;
    }

    public Uploadfile myfile(byte[] myfile) {
        this.myfile = myfile;
        return this;
    }

    public void setMyfile(byte[] myfile) {
        this.myfile = myfile;
    }

    public String getMyfileContentType() {
        return myfileContentType;
    }

    public Uploadfile myfileContentType(String myfileContentType) {
        this.myfileContentType = myfileContentType;
        return this;
    }

    public void setMyfileContentType(String myfileContentType) {
        this.myfileContentType = myfileContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uploadfile)) {
            return false;
        }
        return id != null && id.equals(((Uploadfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Uploadfile{" +
            "id=" + getId() +
            ", myfile='" + getMyfile() + "'" +
            ", myfileContentType='" + getMyfileContentType() + "'" +
            "}";
    }
}
