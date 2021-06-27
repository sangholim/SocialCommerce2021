package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductShipping.
 */
@Entity
@Table(name = "product_shipping")
public class ProductShipping extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_group")
    private Boolean isGroup;

    @Column(name = "default_shipping_price")
    private Integer defaultShippingPrice;

    @Column(name = "free_shipping_price")
    private Integer freeShippingPrice;

    @Column(name = "jeju_shipping_price")
    private Integer jejuShippingPrice;

    @Column(name = "difficult_shipping_price")
    private Integer difficultShippingPrice;

    @Column(name = "refund_shipping_price")
    private Integer refundShippingPrice;

    @Column(name = "exchange_shipping_price")
    private Integer exchangeShippingPrice;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productShipping")
    @JsonIgnoreProperties(value = { "product", "productShipping" }, allowSetters = true)
    private Set<ProductShippingRel> productShippingRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductShipping id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductShipping name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsGroup() {
        return this.isGroup;
    }

    public ProductShipping isGroup(Boolean isGroup) {
        this.isGroup = isGroup;
        return this;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Integer getDefaultShippingPrice() {
        return this.defaultShippingPrice;
    }

    public ProductShipping defaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
        return this;
    }

    public void setDefaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public Integer getFreeShippingPrice() {
        return this.freeShippingPrice;
    }

    public ProductShipping freeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
        return this;
    }

    public void setFreeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public Integer getJejuShippingPrice() {
        return this.jejuShippingPrice;
    }

    public ProductShipping jejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
        return this;
    }

    public void setJejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public Integer getDifficultShippingPrice() {
        return this.difficultShippingPrice;
    }

    public ProductShipping difficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
        return this;
    }

    public void setDifficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public Integer getRefundShippingPrice() {
        return this.refundShippingPrice;
    }

    public ProductShipping refundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
        return this;
    }

    public void setRefundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public Integer getExchangeShippingPrice() {
        return this.exchangeShippingPrice;
    }

    public ProductShipping exchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
        return this;
    }

    public void setExchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductShipping activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductShipping createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductShipping createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductShipping lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductShipping lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductShippingRel> getProductShippingRels() {
        return this.productShippingRels;
    }

    public ProductShipping productShippingRels(Set<ProductShippingRel> productShippingRels) {
        this.setProductShippingRels(productShippingRels);
        return this;
    }

    public void setProductShippingRels(Set<ProductShippingRel> productShippingRels) {
        if (this.productShippingRels != null) {
            this.productShippingRels.forEach(i -> i.setProductShipping(null));
        }
        if (productShippingRels != null) {
            productShippingRels.forEach(i -> i.setProductShipping(this));
        }
        this.productShippingRels = productShippingRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductShipping)) {
            return false;
        }
        return id != null && id.equals(((ProductShipping) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductShipping{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isGroup='" + getIsGroup() + "'" +
            ", defaultShippingPrice=" + getDefaultShippingPrice() +
            ", freeShippingPrice=" + getFreeShippingPrice() +
            ", jejuShippingPrice=" + getJejuShippingPrice() +
            ", difficultShippingPrice=" + getDifficultShippingPrice() +
            ", refundShippingPrice=" + getRefundShippingPrice() +
            ", exchangeShippingPrice=" + getExchangeShippingPrice() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
