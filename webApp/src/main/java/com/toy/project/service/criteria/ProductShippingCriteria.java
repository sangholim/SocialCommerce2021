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
 * Criteria class for the {@link com.toy.project.domain.ProductShipping} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductShippingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-shippings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductShippingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter isGroup;

    private IntegerFilter defaultShippingPrice;

    private IntegerFilter freeShippingPrice;

    private IntegerFilter jejuShippingPrice;

    private IntegerFilter difficultShippingPrice;

    private IntegerFilter refundShippingPrice;

    private IntegerFilter exchangeShippingPrice;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productShippingRelId;

    public ProductShippingCriteria() {}

    public ProductShippingCriteria(ProductShippingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.isGroup = other.isGroup == null ? null : other.isGroup.copy();
        this.defaultShippingPrice = other.defaultShippingPrice == null ? null : other.defaultShippingPrice.copy();
        this.freeShippingPrice = other.freeShippingPrice == null ? null : other.freeShippingPrice.copy();
        this.jejuShippingPrice = other.jejuShippingPrice == null ? null : other.jejuShippingPrice.copy();
        this.difficultShippingPrice = other.difficultShippingPrice == null ? null : other.difficultShippingPrice.copy();
        this.refundShippingPrice = other.refundShippingPrice == null ? null : other.refundShippingPrice.copy();
        this.exchangeShippingPrice = other.exchangeShippingPrice == null ? null : other.exchangeShippingPrice.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productShippingRelId = other.productShippingRelId == null ? null : other.productShippingRelId.copy();
    }

    @Override
    public ProductShippingCriteria copy() {
        return new ProductShippingCriteria(this);
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

    public BooleanFilter getIsGroup() {
        return isGroup;
    }

    public BooleanFilter isGroup() {
        if (isGroup == null) {
            isGroup = new BooleanFilter();
        }
        return isGroup;
    }

    public void setIsGroup(BooleanFilter isGroup) {
        this.isGroup = isGroup;
    }

    public IntegerFilter getDefaultShippingPrice() {
        return defaultShippingPrice;
    }

    public IntegerFilter defaultShippingPrice() {
        if (defaultShippingPrice == null) {
            defaultShippingPrice = new IntegerFilter();
        }
        return defaultShippingPrice;
    }

    public void setDefaultShippingPrice(IntegerFilter defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public IntegerFilter getFreeShippingPrice() {
        return freeShippingPrice;
    }

    public IntegerFilter freeShippingPrice() {
        if (freeShippingPrice == null) {
            freeShippingPrice = new IntegerFilter();
        }
        return freeShippingPrice;
    }

    public void setFreeShippingPrice(IntegerFilter freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public IntegerFilter getJejuShippingPrice() {
        return jejuShippingPrice;
    }

    public IntegerFilter jejuShippingPrice() {
        if (jejuShippingPrice == null) {
            jejuShippingPrice = new IntegerFilter();
        }
        return jejuShippingPrice;
    }

    public void setJejuShippingPrice(IntegerFilter jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public IntegerFilter getDifficultShippingPrice() {
        return difficultShippingPrice;
    }

    public IntegerFilter difficultShippingPrice() {
        if (difficultShippingPrice == null) {
            difficultShippingPrice = new IntegerFilter();
        }
        return difficultShippingPrice;
    }

    public void setDifficultShippingPrice(IntegerFilter difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public IntegerFilter getRefundShippingPrice() {
        return refundShippingPrice;
    }

    public IntegerFilter refundShippingPrice() {
        if (refundShippingPrice == null) {
            refundShippingPrice = new IntegerFilter();
        }
        return refundShippingPrice;
    }

    public void setRefundShippingPrice(IntegerFilter refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public IntegerFilter getExchangeShippingPrice() {
        return exchangeShippingPrice;
    }

    public IntegerFilter exchangeShippingPrice() {
        if (exchangeShippingPrice == null) {
            exchangeShippingPrice = new IntegerFilter();
        }
        return exchangeShippingPrice;
    }

    public void setExchangeShippingPrice(IntegerFilter exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
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

    public LongFilter getProductShippingRelId() {
        return productShippingRelId;
    }

    public LongFilter productShippingRelId() {
        if (productShippingRelId == null) {
            productShippingRelId = new LongFilter();
        }
        return productShippingRelId;
    }

    public void setProductShippingRelId(LongFilter productShippingRelId) {
        this.productShippingRelId = productShippingRelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductShippingCriteria that = (ProductShippingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(isGroup, that.isGroup) &&
            Objects.equals(defaultShippingPrice, that.defaultShippingPrice) &&
            Objects.equals(freeShippingPrice, that.freeShippingPrice) &&
            Objects.equals(jejuShippingPrice, that.jejuShippingPrice) &&
            Objects.equals(difficultShippingPrice, that.difficultShippingPrice) &&
            Objects.equals(refundShippingPrice, that.refundShippingPrice) &&
            Objects.equals(exchangeShippingPrice, that.exchangeShippingPrice) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productShippingRelId, that.productShippingRelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            isGroup,
            defaultShippingPrice,
            freeShippingPrice,
            jejuShippingPrice,
            difficultShippingPrice,
            refundShippingPrice,
            exchangeShippingPrice,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productShippingRelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductShippingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (isGroup != null ? "isGroup=" + isGroup + ", " : "") +
            (defaultShippingPrice != null ? "defaultShippingPrice=" + defaultShippingPrice + ", " : "") +
            (freeShippingPrice != null ? "freeShippingPrice=" + freeShippingPrice + ", " : "") +
            (jejuShippingPrice != null ? "jejuShippingPrice=" + jejuShippingPrice + ", " : "") +
            (difficultShippingPrice != null ? "difficultShippingPrice=" + difficultShippingPrice + ", " : "") +
            (refundShippingPrice != null ? "refundShippingPrice=" + refundShippingPrice + ", " : "") +
            (exchangeShippingPrice != null ? "exchangeShippingPrice=" + exchangeShippingPrice + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productShippingRelId != null ? "productShippingRelId=" + productShippingRelId + ", " : "") +
            "}";
    }
}
