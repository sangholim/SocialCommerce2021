package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A OptionDesign.
 */
@Entity
@Table(name = "option_design")
public class OptionDesign extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "optionDesign")
    @JsonIgnoreProperties(value = { "productOption", "optionDesign" }, allowSetters = true)
    private Set<ProductOptionDesignRel> productOptionDesignRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OptionDesign id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public OptionDesign value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public OptionDesign activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public OptionDesign createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public OptionDesign createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public OptionDesign lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public OptionDesign lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductOptionDesignRel> getProductOptionDesignRels() {
        return this.productOptionDesignRels;
    }

    public OptionDesign productOptionDesignRels(Set<ProductOptionDesignRel> productOptionDesignRels) {
        this.setProductOptionDesignRels(productOptionDesignRels);
        return this;
    }

    public void setProductOptionDesignRels(Set<ProductOptionDesignRel> productOptionDesignRels) {
        if (this.productOptionDesignRels != null) {
            this.productOptionDesignRels.forEach(i -> i.setOptionDesign(null));
        }
        if (productOptionDesignRels != null) {
            productOptionDesignRels.forEach(i -> i.setOptionDesign(this));
        }
        this.productOptionDesignRels = productOptionDesignRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionDesign)) {
            return false;
        }
        return id != null && id.equals(((OptionDesign) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionDesign{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
