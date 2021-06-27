package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductView.
 */
@Entity
@Table(name = "product_view")
public class ProductView extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "is_detail")
    private Boolean isDetail;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productView")
    @JsonIgnoreProperties(value = { "product", "productView" }, allowSetters = true)
    private Set<ProductViewRel> productViewRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductView id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductView name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public ProductView type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public ProductView content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsDetail() {
        return this.isDetail;
    }

    public ProductView isDetail(Boolean isDetail) {
        this.isDetail = isDetail;
        return this;
    }

    public void setIsDetail(Boolean isDetail) {
        this.isDetail = isDetail;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductView activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductView createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductView createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductView lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductView lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductViewRel> getProductViewRels() {
        return this.productViewRels;
    }

    public ProductView productViewRels(Set<ProductViewRel> productViewRels) {
        this.setProductViewRels(productViewRels);
        return this;
    }

    public void setProductViewRels(Set<ProductViewRel> productViewRels) {
        if (this.productViewRels != null) {
            this.productViewRels.forEach(i -> i.setProductView(null));
        }
        if (productViewRels != null) {
            productViewRels.forEach(i -> i.setProductView(this));
        }
        this.productViewRels = productViewRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductView)) {
            return false;
        }
        return id != null && id.equals(((ProductView) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductView{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", isDetail='" + getIsDetail() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
