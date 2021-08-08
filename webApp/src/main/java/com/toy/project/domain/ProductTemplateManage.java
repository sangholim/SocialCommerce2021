package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductTemplateManage.
 */
@Entity
@Table(name = "product_template_manage")
public class ProductTemplateManage extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Size(max = 50)
    @Column(name = "illegal_usage_type", length = 50)
    private String illegalUsageType;

    @Column(name = "illegal_usage_url")
    private String illegalUsageUrl;

    @Size(max = 50)
    @Column(name = "exchange_type", length = 50)
    private String exchangeType;

    @Column(name = "exchange_url")
    private String exchangeUrl;

    @Column(name = "shipping_release_type")
    private String shippingReleaseType;

    @Size(max = 50)
    @Column(name = "separate_shipping_price_type", length = 50)
    private String separateShippingPriceType;

    @Size(max = 50)
    @Column(name = "bundle_shipping_type", length = 50)
    private String bundleShippingType;

    @Column(name = "default_shipping_price")
    private Long defaultShippingPrice;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productTemplateManage")
    @JsonIgnoreProperties(value = { "productTemplateManage" }, allowSetters = true)
    private Set<ProductAnnounceTemplate> productAnnounceTemplates = new HashSet<>();

    @OneToMany(mappedBy = "productTemplateManage")
    @JsonIgnoreProperties(value = { "product", "productTemplateManage" }, allowSetters = true)
    private Set<ProductTemplate> productCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIllegalUsageType() {
        return this.illegalUsageType;
    }

    public void setIllegalUsageType(String illegalUsageType) {
        this.illegalUsageType = illegalUsageType;
    }

    public String getIllegalUsageUrl() {
        return this.illegalUsageUrl;
    }

    public void setIllegalUsageUrl(String illegalUsageUrl) {
        this.illegalUsageUrl = illegalUsageUrl;
    }

    public String getExchangeType() {
        return this.exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getExchangeUrl() {
        return this.exchangeUrl;
    }

    public void setExchangeUrl(String exchangeUrl) {
        this.exchangeUrl = exchangeUrl;
    }

    public String getShippingReleaseType() {
        return this.shippingReleaseType;
    }

    public void setShippingReleaseType(String shippingReleaseType) {
        this.shippingReleaseType = shippingReleaseType;
    }

    public String getSeparateShippingPriceType() {
        return this.separateShippingPriceType;
    }

    public void setSeparateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
    }

    public String getBundleShippingType() {
        return this.bundleShippingType;
    }

    public void setBundleShippingType(String bundleShippingType) {
        this.bundleShippingType = bundleShippingType;
    }

    public Long getDefaultShippingPrice() {
        return this.defaultShippingPrice;
    }

    public void setDefaultShippingPrice(Long defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<ProductAnnounceTemplate> getProductAnnounceTemplates() {
        return this.productAnnounceTemplates;
    }

    public void setProductAnnounceTemplates(Set<ProductAnnounceTemplate> productAnnounceTemplates) {
        if (this.productAnnounceTemplates != null) {
            this.productAnnounceTemplates.forEach(i -> i.setProductTemplateManage(null));
        }
        if (productAnnounceTemplates != null) {
            productAnnounceTemplates.forEach(i -> i.setProductTemplateManage(this));
        }
        this.productAnnounceTemplates = productAnnounceTemplates;
    }

    public Set<ProductTemplate> getProductCategories() {
        return this.productCategories;
    }

    public void setProductCategories(Set<ProductTemplate> productTemplates) {
        if (this.productCategories != null) {
            this.productCategories.forEach(i -> i.setProductTemplateManage(null));
        }
        if (productTemplates != null) {
            productTemplates.forEach(i -> i.setProductTemplateManage(this));
        }
        this.productCategories = productTemplates;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTemplateManage)) {
            return false;
        }
        return id != null && id.equals(((ProductTemplateManage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTemplateManage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", illegalUsageType='" + getIllegalUsageType() + "'" +
            ", illegalUsageUrl='" + getIllegalUsageUrl() + "'" +
            ", exchangeType='" + getExchangeType() + "'" +
            ", exchangeUrl='" + getExchangeUrl() + "'" +
            ", shippingReleaseType='" + getShippingReleaseType() + "'" +
            ", separateShippingPriceType='" + getSeparateShippingPriceType() + "'" +
            ", bundleShippingType='" + getBundleShippingType() + "'" +
            ", defaultShippingPrice=" + getDefaultShippingPrice() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
