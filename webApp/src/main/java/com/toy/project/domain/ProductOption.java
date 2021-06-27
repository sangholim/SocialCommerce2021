package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductOption.
 */
@Entity
@Table(name = "product_option")
public class ProductOption extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price_sign")
    private String priceSign;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "status")
    private String status;

    @Column(name = "code")
    private String code;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productOption")
    @JsonIgnoreProperties(value = { "product", "productOption" }, allowSetters = true)
    private Set<ProductOptionRel> productOptionRels = new HashSet<>();

    @OneToMany(mappedBy = "productOption")
    @JsonIgnoreProperties(value = { "productOption", "optionPackage" }, allowSetters = true)
    private Set<ProductOptionPackageRel> productOptionPackageRels = new HashSet<>();

    @OneToMany(mappedBy = "productOption")
    @JsonIgnoreProperties(value = { "productOption", "optionColor" }, allowSetters = true)
    private Set<ProductOptionColorRel> productOptionColorRels = new HashSet<>();

    @OneToMany(mappedBy = "productOption")
    @JsonIgnoreProperties(value = { "productOption", "optionDesign" }, allowSetters = true)
    private Set<ProductOptionDesignRel> productOptionDesignRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductOption id(Long id) {
        this.id = id;
        return this;
    }

    public String getPriceSign() {
        return this.priceSign;
    }

    public ProductOption priceSign(String priceSign) {
        this.priceSign = priceSign;
        return this;
    }

    public void setPriceSign(String priceSign) {
        this.priceSign = priceSign;
    }

    public Integer getPrice() {
        return this.price;
    }

    public ProductOption price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return this.stock;
    }

    public ProductOption stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return this.status;
    }

    public ProductOption status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public ProductOption code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductOption activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductOption createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductOption createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductOption lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductOption lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductOptionRel> getProductOptionRels() {
        return this.productOptionRels;
    }

    public ProductOption productOptionRels(Set<ProductOptionRel> productOptionRels) {
        this.setProductOptionRels(productOptionRels);
        return this;
    }

    public void setProductOptionRels(Set<ProductOptionRel> productOptionRels) {
        if (this.productOptionRels != null) {
            this.productOptionRels.forEach(i -> i.setProductOption(null));
        }
        if (productOptionRels != null) {
            productOptionRels.forEach(i -> i.setProductOption(this));
        }
        this.productOptionRels = productOptionRels;
    }

    public Set<ProductOptionPackageRel> getProductOptionPackageRels() {
        return this.productOptionPackageRels;
    }

    public ProductOption productOptionPackageRels(Set<ProductOptionPackageRel> productOptionPackageRels) {
        this.setProductOptionPackageRels(productOptionPackageRels);
        return this;
    }

    public void setProductOptionPackageRels(Set<ProductOptionPackageRel> productOptionPackageRels) {
        if (this.productOptionPackageRels != null) {
            this.productOptionPackageRels.forEach(i -> i.setProductOption(null));
        }
        if (productOptionPackageRels != null) {
            productOptionPackageRels.forEach(i -> i.setProductOption(this));
        }
        this.productOptionPackageRels = productOptionPackageRels;
    }

    public Set<ProductOptionColorRel> getProductOptionColorRels() {
        return this.productOptionColorRels;
    }

    public ProductOption productOptionColorRels(Set<ProductOptionColorRel> productOptionColorRels) {
        this.setProductOptionColorRels(productOptionColorRels);
        return this;
    }

    public void setProductOptionColorRels(Set<ProductOptionColorRel> productOptionColorRels) {
        if (this.productOptionColorRels != null) {
            this.productOptionColorRels.forEach(i -> i.setProductOption(null));
        }
        if (productOptionColorRels != null) {
            productOptionColorRels.forEach(i -> i.setProductOption(this));
        }
        this.productOptionColorRels = productOptionColorRels;
    }

    public Set<ProductOptionDesignRel> getProductOptionDesignRels() {
        return this.productOptionDesignRels;
    }

    public ProductOption productOptionDesignRels(Set<ProductOptionDesignRel> productOptionDesignRels) {
        this.setProductOptionDesignRels(productOptionDesignRels);
        return this;
    }

    public void setProductOptionDesignRels(Set<ProductOptionDesignRel> productOptionDesignRels) {
        if (this.productOptionDesignRels != null) {
            this.productOptionDesignRels.forEach(i -> i.setProductOption(null));
        }
        if (productOptionDesignRels != null) {
            productOptionDesignRels.forEach(i -> i.setProductOption(this));
        }
        this.productOptionDesignRels = productOptionDesignRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOption)) {
            return false;
        }
        return id != null && id.equals(((ProductOption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOption{" +
            "id=" + getId() +
            ", priceSign='" + getPriceSign() + "'" +
            ", price=" + getPrice() +
            ", stock=" + getStock() +
            ", status='" + getStatus() + "'" +
            ", code='" + getCode() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
