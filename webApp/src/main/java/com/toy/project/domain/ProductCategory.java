package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductCategory.
 */
@Entity
@Table(name = "product_category")
public class ProductCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "main")
    private String main;

    @Column(name = "sub")
    private String sub;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productCategory")
    @JsonIgnoreProperties(value = { "product", "productCategory" }, allowSetters = true)
    private Set<ProductCategoryRel> productCategoryRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategory id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMain() {
        return this.main;
    }

    public ProductCategory main(String main) {
        this.main = main;
        return this;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSub() {
        return this.sub;
    }

    public ProductCategory sub(String sub) {
        this.sub = sub;
        return this;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public ProductCategory sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductCategory activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductCategory createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductCategory createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductCategory lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductCategory lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductCategoryRel> getProductCategoryRels() {
        return this.productCategoryRels;
    }

    public ProductCategory productCategoryRels(Set<ProductCategoryRel> productCategoryRels) {
        this.setProductCategoryRels(productCategoryRels);
        return this;
    }

    public void setProductCategoryRels(Set<ProductCategoryRel> productCategoryRels) {
        if (this.productCategoryRels != null) {
            this.productCategoryRels.forEach(i -> i.setProductCategory(null));
        }
        if (productCategoryRels != null) {
            productCategoryRels.forEach(i -> i.setProductCategory(this));
        }
        this.productCategoryRels = productCategoryRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }
        return id != null && id.equals(((ProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", main='" + getMain() + "'" +
            ", sub='" + getSub() + "'" +
            ", description='" + getDescription() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
