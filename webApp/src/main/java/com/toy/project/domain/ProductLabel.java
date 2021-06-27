package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductLabel.
 */
@Entity
@Table(name = "product_label")
public class ProductLabel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productLabel")
    @JsonIgnoreProperties(value = { "product", "productLabel" }, allowSetters = true)
    private Set<ProductLabelRel> productLabelRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductLabel id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductLabel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return this.color;
    }

    public ProductLabel color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContent() {
        return this.content;
    }

    public ProductLabel content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return this.type;
    }

    public ProductLabel type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductLabel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductLabel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductLabel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductLabel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductLabel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductLabelRel> getProductLabelRels() {
        return this.productLabelRels;
    }

    public ProductLabel productLabelRels(Set<ProductLabelRel> productLabelRels) {
        this.setProductLabelRels(productLabelRels);
        return this;
    }

    public void setProductLabelRels(Set<ProductLabelRel> productLabelRels) {
        if (this.productLabelRels != null) {
            this.productLabelRels.forEach(i -> i.setProductLabel(null));
        }
        if (productLabelRels != null) {
            productLabelRels.forEach(i -> i.setProductLabel(this));
        }
        this.productLabelRels = productLabelRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductLabel)) {
            return false;
        }
        return id != null && id.equals(((ProductLabel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductLabel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", content='" + getContent() + "'" +
            ", type='" + getType() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
