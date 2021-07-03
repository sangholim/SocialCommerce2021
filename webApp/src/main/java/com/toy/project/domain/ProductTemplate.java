package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductTemplate.
 */
@Entity
@Table(name = "product_template")
public class ProductTemplate extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "content_file_url")
    private String contentFileUrl;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productTemplate")
    @JsonIgnoreProperties(value = { "product", "productTemplate" }, allowSetters = true)
    private Set<ProductTemplateRel> productTemplateRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductTemplate id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public ProductTemplate type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentFileUrl() {
        return this.contentFileUrl;
    }

    public ProductTemplate contentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
        return this;
    }

    public void setContentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductTemplate activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductTemplate createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductTemplate createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductTemplate lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductTemplate lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductTemplateRel> getProductTemplateRels() {
        return this.productTemplateRels;
    }

    public ProductTemplate productTemplateRels(Set<ProductTemplateRel> productTemplateRels) {
        this.setProductTemplateRels(productTemplateRels);
        return this;
    }

    public ProductTemplate addProductTemplateRel(ProductTemplateRel productTemplateRel) {
        this.productTemplateRels.add(productTemplateRel);
        productTemplateRel.setProductTemplate(this);
        return this;
    }

    public ProductTemplate removeProductTemplateRel(ProductTemplateRel productTemplateRel) {
        this.productTemplateRels.remove(productTemplateRel);
        productTemplateRel.setProductTemplate(null);
        return this;
    }

    public void setProductTemplateRels(Set<ProductTemplateRel> productTemplateRels) {
        if (this.productTemplateRels != null) {
            this.productTemplateRels.forEach(i -> i.setProductTemplate(null));
        }
        if (productTemplateRels != null) {
            productTemplateRels.forEach(i -> i.setProductTemplate(this));
        }
        this.productTemplateRels = productTemplateRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTemplate)) {
            return false;
        }
        return id != null && id.equals(((ProductTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", contentFileUrl='" + getContentFileUrl() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
