package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductNotice.
 */
@Entity
@Table(name = "product_notice")
public class ProductNotice extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "priority_display")
    private Boolean priorityDisplay;

    @Column(name = "all_product_display")
    private Boolean allProductDisplay;

    @Column(name = "target")
    private String target;

    @Column(name = "enable_display_date")
    private Boolean enableDisplayDate;

    @Column(name = "display_date_from")
    private Instant displayDateFrom;

    @Column(name = "display_date_to")
    private Instant displayDateTo;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productNotice")
    @JsonIgnoreProperties(value = { "product", "productNotice" }, allowSetters = true)
    private Set<ProductNoticeRel> productNoticeRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductNotice id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductNotice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public ProductNotice type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentFileUrl() {
        return this.contentFileUrl;
    }

    public ProductNotice contentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
        return this;
    }

    public void setContentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
    }

    public Boolean getPriorityDisplay() {
        return this.priorityDisplay;
    }

    public ProductNotice priorityDisplay(Boolean priorityDisplay) {
        this.priorityDisplay = priorityDisplay;
        return this;
    }

    public void setPriorityDisplay(Boolean priorityDisplay) {
        this.priorityDisplay = priorityDisplay;
    }

    public Boolean getAllProductDisplay() {
        return this.allProductDisplay;
    }

    public ProductNotice allProductDisplay(Boolean allProductDisplay) {
        this.allProductDisplay = allProductDisplay;
        return this;
    }

    public void setAllProductDisplay(Boolean allProductDisplay) {
        this.allProductDisplay = allProductDisplay;
    }

    public String getTarget() {
        return this.target;
    }

    public ProductNotice target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getEnableDisplayDate() {
        return this.enableDisplayDate;
    }

    public ProductNotice enableDisplayDate(Boolean enableDisplayDate) {
        this.enableDisplayDate = enableDisplayDate;
        return this;
    }

    public void setEnableDisplayDate(Boolean enableDisplayDate) {
        this.enableDisplayDate = enableDisplayDate;
    }

    public Instant getDisplayDateFrom() {
        return this.displayDateFrom;
    }

    public ProductNotice displayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
        return this;
    }

    public void setDisplayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public Instant getDisplayDateTo() {
        return this.displayDateTo;
    }

    public ProductNotice displayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
        return this;
    }

    public void setDisplayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductNotice activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductNotice createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductNotice createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductNotice lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductNotice lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductNoticeRel> getProductNoticeRels() {
        return this.productNoticeRels;
    }

    public ProductNotice productNoticeRels(Set<ProductNoticeRel> productNoticeRels) {
        this.setProductNoticeRels(productNoticeRels);
        return this;
    }

    public ProductNotice addProductNoticeRel(ProductNoticeRel productNoticeRel) {
        this.productNoticeRels.add(productNoticeRel);
        productNoticeRel.setProductNotice(this);
        return this;
    }

    public ProductNotice removeProductNoticeRel(ProductNoticeRel productNoticeRel) {
        this.productNoticeRels.remove(productNoticeRel);
        productNoticeRel.setProductNotice(null);
        return this;
    }

    public void setProductNoticeRels(Set<ProductNoticeRel> productNoticeRels) {
        if (this.productNoticeRels != null) {
            this.productNoticeRels.forEach(i -> i.setProductNotice(null));
        }
        if (productNoticeRels != null) {
            productNoticeRels.forEach(i -> i.setProductNotice(this));
        }
        this.productNoticeRels = productNoticeRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductNotice)) {
            return false;
        }
        return id != null && id.equals(((ProductNotice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductNotice{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", contentFileUrl='" + getContentFileUrl() + "'" +
            ", priorityDisplay='" + getPriorityDisplay() + "'" +
            ", allProductDisplay='" + getAllProductDisplay() + "'" +
            ", target='" + getTarget() + "'" +
            ", enableDisplayDate='" + getEnableDisplayDate() + "'" +
            ", displayDateFrom='" + getDisplayDateFrom() + "'" +
            ", displayDateTo='" + getDisplayDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
