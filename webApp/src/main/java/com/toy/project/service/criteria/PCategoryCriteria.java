package com.toy.project.service.criteria;

import com.toy.project.domain.enumeration.ProductCategoryType;
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
 * Criteria class for the {@link com.toy.project.domain.PCategory} entity. This class is used
 * in {@link com.toy.project.web.rest.PCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /p-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PCategoryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ProductCategoryType
     */
    public static class ProductCategoryTypeFilter extends Filter<ProductCategoryType> {

        public ProductCategoryTypeFilter() {}

        public ProductCategoryTypeFilter(ProductCategoryTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProductCategoryTypeFilter copy() {
            return new ProductCategoryTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ProductCategoryTypeFilter type;

    private StringFilter categoryMain;

    private StringFilter categorySub;

    private StringFilter description;

    private IntegerFilter isUse;

    private IntegerFilter sortOrder;

    private InstantFilter regdate;

    private IntegerFilter isUseDiscount;

    public PCategoryCriteria() {}

    public PCategoryCriteria(PCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.categoryMain = other.categoryMain == null ? null : other.categoryMain.copy();
        this.categorySub = other.categorySub == null ? null : other.categorySub.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.isUse = other.isUse == null ? null : other.isUse.copy();
        this.sortOrder = other.sortOrder == null ? null : other.sortOrder.copy();
        this.regdate = other.regdate == null ? null : other.regdate.copy();
        this.isUseDiscount = other.isUseDiscount == null ? null : other.isUseDiscount.copy();
    }

    @Override
    public PCategoryCriteria copy() {
        return new PCategoryCriteria(this);
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

    public ProductCategoryTypeFilter getType() {
        return type;
    }

    public ProductCategoryTypeFilter type() {
        if (type == null) {
            type = new ProductCategoryTypeFilter();
        }
        return type;
    }

    public void setType(ProductCategoryTypeFilter type) {
        this.type = type;
    }

    public StringFilter getCategoryMain() {
        return categoryMain;
    }

    public StringFilter categoryMain() {
        if (categoryMain == null) {
            categoryMain = new StringFilter();
        }
        return categoryMain;
    }

    public void setCategoryMain(StringFilter categoryMain) {
        this.categoryMain = categoryMain;
    }

    public StringFilter getCategorySub() {
        return categorySub;
    }

    public StringFilter categorySub() {
        if (categorySub == null) {
            categorySub = new StringFilter();
        }
        return categorySub;
    }

    public void setCategorySub(StringFilter categorySub) {
        this.categorySub = categorySub;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getIsUse() {
        return isUse;
    }

    public IntegerFilter isUse() {
        if (isUse == null) {
            isUse = new IntegerFilter();
        }
        return isUse;
    }

    public void setIsUse(IntegerFilter isUse) {
        this.isUse = isUse;
    }

    public IntegerFilter getSortOrder() {
        return sortOrder;
    }

    public IntegerFilter sortOrder() {
        if (sortOrder == null) {
            sortOrder = new IntegerFilter();
        }
        return sortOrder;
    }

    public void setSortOrder(IntegerFilter sortOrder) {
        this.sortOrder = sortOrder;
    }

    public InstantFilter getRegdate() {
        return regdate;
    }

    public InstantFilter regdate() {
        if (regdate == null) {
            regdate = new InstantFilter();
        }
        return regdate;
    }

    public void setRegdate(InstantFilter regdate) {
        this.regdate = regdate;
    }

    public IntegerFilter getIsUseDiscount() {
        return isUseDiscount;
    }

    public IntegerFilter isUseDiscount() {
        if (isUseDiscount == null) {
            isUseDiscount = new IntegerFilter();
        }
        return isUseDiscount;
    }

    public void setIsUseDiscount(IntegerFilter isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PCategoryCriteria that = (PCategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(categoryMain, that.categoryMain) &&
            Objects.equals(categorySub, that.categorySub) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isUse, that.isUse) &&
            Objects.equals(sortOrder, that.sortOrder) &&
            Objects.equals(regdate, that.regdate) &&
            Objects.equals(isUseDiscount, that.isUseDiscount)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, categoryMain, categorySub, description, isUse, sortOrder, regdate, isUseDiscount);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PCategoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (categoryMain != null ? "categoryMain=" + categoryMain + ", " : "") +
            (categorySub != null ? "categorySub=" + categorySub + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (isUse != null ? "isUse=" + isUse + ", " : "") +
            (sortOrder != null ? "sortOrder=" + sortOrder + ", " : "") +
            (regdate != null ? "regdate=" + regdate + ", " : "") +
            (isUseDiscount != null ? "isUseDiscount=" + isUseDiscount + ", " : "") +
            "}";
    }
}
