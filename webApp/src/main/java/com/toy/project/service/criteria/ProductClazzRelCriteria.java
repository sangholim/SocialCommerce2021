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
 * Criteria class for the {@link com.toy.project.domain.ProductClazzRel} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductClazzRelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-clazz-rels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductClazzRelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter useCalculation;

    private StringFilter calculation;

    private InstantFilter calculationDateFrom;

    private InstantFilter calculationDateTo;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productId;

    private LongFilter clazzId;

    public ProductClazzRelCriteria() {}

    public ProductClazzRelCriteria(ProductClazzRelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.useCalculation = other.useCalculation == null ? null : other.useCalculation.copy();
        this.calculation = other.calculation == null ? null : other.calculation.copy();
        this.calculationDateFrom = other.calculationDateFrom == null ? null : other.calculationDateFrom.copy();
        this.calculationDateTo = other.calculationDateTo == null ? null : other.calculationDateTo.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.clazzId = other.clazzId == null ? null : other.clazzId.copy();
    }

    @Override
    public ProductClazzRelCriteria copy() {
        return new ProductClazzRelCriteria(this);
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

    public BooleanFilter getUseCalculation() {
        return useCalculation;
    }

    public BooleanFilter useCalculation() {
        if (useCalculation == null) {
            useCalculation = new BooleanFilter();
        }
        return useCalculation;
    }

    public void setUseCalculation(BooleanFilter useCalculation) {
        this.useCalculation = useCalculation;
    }

    public StringFilter getCalculation() {
        return calculation;
    }

    public StringFilter calculation() {
        if (calculation == null) {
            calculation = new StringFilter();
        }
        return calculation;
    }

    public void setCalculation(StringFilter calculation) {
        this.calculation = calculation;
    }

    public InstantFilter getCalculationDateFrom() {
        return calculationDateFrom;
    }

    public InstantFilter calculationDateFrom() {
        if (calculationDateFrom == null) {
            calculationDateFrom = new InstantFilter();
        }
        return calculationDateFrom;
    }

    public void setCalculationDateFrom(InstantFilter calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public InstantFilter getCalculationDateTo() {
        return calculationDateTo;
    }

    public InstantFilter calculationDateTo() {
        if (calculationDateTo == null) {
            calculationDateTo = new InstantFilter();
        }
        return calculationDateTo;
    }

    public void setCalculationDateTo(InstantFilter calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
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

    public LongFilter getClazzId() {
        return clazzId;
    }

    public LongFilter clazzId() {
        if (clazzId == null) {
            clazzId = new LongFilter();
        }
        return clazzId;
    }

    public void setClazzId(LongFilter clazzId) {
        this.clazzId = clazzId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductClazzRelCriteria that = (ProductClazzRelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(useCalculation, that.useCalculation) &&
            Objects.equals(calculation, that.calculation) &&
            Objects.equals(calculationDateFrom, that.calculationDateFrom) &&
            Objects.equals(calculationDateTo, that.calculationDateTo) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(clazzId, that.clazzId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            useCalculation,
            calculation,
            calculationDateFrom,
            calculationDateTo,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productId,
            clazzId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductClazzRelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (useCalculation != null ? "useCalculation=" + useCalculation + ", " : "") +
            (calculation != null ? "calculation=" + calculation + ", " : "") +
            (calculationDateFrom != null ? "calculationDateFrom=" + calculationDateFrom + ", " : "") +
            (calculationDateTo != null ? "calculationDateTo=" + calculationDateTo + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (clazzId != null ? "clazzId=" + clazzId + ", " : "") +
            "}";
    }
}
