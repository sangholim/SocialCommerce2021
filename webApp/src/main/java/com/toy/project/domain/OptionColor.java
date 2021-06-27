package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A OptionColor.
 */
@Entity
@Table(name = "option_color")
public class OptionColor extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "optionColor")
    @JsonIgnoreProperties(value = { "productOption", "optionColor" }, allowSetters = true)
    private Set<ProductOptionColorRel> productOptionColorRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OptionColor id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public OptionColor code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public OptionColor value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public OptionColor activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public OptionColor createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public OptionColor createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public OptionColor lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public OptionColor lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductOptionColorRel> getProductOptionColorRels() {
        return this.productOptionColorRels;
    }

    public OptionColor productOptionColorRels(Set<ProductOptionColorRel> productOptionColorRels) {
        this.setProductOptionColorRels(productOptionColorRels);
        return this;
    }

    public void setProductOptionColorRels(Set<ProductOptionColorRel> productOptionColorRels) {
        if (this.productOptionColorRels != null) {
            this.productOptionColorRels.forEach(i -> i.setOptionColor(null));
        }
        if (productOptionColorRels != null) {
            productOptionColorRels.forEach(i -> i.setOptionColor(this));
        }
        this.productOptionColorRels = productOptionColorRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionColor)) {
            return false;
        }
        return id != null && id.equals(((OptionColor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionColor{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", value='" + getValue() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
