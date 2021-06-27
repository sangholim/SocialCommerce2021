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
 * Criteria class for the {@link com.toy.project.domain.ProductLabelRel} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductLabelRelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-label-rels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductLabelRelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isDisplayDate;

    private InstantFilter displayDateFrom;

    private InstantFilter displayDateTo;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productId;

    private LongFilter productLabelId;

    public ProductLabelRelCriteria() {}

    public ProductLabelRelCriteria(ProductLabelRelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isDisplayDate = other.isDisplayDate == null ? null : other.isDisplayDate.copy();
        this.displayDateFrom = other.displayDateFrom == null ? null : other.displayDateFrom.copy();
        this.displayDateTo = other.displayDateTo == null ? null : other.displayDateTo.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productLabelId = other.productLabelId == null ? null : other.productLabelId.copy();
    }

    @Override
    public ProductLabelRelCriteria copy() {
        return new ProductLabelRelCriteria(this);
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

    public BooleanFilter getIsDisplayDate() {
        return isDisplayDate;
    }

    public BooleanFilter isDisplayDate() {
        if (isDisplayDate == null) {
            isDisplayDate = new BooleanFilter();
        }
        return isDisplayDate;
    }

    public void setIsDisplayDate(BooleanFilter isDisplayDate) {
        this.isDisplayDate = isDisplayDate;
    }

    public InstantFilter getDisplayDateFrom() {
        return displayDateFrom;
    }

    public InstantFilter displayDateFrom() {
        if (displayDateFrom == null) {
            displayDateFrom = new InstantFilter();
        }
        return displayDateFrom;
    }

    public void setDisplayDateFrom(InstantFilter displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public InstantFilter getDisplayDateTo() {
        return displayDateTo;
    }

    public InstantFilter displayDateTo() {
        if (displayDateTo == null) {
            displayDateTo = new InstantFilter();
        }
        return displayDateTo;
    }

    public void setDisplayDateTo(InstantFilter displayDateTo) {
        this.displayDateTo = displayDateTo;
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

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getProductLabelId() {
        return productLabelId;
    }

    public LongFilter productLabelId() {
        if (productLabelId == null) {
            productLabelId = new LongFilter();
        }
        return productLabelId;
    }

    public void setProductLabelId(LongFilter productLabelId) {
        this.productLabelId = productLabelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductLabelRelCriteria that = (ProductLabelRelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isDisplayDate, that.isDisplayDate) &&
            Objects.equals(displayDateFrom, that.displayDateFrom) &&
            Objects.equals(displayDateTo, that.displayDateTo) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productLabelId, that.productLabelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            isDisplayDate,
            displayDateFrom,
            displayDateTo,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productId,
            productLabelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductLabelRelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isDisplayDate != null ? "isDisplayDate=" + isDisplayDate + ", " : "") +
            (displayDateFrom != null ? "displayDateFrom=" + displayDateFrom + ", " : "") +
            (displayDateTo != null ? "displayDateTo=" + displayDateTo + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (productLabelId != null ? "productLabelId=" + productLabelId + ", " : "") +
            "}";
    }
}
