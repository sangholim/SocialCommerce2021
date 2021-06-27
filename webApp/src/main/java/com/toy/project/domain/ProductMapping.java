package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductMapping.
 */
@Entity
@Table(name = "product_mapping")
public class ProductMapping extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productMapping")
    @JsonIgnoreProperties(value = { "product", "productMapping" }, allowSetters = true)
    private Set<ProductMappingRel> productMappingRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductMapping id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductMapping name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public ProductMapping type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public ProductMapping content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductMapping activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductMapping createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductMapping createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductMapping lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductMapping lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductMappingRel> getProductMappingRels() {
        return this.productMappingRels;
    }

    public ProductMapping productMappingRels(Set<ProductMappingRel> productMappingRels) {
        this.setProductMappingRels(productMappingRels);
        return this;
    }

    public void setProductMappingRels(Set<ProductMappingRel> productMappingRels) {
        if (this.productMappingRels != null) {
            this.productMappingRels.forEach(i -> i.setProductMapping(null));
        }
        if (productMappingRels != null) {
            productMappingRels.forEach(i -> i.setProductMapping(this));
        }
        this.productMappingRels = productMappingRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductMapping)) {
            return false;
        }
        return id != null && id.equals(((ProductMapping) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductMapping{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
