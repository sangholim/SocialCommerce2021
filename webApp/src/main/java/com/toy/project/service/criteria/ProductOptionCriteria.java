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
 * Criteria class for the {@link com.toy.project.domain.ProductOption} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductOptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-options?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductOptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter priceSign;

    private IntegerFilter price;

    private IntegerFilter stock;

    private StringFilter status;

    private StringFilter code;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productOptionRelId;

    private LongFilter productOptionPackageRelId;

    private LongFilter productOptionColorRelId;

    private LongFilter productOptionDesignRelId;

    public ProductOptionCriteria() {}

    public ProductOptionCriteria(ProductOptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.priceSign = other.priceSign == null ? null : other.priceSign.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productOptionRelId = other.productOptionRelId == null ? null : other.productOptionRelId.copy();
        this.productOptionPackageRelId = other.productOptionPackageRelId == null ? null : other.productOptionPackageRelId.copy();
        this.productOptionColorRelId = other.productOptionColorRelId == null ? null : other.productOptionColorRelId.copy();
        this.productOptionDesignRelId = other.productOptionDesignRelId == null ? null : other.productOptionDesignRelId.copy();
    }

    @Override
    public ProductOptionCriteria copy() {
        return new ProductOptionCriteria(this);
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

    public StringFilter getPriceSign() {
        return priceSign;
    }

    public StringFilter priceSign() {
        if (priceSign == null) {
            priceSign = new StringFilter();
        }
        return priceSign;
    }

    public void setPriceSign(StringFilter priceSign) {
        this.priceSign = priceSign;
    }

    public IntegerFilter getPrice() {
        return price;
    }

    public IntegerFilter price() {
        if (price == null) {
            price = new IntegerFilter();
        }
        return price;
    }

    public void setPrice(IntegerFilter price) {
        this.price = price;
    }

    public IntegerFilter getStock() {
        return stock;
    }

    public IntegerFilter stock() {
        if (stock == null) {
            stock = new IntegerFilter();
        }
        return stock;
    }

    public void setStock(IntegerFilter stock) {
        this.stock = stock;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public LongFilter getProductOptionRelId() {
        return productOptionRelId;
    }

    public LongFilter productOptionRelId() {
        if (productOptionRelId == null) {
            productOptionRelId = new LongFilter();
        }
        return productOptionRelId;
    }

    public void setProductOptionRelId(LongFilter productOptionRelId) {
        this.productOptionRelId = productOptionRelId;
    }

    public LongFilter getProductOptionPackageRelId() {
        return productOptionPackageRelId;
    }

    public LongFilter productOptionPackageRelId() {
        if (productOptionPackageRelId == null) {
            productOptionPackageRelId = new LongFilter();
        }
        return productOptionPackageRelId;
    }

    public void setProductOptionPackageRelId(LongFilter productOptionPackageRelId) {
        this.productOptionPackageRelId = productOptionPackageRelId;
    }

    public LongFilter getProductOptionColorRelId() {
        return productOptionColorRelId;
    }

    public LongFilter productOptionColorRelId() {
        if (productOptionColorRelId == null) {
            productOptionColorRelId = new LongFilter();
        }
        return productOptionColorRelId;
    }

    public void setProductOptionColorRelId(LongFilter productOptionColorRelId) {
        this.productOptionColorRelId = productOptionColorRelId;
    }

    public LongFilter getProductOptionDesignRelId() {
        return productOptionDesignRelId;
    }

    public LongFilter productOptionDesignRelId() {
        if (productOptionDesignRelId == null) {
            productOptionDesignRelId = new LongFilter();
        }
        return productOptionDesignRelId;
    }

    public void setProductOptionDesignRelId(LongFilter productOptionDesignRelId) {
        this.productOptionDesignRelId = productOptionDesignRelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductOptionCriteria that = (ProductOptionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(priceSign, that.priceSign) &&
            Objects.equals(price, that.price) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(status, that.status) &&
            Objects.equals(code, that.code) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productOptionRelId, that.productOptionRelId) &&
            Objects.equals(productOptionPackageRelId, that.productOptionPackageRelId) &&
            Objects.equals(productOptionColorRelId, that.productOptionColorRelId) &&
            Objects.equals(productOptionDesignRelId, that.productOptionDesignRelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            priceSign,
            price,
            stock,
            status,
            code,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productOptionRelId,
            productOptionPackageRelId,
            productOptionColorRelId,
            productOptionDesignRelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOptionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (priceSign != null ? "priceSign=" + priceSign + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (stock != null ? "stock=" + stock + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productOptionRelId != null ? "productOptionRelId=" + productOptionRelId + ", " : "") +
            (productOptionPackageRelId != null ? "productOptionPackageRelId=" + productOptionPackageRelId + ", " : "") +
            (productOptionColorRelId != null ? "productOptionColorRelId=" + productOptionColorRelId + ", " : "") +
            (productOptionDesignRelId != null ? "productOptionDesignRelId=" + productOptionDesignRelId + ", " : "") +
            "}";
    }
}
