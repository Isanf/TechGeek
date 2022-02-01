package com.epita.techgeek.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Category category;

    @OneToMany(mappedBy = "module")
    private Set<Framework> frameworks = new HashSet<>();

    @OneToMany(mappedBy = "module")
    private Set<Language> languages = new HashSet<>();

    @OneToOne(mappedBy = "module")
    @JsonIgnore
    private Live live;

    @OneToOne(mappedBy = "module")
    @JsonIgnore
    private Video video;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Professor professor;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Volunteer volunteer;

    @ManyToOne
    @JsonIgnoreProperties("modules")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Module title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Module description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Module startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Module endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Category getCategory() {
        return category;
    }

    public Module category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Framework> getFrameworks() {
        return frameworks;
    }

    public Module frameworks(Set<Framework> frameworks) {
        this.frameworks = frameworks;
        return this;
    }

    public Module addFramework(Framework framework) {
        this.frameworks.add(framework);
        framework.setModule(this);
        return this;
    }

    public Module removeFramework(Framework framework) {
        this.frameworks.remove(framework);
        framework.setModule(null);
        return this;
    }

    public void setFrameworks(Set<Framework> frameworks) {
        this.frameworks = frameworks;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public Module languages(Set<Language> languages) {
        this.languages = languages;
        return this;
    }

    public Module addLanguage(Language language) {
        this.languages.add(language);
        language.setModule(this);
        return this;
    }

    public Module removeLanguage(Language language) {
        this.languages.remove(language);
        language.setModule(null);
        return this;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Live getLive() {
        return live;
    }

    public Module live(Live live) {
        this.live = live;
        return this;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public Video getVideo() {
        return video;
    }

    public Module video(Video video) {
        this.video = video;
        return this;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Course getCourse() {
        return course;
    }

    public Module course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Module professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public Module volunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
        return this;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Student getStudent() {
        return student;
    }

    public Module student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Module)) {
            return false;
        }
        return id != null && id.equals(((Module) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
