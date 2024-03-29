package com.toy.project.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.toy.project.domain.ProductViewContent} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductViewContentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-view-contents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductViewContentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter isDetail;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productViewId;

    public ProductViewContentCriteria() {}

    public ProductViewContentCriteria(ProductViewContentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.isDetail = other.isDetail == null ? null : other.isDetail.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productViewId = other.productViewId == null ? null : other.productViewId.copy();
    }

    @Override
    public ProductViewContentCriteria copy() {
        return new ProductViewContentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getIsDetail() {
        return isDetail;
    }

    public BooleanFilter isDetail() {
        if (isDetail == null) {
            isDetail = new BooleanFilter();
        }
        return isDetail;
    }

    public void setIsDetail(BooleanFilter isDetail) {
        this.isDetail = isDetail;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public BooleanFilter activated() {
        if (activated == null) {
            activated = new BooleanFilter();
        }
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new InstantFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getProductViewId() {
        return productViewId;
    }

    public LongFilter productViewId() {
        if (productViewId == null) {
            productViewId = new LongFilter();
        }
        return productViewId;
    }

    public void setProductViewId(LongFilter productViewId) {
        this.productViewId = productViewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductViewContentCriteria that = (ProductViewContentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(isDetail, that.isDetail) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productViewId, that.productViewId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isDetail, activated, createdBy, createdDate, lastModifiedBy, lastModifiedDate, productViewId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductViewContentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (isDetail != null ? "isDetail=" + isDetail + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productViewId != null ? "productViewId=" + productViewId + ", " : "") +
            "}";
    }
}
