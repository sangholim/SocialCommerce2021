package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Clazz.
 */
@Entity
@Table(name = "clazz")
public class Clazz extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "level")
    private String level;

    @Column(name = "enable_lecture")
    private Boolean enableLecture;

    @Column(name = "lecturer")
    private String lecturer;

    @Column(name = "calculation")
    private Integer calculation;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "clazz")
    @JsonIgnoreProperties(value = { "product", "clazz" }, allowSetters = true)
    private Set<ProductClazzRel> productClazzRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clazz id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Clazz name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public Clazz type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return this.level;
    }

    public Clazz level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getEnableLecture() {
        return this.enableLecture;
    }

    public Clazz enableLecture(Boolean enableLecture) {
        this.enableLecture = enableLecture;
        return this;
    }

    public void setEnableLecture(Boolean enableLecture) {
        this.enableLecture = enableLecture;
    }

    public String getLecturer() {
        return this.lecturer;
    }

    public Clazz lecturer(String lecturer) {
        this.lecturer = lecturer;
        return this;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public Integer getCalculation() {
        return this.calculation;
    }

    public Clazz calculation(Integer calculation) {
        this.calculation = calculation;
        return this;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public Clazz activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Clazz createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public Clazz createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public Clazz lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public Clazz lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductClazzRel> getProductClazzRels() {
        return this.productClazzRels;
    }

    public Clazz productClazzRels(Set<ProductClazzRel> productClazzRels) {
        this.setProductClazzRels(productClazzRels);
        return this;
    }

    public void setProductClazzRels(Set<ProductClazzRel> productClazzRels) {
        if (this.productClazzRels != null) {
            this.productClazzRels.forEach(i -> i.setClazz(null));
        }
        if (productClazzRels != null) {
            productClazzRels.forEach(i -> i.setClazz(this));
        }
        this.productClazzRels = productClazzRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clazz)) {
            return false;
        }
        return id != null && id.equals(((Clazz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clazz{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", level='" + getLevel() + "'" +
            ", enableLecture='" + getEnableLecture() + "'" +
            ", lecturer='" + getLecturer() + "'" +
            ", calculation=" + getCalculation() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
